package proxy;

import proxy.impl.BigFactory;
import proxy.impl.SmallFactory;
import proxy.interfaces.CakeFactory;
import proxy.interfaces.ToyFactory;

import java.lang.reflect.Method;

/**
 * @author 尉迟涛
 * create time : 2020/2/21 22:38
 * description :
 */
public class Main {

    public static void main(String[] args) {
        // 我的小店
        MyShop myShop = new MyShop();

        // 我能联系到的工厂，可以定制玩具和蛋糕
        BigFactory bigFactory = new BigFactory();
        SmallFactory smallFactory = new SmallFactory();

        // 定制一个玩具
        myShop.setFactory(bigFactory);
        // 通知工厂
        ToyFactory toyFactoryBig = (ToyFactory) myShop.myShopProxy();
        // 售前服务、制造、售后服务（这里实际上是调用了proxy对象）
        String toy1 = toyFactoryBig.makeToy("变形金刚");
        // 看一下玩具
        System.out.println(toy1);
        System.out.println("-------------------");

        // 定制一个蛋糕
        myShop.setFactory(smallFactory);
        CakeFactory toyFactorySmall = (CakeFactory) myShop.myShopProxy();
        String cake1 = toyFactorySmall.makeCake("草莓味");
        System.out.println(cake1);
        System.out.println("-------------------");

        // 当然，我们也可以找大工厂定制一个蛋糕
        myShop.setFactory(bigFactory);
        CakeFactory cakeFactoryBig = (CakeFactory) myShop.myShopProxy();
        String cake2 = cakeFactoryBig.makeCake("芒果味");
        System.out.println(cake2);
        System.out.println("-------------------");


        // 运行工具得到 proxy 的 class 文件
        ProxyUtils.generateClassFile(
                bigFactory.getClass(),
                toyFactoryBig.getClass().getSimpleName()
        );
        ProxyUtils.generateClassFile(
                smallFactory.getClass(),
                toyFactorySmall.getClass().getSimpleName()
        );

        Method[] methods = toyFactorySmall.getClass().getMethods();
        for(Method method:methods) {
            //打印方法名称
            System.out.println(method.getName());
        }

    }

}
