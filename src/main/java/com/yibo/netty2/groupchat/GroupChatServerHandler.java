package com.yibo.netty2.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Author: huangyibo
 * @Date: 2021/3/4 23:52
 * @Description:
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个Channel组，管理所有的Channel
    //GlobalEventExecutor.INSTANCE 全局事件执行器，是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        //遍历channelGroup，根据不同的情况，回送不同的消息
        channelGroup.forEach(ch -> {
            if(channel != ch){
                //不是当前客户端，转发消息
                ch.writeAndFlush("[客户端]"+channel.remoteAddress() + "发送消息：" + msg + "\n");
            }else {
                //回显自己发送的消息，字节发给自己，也可以什么都不做
                ch.writeAndFlush("[自己]发送了消息：" + msg + "\n");
            }
        });
    }

    /**
     * 表示客户端和服务器端已经建立好了连接
     * handlerAdded 表示连接建立，一旦连接，第一个被执行
     * 将当前Channel加入到channelGroup
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该用户加入聊天的信息推送给其他客户端
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" 加入聊天\n");
        channelGroup.add(channel);
    }

    /**
     * 表示客户端和服务器端的连接已经断掉
     * 将用户离开推送给在线的客户端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端] - " + channel.remoteAddress() + " 离开了\n");
        System.out.println("channelGroup size: "+channelGroup.size());
    }

    /**
     * 表示Channel处于活动状态 提示上线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线了~\n");
    }

    /**
     * 表示Channel处于不活动状态，提示离线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 离线了~\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
