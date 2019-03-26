package structure.linear;

/**
 * https://leetcode.com/problems/add-two-numbers/solution/
 */
public class AddTwoNumbers {

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ListNode la = new ListNode(1);
        ListNode lb = new ListNode(2);
        ListNode lc = new ListNode(8);
        la.next = lb;
        lb.next = lc;
        //1->2->8
        ListNode ma = new ListNode(6);
        ListNode mb = new ListNode(3);
        ListNode mc = new ListNode(9);
        ma.next = mb;
        mb.next = mc;
        //6->3->9

        ListNode result = addTwoNumbers(la, ma);

        StringBuilder sb = new StringBuilder(result.val + "");
        while (result.next != null) {
            result = result.next;
            sb.append("->").append(result.val);
        }
        System.out.println(sb.toString());
    }

    private static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;
        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }

        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return dummyHead.next;
    }
}
