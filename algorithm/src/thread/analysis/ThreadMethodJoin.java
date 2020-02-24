package thread.analysis;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 12:45
 * description : join()把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行，调用join的后执行。
 * <p>
 * join 内部使用了 wait 方法，然后通过 notifyAll 唤醒调用线程。
 * <p>
 * 如果synchronized获得对象锁是线程的实例时，此时比较特殊，当该线程终止的时候，
 * 会调用线程自身的notifyAll()方法，会通知所有等待在该线程对象上的线程
 * <p>
 * 参考：https://blog.csdn.net/chenkaibsw/article/details/80912878
 */
public class ThreadMethodJoin {

    public static void main(String[] args) {
        // join
        Thread previousThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            JoinThread joinDemo = new JoinThread(previousThread, i);
            joinDemo.start();
            previousThread = joinDemo;
        }
        System.out.println("join is end (" + Thread.currentThread().getName() + ")");

        // 你会观察到数字按顺序被打印出来（虽然线程启动是无序的），因为previousThread在新线程中调用了join，
        // 在join点，新线程会被阻塞，直到调用join的线程执行完毕。
        // 执行顺序：
        // mainThread -> t0.start -> mainThread.join (t0被阻塞)
        //            -> t1.start -> t0.join         (t1被阻塞)
        //            -> t2.start -> t1.join         (t2被阻塞)
        //            ......

        System.out.println("-------------");
    }

    private static class JoinThread extends Thread {
        private int i;
        private Thread previousThread; //上一个线程

        JoinThread(Thread previousThread, int i) {
            this.previousThread = previousThread;
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println("start " + i);
            try {
                //调用上一个线程的join方法，如果注释掉这行代码，数字是混乱的
                previousThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("----- thread " + i + " is end (" + Thread.currentThread().getName() + ")");
        }


    }

}
