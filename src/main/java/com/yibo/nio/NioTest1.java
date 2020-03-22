package com.yibo.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author: huangyibo
 * @Date: 2019/3/28 0:02
 * @Description:
 */
public class NioTest1 {

    public static void main(String[] args) {
        //通过nio生成随机数，然后在打印出来
//        method();

        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("capacity："+buffer.capacity());
        for (int i = 0;i < 5;i++){
            int randomNumber = new SecureRandom().nextInt(20);
            //这里相当于把数据写到buffer中
            buffer.put(randomNumber);
        }

        System.out.println("before flip limit："+buffer.capacity());

        //上面是写，下面为读，通过flip()方法进行读写的切换
        buffer.flip();

        System.out.println("after flip limit："+buffer.capacity());

        System.out.println("enter while loop");

        while(buffer.hasRemaining()){
            System.out.println("position：" + buffer.position());
            System.out.println("limit：" + buffer.limit());
            System.out.println("capacity：" + buffer.capacity());

            //这里相当于从buffer中读出数据
            System.out.println(buffer.get());
        }

    }

    /**
     * 通过nio生成随机数，然后在打印出来
     */
    private static void method() {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0;i < buffer.capacity();i++){
            int randomNumber = new SecureRandom().nextInt(20);
            //这里相当于把数据写到buffer中
            buffer.put(randomNumber);
        }

        //上面是写，下面为读，通过flip()方法进行读写的切换
        buffer.flip();

        while(buffer.hasRemaining()){
            //这里相当于从buffer中读出数据
            System.out.println(buffer.get());
        }
    }
}
