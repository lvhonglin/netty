package com.kunpeng.test.day1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.omg.CORBA.TIMEOUT;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap handler = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new StringEncoder(Charset.defaultCharset()));
                    }
                });
        ChannelFuture localhost = handler.connect(new InetSocketAddress("localhost", 8181));
        localhost.sync();
        while (true){
            Thread.sleep(1000);
            localhost.channel().writeAndFlush("test1");
        }

    }
}
