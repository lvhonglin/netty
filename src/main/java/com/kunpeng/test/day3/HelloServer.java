package com.kunpeng.test.day3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
                        socketChannel.pipeline().addLast(new MyHandler());
                    }
                }).bind(8181);
        bind.sync();
    }
}
