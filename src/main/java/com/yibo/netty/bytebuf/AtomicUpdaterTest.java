package com.yibo.netty.bytebuf;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author: huangyibo
 * @Date: 2020/1/7 1:15
 * @Description:
 */
public class AtomicUpdaterTest {

    public static void main(String[] args) {
        Person person = new Person();

        /*for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //这里age++操作的结果，体现volatile不能不能保证线程安全
                System.out.println(person.age++);
            });
            thread.start();
        }*/

        AtomicIntegerFieldUpdater<Person> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Person.class,"age");

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(atomicIntegerFieldUpdater.getAndIncrement(person));
            });
            thread.start();
        }
    }
}

class Person{
    volatile int age = 1;
}
