package com.yibo.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: huangyibo
 * @Date: 2021/2/28 1:03
 * @Description:
 */
public class Reactor implements Runnable
{
    private Selector selector;
    private ServerSocketChannel serverSocket;

    public Reactor(int port) throws IOException {
        //Reactor初始化
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        //非阻塞
        serverSocket.configureBlocking(false);

        //分步处理,第一步,接收accept事件
        SelectionKey selectionKey = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        //attach callback object, Acceptor
        selectionKey.attach(new Acceptor());
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    //Reactor负责dispatch收到的事件
                    dispatch((SelectionKey) (iterator.next()));
                }
                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment());
        //调用之前注册的callback对象
        if (r != null) {
            r.run();
        }
    }

    // inner class
    class Acceptor implements Runnable {
        public void run() {
            try {
                SocketChannel channel = serverSocket.accept();
                if (channel != null)
                    new Handler(selector, channel);
            } catch (IOException ex)
            { /* ... */ }
        }
    }
}
