package com.kunpeng.test.day13;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Server2 {
    public static void main(String[] args) throws InterruptedException {
        Set<Channel> channels=new HashSet<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        System.out.println("channels大小:"+channels.size());
                        for(Channel channel:channels) {
                            channel.writeAndFlush("心跳");
                        }
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();
        new ServerBootstrap().group(new NioEventLoopGroup(),new NioEventLoopGroup()).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new LoggingHandler());
                socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new StringEncoder());
                socketChannel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                        channelHandlerContext.fireChannelRead(s);
                    }
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        super.channelActive(ctx);
                        System.out.println("客户端注册："+ctx.channel());
                        channels.add(ctx.channel());
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        super.channelInactive(ctx);
                        System.out.println("客户端取消："+ctx.channel());
                        channels.remove(ctx.channel());
                    }
                });
                socketChannel.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        System.out.println("处理器3收到消息了："+msg);
                        super.write(ctx, msg, promise);
                    }

                    @Override
                    public void flush(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("处理器3flush完了");
                        super.flush(ctx);
                    }
                });
                socketChannel.pipeline().addLast(new ChannelOutboundHandlerAdapter(){
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        System.out.println("处理器4收到消息了："+msg);
                        super.write(ctx, msg, promise);
                    }

                    @Override
                    public void flush(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("处理器4flush完了");
                        super.flush(ctx);
                    }
                });

            }
        }).bind(2222).sync();

    }
}
