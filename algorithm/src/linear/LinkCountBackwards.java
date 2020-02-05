package linear;

/**
 * @author 尉涛
 * @date 2020-02-03 21:37
 * 求链表中倒数第N个节点
 **/
public class LinkCountBackwards {

    public static void main(String[] args) {
        MyLink first = new MyLink(0);
        MyLink temp = first;
        for (int i = 1; i < 10; i++) {
            temp.next = new MyLink(i);
            temp = temp.next;
        }

        showTest(first, 1);
        showTest(first, 5);
        showTest(first, 50);
        showTest(null, 1);
        showTest(null, 0);
        showTest(new MyLink(0), 1);
        showTest(new MyLink(0), 2);
    }

    private static void showTest(MyLink first, int n) {
        MyLink result = getCountBackNode(first, n);
        if (result != null) {
            System.out.println(result.num);
        } else {
            System.out.println("no result");
        }
    }


    private static MyLink getCountBackNode(MyLink first, int n) {
        if (first == null || n == 0) {
            return null;
        }

        MyLink findEnd = first, findBackN = first;
        for (int i = 0; i < n - 1; i++) {
            if (findEnd.next == null) {
                return null;
            }
            findEnd = findEnd.next;
        }

        while (findEnd.next != null) {
            findBackN = findBackN.next;
            findEnd = findEnd.next;
        }
        return findBackN;
    }

    private static class MyLink {
        private int num;
        private MyLink next;

        public MyLink(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public MyLink getNext() {
            return next;
        }

        public void setNext(MyLink next) {
            this.next = next;
        }
    }

}
