package com.yibo.netty2.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author: huangyibo
 * @Date: 2021/3/5 1:13
 * @Description:
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        //将buffer转成字符串
        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("客户端端接收到数据：" + message);
        System.out.println("服务器接收到消息量："+(++count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送10条hello,server
        for (int i = 0; i < 10; i++) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server "+i, CharsetUtil.UTF_8));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
