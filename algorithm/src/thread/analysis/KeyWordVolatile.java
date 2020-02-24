package thread.analysis;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 15:45
 * description :参考 https://www.cnblogs.com/blog-Aevin/p/9302678.html
 * <p>
 * volatile让变量每次在使用的时候，都从主存中取。而不是从各个线程的“工作内存”。
 * volatile具有synchronized关键字的“可见性”，但是没有synchronized关键字的“并发正确性”，也就是说不保证线程执行的有序性。
 * 也就是说，volatile变量对于每次使用，线程都能得到当前volatile变量的最新值。但是volatile变量并不保证并发的正确性。
 * <p>
 * 在Java内存模型中，有main memory，每个线程也有自己的memory (例如寄存器)。
 * 为了性能，一个线程会在自己的memory中保持要访问的变量的副本。
 * 这样就会出现同一个变量在某个瞬间，在一个线程的memory中的值可能与另外一个线程memory中的值，
 * 或者main memory中的值不一致的情况。
 * 一个变量声明为volatile，就意味着这个变量是随时会被其他线程修改的，因此不能将它cache在线程memory中。
 * <p>
 * 这个解释同上，似乎更好理解：
 * Java有个思想叫“主”内存区域，这里存放了变量目前的“准确值”。每个线程可以有它自己的变量拷贝，而这个变量拷贝值可以和“主”内存区域里存放的不同。
 * 用volatile修饰后的变量不允许有不同于“主”内存区域的变量拷贝。
 * 理所当然的，volatile修饰的变量存取时比一般变量消耗的资源要多一点，因为线程有它自己的变量拷贝更为高效。
 * <p>
 * volatile只是在线程内存和“主”内存间同步某个变量的值，而synchronized通过锁定和解锁某个监视器同步所有变量的值。
 * 显然synchronized要比volatile消耗更多资源。
 */
public class KeyWordVolatile {

    /**
     * 假如pleaseStop没有被声明为volatile，线程执行run的时候检查的是自己的副本，
     * 就不能及时得知其他线程已经调用tellMeToStop()修改了pleaseStop的值。
     */
    public class StoppableTask extends Thread {
        private volatile boolean pleaseStop;

        public void run() {
            while (!pleaseStop) {
                // do some stuff...
            }
        }

        public void tellMeToStop() {
            pleaseStop = true;
        }
    }

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++)
            //  建立100个线程
            threads[i] = new JoinThread();
        for (int i = 0; i < threads.length; i++)
            //  运行刚才建立的100个线程
            threads[i].start();
        for (int i = 0; i < threads.length; i++)
            //  100个线程都执行完后继续
            threads[i].join();
        System.out.println(" n= " + JoinThread.n);
    }

    /**
     * 在使用volatile关键时一定要谨慎，如果自己没有把握，可以使用synchronized来代替volatile。
     * <p>
     * volatile关键字用于声明简单类型变量，如int、float、 boolean等数据类型。
     * 如果这些简单数据类型声明为volatile，对它们的操作就会变成原子级别的。
     * 但这有一定的限制。例如，下面的例子中的n就不是原子级别的
     * <p>
     * 如果对n的操作是原子级别的，最后输出的结果应该为n=1000，而在执行上面积代码时，
     * 很多时侯输出的n都小于1000，这说明n=n+1不是原子级别的操作。
     * 原因是声明为volatile的简单变量如果当前值由该变量以前的值相关，
     * 那么volatile关键字不起作用，也就是说如下的表达式都不是原子操作：
     * n = n + 1;
     * n ++;
     * <p>
     * 如果要想使这种情况变成原子操作，需要使用synchronized关键字
     * public static synchronized void inc() {
     * n ++ ;
     * }
     */
    private static class JoinThread extends Thread {
        private static volatile int n = 0;

        public void run() {
            for (int i = 0; i < 10; i++)
                try {
                    n = n + 1;
                    sleep(3);  //  为了使运行结果更随机，延迟3毫秒
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
