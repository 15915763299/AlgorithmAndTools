package thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author 尉涛
 * @date 2020-02-22 10:17
 **/
public class MainTest {

    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("ID: " + threadInfo.getThreadId() +
                    ", NAME: " + threadInfo.getThreadName());
        }
    }


}
