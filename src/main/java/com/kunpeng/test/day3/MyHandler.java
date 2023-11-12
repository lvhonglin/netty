package com.kunpeng.test.day3;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class MyHandler extends ChannelInboundHandlerAdapter {
    public static Set<Channel> clients=new HashSet<>();
    public static LinkedList<String> msgs=new LinkedList<>();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        clients.add(ctx.channel());
        System.out.println("客户端连接了:"+clients.size()+":"+ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        clients.remove(ctx.channel());
        System.out.println("客户端断开了:"+clients.size()+":"+ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        System.out.println("客户端给我发消息了："+msg+":"+ctx.channel());
        msgs.add((String) msg);
        System.out.println("队列大小："+msgs.size()+":"+ctx.channel());
        ctx.channel().writeAndFlush("我接收到你给我发的消息了"+":"+ctx.channel());
    }
}
