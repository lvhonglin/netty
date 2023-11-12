package com.kunpeng.test.day10;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ConcurrentHashMap;

public class HelloServer {
    public static ConcurrentHashMap<String, Channel> map=new ConcurrentHashMap<>();
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture bind = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioDatagramChannel.class)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
                        System.out.println("receive packet:" + datagramPacket.content().toString(CharsetUtil.UTF_8));
                        ByteBuf buf = Unpooled.copiedBuffer("Sth", CharsetUtil.UTF_8);
                        //通过组装bytebuf 获取发送报文的发送者 得到要返回的报文
                        DatagramPacket packet = new DatagramPacket(buf, datagramPacket.sender());
                        channelHandlerContext.writeAndFlush(packet);
                    }
                }).bind(8181);
        bind.sync();
    }
}
