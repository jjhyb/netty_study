package com.yibo.netty.example2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author: huangyibo
 * @Date: 2019/3/9 1:34
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("远程客户端地址："+ctx.channel().remoteAddress() + "-->" + msg);
        ctx.channel().writeAndFlush("from server：" + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        //如果出现了异常，将连接关闭掉
        cause.printStackTrace();
        ctx.channel().close();
    }
}
