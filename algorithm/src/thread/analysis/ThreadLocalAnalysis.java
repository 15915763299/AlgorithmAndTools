package thread.analysis;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 尉涛
 * @date 2020-01-31 18:05
 * <p>
 * ThreadLocal 原理：
 * 每新建一个 ThreadLocal，都会产生新的 nextHashCode（nextHashCode 每次递增一个 HASH_INCREMENT）
 * 新的 ThreadLocal 会根据这个 hashCode（与存入数组的长度）生成自己的下标，
 * 把自己与需要存入的 value 形成键值对 ThreadLocalMap.Entry，
 * 按照计算出的下标存入当前线程的 ThreadLocalMap 对象中的一个数组
 * （每个线程会维护一个 ThreadLocalMap 对象）
 * 如果新的 ThreadLocal 键值对下标与数组中的发生了“碰撞”则 rehash（double size）
 * <p>
 * Entry 使用了 WeakReference 弱应用，被弱引用关联的对象实例只能生存到下一次垃圾收集发生之前
 * 当 ThreadLocal 没有任何强引用的时候，Entry内的弱引用便会被回收，同时 ThreadLocal 也被回收。
 * <p>
 * 虽然 ThreadLocal 被回收了，但是 Thread Ref -> Thread -> ThreadLocalMap -> Entry -> value
 * 这条强引用链还存在，所以，最好的做法是不在需要使用ThreadLocal变量后，都调用它的remove()方法
 * （ThreadLocalMap 的自清理方法 expungeStaleEntry 有些时候是不及时的）
 * <p>
 * 一个 ThreadLocal 在一个线程中只存一个键值对
 * ThreadLocal 本身并不存储值，它只是作为一个 key 来让线程从 ThreadLocalMap 获取 value
 **/
public class ThreadLocalAnalysis {

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
        for (int j = 0; j < 20; j++) {
            threadLocalHashCode = nextHashCode.getAndAdd(HASH_INCREMENT);
            System.out.println("threadLocalHashCode: " + threadLocalHashCode);

            int index = threadLocalHashCode & (INITIAL_CAPACITY - 1);
            System.out.println("index: " + index);
        }
        // j 在 INITIAL_CAPACITY 之内，产生的 index 没有重复

        System.out.println();

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
