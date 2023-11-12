package com.kunpeng.test.day2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class HelloServer {
    public static void main(String[] args) throws InterruptedException {
        DefaultEventLoopGroup eventExecutors = new DefaultEventLoopGroup(3);
        ChannelFuture bind = new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(eventExecutors, new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                Channel channel = ctx.channel();
                                channel.writeAndFlush("哈哈");
                                ByteBuf buf = (ByteBuf) msg;
                                String s = buf.toString(Charset.defaultCharset());
                                System.out.println(Thread.currentThread() + ":" + s);

                            }

                        }).addLast(new StringEncoder());
                    }
                }).bind(8181);
        bind.sync();


    }
}
