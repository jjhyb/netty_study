package com.yibo.netty2.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Author: huangyibo
 * @Date: 2021/3/4 23:52
 * @Description:
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        //将buffer转成字符串
        String message = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务器端接收到数据：" + message);
        System.out.println("服务器接收到消息量："+(++count));

        //服务器会送数据给客户端，回送一个随机id值
        ByteBuf responseBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8);
        ctx.channel().writeAndFlush(responseBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
