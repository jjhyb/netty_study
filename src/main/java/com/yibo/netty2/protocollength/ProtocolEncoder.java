package com.yibo.netty2.protocollength;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: huangyibo
 * @Date: 2021/3/13 1:44
 * @Description:
 */
public class ProtocolEncoder extends MessageToByteEncoder<ProtocolBean> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolBean msg, ByteBuf out) throws Exception {
        if(msg == null){
            throw new Exception("msg is null");
        }
        out.writeByte(msg.getType());
        out.writeByte(msg.getFlag());
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}
