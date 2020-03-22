package com.yibo.decorator;

/**
 * @author: huangyibo
 * @Date: 2019/3/23 23:49
 * @Description:
 */
public class ConcreteDecorator1 extends Decorator {

    public ConcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        doAnotherThing();
    }

    private void doAnotherThing(){
        System.out.println("功能B");
    }
}
