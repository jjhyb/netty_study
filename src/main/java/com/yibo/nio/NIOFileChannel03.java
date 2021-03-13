package com.yibo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/1/27 1:25
 * @Description:
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("input.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("input2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (fileChannel01.read(byteBuffer) != -1) { //循环读取
            //将buffer中的数据写入到 fileChannel02 -- 2.txt
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
            byteBuffer.clear(); //清空buffer
        }
        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
