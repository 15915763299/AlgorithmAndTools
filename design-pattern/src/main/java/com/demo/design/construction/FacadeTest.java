package com.demo.design.construction;

/**
 * @author 尉涛
 * @date 2020-03-23 21:04
 * http://c.biancheng.net/view/1369.html
 * 外观（Facade）模式的定义：
 * 是一种通过为多个复杂的子系统提供一个一致的接口，而使这些子系统更加容易被访问的模式。
 * 该模式对外有一个统一接口，外部应用程序不用关心内部子系统的具体的细节，这样会大大降低应用程序的复杂度，提高了程序的可维护性。
 **/
public class FacadeTest {

    public static void main(String[] args) {
        Facade f = new Facade();
        f.method();
    }

    /**
     * 外观角色
     */
    private static class Facade {
        private SubSystem01 obj1 = new SubSystem01();
        private SubSystem02 obj2 = new SubSystem02();
        private SubSystem03 obj3 = new SubSystem03();

        public void method() {
            obj1.method1();
            obj2.method2();
            obj3.method3();
        }
    }

    /**
     * 子系统角色
     */
    private static class SubSystem01 {
        public void method1() {
            System.out.println("子系统01的method1()被调用");
        }
    }

    /**
     * 子系统角色
     */
    private static class SubSystem02 {
        public void method2() {
            System.out.println("子系统02的method2()被调用");
        }
    }

    /**
     * 子系统角色
     */
    private static class SubSystem03 {
        public void method3() {
            System.out.println("子系统03的method3()被调用");
        }
    }
}
