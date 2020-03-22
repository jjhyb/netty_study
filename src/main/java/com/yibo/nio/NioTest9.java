package com.yibo.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author: huangyibo
 * @Date: 2019/3/30 20:57
 * @Description: MappedByteBuffer 内存映射文件
 */
public class NioTest9 {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 6);

        mappedByteBuffer.put(0, (byte) 'a');
        mappedByteBuffer.put(4, (byte) 'b');

        randomAccessFile.close();
    }
}
