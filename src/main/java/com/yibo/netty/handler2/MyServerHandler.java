package com.yibo.netty.handler2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author: huangyibo
 * @Date: 2019/3/10 14:29
 * @Description: TCP粘包拆包效果演示
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //定义真正存储客户端发送过来的字节的数组
        byte[] buffer = new byte[msg.readableBytes()];
        //将数据读取到byte中
        msg.readBytes(buffer);

        String message = new String(buffer, Charset.forName("UTF-8"));
        System.out.println("服务端收到的消息内容：" + message);
        System.out.println("服务端收到的消息数量：" + (++count));

        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(),Charset.forName("UTF-8"));
        ctx.writeAndFlush(responseByteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        //如果出现了异常，将连接关闭掉
        cause.printStackTrace();
        ctx.channel().close();
    }
}
