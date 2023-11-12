package com.kunpeng.test.day3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap handler = new Bootstrap()
                .group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new StringEncoder(Charset.defaultCharset()));
                        socketChannel.pipeline().addLast(new StringDecoder(Charset.defaultCharset()));
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println("服务端给我返回了："+(String)msg);
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = handler.connect(new InetSocketAddress("localhost", 8181));
        channelFuture.sync();
        while (true) {
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();
            channel.writeAndFlush(msg);
        }

    }
}
