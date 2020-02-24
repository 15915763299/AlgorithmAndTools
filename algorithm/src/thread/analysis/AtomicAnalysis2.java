package thread.analysis;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 21:43
 * description : https://blog.csdn.net/qq_20499001/article/details/90640734
 */
public class AtomicAnalysis2 {
    //private static final Unsafe unsafe = Unsafe.getUnsafe();

    // 请求总数
    private static final int CLIENT_TOTAL = 5000;
    // 同时并发执行的线程数
    private static final int THREAD_TOTAL = 200;

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // TODO Semaphore和 CountDownLatch模拟并发
        // 在 semaphore.acquire() 和 semaphore.release()之间的代码，同一时刻只允许指定个数的线程进入
        final Semaphore semaphore = new Semaphore(THREAD_TOTAL);
        // 等待指定数量的线程执行完毕后，执行主线程
        final CountDownLatch countDownLatch = new CountDownLatch(CLIENT_TOTAL);

        for (int i = 0; i < CLIENT_TOTAL; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("count:{" + count.get() + "}");
    }

    private static void add() {
        count.incrementAndGet();
    }
}
