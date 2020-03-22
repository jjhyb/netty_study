package com.yibo.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @author: huangyibo
 * @Date: 2019/4/1 1:11
 * @Description:
 */
public class NioTest13 {

    public static void main(String[] args) throws IOException {
        String inputFile = "NioTest13_In.txt";
        String outputFile = "NioTest13_Out.txt";

        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile,"r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile,"rw");

        long inputLength = new File(inputFile).length();
        FileChannel inputChannel = inputRandomAccessFile.getChannel();
        FileChannel outputChannel = outputRandomAccessFile.getChannel();

        MappedByteBuffer inputData = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);

        Charset charset = Charset.forName("utf-8");
        //decoder解码
        CharsetDecoder decoder = charset.newDecoder();
        //encoder编码
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(inputData);
        ByteBuffer outputData = encoder.encode(charBuffer);

        outputChannel.write(outputData);

        inputRandomAccessFile.close();
        outputRandomAccessFile.close();

        //列出操作系统支持的字符集
        Charset.availableCharsets().forEach((key,value) -> {
            System.out.println(key + " : " + value);
        });
    }
}
