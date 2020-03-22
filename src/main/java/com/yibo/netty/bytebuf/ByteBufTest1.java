package com.yibo.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author: huangyibo
 * @Date: 2020/1/6 20:58
 * @Description:
 */
public class ByteBufTest1 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            byteBuf.writeByte(i);
        }

        /*for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.getByte(i));
            //通过索引来访问Byte时并不会并不会改变真实的读索引和写索引，
            //我们可以通过ByteBuf的readerIndex()与writerIndex()方法分别直接修改读索引和写索引
        }*/

        for (int i = 0; i < byteBuf.capacity(); i++) {
            System.out.println(byteBuf.readByte());
        }
    }
}
