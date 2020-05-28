package com.demo.design.construction;

/**
 * @author 尉涛
 * @date 2020-03-23 20:29
 * http://c.biancheng.net/view/1366.html
 * 装饰（Decorator）模式的定义：
 * 指在不改变现有对象结构的情况下，动态地给该对象增加一些职责（即增加其额外功能）的模式，
 * 它属于对象结构型模式。
 **/
public class DecoratorTest {

    public static void main(String[] args) {
        FunctionAbstract a = new A();
        a.function();
        System.out.println("---------------------------------");
        FunctionAbstract d = new DecoratedSth(a);
        d.function();
    }

    /**
     * 功能抽象接口
     */
    interface FunctionAbstract {
        void function();
    }

    private static class A implements FunctionAbstract {
        public A() {
            System.out.println("Hello，我是A，我实现了某个功能");
        }

        public void function() {
            System.out.println("我是A，这是我的功能");
        }
    }

    /**
     * 抽象装饰类
     * 封装了要装饰的接口，同时也实现了这个接口
     */
    private static class Decorator implements FunctionAbstract {
        private FunctionAbstract anAbstract;

        public Decorator(FunctionAbstract anAbstract) {
            this.anAbstract = anAbstract;
        }

        public void function() {
            anAbstract.function();
        }
    }

    /**
     * 装饰了A
     */
    private static class DecoratedSth extends Decorator {
        public DecoratedSth(FunctionAbstract anAbstract) {
            super(anAbstract);
        }

        @Override
        public void function() {
            super.function();
            addedFunction();
        }

        public void addedFunction() {
            System.out.println("增加了额外功能");
        }
    }
}
