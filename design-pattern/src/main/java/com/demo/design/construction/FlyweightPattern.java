package com.demo.design.construction;

import java.util.HashMap;

/**
 * @author 尉涛
 * @date 2020-03-23 23:03
 * http://c.biancheng.net/view/1371.html
 * 享元（Flyweight）模式的定义：
 * 运用共享技术来有効地支持大量细粒度对象的复用。
 * 它通过共享已经存在的又橡来大幅度减少需要创建的对象数量、避免大量相似类的开销，从而提高系统资源的利用率。
 *
 **/
public class FlyweightPattern {

    public static void main(String[] args) {
        FlyweightFactory factory = new FlyweightFactory();
        Flyweight f01 = factory.getFlyweight("a");
        Flyweight f02 = factory.getFlyweight("a");
        Flyweight f03 = factory.getFlyweight("a");
        Flyweight f11 = factory.getFlyweight("b");
        Flyweight f12 = factory.getFlyweight("b");
        f01.operation(new UnsharedConcreteFlyweight("第1次调用a。"));
        f02.operation(new UnsharedConcreteFlyweight("第2次调用a。"));
        f03.operation(new UnsharedConcreteFlyweight("第3次调用a。"));
        f11.operation(new UnsharedConcreteFlyweight("第1次调用b。"));
        f12.operation(new UnsharedConcreteFlyweight("第2次调用b。"));
    }


    /**
     * 非享元角色
     * 定义了一些差异，由外部控制使用哪种非共享元素的行为
     */
    private static class UnsharedConcreteFlyweight {
        private String info;

        UnsharedConcreteFlyweight(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    //抽象享元角色
    private static interface Flyweight {
        public void operation(UnsharedConcreteFlyweight state);
    }

    //具体享元角色
    private static class ConcreteFlyweight implements Flyweight {
        private String key;

        ConcreteFlyweight(String key) {
            this.key = key;
            System.out.println("具体享元" + key + "被创建！");
        }

        public void operation(UnsharedConcreteFlyweight outState) {
            System.out.print("具体享元" + key + "被调用，");
            System.out.println("非享元信息是:" + outState.getInfo());
        }
    }

    /**
     * 享元工厂角色
     * 利用一个Map来缓存共享元素
     */
    private static class FlyweightFactory {
        private HashMap<String, Flyweight> flyweights = new HashMap<String, Flyweight>();

        public Flyweight getFlyweight(String key) {
            Flyweight flyweight = flyweights.get(key);
            if (flyweight != null) {
                System.out.println("具体享元" + key + "已经存在，被成功获取！");
            } else {
                flyweight = new ConcreteFlyweight(key);
                flyweights.put(key, flyweight);
            }
            return flyweight;
        }
    }

}
