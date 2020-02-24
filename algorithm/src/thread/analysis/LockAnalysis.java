package thread.analysis;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 14:17
 * description :
 * <p>
 * 对Lock的说明：https://www.cnblogs.com/wuhan729/p/8601108.html
 */
public class LockAnalysis {

    public static void main(String[] args) throws Exception {
        // lock.lock();的使用方式
        System.out.println("------ lock ------");
        final LockTest lockTest = new LockTest();
        Thread t0 = new Thread() {
            public void run() {
                lockTest.insert(Thread.currentThread());
            }
        };
        Thread t1 = new Thread() {
            public void run() {
                lockTest.insert(Thread.currentThread());
            }
        };
        t0.start();
        t1.start();
        t0.join();
        t1.join();

        // lock.tryLock();的使用方式
        System.out.println("------ tryLock ------");
        final TryLockTest tryLockTest = new TryLockTest();
        Thread t2 = new Thread() {
            public void run() {
                tryLockTest.insert(Thread.currentThread());
            }
        };
        Thread t3 = new Thread() {
            public void run() {
                tryLockTest.insert(Thread.currentThread());
            }
        };
        t2.start();
        t3.start();
        t2.join();
        t3.join();

        // lock.lockInterruptibly();的使用方式
        System.out.println("------ lockInterruptibly ------");
        final LockInterruptiblyTest lockInterruptiblyTest = new LockInterruptiblyTest();
        Thread t4 = new Thread() {
            public void run() {
                lockInterruptiblyTest.insert(Thread.currentThread());
            }
        };
        Thread t5 = new Thread() {
            public void run() {
                lockInterruptiblyTest.insert(Thread.currentThread());
            }
        };
        t4.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t5.start();

        // 等待一下，然后打断t5
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t5.interrupt();


        // 对于读写锁 ReentrantReadWriteLock，这里就不多写示例了
        // ReentrantReadWriteLock 中 readLock() 和 writeLock() 用来获取读锁和写锁。
    }

    private static class LockTest {
        private ArrayList<Integer> arrayList = new ArrayList<>();
        private Lock lock = new ReentrantLock();

        void insert(Thread thread) {
            lock.lock();
            try {
                System.out.println(thread.getName() + "得到了锁");
                for (int i = 0; i < 5; i++) {
                    arrayList.add(i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println(thread.getName() + "释放了锁");
                lock.unlock();
            }
        }
    }

    private static class TryLockTest {
        private ArrayList<Integer> arrayList = new ArrayList<>();
        private Lock lock = new ReentrantLock();

        void insert(Thread thread) {
            boolean result = false;
            try {
                //会等待的 tryLock
                result = lock.tryLock(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //TODO 瞬间返回的 tryLock，会报获取锁失败
//            if (lock.tryLock()) {
            if (result) {
                try {
                    System.out.println(thread.getName() + "得到了锁");
                    for (int i = 0; i < 5; i++) {
                        arrayList.add(i);
                    }
                } finally {
                    System.out.println(thread.getName() + "释放了锁");
                    lock.unlock();
                }
            } else {
                System.out.println(thread.getName() + "获取锁失败");
            }


        }
    }

    private static class LockInterruptiblyTest {
        private Lock lock = new ReentrantLock();

        void insert(Thread thread) {
            try {
                lock.lockInterruptibly();
                try {
                    System.out.println(thread.getName() + "得到了锁");
                    long startTime = System.currentTimeMillis();
                    for (; ; ) {
                        if (System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
                            break;
                    }
                } finally {
                    System.out.println(Thread.currentThread().getName() + "执行finally");
                    lock.unlock();
                    System.out.println(thread.getName() + "释放了锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
