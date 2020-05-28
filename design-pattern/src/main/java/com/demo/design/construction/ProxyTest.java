package com.demo.design.construction;

/**
 * @author 尉涛
 * @date 2020-03-23 09:34
 * http://c.biancheng.net/view/1359.html
 * 代理模式的定义：
 * 由于某些原因需要给某对象提供一个代理以控制对该对象的访问。
 * 这时，访问对象不适合或者不能直接引用目标对象，代理对象作为访问对象和目标对象之间的中介。
 **/
public class ProxyTest {

    public static void main(String[] args) {
        // 声明接口（不声明A或者Proxy，体现代理的隐蔽性）
        FunctionAbstract aProxy = new Proxy();
        // 代理了A的功能
        aProxy.function();
    }

    /**
     * 功能抽象接口
     */
    interface FunctionAbstract {
        void function();
    }

    /**
     * 拥有该功能的A
     */
    private static class A implements FunctionAbstract {
        public void function() {
            System.out.println("不知道我是谁吧[狗头]");
        }
    }

    /**
     * A的代理
     * 要拥有A的功能，提供给外界（实现A的功能接口）
     * 然后在接口实现中调用A的对应方法，并且可以加一点自己的实现
     */
    private static class Proxy implements FunctionAbstract {
        private A a;

        public void function() {
            if (a == null) {
                a = new A();
            }
            pre();
            a.function();
            post();
        }

        void pre() {
            System.out.println("执行功能的预处理。");
        }

        void post() {
            System.out.println("执行功能的后续处理。");
        }
    }
}
