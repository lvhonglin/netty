package com.kunpeng.test.day8;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf out) throws Exception {
        out.writeBytes(new byte[]{1,2,3,4});//4个字节的魔数1，2，3，4
        out.writeByte(1);//1个字节的版本号
        //1个字节的序列化方式jdk是0，json是1
        out.writeByte(0);
       
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        int size = byteArrayOutputStream.size();
        out.writeInt(size);
        out.writeBytes(byteArrayOutputStream.toByteArray());
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        byte a = in.readByte();
        byte b = in.readByte();
        byte c = in.readByte();
        byte d = in.readByte();
        byte version = in.readByte();
        byte serializeType = in.readByte();
        int size = in.readInt();
        byte[] bytes = new byte[size];
        in.readBytes(bytes);
        Message message=null;
        if(serializeType==0){
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            message = (Message)objectInputStream.readObject();
        }
//        System.out.println(a+"_"+b+"_"+c+"_"+d+"_"+version+"_"+serializeType+"_"+size+"_"+message);
        list.add(message);
    }
}
