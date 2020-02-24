package thread.analysis;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 22:05
 * description : 参考：https://blog.csdn.net/qq_35029061/article/details/86750369
 * <p>
 * Callable接口代表一段可以调用并返回结果的代码;
 * Future接口表示异步任务，是还没有完成的任务给出的未来结果
 * 所以说Callable用于产生结果，Future用于获取结果。
 * <p>
 * 源码解读：https://www.cnblogs.com/linghu-java/p/8991824.html
 */
public class FutureTaskAnalysis2 {

    public static void main(String[] args) {
        // 要执行的任务
        MyCallable callable1 = new MyCallable(1000);
        MyCallable callable2 = new MyCallable(2000);

        // 将Callable写的任务封装到一个由执行者调度的FutureTask对象
        FutureTask<String> futureTask1 = new FutureTask<>(callable1);
        FutureTask<String> futureTask2 = new FutureTask<>(callable2);

        // 创建线程池并返回ExecutorService实例
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);

        while (true) {
            try {
                //  两个任务都完成
                if (futureTask1.isDone() && futureTask2.isDone()) {
                    System.out.println("Done");
                    // 关闭线程池和服务
                    executor.shutdown();
                    return;
                }

                // 任务1没有完成，会等待，直到任务完成
                if (!futureTask1.isDone()) {
                    System.out.println("FutureTask1 output=" + futureTask1.get());
                }

                System.out.println("Waiting for FutureTask2 to complete");
                String s = futureTask2.get(200L, TimeUnit.MILLISECONDS);
                if (s != null) {
                    System.out.println("FutureTask2 output=" + s);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                //do nothing
            }
        }
    }

    private static class MyCallable implements Callable<String> {
        private long waitTime;

        MyCallable(int timeInMillis) {
            this.waitTime = timeInMillis;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(waitTime);
            //return the thread name executing this callable task
            return Thread.currentThread().getName();
        }

    }
}