package com.yibo.netty2.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: huangyibo
 * @Date: 2021/3/13 1:44
 * @Description:
 */
public class MessageProtocolEncoder extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("MessageProtocolEncoder.encode 被调用");
        out.writeInt(msg.getType());
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
