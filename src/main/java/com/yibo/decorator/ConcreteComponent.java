package com.yibo.decorator;

/**
 * @author: huangyibo
 * @Date: 2019/3/23 23:45
 * @Description:
 */
public class ConcreteComponent implements Component {

    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
