// Idea: always take the smallest front node among all the lists; a min-heap holding one front node per list finds it in O(log k), and merging two at a time is the simpler but slower alternative.
// Common pitfall: merging pairwise into one growing result re-walks that result for every new list, so it costs O(k*N); the heap version is O(N log k) because the heap never holds more than k nodes.
package week08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class MergeKLists {

    static ListNode mergeKListsPairwise(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        ListNode result = lists[0];
        for (int i = 1; i < lists.length; i++) {
            result = mergeTwo(result, lists[i]);
        }
        return result;
    }

    static ListNode mergeTwo(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        PriorityQueue<ListNode> heap = new PriorityQueue<>((x, y) -> Integer.compare(x.val, y.val));
        for (ListNode head : lists) {
            if (head != null) {
                heap.add(head);
            }
        }
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        while (!heap.isEmpty()) {
            ListNode smallest = heap.poll();
            tail.next = smallest;
            tail = smallest;
            if (smallest.next != null) {
                heap.add(smallest.next);
            }
        }
        return dummy.next;
    }

    static ListNode[] build(int[][] arrays) {
        ListNode[] lists = new ListNode[arrays.length];
        for (int i = 0; i < arrays.length; i++) {
            lists[i] = ListNode.fromArray(arrays[i]);
        }
        return lists;
    }

    static void check(String label, int[][] arrays, String expected) {
        // Each version gets its own fresh lists, because merging relinks the original nodes.
        String pairwise = ListNode.show(mergeKListsPairwise(arrays == null ? null : build(arrays)));
        String heap = ListNode.show(mergeKLists(arrays == null ? null : build(arrays)));
        boolean ok = expected.equals(pairwise) && expected.equals(heap);
        System.out.println((ok ? "PASS" : "FAIL") + "  " + label
                + "  expected " + expected + ", pairwise " + pairwise + ", heap " + heap);
    }

    public static void main(String[] args) {
        check("[[1,4,5],[1,3,4],[2,6]]", new int[][] {{1, 4, 5}, {1, 3, 4}, {2, 6}}, "[1,1,2,3,4,4,5,6]");
        check("[[2],[1],[3]]", new int[][] {{2}, {1}, {3}}, "[1,2,3]");
        check("[[],[1]]", new int[][] {{}, {1}}, "[1]");
        check("[[]]", new int[][] {{}}, "[]");
        check("[]", new int[][] {}, "[]");
        // Extra edge cases: a null array, and lists with duplicates and negatives.
        check("null", null, "[]");
        check("[[-3,0,0],[],[-3,5]]", new int[][] {{-3, 0, 0}, {}, {-3, 5}}, "[-3,-3,0,0,5]");

        // Fixed seed so a failure can be reproduced by running the program again.
        Random random = new Random(8);
        int errors = 0;
        for (int t = 0; t < 100; t++) {
            int[][] arrays = new int[random.nextInt(7)][];
            List<Integer> all = new ArrayList<>();
            for (int i = 0; i < arrays.length; i++) {
                arrays[i] = random.ints(random.nextInt(6), -20, 20).sorted().toArray();
                for (int v : arrays[i]) {
                    all.add(v);
                }
            }
            Collections.sort(all);
            String expected = all.toString().replace(" ", "");
            String pairwise = ListNode.show(mergeKListsPairwise(build(arrays)));
            String heap = ListNode.show(mergeKLists(build(arrays)));
            if (!expected.equals(pairwise) || !expected.equals(heap)) {
                errors++;
                System.out.println("FAIL  " + Arrays.deepToString(arrays) + "  pairwise " + pairwise + ", heap " + heap);
            }
        }
        System.out.println((errors == 0 ? "PASS" : "FAIL") + "  100 random inputs, both versions match the sorted values, "
                + errors + " errors");
    }
}
