# wind-netty-protocol
Custom netty coding, using the host mode for the company to push orders corresponding to a channel

>架构图

Agent    <-  server admin

|                ｜

watchdog  ->  monitor central

>通信业务流程

- Agent

springboot工程打成Tomcat embed jar部署在医院内网前置机上，负责获取服务端的支付消息并调用银行客户端完成医院医保账户对企业打款

  - 向支付中心拉取配置信息，不同医院会有不同配置（医院标识、对接银行接口配置等）

  - 通过保持https长连接，从支付中心获取支付消息

  - 提供Agent应用状态的接口给WatchDog

  - 支付数据状态保存

- 服务端

用户的支付动作会通过Agent调用银行接口完成支付及保存支付结果回调信息，货款支付信息也需要同步到结算子系统

  - 提供支付数据推送接口

  - 基于Http long polling，异步返回数据

  - 基于netty双向通信

  - 提供支付结果回调接口（网银客户端支付成功后，Agent通过该接口将银行支付结果反馈给支付中心，支付中心再反馈给结算中心）

支付数据状态保存

>客户端与服务端通信

- 客户端把一个 Java 对象按照通信协议转换成二进制数据包。
- 通过网络，把这段二进制数据包发送到服务端，数据的传输过程由 TCP/IP 协议负责数据的传输，与我们的应用层无关。
- 服务端接受到数据之后，按照协议取出二进制数据包中的相应字段，包装成 Java 对象，交给应用逻辑处理。
- 服务端处理完之后，如果需要吐出响应给客户端，那么按照相同的流程进行。

>编码过程分为三个过程

- 创建一个 ByteBuf，这里我们调用 Netty 的 ByteBuf 分配器来创建，ioBuffer() 方法会返回适配 io 读写相关的内存，它会尽可能创建一个直接内存，直接内存可以理解为不受 jvm 堆管理的内存空间，写到 IO 缓冲区的效果更高。
- 将 Java 对象序列化成二进制数据包。
- 逐个往 ByteBuf 写入字段


用于结算中心初始化医院初始机的网络通信，为客户端提供网络通信sdk

>maven

```
<groupId>com.hxmec</groupId>
<artifactId>netty-server</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

>公共配置


- 连接配置
    ```
    netty.hxmec.localHostAddress=192.168.38.161
    netty.hxmec.localHostPort=7000

    netty.hxmec.remoteHostAddress=192.168.38.161
    netty.hxmec.remoteHostPort=8080

    netty.hxmec.serverBossThreadNum=3 //boss线程组的线程数
    netty.hxmec.serverWorkerThreadNum=4 // worker线程组的线程数

    server.port=9000

    ```

- 模仿tcp连接在第一次建立通道时.在client端和server端都要存放一个uid。便于确认是哪一个通道。
    - 客户端配置
    
            本地启动线程需要监听的ip和端口，以及远程的ip和端口 uuid
    - 服务端配置
    
            本地启动线程需要监听的端口

>使用场景

-服务端发消息(自定义密钥加密)

    ```
    BaseAppMetaDataDTO baseAppMetaDataDTO = new BaseAppMetaDataDTO();
     ChannelManager.broadcastMess(ConfigUtlis.getAppId(),baseAppMetaDataDTO,"key");
    ```

- 服务端发送消息(自己默认密钥加密)
    根据配置加载的uid，选择要发送的对应通道
    
    ```
     BaseAppMetaDataDTO baseAppMetaDataDTO = new BaseAppMetaDataDTO();
     ChannelManager.broadcastMess(ConfigUtlis.getAppId(),baseAppMetaDataDTO);
    ```
    
- 服务端接收消息 接收appId
  - 需要在注解上声明notifyAppId
  
    ```
    public class AppInfoRecevier implements NotifyStringRecevier {

    @Override
    public void update(String message) {
        System.out.println(message);
    }
}
    ```

- 服务端接收消息 BaseAppMetaDataBO
  
  ```
    public class MsgRecevier implements NotifyReceiver {

    @Override
    public void doUpdate(JSONObject message) {

        System.out.println(message);
    }
}
  ```

- 客户端发送消息(选择加密)

    ```
     @Autowired
    private NettyClient nettyClient;

    public String transToMessage(){
        ChannelFuture channelFuture = nettyClient.getChannelFuture();
        Channel channel = channelFuture.channel();
        BaseAppMetaDataDTO baseAppMetaDataDTO = new BaseAppMetaDataDTO(); //新建需要传输的实体，这里传输的为顶级父类
        TransferManager.broadcastMess(baseAppMetaDataDTO,channel,"key");
    }
    ```
 
- 客户端发送消息(自己加密)

    ```
    @Autowired
    private NettyClient nettyClient;

    public String transToMessage(){
        ChannelFuture channelFuture = nettyClient.getChannelFuture();
        Channel channel = channelFuture.channel();
        BaseAppMetaDataDTO baseAppMetaDataDTO = new BaseAppMetaDataDTO(); //新建需要传输的实体，这里传输的为顶级父类
        TransferManager.broadcastMess(baseAppMetaDataDTO,channel);
    }
    ```

    
- 客户端接收消息

   ```
    public class MsgRecevier implements NotifyReceiver {

    @Override
    public int type() {
        return 0;
    }

    @Override
    public void doUpdate(JSONObject message) {

        System.out.println(message);
    }
}
   ```
