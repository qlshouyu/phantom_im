# Type类型详细定义
## message
### chat
通常用于表示聊天消息，是在即时通讯场景下，实体之间进行一对一聊天时使用的类型。比如用户之间的实时对话消息，就可以将type设为chat,例如： 
```JSON
{
    "v":"1.0",
    "c":"message",
    "id":"1",
    "from":"phantom1@phantom_im.com/desktop",
    "to":"phantom2@phantom_im.com/phone",
    "type":"chat",
    "body":"Hello Phantom IM"
}
```
### ack
服务端给客户端发送的消息进行回应使用ack。例如：
```JSON
{
    "v":"1.0",
    "c":"message",
    "id":"1",
    "from":"phantom1@phantom_im.com/desktop",
    "to":"phantom2@phantom_im.com/phone",
    "type":"ack",
    "body":{"code":200,"tip":"成功"}
}
{
    "v":"1.0",
    "c":"message",
    "id":"1",
    "from":"phantom1@phantom_im.com/desktop",
    "to":"phantom2@phantom_im.com/phone",
    "type":"ack",
    "body":{"code":401,"tip":"Not found user"}
}
```
  
### groupchat
用于群聊场景，表明该消息是在群组聊天环境中发送的。在多用户聊天房间里的消息，其type可能就是groupchat。
### headline
一般用于表示新闻、通知等类似头条消息的类型，这类消息通常具有广播性质，向多个用户发送重要信息。
### normal
是默认的消息类型，如果没有特殊的场景需求，普通的消息可以使用这个类型。
presence 类型的 type 取值 error 与 message 中的error类似，当处理 presence stanza 出现错误时使用。例如在订阅或发布存在信息过程中发生错误，会用到该类型。
###  probe
用于查询其他实体的在线状态，发送方通过这种类型的 presence stanza 来获取目标实体的当前状态信息。
### subscribe
用于请求订阅其他实体的存在信息，当一个实体希望接收另一个实体的在线状态变化通知时，会发送type为subscribe的 presence stanza 。
### subscribed
表示对订阅请求的确认，即接收方同意了发送方的订阅请求，会回复type为subscribed的 presence stanza 。
###  unavailable
表示实体的不可用状态，比如用户离线时，会发送type为unavailable的 presence stanza ，通知其他关注该实体的用户。
### unsubscribe
用于取消对其他实体存在信息的订阅请求。
### unsubscribed
是对取消订阅请求的确认回复。
## IQ
### get
用于请求获取信息，比如客户端向服务器请求获取用户的好友列表，就可以发送type为get的 IQ stanza ，例如：
```JSON
{
    "v":"1.0",
    "c":"iq",
    "id":"1",
    "from":"phantom1@phantom_im.com/desktop",
    "to":"phantom_im.com",
    "type":"get",
    "body":{
        // n 表示name名称，get参数描述
        "n":"query",
        // body
        "b":"roster"
    }
}
```
表示请求获取好友列表信息。

### set
用于提供数据以完成某项操作，比如设置新的值、更新配置等。如客户端向服务器发送设置用户昵称的请求，可能会使用type为set的 IQ stanza 。
### result
是对成功的get或set请求的响应，表明请求已成功处理。例如服务器响应客户端的好友列表请求时，会发送type为result的 IQ stanza ，并在其中包含好友列表数据。
### error
当处理 IQ 请求出现错误时返回，在<error/>子元素中包含错误详情，如:

```JSON
{
    "v":"1.0",
    "c":"iq",
    "id":"1",
    "from":"phantom1@phantom_im.com/desktop",
    "to":"phantom_im.com",
    "type":"error",
    "body"{"code":401,"msg":"Not found user"}
}
```
表示 IQ 请求处理过程中出现了错误。