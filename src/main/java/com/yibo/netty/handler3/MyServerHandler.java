package com.yibo.netty.handler3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @author: huangyibo
 * @Date: 2020/1/8 22:20
 * @Description:
 */
public class MyServerHandler extends SimpleChannelInboundHandler<PersonProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PersonProtocol msg) throws Exception {
        //接收客户端消息
        //获取消息的长度和消息实际内容
        int length = msg.getLength();
        byte[] content = msg.getContent();

        System.out.println("服务端接收到的数据：");
        System.out.println("数据长度："+length);
        System.out.println("数据内容："+new String(content, Charset.forName("UTF-8")));

        System.out.println("服务端接收到的消息数量："+(++count));

        //向客户端写消息
        String responseMessage = UUID.randomUUID().toString();

        //获取消息的长度和实际消息内容字节数组
        int responseLength = responseMessage.getBytes("UTF-8").length;
        byte[] responseContent = responseMessage.getBytes("UTF-8");

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(responseLength);
        personProtocol.setContent(responseContent);

        //将消息写出，会经过MyPersonEncoder消息编码器编码
        ctx.channel().writeAndFlush(personProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        //如果出现了异常，将连接关闭掉
        ctx.channel().close();
    }
}
