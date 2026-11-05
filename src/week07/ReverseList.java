// Idea: walk the list once and turn each node's next arrow around to point at the node before it, saving the next node first so the rest of the list isn't lost.
// Mistake: my loop did curr.next = prev and then curr = curr.next, which by then was prev, so [1,2,3,4,5] came out as [1]; my recursive one cut head.next before using it and hooked head onto newHead instead of the old second node, so it gave [5,1].
package week07;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReverseList {

    public static ListNode reverse(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public static ListNode reverseRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public static void main(String[] args) {
        check(new int[] {1, 2, 3, 4, 5}, "[5,4,3,2,1]");
        check(new int[] {1, 2}, "[2,1]");
        check(new int[] {7}, "[7]");
        check(new int[] {}, "[]");
        check(new int[] {1, 1, 2} /* duplicates */, "[2,1,1]");
        check(new int[] {-3, 0, 3} /* negatives and zero */, "[3,0,-3]");

        randomTest(100);
    }

    private static void check(int[] values, String expected) {
        String loop = run(values, false);
        String recursive = run(values, true);
        String result = expected.equals(loop) && expected.equals(recursive) ? "PASS" : "FAIL";
        System.out.println(result + "  " + ListNode.show(ListNode.fromArray(values)) + "  expected: " + expected
                + "  loop: " + loop + "  recursive: " + recursive);
    }

    // Reverses a fresh list and checks the answer is made of the SAME node objects, in reverse order.
    private static String run(int[] values, boolean recursive) {
        ListNode head = ListNode.fromArray(values);
        List<ListNode> before = new ArrayList<>();
        for (ListNode n = head; n != null; n = n.next) {
            before.add(n);
        }
        ListNode result = recursive ? reverseRecursive(head) : reverse(head);
        int i = before.size() - 1;
        for (ListNode n = result; n != null; n = n.next, i--) {
            if (i < 0 || n != before.get(i)) {
                return "wrong or new nodes";
            }
        }
        if (i != -1) {
            return "lost nodes";
        }
        return ListNode.show(result);
    }

    // Fixed seed so a failure can be reproduced by running the program again.
    private static void randomTest(int count) {
        Random random = new Random(42);
        int errors = 0;
        for (int t = 0; t < count; t++) {
            int[] values = new int[random.nextInt(30)];
            int[] reversed = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                values[i] = random.nextInt(201) - 100;
            }
            for (int i = 0; i < values.length; i++) {
                reversed[i] = values[values.length - 1 - i];
            }
            String expected = ListNode.show(ListNode.fromArray(reversed));
            String loop = run(values, false);
            String recursive = run(values, true);
            if (!expected.equals(loop) || !expected.equals(recursive)) {
                errors++;
                System.out.println("ERROR  expected: " + expected + "  loop: " + loop + "  recursive: " + recursive);
            }
        }
        System.out.println();
        System.out.println((errors == 0 ? "PASS" : "FAIL") + "  " + count + " random lists, "
                + errors + " where a version gave the wrong answer");
    }
}
