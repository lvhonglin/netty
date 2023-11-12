package com.kunpeng.test.day9;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        Bootstrap handler = new Bootstrap()
                .group(eventExecutors)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,1)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new LoggingHandler());
                        socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,6,4));
                        socketChannel.pipeline().addLast(new MessageCodec());
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                RegisterMessage registerMessage = new RegisterMessage();
                                registerMessage.setName("client1");
                                ctx.writeAndFlush(registerMessage);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (true) {
                                            Scanner scanner = new Scanner(System.in);
                                            System.out.println("要发送消息请回车");
                                            scanner.nextLine();
                                            System.out.println("请输入目标名");
                                            String name = scanner.nextLine();
                                            System.out.println("请输入发送的消息");
                                            String msg = scanner.nextLine();
                                            RealMessage realMessage = new RealMessage(msg, name);
                                            ctx.channel().writeAndFlush(realMessage);
                                        }
                                    }
                                }).start();
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                super.channelRead(ctx, msg);
                                System.out.println("接收到了:"+((Message)msg).msg());
                            }
                        });
                    }
                });
        ChannelFuture channelFuture = handler.connect(new InetSocketAddress("localhost", 8181));
        channelFuture.sync();
    }
}
