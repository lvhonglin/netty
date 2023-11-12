package com.kunpeng.test.day4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class HelloServer {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture bind = new ServerBootstrap()
                .group(new NioEventLoopGroup(1), new NioEventLoopGroup(Runtime.getRuntime().availableProcessors()))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new StringDecoder(Charset.defaultCharset()));
                        socketChannel.pipeline().addLast(new StringEncoder(Charset.defaultCharset()));
                        socketChannel.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

                                super.write(ctx, msg+"_增强了1", promise);
                            }
                        });
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ctx.channel().writeAndFlush("我收到你的消息了_"+(String) msg);
                            }
                        });
                        socketChannel.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

                                super.write(ctx, msg+"_增强了2", promise);
                            }
                        });

                    }
                }).bind(8181);
        bind.sync();
    }
}
