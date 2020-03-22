package com.yibo.nio;

import java.nio.ByteBuffer;

/**
 * @author: huangyibo
 * @Date: 2019/3/30 1:10
 * @Description:
 *
 * ByteBuffer类型化的get和put方法
 */
public class NioTest5 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(64);
        buffer.putLong(5000000L);
        buffer.putDouble(14.12313D);
        buffer.putChar('你');
        buffer.putShort((short)2);
        buffer.putChar('好');

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
    }
}
