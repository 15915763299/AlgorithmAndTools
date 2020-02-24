package thread.analysis;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 尉涛
 * @date 2020-02-01 16:26
 **/
public class ThreadPoolExecutorAnalysis {

    public static void main(String[] args) throws IOException {
//        // 线程数量固定的线程池
//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
//        // 只有非核心线程的线程池
//        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//        // 核心线程数固定、非核心线程数无限制
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
//        // 只有一个核心线程，顺序执行
//        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        int corePoolSize = 2;
        int maximumPoolSize = 4;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        // BlockingQueue即阻塞队列
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2);
        ThreadFactory threadFactory = new NameTreadFactory();
        RejectedExecutionHandler handler = new MyRejectPolicy();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        executor.prestartAllCoreThreads(); // 预启动所有核心线程

        // 核心线程2个，最大线程4个，阻塞队列长度为2
        // 瞬间运行 10 个，每个执行时长超过全部这个“瞬间”的时长
        // 前4个正常运行，阻塞2个，剩下4个被拒绝
        // 你会观察到会有2个被阻塞的任务的log稍晚于前面8个任务的log打出
        for (int i = 1; i <= 10; i++) {
            MyRunnableTask task = new MyRunnableTask(String.valueOf(i));
            executor.execute(task);
        }

        System.in.read(); //阻塞主线程
    }

    /**
     * 线程实现，实现ThreadFactory接口
     */
    static class NameTreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    /**
     * 拒绝策略，实现RejectedExecutionHandler接口
     * 当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize，如果还有任务到来就会采取任务拒绝策略，通常有以下四种策略：
     * <p>
     * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
     * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
     * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
     * ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务
     */
    public static class MyRejectPolicy implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            doLog(r, e);
        }

        private void doLog(Runnable r, ThreadPoolExecutor e) {
            // 可做日志记录等
            System.err.println(r.toString() + " rejected");
        }
    }


    /**
     * 测试 ThreadPoolExecutor
     */
    static class MyRunnableTask implements Runnable {
        private String name;

        MyRunnableTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(this.toString() + " is running!");
                Thread.sleep(3000); //让任务执行慢点
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "MyTask [name=" + name + "]";
        }
    }

}
