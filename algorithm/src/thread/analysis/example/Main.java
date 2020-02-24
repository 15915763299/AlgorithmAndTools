package thread.analysis.example;

/**
 * @author 尉迟涛
 * create time : 2020/2/15 17:17
 * description : 参考 https://www.jianshu.com/p/1dafbf42cc54
 */
public class Main {

    public static void main(String[] args) {
        String[] packets = {
                "First packet",
                "Second packet",
                "Third packet",
                "Fourth packet",
                "End"
        };
        Transmitter transmitter = new Transmitter();

        Thread sender = new Thread(new Sender(transmitter, packets));
        Thread receiver = new Thread(new Receiver(transmitter));

        sender.start();
        receiver.start();
    }

    public static int randomTime(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

}
