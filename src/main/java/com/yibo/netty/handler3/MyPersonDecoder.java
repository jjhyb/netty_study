package com.yibo.netty.handler3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author: huangyibo
 * @Date: 2020/1/8 22:10
 * @Description: 自定义消息解码器
 */
public class MyPersonDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyPersonDecoder decode invoked!");
        //自定义消息解码器，先获取到消息的长度
        int length = in.readInt();
        //创建消息长度的数组用于存储消息
        byte[] content = new byte[length];
        //将消息内容读取到数组中
        in.readBytes(content);

        PersonProtocol personProtocol = new PersonProtocol();
        personProtocol.setLength(length);
        personProtocol.setContent(content);
        out.add(personProtocol);
    }
}
