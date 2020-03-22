package com.yibo.netty.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author: huangyibo
 * @Date: 2020/1/8 22:17
 * @Description: 自定义消息编码器
 */
public class MyPersonEncoder extends MessageToByteEncoder<PersonProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, PersonProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MyPersonEncoder encode invoked!");

        //消息编码器，将消息长度和消息实际内容依次写出到网络
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
