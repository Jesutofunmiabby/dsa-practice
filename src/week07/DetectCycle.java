// Idea: a slow pointer (1 step) and a fast pointer (2 steps) must meet inside a cycle; then a pointer from the head and one from the meeting point, both moving 1 step, meet exactly at the cycle's start.
// Mistake: after the pointers met I only moved p from the head and left slow standing still, so p just walked to the meeting point and [3,2,0,-4] with pos 1 returned the -4 node instead of the 2 node.
package week07;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/*
 * Why the second phase lands on the start of the cycle:
 *
 * Let a = steps from the head to the start of the cycle, and c = the length of the cycle.
 * When slow and fast meet, say slow has taken k steps. Fast has taken 2k steps, so fast is
 * k steps ahead. Being ahead and still on the same node means those extra k steps were whole
 * laps of the cycle, so k is a multiple of c.
 *
 * Slow is k steps from the head. If it walks a more steps it will be k + a steps from the head,
 * which is the same spot as a steps from the head plus a whole number of laps (k is a multiple
 * of c). a steps from the head is exactly the start of the cycle.
 *
 * So if p starts at the head and slow starts at the meeting point and both move one step at a
 * time, after a steps both are at the start of the cycle. They can't meet earlier: before a
 * steps p is still on the part before the cycle, where slow never goes.
 */
public class DetectCycle {

    // Simple version: remember every node visited. The first node seen twice is the start.
    public static ListNode detectCycleWithSet(ListNode head) {
        Set<ListNode> seen = new HashSet<>();
        for (ListNode n = head; n != null; n = n.next) {
            if (!seen.add(n)) {
                return n;
            }
        }
        return null;
    }

    // No extra memory: slow and fast pointers, then walk from the head and the meeting point together.
    public static ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode p = head;
                while (p != slow) {
                    p = p.next;
                    slow = slow.next;
                }
                return p;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        check(new int[] {3, 2, 0, -4}, 1);
        check(new int[] {1, 2}, 0);
        check(new int[] {1}, 0);
        check(new int[] {1}, -1);
        check(new int[] {1, 2, 3}, -1);
        check(new int[] {}, -1);
        check(new int[] {1, 2, 3} /* last node points to itself */, 2);
        check(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10} /* long part before the cycle */, 7);
        check(new int[] {5, 5, 5, 5} /* equal values, so only == can tell nodes apart */, 2);

        randomTest(200);
    }

    private static void check(int[] values, int pos) {
        ListNode head = ListNode.fromArrayWithCycle(values, pos);
        ListNode expected = nodeAt(head, pos);
        ListNode set = detectCycleWithSet(head);
        ListNode pointers = detectCycle(head);
        String result = set == expected && pointers == expected ? "PASS" : "FAIL";
        System.out.println(result + "  " + Arrays.toString(values) + ", pos " + pos
                + "  expected: " + describe(head, expected, values.length)
                + "  HashSet: " + describe(head, set, values.length)
                + "  slow/fast: " + describe(head, pointers, values.length));
    }

    // The node at the given index, or null for -1.
    private static ListNode nodeAt(ListNode head, int index) {
        if (index < 0) {
            return null;
        }
        ListNode n = head;
        for (int i = 0; i < index; i++) {
            n = n.next;
        }
        return n;
    }

    // Names a node by where it sits in the list, found with ==, so equal values can't fool us.
    private static String describe(ListNode head, ListNode node, int size) {
        if (node == null) {
            return "null";
        }
        ListNode n = head;
        for (int i = 0; i < size; i++, n = n.next) {
            if (n == node) {
                return "node " + i + " (value " + node.val + ")";
            }
        }
        return "a node not in the list";
    }

    // Fixed seed so a failure can be reproduced by running the program again.
    private static void randomTest(int count) {
        Random random = new Random(42);
        int errors = 0;
        for (int t = 0; t < count; t++) {
            int[] values = new int[random.nextInt(30)];
            for (int i = 0; i < values.length; i++) {
                values[i] = random.nextInt(5);
            }
            int pos = random.nextInt(values.length + 1) - 1;
            ListNode head = ListNode.fromArrayWithCycle(values, pos);
            ListNode expected = nodeAt(head, pos);
            ListNode set = detectCycleWithSet(head);
            ListNode pointers = detectCycle(head);
            if (set != expected || pointers != expected) {
                errors++;
                System.out.println("ERROR  " + Arrays.toString(values) + ", pos " + pos
                        + "  HashSet: " + describe(head, set, values.length)
                        + "  slow/fast: " + describe(head, pointers, values.length));
            }
        }
        System.out.println();
        System.out.println((errors == 0 ? "PASS" : "FAIL") + "  " + count + " random lists, "
                + errors + " where a version returned the wrong node");
    }
}
