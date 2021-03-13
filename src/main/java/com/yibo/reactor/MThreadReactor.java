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
 * @Date: 2021/2/28 1:29
 * @Description:
 */
public class MThreadReactor implements Runnable {

    //subReactors集合, 一个selector代表一个subReactor
    Selector[] selectors=new Selector[2];
    int next = 0;
    final ServerSocketChannel serverSocket;

    private MThreadReactor(int port) throws IOException {
        //Reactor初始化
        selectors[0]=Selector.open();
        selectors[1]= Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        //非阻塞
        serverSocket.configureBlocking(false);

        //分步处理,第一步,接收accept事件
        SelectionKey selectionKey = serverSocket.register( selectors[0], SelectionKey.OP_ACCEPT);
        //attach callback object, Acceptor
        selectionKey.attach(new Acceptor());
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i <2 ; i++) {
                    selectors[i].select();
                    Set<SelectionKey> selectionKeys = selectors[i].selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        //Reactor负责dispatch收到的事件
                        dispatch((SelectionKey) (iterator.next()));
                    }
                    selectionKeys.clear();
                }
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


    class Acceptor { // ...
        public synchronized void run() throws IOException {
            //主selector负责accept
            SocketChannel connection = serverSocket.accept();
            if (connection != null) {
                //选个subReactor去负责接收到的connection
                new Handler(selectors[next], connection);
            }
            if (++next == selectors.length) {
                next = 0;
            }
        }
    }
}
