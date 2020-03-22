package com.yibo.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @author: huangyibo
 * @Date: 2020/1/8 22:29
 * @Description:
 */
public class MyClientHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        for (int i = 0; i < 10; i++) {
            String message = "send from client ";
            byte[] content = message.getBytes("UTF-8");
            int length = message.getBytes("UTF-8").length;
            PersonProtocol personProtocol = new PersonProtocol();
            personProtocol.setLength(length);
            personProtocol.setContent(content);
            ctx.writeAndFlush(personProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("客户端接收到的消息：");
        System.out.println("消息长度："+length);
        System.out.println("消息内容："+new String(content, Charset.forName("UTF-8")));
        System.out.println("客户端接收到的消息数量："+(++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        //如果出现了异常，将连接关闭掉
        ctx.channel().close();
    }
}
