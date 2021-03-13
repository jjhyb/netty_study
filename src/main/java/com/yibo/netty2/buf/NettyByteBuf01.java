package com.yibo.netty2.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author: huangyibo
 * @Date: 2021/3/4 0:06
 * @Description:
 */
public class NettyByteBuf01 {

    public static void main(String[] args) {
        //创建一个ByteBuf
        //该对象包含一个数组arr,是一个byte[10]
        //Netty的ByteBuf在读写切换不需要flip方法反转，
        // 因为底层维护了一个readerIndex 和 writerIndex
        //通过readerIndex、writerIndex、capacity将ByteBuf分成三段
        //0 ---> readerIndex 已经读取的区域
        //readerIndex ---> writerIndex 可以读取的区域
        //writerIndex ---> capacity 可以写入的区域
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buffer.readBytes(i);
        }
        //输出
        for (int i = 0; i < buffer.capacity(); i++) {
            System.out.println(buffer.readByte());

            //执行指定索引获取数据readerIndex不会变化
            //System.out.println(buffer.getByte(i));
        }
    }
}
