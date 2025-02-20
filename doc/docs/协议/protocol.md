# JMPP协议
## 介绍
JMPP（JSON Messaging and Presence Protocol）是基于JSON的文本协议，参考XMPP，但是基于XMPP进行了简化减少消息体大小，提供扩展性和吞吐量
## 详细
### 系统架构
- **实体（Entities）：**  JMPP 系统中的基本元素，包括用户、服务器和各种应用程序等。每个实体都有一个唯一的标识符，称为 JID（JMPP ID），格式为 user@domain/resource，如gaolu@phantom_im.com/desktop。
- **客户端 - 服务器架构：** 客户端通过网络与服务器建立连接，向服务器发送请求并接收服务器推送的信息。服务器负责管理用户账户、存储数据、路由消息等，还能与其他服务器进行通信以实现跨域交互。
- **服务器集群：** 在大规模应用中，常采用服务器集群来提高系统的性能、可靠性和可扩展性。服务器之间通过内部协议进行数据同步和消息路由
### 消息类别
- **stream:** 流，表示一个会话的结束或者开始，type包括：start,end
- **message:** 普通消息
- **presence:** 实体在线状态，例如上线，下线，隐身等用户流程状态信息
- **iq:** 用于请求和响应信息，如查询用户信息、获取服务器配置等
### 协议结构
#### 说明： 
- v 版本: 协议版本，暂时1.0
- c 类别:  目前有：message，presence，iq三大类别
- to 属性：指定消息的目标 JID。在客户端到服务器的流中，若消息有特定收件人需包含该属性；服务器向客户端发送消息时，若从其他实体接收则不修改该属性，若自行生成则可包含客户端的完整 JID 。在服务器到服务器的流中，消息必须包含 to 属性，且其值的域名部分需与接收服务器的 FQDN 匹配。
- from 属性：表示消息的发送者 JID。在客户端到服务器的流中，服务器接收客户端消息时会添加或覆盖该属性；服务器生成消息时，根据不同情况设置该属性。在服务器到服务器的流中，消息必须包含 from 属性，且其值的域名部分需与发送服务器的 FQDN 匹配。
- id 属性：用于跟踪响应或错误消息，对于 message 和 presence 类型的 stanza，建议包含该属性；若生成的 stanza 包含 id 属性，响应或错误 stanza 也必须包含且值要匹配。
- type 属性：指定消息的目的或上下文，具体取值在 [type类别](sub_type.md) 中定义，常见值如 "chat" 用于聊天消息。
#### 案例：
- message
```JSON
{
    "v":"1.0",
    "c":"message",
    "id":"1",
    "from":"phantom1@phantom_im.com/desktop",
    "to":"phantom2@phantom_im.com/phone",
    "type":"chat"
}
```
- iq
```JSON
{
    "v":"1.0",
    "c":"iq",
    "id":"1",
    "from":"phantom1@phantom_im.com/desktop",
    "to":"phantom_im.com",
    "type":"get"
}
```