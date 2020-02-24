package thread.analysis;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 13:00
 * description : 参考 https://blog.csdn.net/zjy15203167987/article/details/82531772
 * <p>
 * Java中每一个对象都可以作为锁，这是synchronized实现同步的基础：
 * 1、普通同步方法（实例方法），锁是当前实例对象 ，进入同步代码前要获得当前实例的锁
 * 2、静态同步方法，锁是当前类的class对象 ，进入同步代码前要获得当前类对象的锁
 * 3、同步方法块，锁是括号里面的对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。
 * <p>
 * 可重入锁：
 * 如果锁具备可重入性，则称作为可重入锁。
 * 像synchronized和ReentrantLock都是可重入锁，可重入性在我看来实际上表明了锁的分配机制：
 * 基于线程的分配，而不是基于方法调用的分配。举个简单的例子，当一个线程执行到某个synchronized方法时，
 * 比如说method1，而在method1中会调用另外一个synchronized方法method2，此时线程不必重新去申请锁，
 * 而是可以直接执行方法method2。
 * <p>
 * 可中断锁：
 * 在Java中，synchronized就不是可中断锁，而Lock是可中断锁。
 * <p>
 * 公平锁：
 * 公平锁即尽量以请求锁的顺序来获取锁。比如同是有多个线程在等待一个锁，当这个锁被释放时，
 * 等待时间最久的线程（最先请求的线程）会获得该所，这种就是公平锁。
 * 在Java中，synchronized就是非公平锁，它无法保证等待的线程获取锁的顺序。
 * 而对于ReentrantLock和ReentrantReadWriteLock，它默认情况下是非公平锁，但是可以设置为公平锁。
 * <p>
 * 读写锁：
 * 读写锁将对一个资源（比如文件）的访问分成了2个锁，一个读锁和一个写锁。
 * ReadWriteLock就是读写锁，它是一个接口，ReentrantReadWriteLock实现了这个接口。
 */
public class KeyWordSynchronize {


    public static void main(String[] args) throws InterruptedException {
        // 执行两次任务，期望结果 20000
        Increase increase = new Increase();
        Thread t1 = new Thread(increase);
        Thread t2 = new Thread(increase);
        t1.start();
        t2.start();
        // 让两个线程执行完再输出
        t1.join();
        t2.join();
        System.out.println(Increase.i);
        // 结果：不却定的数字，因为可能会出现这类情况：
        // 比如此时i=10，t1获取资源进行了操作i++，但结果还没返回，
        // 此时t2去获取资源，获取到i=10，然后进行操作，此时t1、t2的返回结果时一样的
        // 这就造成了本来应该+2的，结果只+1

        SynIncrease synIncrease = new SynIncrease();
        Thread t3 = new Thread(synIncrease);
        Thread t4 = new Thread(synIncrease);
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        System.out.println(SynIncrease.i);
        // 结果：20000
        // 有了锁就不一样，对于同一对象的执行，两个线程会去竞争锁，没竞争到的就等待，所以不会出现重叠的++操作


        // 对于同一对象的不同方法的效果
        final DifferentMethodSyn dms = new DifferentMethodSyn();
        // :: 是lambda表达式
        Thread t5 = new Thread(dms::method1);
        Thread t6 = new Thread(dms::method2);
        t5.start();
        t6.start();
        t5.join();
        t6.join();
        // 可以看到同一对象的不同方法都上锁时，method1执行完后才执行method2
        // 这里如果去掉method2的锁，method2会直接开始执行，
        // 就是当其他线程来访问非synchronized修饰的方法时是可以访问的


        // 死锁展示
        System.out.println("----- dead lock -----");
        final Object o1 = new Object();
        final Object o2 = new Object();

        Thread t7 = new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " start");

            while (true) {
                synchronized (o1) {
                    System.out.println(threadName + " get o1");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o2) {
                        System.out.println(threadName + " get o2");
                    }
                }
            }
        });
        t7.start();

        new Thread(() -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " start");

            while (true) {
                synchronized (o2) {
                    System.out.println(threadName + " get o2");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (o1) {
                        System.out.println(threadName + " get o1");
                    }
                }
            }
        }).start();

        // 出现死锁了，等待5秒，去打断其中一个线程
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("invoke interrupt");
        t7.interrupt();
        // 当一个线程获取了锁之后，是不会被interrupt()方法中断的。
        // 继续死锁......
        // 如果想打断，可以用Lock，参考LockAnalysis.java中的示例


        //java中，解决死锁一般有如下方法：
        //1)尽量使用tryLock(long timeout, TimeUnit unit)的方法(ReentrantLock、ReentrantReadWriteLock)，
        // 设置超时时间，超时可以退出防止死锁。
        //2)尽量使用java.util.concurrent(jdk 1.5以上)包的并发类代替手写控制并发，
        // 比较常用的是ConcurrentHashMap、ConcurrentLinkedQueue、AtomicBoolean等等，
        // 实际应用中java.util.concurrent.atomic十分有用，简单方便且效率比使用Lock更高
        //3)尽量降低锁的使用粒度，尽量不要几个功能用同一把锁
        //4)尽量减少同步的代码块

        // 但是如果此时我们调用wait，因为没有锁，所以会报错......
        t7.wait();
    }


    private static class Increase implements Runnable {
        //共享资源
        private static int i = 0;

        private void increase() {
            i++;
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                increase();
            }
        }
    }

    private static class SynIncrease implements Runnable {
        //共享资源
        private static int i = 0;

        /**
         * synchronized 修饰实例方法
         */
        private synchronized void increase() {
            i++;
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                increase();
            }
        }
    }

    private static class DifferentMethodSyn {
        public synchronized void method1() {
            System.out.println("Method 1 start");
            try {
                System.out.println("Method 1 execute");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Method 1 end");
        }

        public synchronized void method2() {
            System.out.println("Method 2 start");
            try {
                System.out.println("Method 2 execute");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Method 2 end");
        }
    }
}
