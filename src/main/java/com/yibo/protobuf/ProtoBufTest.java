package com.yibo.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author: huangyibo
 * @Date: 2019/3/11 23:46
 * @Description:
 * DataInfo.Student.Builder builder = DataInfo.Student.newBuilder(); 生成出一个构建器对象，返回一个Builder对象
 * 
 */
public class ProtoBufTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三").setAge(20).setAddress("深圳").build();

        //序列化为字节数组
        byte[] student2ByteArray = student.toByteArray();

        //将字节数组反序列
        DataInfo.Student student1 = DataInfo.Student.parseFrom(student2ByteArray);
        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());
    }
}
