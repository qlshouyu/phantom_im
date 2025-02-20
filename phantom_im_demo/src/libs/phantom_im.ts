import { v4 as uuidv4 } from "uuid";

export default class PhantomIM {
    static _baseUrl?: string;
    static _app?: string;
    static C_PRESENCE:string="presence"
    static C_IQ:string="iq"
    static T_SUB_SET="set"

    static init(baseUrl: string, app: string) {
      PhantomIM._baseUrl = baseUrl;
      PhantomIM._app = app;
    }
}

export class Client {
    _jid: string;
    _socket?: WebSocket;
    _room?: string;
    _connected?: boolean;
    _pubConfig?: any;
    _listener: PhantomIMListener;

    // 添加一个指令队列，按先进先出的顺序把指令通过websocket发送出去
    _commandQueue: Array<any> = [];
    _sendingCommand?: any; // 当前正在发送的指令

    constructor(jid: string, listener: PhantomIMListener) {
        this._jid = jid;
        this._listener = listener;
    }


    // 连接Websocket
    connect(): Promise<any> {
        const url = `${PhantomIM._baseUrl}/phantom_im/api/v1/ws?jid=${this._jid}`;
        console.info(`ws:${url}`);
        const that = this;
        return new Promise((resolve, reject) => {
            that._socket = new WebSocket(url);
            that._socket.onopen = (event) => {
                that._connected = true;
                console.info('onopen:', event);
                this._listener.onConnected?.(event);
                that.processCommandQueue(); // 开始处理指令队列
            };
            that._socket.onerror = (error) => {
                console.info('onerror:', error);
                reject({ error: 1001, msg: error });
            };
            that._socket.onmessage = (msg: any) => {
                const msgObj: JMPMessage = JSON.parse(msg.data) as JMPMessage;
                console.info('onmessage:', msgObj);
                switch (msgObj.type) {
                    case "ack": { 
                        if (msgObj.id === that._sendingCommand?.id) {
                            console.info('Command sent successfully:', msgObj.id);
                            that._sendingCommand = undefined;
                        } 
                        that.processCommandQueue(); // 继续处理下一个指令
                        break;
                    } 
                    default: {
                        that._listener.onMessage?.(msgObj)
                    }
                }
            };
        });
    }

    // 设置Tag列表，把指令塞到队列中
    setTags(tags: Array<any>): void {
        const that=this;
        // 获取当前浏览器的服务器ip或域名
        console.info(window.location.hostname);
        let server=window.location.hostname;
        if(PhantomIM._baseUrl&&PhantomIM._baseUrl.length>0) {
            server = PhantomIM._baseUrl?.replace(/^(https?|wss?):\/\//, ''); 
        }
      
      const command = {
        id: uuidv4(),
        c: PhantomIM.C_IQ,
        type: PhantomIM.T_SUB_SET,
        from: this._jid,
        to:[server+"@server"],
        body: {
            name: "tag",
            body: tags
        }
      };
      this._commandQueue.push(command);
      // 如果没有正在发送的指令，开始处理队列
      if (!this._sendingCommand) {
          this.processCommandQueue(); 
      }
    }

    // 处理指令队列
    private processCommandQueue(): void {
      if (this._sendingCommand || this._commandQueue.length === 0) {
          return;
      }
      this._sendingCommand = this._commandQueue.shift();
      if (this._socket && this._socket.readyState === WebSocket.OPEN) {
          this._socket.send(JSON.stringify(this._sendingCommand));
      } else {
          console.warn('WebSocket is not open, retrying...');
          setTimeout(() => this.processCommandQueue(), 1000); // 1秒后重试
      }
    }
}

export class PhantomIMListener {
    onConnected?: (msg: any) => void;
    onMessage?: (msg: JMPMessage) => void;
    onError?: Function;
}

export class JMPMessage {
    id?: string;
    c?:string
    type?: string;
    b?: any;
    from?:string;
    to?:Array<string>;
    body?:any;
    t?:string
}

export class IQMessage {
    name?: string;
    body?:any
}

export const getMilliseconds = () => {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    const milliseconds = String(now.getMilliseconds()).padStart(3, '0');
  
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}.${milliseconds}`;
  };