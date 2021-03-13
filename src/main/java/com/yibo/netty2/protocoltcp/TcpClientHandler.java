package com.yibo.netty2.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author: huangyibo
 * @Date: 2021/3/5 1:13
 * @Description:
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //接收到数据并处理
        int messageType = msg.getType();
        int length = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("客户端接收到消息类型：" + messageType);
        System.out.println("客户端接收到消息数据长度：" + length);
        System.out.println("客户器端接收到消息数据：" + new String(content, CharsetUtil.UTF_8));
        System.out.println("客户器端接收到消息数量："+(++count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //发送10条你好，中国
        for (int i = 0; i < 10; i++) {
            String msg = "你好，中国 "+i;
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);

            //创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setType(1);
            messageProtocol.setLength(content.length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
