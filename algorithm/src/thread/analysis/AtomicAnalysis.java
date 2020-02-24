package thread.analysis;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 20:58
 * description : 参考 ttps://www.jianshu.com/p/ae25eb3cfb5d
 * 参考(这个好)：https://blog.csdn.net/qq_20499001/article/details/90640734
 * <p>
 * CAS的缺点：
 * 1.CPU开销较大
 * 在并发量比较高的情况下，如果许多线程反复尝试更新某一个变量，却又一直更新不成功，循环往复，会给CPU带来很大的压力。
 * 2.不能保证代码块的原子性
 * CAS机制所保证的只是一个变量的原子性操作，而不能保证整个代码块的原子性。比如需要保证3个变量共同进行原子性的更新，就不得不使用Synchronized了。
 */
public class AtomicAnalysis {

    public static void main(String[] args) {
        AtomicBooleanTest ast = new AtomicBooleanTest();
        Thread t0 = new Thread(ast);
        Thread t1 = new Thread(ast);
        t0.start();
        t1.start();

        // 两个线程同时执行
        // 当第一个线程执行时，flag为true，flag.compareAndSet(true, false)返回true，修改flag为false，进入sleep
        // 第二个线程执行flag.compareAndSet(true, false)，返回false，进入修正递归。
        // 直到flag.set(true);，第二个进程得以结束递归。

        try {
            t0.join();
            t1.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("-----------");
        AtomicInteger integer = new AtomicInteger();
        System.out.println(integer.get());

        System.out.println("-----------");
        //以原子方式将输入的数值与实例中原本的值相加，并返回最后的结果
        System.out.println(integer.addAndGet(5));
        System.out.println(integer.get());

        System.out.println("-----------");
        //以原子的方式将实例中的原值进行加1操作，并返回最终相加后的结果
        System.out.println(integer.incrementAndGet());
        System.out.println(integer.get());

        System.out.println("-----------");
        //将实例中的值更新为新值，并返回旧值
        System.out.println(integer.getAndSet(3));
        System.out.println(integer.get());

        System.out.println("-----------");
        //以原子的方式将实例中的原值加1，返回的是自增前的旧值
        System.out.println(integer.getAndIncrement());
        System.out.println(integer.get());
    }

    private static class AtomicBooleanTest implements Runnable {
        private static AtomicBoolean flag = new AtomicBoolean(true);

        @Override
        public void run() {
            //  乐观锁需要操作和冲突检测这两个步骤具备原子性
            System.out.println(Thread.currentThread().getName() + "-judge, flag:" + flag.get());
            if (flag.compareAndSet(true, false)) {
                System.out.println(Thread.currentThread().getName() + "-change, flag:" + flag.get());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flag.set(true);
            } else {
                System.err.println(Thread.currentThread().getName() + "-error, flag:" + flag.get());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                run();
            }

        }
    }
}
