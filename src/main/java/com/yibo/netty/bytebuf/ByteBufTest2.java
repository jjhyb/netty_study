package com.yibo.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @author: huangyibo
 * @Date: 2020/1/6 22:30
 * @Description:
 */
public class ByteBufTest2 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("吴hello world", Charset.forName("UTF-8"));
        if(byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            System.out.println(new String(content,Charset.forName("UTF-8")));

            System.out.println(byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            int length = byteBuf.readableBytes();
            System.out.println(length);

            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char)byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0,4,Charset.forName("UTF-8")));
        }
    }
}
