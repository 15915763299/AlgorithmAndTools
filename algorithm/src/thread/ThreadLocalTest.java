package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 尉涛
 * @date 2020-01-31 18:05
 **/
public class ThreadLocalTest {

    /**
     * The difference between successively generated hash codes - turns
     * implicit sequential thread-local IDs into near-optimally spread
     * multiplicative hash values for power-of-two-sized tables.
     * <p>
     * 这个数字是为了防止有多个ThreadLocal时，Hash发生碰撞
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * The initial capacity -- MUST be a power of two.
     */
    private static final int INITIAL_CAPACITY = 16;


    public static void main(String[] args) throws Exception {
        // 新建一个AtomicInteger，初始值为0
        AtomicInteger nextHashCode = new AtomicInteger();
        System.out.println(nextHashCode.get());

        // 测试哈希值
        int threadLocalHashCode;
        for (int j = 0; j < 15; j++) {
            threadLocalHashCode = nextHashCode.getAndAdd(HASH_INCREMENT);
            System.out.println("threadLocalHashCode: " + threadLocalHashCode);

            int index = threadLocalHashCode & (INITIAL_CAPACITY - 1);
            System.out.println("index: " + index);
        }

        // 实例测试
        final Test test = new Test();
        test.set();
        test.print();

        Thread anotherThread = new Thread(() -> {
            test.set();
            test.print();
        });
        anotherThread.start();
        // 主线程等到anotherThread结束才结束，这里是为了按顺序显示
        anotherThread.join();

        // 确认主线程的变量未被修改
        test.print();

        // 改变变量
        test.stringLocal.set("123");
        System.out.println(test.stringLocal.get());
    }


    private static class Test {
        ThreadLocal<Long> longLocal = new ThreadLocal<>();
        ThreadLocal<String> stringLocal = new ThreadLocal<>();

        void set() {
            longLocal.set(Thread.currentThread().getId());
            stringLocal.set(Thread.currentThread().getName());
        }

        void print() {
            System.out.println("param1: " + longLocal.get());
            System.out.println("param2: " + stringLocal.get());
        }
    }

}
