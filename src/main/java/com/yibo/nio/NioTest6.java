package com.yibo.nio;

import java.nio.ByteBuffer;

/**
 * @author: huangyibo
 * @Date: 2019/3/30 1:36
 * @Description:
 *
 * slice Buffer 和原有Buffer共享相同的底层数组
 */
public class NioTest6 {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i=0;i<buffer.capacity();i++){
            buffer.put((byte)i);
        }
        buffer.position(2);
        buffer.limit(6);

        ByteBuffer sliceBuffer = buffer.slice();
        for (int i=0;i < sliceBuffer.capacity();i++){
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i,b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
