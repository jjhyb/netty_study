package com.yibo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: huangyibo
 * @Date: 2019/3/30 19:22
 * @Description: DirectByteBuffer
 */
public class NioTest8 {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("input2.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while(true){
            //每一次读取之前都将buffer状态初始化
            buffer.clear();
            int read = inputChannel.read(buffer);

            System.out.println("read：" + read);

            if(-1 == read){
                break;
            }
            buffer.flip();
            outputChannel.write(buffer);
        }

        inputChannel.close();
        outputChannel.close();
    }
}
