package com.yibo.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @Author: huangyibo
 * @Date: 2021/2/28 1:07
 * @Description:
 */
public class Handler implements Runnable {

    private SocketChannel channel;

    private SelectionKey selectionKey;

    ByteBuffer input = ByteBuffer.allocate(1024);
    ByteBuffer output = ByteBuffer.allocate(1024);
    static final int READING = 0, SENDING = 1;
    int state = READING;

    public Handler(Selector selector, SocketChannel c) throws IOException {
        this.channel = c;
        c.configureBlocking(false);
        // Optionally try first read now
        this.selectionKey = channel.register(selector, 0);

        //将Handler作为callback对象
        this.selectionKey.attach(this);

        //第二步,注册Read就绪事件
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    private boolean inputIsComplete() {
        /* ... */
        return false;
    }

    private boolean outputIsComplete() {
        /* ... */
        return false;
    }

    private void process() {
        /* ... */
        return;
    }

    public void run() {
        try {
            if (state == READING) {
                read();
            } else if (state == SENDING) {
                send();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read() throws IOException {
        channel.read(input);
        if (inputIsComplete()) {
            process();

            state = SENDING;
            // Normally also do first write now
            //第三步,接收write就绪事件
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void send() throws IOException {
        channel.write(output);

        //write完就结束了, 关闭select key
        if (outputIsComplete()) {
            selectionKey.cancel();
        }
    }
}