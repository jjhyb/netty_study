package com.yibo.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: huangyibo
 * @Date: 2020/1/8 0:21
 * @Description:
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decoder invoked!");

        System.out.println(in.readableBytes());

        //Long类型为8个字节
        if(in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
