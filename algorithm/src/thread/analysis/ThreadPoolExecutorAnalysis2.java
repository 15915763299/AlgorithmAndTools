package thread.analysis;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 尉涛
 * @date 2020-02-01 16:26
 **/
public class ThreadPoolExecutorAnalysis2 {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }


    public static void main(String[] args) throws IOException {
        printAsBinary("        -1", -1);
        printAsBinary("        -2", -2);
        printAsBinary("        -3", -3);
        printAsBinary("CAPACITY  ", CAPACITY);
        printAsBinary("RUNNING   ", RUNNING);
        printAsBinary("SHUTDOWN  ", SHUTDOWN);
        printAsBinary("STOP      ", STOP);
        System.out.println(STOP);
        printAsBinary("TIDYING   ", TIDYING);
        printAsBinary("TERMINATED", TERMINATED);

        ThreadPoolExecutorAnalysis2 instance = new ThreadPoolExecutorAnalysis2();
        System.out.println(instance.ctl.get());
        printAsBinary("       ctl", instance.ctl.get());

    }

    private static void printAsBinary(String name, int num){
        System.out.println(name + ": " + Integer.toBinaryString(num));
    }
}
