package com.yibo.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: huangyibo
 * @Date: 2019/3/10 14:29
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("远程客户端地址："+ctx.channel().remoteAddress() + "-->" + msg);
        ctx.channel().writeAndFlush(987654321L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        //如果出现了异常，将连接关闭掉
        cause.printStackTrace();
        ctx.channel().close();
    }
}
