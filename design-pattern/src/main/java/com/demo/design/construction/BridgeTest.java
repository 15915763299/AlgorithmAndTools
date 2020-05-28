package com.demo.design.construction;

/**
 * @author 尉涛
 * @date 2020-03-23 20:13
 * http://c.biancheng.net/view/1364.html
 * 桥接（Bridge）模式的定义如下：
 * 将抽象与实现分离，使它们可以独立变化。
 * 它是用组合关系代替继承关系来实现，从而降低了抽象和实现这两个可变维度的耦合度。
 **/
public class BridgeTest {

    public static void main(String[] args) {
        FunctionAbstract anAbstract = new A();
        Bridge bridge = new BridgeImpl(anAbstract);
        bridge.Operation();
    }

    /**
     * 功能抽象接口
     */
    interface FunctionAbstract {
        void function();
    }


    public static class A implements FunctionAbstract {
        public void function() {
            System.out.println("我是 -A- 的某个功能");
        }
    }

    public static class B implements FunctionAbstract {
        public void function() {
            System.out.println("我是 -B- 的某个功能");
        }
    }

    /**
     * 我是桥，我可以接上实现了FunctionAbstract的类的对象
     */
    private static abstract class Bridge {
        protected FunctionAbstract anAbstract;

        protected Bridge(FunctionAbstract anAbstract) {
            this.anAbstract = anAbstract;
        }

        public abstract void Operation();
    }

    /**
     * 我是桥的实现
     */
    private static class BridgeImpl extends Bridge {

        protected BridgeImpl(FunctionAbstract anAbstract) {
            super(anAbstract);
        }

        public void Operation() {
            System.out.println("看看我桥接上了谁~");
            anAbstract.function();
        }
    }
}
