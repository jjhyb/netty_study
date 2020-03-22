package com.yibo.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: huangyibo
 * @Date: 2019/3/28 0:54
 * @Description:
 *
 * 传统的io如何通过调用相应的方法切换至相应的nio对象
 */
public class NioTest2 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("NioTest2.txt");
        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        fileChannel.read(byteBuffer);

        byteBuffer.flip();

        while(byteBuffer.remaining() > 0){
            byte b = byteBuffer.get();
            System.out.println("Character：" + (char)b);
        }
        fileInputStream.close();
    }
}
