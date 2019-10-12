# hx-netty-server-sdk

用于结算中心初始化医院初始机的网络通信，为客户端提供网络通信sdk

>maven

```
<groupId>com.hxmec</groupId>
<artifactId>netty-server</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

>公共配置

- SM4 加密方式

        ```
    	/**
	     * 加密
	     */
		String msg = "qqqqqqsssssaaaa";
		String key = "122D9FC926BF4996";//密钥1
		String cbc_iv = "91BB2E92B13D4398";//密钥2
		String encryStr = SM4Crypter.encrypt(msg.getBytes("GBK"), key, cbc_iv);
		System.out.println("密文： "+encryStr);

		 /**
	     * 解密
	     */
		encryStr = "mAjoJgzNcK5hENoL49Y+SoLSglqtEPdcHbgUHjAC7WyqqQy3qbbKn9TtbFyS48t8tQbGk8B9quoxWAckEcbNNGQzfYjJqHfmCrLCVqDDLNdPas5/cvrx3WmJSveGyAzS4cbG5QWG45Ev8fURzH6lhXzeaRpWdlQgdRyGXzh764G02kk3ZZl1B1jn/b7DAQOQ+XUkC65wA4Jwk1iYeJATn1iicH5FqosGaX3Zhhw/55OmxZG5n6Et1F3ZUD2OzB+s1h30d53RH48gadLrxCpQ6HAg8Oq3U1De/IMxCHEbqNdapsBeQj5UyUstPElrLitf+NhlqwIBE6BFTPJWRvAhsN1tAqWNjRGIz8oTr4jk7C4HG/gRtn+i2hx6ssyhFnGfl+QwAnHaYDVheRUZZn5Emrklhau/Y6SW/qJhB5AEiChBZP4wDASmBiSXOTpw1ekDx0vzqutY6eLKioTBFf2+YFbgLfOoxqpI9HJfoRqtPisJBpRmGClHE8pcZldE419gwYLMQJLNFQupB0VgjPxgMSDpSqRh6lCTQveMRqGbIaZ66umE+q3GclG5zRlTr2I1x9EeIuTQpd/YGMPztJEg311uEPzZjsXstwls5UWKHDxTzEKMzE3sNix96/uOfaLX";
		String decryptStr = new String (SM4Crypter.decrypt(encryStr, key, cbc_iv),"GBK");
		System.out.println("明文： "+decryptStr);
        ```
- DES加密方式

```
 //待加密内容
        String str = "测试内容";
        //密码，长度要是8的倍数
        String password = "9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";

        byte[] result = DESCrypter.encrypt(str.getBytes(),password);
        System.out.println("加密后："+new String(result));

        //直接将如上内容解密
        try {
            byte[] decryResult = DESCrypter.decrypt(result, password);
            System.out.println("解密后："+new String(decryResult));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
```

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


![](http://git.hxmec.com/hxgroup-cxy1/hx-netty-server-sdk/blob/master/%E8%B0%83%E7%94%A8%E5%8C%BB%E9%99%A2%E5%89%8D%E7%BD%AE%E6%9C%BA%E6%B6%88%E6%81%AF%E6%94%B6%E5%8F%91%E6%97%B6%E5%BA%8F%E5%9B%BE.jpg)


>使用场景

- 服务端发送消息
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
  - 需要在注解上声明"notifyTransferDTO" 接收到的消息类型为BaseAppMetaDataBO，需要与服务端应用约定解包类型自己解包
  ```
    public class MsgRecevier implements NotifyReceiver {

    @Override
    public void update(BaseAppMetaDataBO message) {

        System.out.println(message);
    }
}
  ```

