package proxy.impl;

import proxy.interfaces.CakeFactory;
import proxy.interfaces.ToyFactory;

/**
 * @author 尉迟涛
 * create time : 2020/2/21 22:30
 * description :
 */
public class BigFactory implements ToyFactory, CakeFactory {

    public String makeCake(String name) {
        String product = name + "蛋糕";
        System.out.println("制造了一个" + product);
        return mark(product);
    }

    public String makeToy(String name) {
        String product = name + "玩具";
        System.out.println("制造了一个" + product);
        return mark(product);
    }

    private String mark(String product) {
        return product + " - Made in Big Factory";
    }
}
