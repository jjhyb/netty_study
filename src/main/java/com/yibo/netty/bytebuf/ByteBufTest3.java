package com.yibo.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

/**
 * @author: huangyibo
 * @Date: 2020/1/6 22:52
 * @Description:
 * Netty ByteBuf提供的三种缓冲区类型：
 * 1、heap Buffer
 * 2、direct Buffer
 * 3、composite Buffer
 *
 * Heap Buffer(堆缓冲区)
 * 这是常用的类型，ByteBuf将数据存储到JVM的堆空间中，并且将实际的数据存放到byte array中来实现
 * 优点：由于数据是存储在JVM的堆中，因此可以快速的创建和快速的释放，并且它提供了直接访问内部字节数组的方法
 * 缺点：每次读写数据时，都需要先将数据复制到直接缓冲区中再进行网络传输
 *
 * Direct Buffer(直接缓冲区)
 * 在堆之外直接分配内存空间，直接缓冲区并不会占用堆的容量空间，因为它是由操作系统在本地内存进行的内存分配
 * 优点：在使用Socket进行数据传递时，性能非常好，因为数据直接位于操作系统的本地内存中，所以不需要从JVM将数据复制到直接缓冲区中，性能很好
 * 缺点：因为Direct Buffer是直接在操作系统内存中的，所以内存空间的分配与释放要比堆空间更加复杂，而且速度要慢一些
 * Netty通过提供内存池来解决这个问题，直接缓冲区并不支持通过字节数组的方式来访问数据
 *
 * 重点：对于后端的业务消息的编解码来说，推荐使用HeapByteBuf；对于I/O通信线程在读写缓冲区时，推荐使用DirectByteBuf
 *
 *
 * Composite Buffer(符合缓冲区)
 *
 *
 * JDK的ByteBuffer与Netty的ByteBuf之间的差异比对
 * 1、Netty的ByteBuf采用了读写索引分离的策略(readerIndex和WriterIndex)，一个初始化(没有任何数据)的ByteBuf的readerIndex和WriterIndex的值都为0，
 * 2、当读索引与写索引处于同一位置时，如果继续读取，那么就会抛出IndexOutOfBoundsException
 * 3、对于ByteBuf的任何读写操作都会分别单独维护读索引和写索引，MaxCapacity最大容量默认的限制就是Integer.MAX_VALUE
 *
 * JDK的ByteBuf的缺点：
 * 1、final byte[] hb；这是JDK的ByteBuffer类中用于存储数据的字段声明，可以看到，其字节数组是被声明为final的，也就是长度是固定不变的，
 * 一旦分配好后就不能动态扩容与收缩；而且当待存储的数据字节很大时就很有可能出现IndexOutOfBoundsException。
 * 如果要预防这个异常，就需要在存储之前确定好待存储的字节大小，如果ByteBuffer的空间不足，只有一种解决方案：创建一个全新的ByteBuffer对象，
 * 将之前的ByteBuffer的数据复制过去，这一切都需要由开发者自己来完成
 * 2、ByteBuffer只使用一个position指针来标识位置信息，在进行读写切换时，就需要调用flip()或rewind()，使用起来很不方便
 *
 * Netty ByteBuf的优点：
 * 1、存储字节的数组是动态的，其最大默认值是Integer.MAX_VALUE，这里的动态性是体现在write方法中的，write方法在执行时会判断buffer容量，如不足则自动扩容
 * 2、ByteBuf的读写索引是完全分开的，使用起来很方便
 *
 *
 * 自旋锁 写法for(;;)
 *
 * AtomicIntegerFieldUpdater要点总结：
 * 1、更新器更新的必须是int类型变量，不能是其包装类型
 * 2、更新器更新的必须是volatile类型变量，volatile可以防止JVM的指令重排序，还能确保线程之间共享变量时的立即可见性
 * 3、待更新变量不能是static的，必须要是实例变量，因为底层采用Unsafe.objectFieldOffset()方法实现，
 * 该方法不支持静态变量(CAS操作本质上是通过对象实例的偏移量来直接进行赋值的)
 * 4、更新器只能修改它可见范围内的变量，因为更新器是通过反射来得到这个变量的，如果变量不可见就会报错
 *
 * 如果要更新的变量是包装类型，那么可以使用AtomicReferenceFieldUpdater来进行更新
 *
 *
 *
 */
public class ByteBufTest3 {

    public static void main(String[] args) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();

        ByteBuf heapBuf = Unpooled.buffer(10);
        ByteBuf directBuf = Unpooled.directBuffer(8);

        compositeByteBuf.addComponents(heapBuf,directBuf);
//        compositeByteBuf.removeComponent(0);

        Iterator<ByteBuf> iterator = compositeByteBuf.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        compositeByteBuf.forEach(System.out::println);
    }
}
