package com.demo.design.construction;

/**
 * @author 尉涛
 * @date 2020-03-23 20:00
 * http://c.biancheng.net/view/1361.html
 * 适配器模式（Adapter）的定义如下：
 * 将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类能一起工作。
 * 适配器模式分为类结构型模式和对象结构型模式两种，前者类之间的耦合度比后者高，
 * 且要求程序员了解现有组件库中的相关组件的内部结构，所以应用相对较少些。
 **/
public class AdapterTest {

    public static void main(String[] args) {
        System.out.println("类适配器模式测试：");
        FunctionAbstract classAdapter = new ClassAdapter();
        classAdapter.function();

        System.out.println("对象适配器模式测试：");
        FunctionAbstract objectAdapter = new ObjectAdapter(new Adaptee());
        objectAdapter.function();
    }

    /**
     * 功能抽象接口，对外开放的，大家会调这个接口，但是不认识Adaptee中需要被适配的方法
     */
    interface FunctionAbstract {
        void function();
    }

    /**
     * 需要被适配的类
     */
    private static class Adaptee {
        public void 请适配我() {
            System.out.println("我是被适配的方法");
        }
    }

    /**
     * 类适配器
     */
    private static class ClassAdapter extends Adaptee implements FunctionAbstract {
        public void function() {
            请适配我();
        }
    }

    /**
     * 对象适配器
     */
    private static class ObjectAdapter implements FunctionAbstract {
        private Adaptee adaptee;

        public ObjectAdapter(Adaptee adaptee) {
            this.adaptee = adaptee;
        }

        public void function() {
            adaptee.请适配我();
        }
    }
}
