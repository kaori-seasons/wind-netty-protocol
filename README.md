# wind-netty-protocol
Custom netty coding, using the host mode for the company to push orders corresponding to a channel


>客户端与服务端通信

- 客户端把一个 Java 对象按照通信协议转换成二进制数据包。
- 通过网络，把这段二进制数据包发送到服务端，数据的传输过程由 TCP/IP 协议负责数据的传输，与我们的应用层无关。
- 服务端接受到数据之后，按照协议取出二进制数据包中的相应字段，包装成 Java 对象，交给应用逻辑处理。
- 服务端处理完之后，如果需要吐出响应给客户端，那么按照相同的流程进行。

>编码过程分为三个过程

- 创建一个 ByteBuf，这里我们调用 Netty 的 ByteBuf 分配器来创建，ioBuffer() 方法会返回适配 io 读写相关的内存，它会尽可能创建一个直接内存，直接内存可以理解为不受 jvm 堆管理的内存空间，写到 IO 缓冲区的效果更高。
- 将 Java 对象序列化成二进制数据包。
- 逐个往 ByteBuf 写入字段

