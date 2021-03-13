package com.yibo.netty2.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Author: huangyibo
 * @Date: 2021/3/13 1:48
 * @Description:
 */
public class MessageProtocolDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        System.out.println("MessageProtocolDecoder.decode 被调用");
        //将二进制字节码 --> MessageProtocol 数据包(对象)
        int messageType = in.readInt();
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        //封装成MessageProtocol 对象，放入list传递给下一个handler
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setType(messageType);
        messageProtocol.setLength(length);
        messageProtocol.setContent(content);
        list.add(messageProtocol);
    }
}
