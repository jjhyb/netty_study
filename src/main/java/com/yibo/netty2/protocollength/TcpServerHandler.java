package com.yibo.netty2.protocollength;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Author: huangyibo
 * @Date: 2021/3/4 23:52
 * @Description:
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<ProtocolBean> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolBean msg) throws Exception {
        //接收到数据并处理
        byte type = msg.getType();
        byte flag = msg.getFlag();
        int length = msg.getLength();
        byte[] content = msg.getContent();
        System.out.println("服务器端接收到消息类型：" + type);
        System.out.println("服务器端接收到消息标志：" + flag);
        System.out.println("服务器端接收到消息数据长度：" + length);
        System.out.println("服务器端接收到消息数据：" + new String(content, CharsetUtil.UTF_8));
        System.out.println("服务器端接收到消息数量："+(++count));

        //服务器会送数据给客户端，回送一个随机id值
        byte[] responseContent = UUID.randomUUID().toString().getBytes(CharsetUtil.UTF_8);
        ProtocolBean protocolBean = new ProtocolBean();
        protocolBean.setType((byte) 1);
        protocolBean.setFlag((byte)1);
        protocolBean.setLength(responseContent.length);
        protocolBean.setContent(responseContent);
        ctx.channel().writeAndFlush(protocolBean);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
