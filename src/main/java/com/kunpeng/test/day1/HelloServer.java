package com.kunpeng.test.day1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.Charset;

public class HelloServer {
    public static void main(String[] args) {
        DefaultEventLoopGroup eventExecutors = new DefaultEventLoopGroup(3);
        new ServerBootstrap()
                .group(new NioEventLoopGroup(),new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                             socketChannel.pipeline().addLast(eventExecutors,new ChannelInboundHandlerAdapter(){
                                 @Override
                                 public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                     ByteBuf buf = (ByteBuf) msg;
                                     String s = buf.toString(Charset.defaultCharset());
                                     System.out.println(Thread.currentThread()+":"+s);
                                 }

                             });
                    }
                }).bind(8181);
    }
}
