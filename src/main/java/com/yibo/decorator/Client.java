package com.yibo.decorator;

/**
 * @author: huangyibo
 * @Date: 2019/3/23 23:54
 * @Description:
 */
public class Client {

    public static void main(String[] args) {
        Component component = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));

        component.doSomething();
    }
}
