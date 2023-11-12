package com.kunpeng.test.day8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.concurrent.ConcurrentHashMap;

public class HelloServer {
    public static ConcurrentHashMap<String, Channel> map=new ConcurrentHashMap<>();
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture bind = new ServerBootstrap()
                .group(new NioEventLoopGroup(1), new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,6,4));
                        socketChannel.pipeline().addLast(new MessageCodec());
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                super.channelRead(ctx,msg);
                                Message message=(Message) msg;
                                if(message instanceof RegisterMessage){
                                    String name = message.name();
                                    Channel channel = map.get(name);
                                    if(channel!=null){
                                        System.out.println("重复注册:"+name);
                                        return;
                                    }else{
                                        map.put(name,ctx.channel());
                                        System.out.println("注册成功:"+name);
                                    }
                                }else if(message instanceof PingMessage){
                                    System.out.println("ping");
                                } else {
                                    String name = message.name();
                                    Channel channel = map.get(name);
                                    if(channel==null){
                                        System.out.println("client 不存在:"+name);
                                    }else {
                                        channel.writeAndFlush(message);
                                        System.out.println("发送成功:"+name+"_"+message.msg());
                                    }
                                }
                            }
                        });
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println("mssadasd");
                            }
                        });
                    }
                }).bind(8181);
        bind.sync();
    }
}
