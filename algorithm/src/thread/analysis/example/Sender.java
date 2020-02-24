package thread.analysis.example;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 17:14
 * description : 发送者
 */
public class Sender implements Runnable {
    private static final String TAG = Sender.class.getSimpleName();
    private Transmitter transmitter;
    private String[] packets;

    Sender(Transmitter transmitter, String[] packets) {
        this.transmitter = transmitter;
        this.packets = packets;
    }

    public void run() {
        for (String packet : packets) {
            transmitter.send(packet);

            // Thread.sleep() to mimic heavy server-side processing
            try {
                long time = Main.randomTime(2000, 5000);
                System.out.println("Sender wait: " + time);
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
//                Thread.currentThread().interrupt();
//                Log.e(TAG,"Thread interrupted", e);
            }
        }
    }
}
