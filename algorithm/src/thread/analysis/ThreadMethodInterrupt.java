package thread.analysis;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 10:02
 * description : https://blog.csdn.net/m0_37661458/article/details/90692419
 * <p>
 * 概念：
 * 1、进程是操作系统进行资源分配的最小单位
 * 同一进程中的多条线程共享该进程中的全部系统资源,而进程和进程之间是相互独立的。
 * 其中资源包括：CPU、内存空间、磁盘IO等。
 * <p>
 * 2、线程是CPU调度的最小单位,必须依赖于进程而存在
 * 线程自己基本上不拥有系统资源,只拥有一点在运行中必不可少的资源(如程序计数器,一组寄存器和栈),
 * 但是它可与同属一个进程的其他的线程共享进程所拥有的全部资源。
 * Java中不管任何程序都必须启动一个main函数的主线程;
 * <p>
 * 3、多核心：各个处理器并行执行不同的进程
 * 4、多线程：让同一个处理器上的多个线程同步执行并共享处理器的执行资源。
 * <p>
 * 核心数、线程数:目前主流CPU都是多核的。增加核心数目就是为了增加线程数，因为操作系统是通过线程来执行任务的，
 * 一般情况下它们是1:1对应关系,也就是说四核CPU一般拥有四个线程。
 * 但 Intel引入超线程技术后，使核心数与线程数形成1:2的关系
 * <p>
 * 5、CPU时间片轮转机制：如果一个时间片100ms，轮转用时5ms，CPU时间的5%会被浪费在了管理开销上。
 * <p>
 * 6、并发：指应用能够交替执行不同的任务，比如单CPU核心下执行多线程并非是同时执行多个任务，
 * 而是在你几乎不可能察觉到的速度下不断去切换这两个任务，已达到"同时执行效果"。
 * 7、并行：指应用能够同时执行不同的任务
 * <p>
 * suspend()方法在调用后，线程不会释放已经占有的资源（比如锁），而是占有着资源进入睡眠状态
 * stop()方法在终结一个线程时不会保证线程的资源正常释放
 * interrupt()会设置一个中断标识位，线程发现需要中断则安全中断。
 * join()把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行，调用join的后执行。
 */
public class ThreadMethodInterrupt {

    public static void main(String[] args) {
        // interrupt
        InterruptThread interruptThread = new InterruptThread();
        interruptThread.start();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        interruptThread.interrupt();
        // 结果：java.lang.InterruptedException: sleep interrupted
        // 但是！！这个时候线程并没有终止


        // 单独调用interrupt()方法不能中断正在运行过程中的线程，只能中断阻塞过程中的线程。
        InterruptThread2 interruptThread2 = new InterruptThread2();
        interruptThread2.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("invoke interrupt");
        interruptThread2.interrupt();
    }


    private static class InterruptThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
//            while (!Thread.interrupted()) {// 这个方法时检查并清除打断（重置为false）这里用isInterrupted就好了
                System.out.println("Thread is running");
                System.out.println("Interrupt flag: " + isInterrupted());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // sleep被interrupt后会重置标志位为false
                    System.out.println("Interrupt flag: " + isInterrupted());
                    // 加上这句话才会真正终止
                    interrupt();
                    e.printStackTrace();
                }
            }
        }
    }

    private static class InterruptThread2 extends Thread {
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            long i = 0;
            long limit = Long.MAX_VALUE / 1000000000;
            while (i < limit) {
                i++;
            }
            System.out.println(System.currentTimeMillis() - start);
        }
    }

    // 正确写法：
    // while (!isInterrupted()) {...}

    //     * Tests whether the current thread has been interrupted.  The
    //     * <i>interrupted status</i> of the thread is cleared by this method.  In
    //     * other words, if this method were to be called twice in succession, the
    //     * second call would return false (unless the current thread were
    //     * interrupted again, after the first call had cleared its interrupted
    //     * status and before the second call had examined it).
    // public static native boolean interrupted();
    // Thread.interrupted() 会清除打断，将其重置为false

}
