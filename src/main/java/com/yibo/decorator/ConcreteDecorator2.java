package com.yibo.decorator;

/**
 * @author: huangyibo
 * @Date: 2019/3/23 23:53
 * @Description:
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        doAnotherThing();
    }
    private void doAnotherThing(){
        System.out.println("功能C");
    }

}
