// Idea: keep a min-heap of the k largest numbers seen so far; whenever it grows past k, throw out its smallest, so at the end its top is the kth largest.
// Mistake: my heap version passed every test, but I thought a min-heap was the wrong choice for "largest". It is right because the top of the heap is the number I can safely throw away: anything smaller than k other numbers can never be the kth largest.
package week08;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class KthLargest {

    static int kthLargestSort(int[] nums, int k) {
        checkK(nums, k);
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        return sorted[sorted.length - k];
    }

    static int kthLargest(int[] nums, int k) {
        checkK(nums, k);
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int n : nums) {
            heap.add(n);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        return heap.peek();
    }

    static void checkK(int[] nums, int k) {
        if (k < 1 || k > nums.length) {
            throw new IllegalArgumentException("k must be between 1 and " + nums.length + ", got " + k);
        }
    }

    static String run(boolean useHeap, int[] nums, int k) {
        try {
            return String.valueOf(useHeap ? kthLargest(nums, k) : kthLargestSort(nums, k));
        } catch (IllegalArgumentException e) {
            return "exception";
        }
    }

    static void check(int[] nums, int k, String expected) {
        String sort = run(false, nums, k);
        String heap = run(true, nums, k);
        boolean ok = expected.equals(sort) && expected.equals(heap);
        System.out.println((ok ? "PASS" : "FAIL") + "  " + Arrays.toString(nums) + ", k=" + k
                + "  expected " + expected + ", sort " + sort + ", heap " + heap);
    }

    public static void main(String[] args) {
        check(new int[] {3, 2, 1, 5, 6, 4}, 2, "5");
        check(new int[] {3, 2, 3, 1, 2, 4, 5, 5, 6}, 4, "4");
        check(new int[] {1}, 1, "1");
        check(new int[] {7, 7, 7}, 2, "7");
        check(new int[] {-1, -2}, 2, "-2");
        check(new int[] {1, 2}, 3, "exception");
        // Extra edge cases: k of 0, a negative k, and an empty array.
        check(new int[] {1, 2}, 0, "exception");
        check(new int[] {1, 2}, -1, "exception");
        check(new int[] {}, 1, "exception");

        // Fixed seed so a failure can be reproduced by running the program again.
        Random random = new Random(8);
        int errors = 0;
        for (int t = 0; t < 100; t++) {
            int[] nums = random.ints(1 + random.nextInt(30), -50, 50).toArray();
            int k = 1 + random.nextInt(nums.length);
            int[] before = nums.clone();
            int sort = kthLargestSort(nums, k);
            int heap = kthLargest(nums, k);
            if (sort != heap || !Arrays.equals(before, nums)) {
                errors++;
                System.out.println("FAIL  " + Arrays.toString(before) + ", k=" + k + "  sort " + sort + ", heap " + heap);
            }
        }
        System.out.println((errors == 0 ? "PASS" : "FAIL") + "  100 random arrays, sort and heap agree, " + errors + " errors");
    }
}
