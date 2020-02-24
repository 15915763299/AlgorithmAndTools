package thread.analysis;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 16:13
 * description :参考 https://www.jianshu.com/p/def7f016dd5e
 * <p>
 * wait需释放锁，所以必须在synchronized中使用
 * notify也要在synchronized中使用，应该指定对象
 */
public class WaitAndNotifyAnalysis {

    private static final NotifyThread t1 = new NotifyThread();
    private static final NotifyThread2 t2 = new NotifyThread2();

    public static void main(String[] args) {
        synchronized (t1) {
            try {
                System.out.println("start");
                t1.start();
                System.out.println("wait");
                t1.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("--------------");

        // 重新展示一下上面的过程
        synchronized (t2) {
            try {
                String threadName = Thread.currentThread().getName();

                // 启动“线程t2”
                System.out.println(threadName + ": start t2");
                t2.start();

                // 主线程等待t2通过notify()唤醒。
                // 不是使t2线程等待，而是当前执行wait的线程等待，执行wait的线程等待，执行wait的线程等待！！
                System.out.println(threadName + ": wait");
                t2.wait();

                System.out.println(threadName + ": continue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class NotifyThread extends Thread {
        @Override
        public void run() {
            synchronized (this) {
                System.out.println("notify");
                this.notify();
            }
        }
    }


    private static class NotifyThread2 extends Thread {
        @Override
        public void run() {
            synchronized (this) {
                try {
                    Thread.sleep(3000); //  使当前线阻塞 1 s，确保主程序的 t1.wait(); 执行之后再执行 notify()
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": notify");
                // 唤醒当前的wait线程
                this.notify();
            }
        }
    }
}
