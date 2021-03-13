package com.yibo.netty2.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @Author: huangyibo
 * @Date: 2021/3/4 0:24
 * @Description:
 */
public class NettyByteBuf02 {

    public static void main(String[] args) {
        //创建一个ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world! 重庆", CharsetUtil.UTF_8);
        //使用相关的api
        if(byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            //将content转成中文字符串
            System.out.println(new String(content,CharsetUtil.UTF_8));

            System.out.println("byteBuf："+byteBuf);
            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());
            System.out.println(byteBuf.readableBytes());//可读的字节数
            
            //使用for循环读取
            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char)byteBuf.getByte(i));
            }

            //按某个范围读取
            System.out.println(byteBuf.getCharSequence(0,5,CharsetUtil.UTF_8));
        }
    }
}
