package com.kunpeng.test.day10;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.SocketUtils;

import java.net.InetSocketAddress;
import java.sql.Time;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        //可以统一使用bootstrap进行配置
        Bootstrap bootstrap = new Bootstrap();
        //实现udp协议 使用的通道
        bootstrap.group(group).channel(NioDatagramChannel.class)
                .remoteAddress("127.0.0.1", 8181)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
                        String resp = datagramPacket.content().toString(CharsetUtil.UTF_8);
                        System.out.println(resp);
                        channelHandlerContext.close();
                    }
                });
        try {
            Channel channel = bootstrap.bind(0).sync().channel();
            ByteBuf buf = Unpooled.copiedBuffer("Send sth", CharsetUtil.UTF_8);
            //通过组装bytebuf 获取发送报文的发送者 得到要返回的报文
            DatagramPacket packet = new DatagramPacket(buf, SocketUtils.socketAddress("127.0.0.1", 8181));
            channel.writeAndFlush(packet).sync();
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
