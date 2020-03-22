package com.yibo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: huangyibo
 * @Date: 2019/3/29 23:30
 * @Description:
 *
 * 用nio将一个文件中的内容写到另外一个文件中
 */
public class NioTest4 {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

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
