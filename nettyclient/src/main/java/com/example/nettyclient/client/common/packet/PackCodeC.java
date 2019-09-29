package com.example.nettyclient.client.common.packet;

import com.example.nettyclient.client.common.Serializer.JSONSerializer;
import com.example.nettyclient.client.common.Serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;

/**
 * 封装自定义协议包
 *
 */
public class PackCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PackCodeC INSTANCE = new PackCodeC();
    private static final Map<Byte,Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte,Serializer> serializeMap;

    static {
        packetTypeMap = new HashMap<>();
        serializeMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
    }

    public ByteBuf encode(ByteBuf byteBuf,Packet packet) {
        // 1. 创建 ByteBuf 对象
        //ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        // 2. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }


    private Serializer getSerializer(byte serializerAlgorithm){
        return serializeMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command){
        return packetTypeMap.get(command);
    }
}
