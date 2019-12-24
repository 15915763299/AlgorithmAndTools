package linear;

/**
 * https://leetcode.com/problems/add-two-numbers/solution/
 *
 * Just like how you would sum two numbers on a piece of paper, we begin by summing
 * the least-significant digits, which is the head of l1l1 and l2l2. Since each digit
 * is in the range of 0 \ldots 90…9, summing two digits may "overflow". For example
 * 5 + 7 = 125+7=12. In this case, we set the current digit to 22 and bring over the
 * carry = 1carry=1 to the next iteration. carrycarry must be either 00 or 11 because
 * the largest possible sum of two digits (including the carry) is 9 + 9 + 1 = 199+9+1=19.
 *
 * Complexity Analysis:
 * Time complexity : O(\max(m, n))O(max(m,n)).
 * Assume that mm and nn represents the length of l1l1 and l2l2 respectively, the algorithm above iterates at most \max(m, n)max(m,n) times.
 * Space complexity : O(\max(m, n))O(max(m,n)).
 * The length of the new list is at most \max(m,n) + 1max(m,n)+1.
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
