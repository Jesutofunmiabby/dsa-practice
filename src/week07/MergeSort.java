// Idea: split the array in half, sort each half by calling mergeSort on it, then merge the two sorted halves by repeatedly taking the smaller front element.
// Mistake: my merge copied the leftovers from a but forgot the leftovers from b, so those slots stayed 0 and [5,2,9,1,5,6] came out as [1,0,0,2,0,5].
package week07;

import java.util.Arrays;
import java.util.Random;

public class MergeSort {

    // Returns a new sorted array; the input is not changed.
    public static int[] mergeSort(int[] nums) {
        if (nums.length <= 1) {
            return nums.clone();
        }
        int mid = nums.length / 2;
        int[] left = mergeSort(Arrays.copyOfRange(nums, 0, mid));
        int[] right = mergeSort(Arrays.copyOfRange(nums, mid, nums.length));
        return merge(left, right);
    }

    // Merges two sorted arrays into one new sorted array.
    public static int[] merge(int[] a, int[] b) {
        int[] out = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                out[k++] = a[i++];
            } else {
                out[k++] = b[j++];
            }
        }
        while (i < a.length) {
            out[k++] = a[i++];
        }
        while (j < b.length) {
            out[k++] = b[j++];
        }
        return out;
    }

    // Takes each element and slides it left until the one before it is not bigger. Returns a new array.
    public static int[] insertionSort(int[] nums) {
        int[] out = nums.clone();
        for (int i = 1; i < out.length; i++) {
            int key = out[i];
            int j = i - 1;
            while (j >= 0 && out[j] > key) {
                out[j + 1] = out[j];
                j--;
            }
            out[j + 1] = key;
        }
        return out;
    }

    public static void main(String[] args) {
        check(new int[] {5, 2, 9, 1, 5, 6}, "[1, 2, 5, 5, 6, 9]");
        check(new int[] {3, -1, 0, -7}, "[-7, -1, 0, 3]");
        check(new int[] {2, 2, 2}, "[2, 2, 2]");
        check(new int[] {1}, "[1]");
        check(new int[] {}, "[]");
        check(new int[] {5, 4, 3, 2, 1} /* reverse order */, "[1, 2, 3, 4, 5]");
        checkMerge(new int[] {2}, new int[] {9}, "[2, 9]");
        checkMerge(new int[] {}, new int[] {1, 2}, "[1, 2]");
        checkMerge(new int[] {1, 4}, new int[] {2, 3, 5, 6}, "[1, 2, 3, 4, 5, 6]");

        randomTest(200);
        timingTest(20_000);
    }

    private static void check(int[] nums, String expected) {
        int[] copy = nums.clone();
        String merged = Arrays.toString(mergeSort(nums));
        String insertion = Arrays.toString(insertionSort(nums));
        boolean unchanged = Arrays.equals(copy, nums);
        String result = expected.equals(merged) && expected.equals(insertion) && unchanged ? "PASS" : "FAIL";
        System.out.println(result + "  " + Arrays.toString(copy) + "  expected: " + expected
                + "  merge sort: " + merged + "  insertion sort: " + insertion
                + (unchanged ? "" : "  (input was changed!)"));
    }

    private static void checkMerge(int[] a, int[] b, String expected) {
        String actual = Arrays.toString(merge(a, b));
        System.out.println((expected.equals(actual) ? "PASS" : "FAIL") + "  merge(" + Arrays.toString(a)
                + ", " + Arrays.toString(b) + ")  expected: " + expected + "  actual: " + actual);
    }

    // 200 random arrays checked against Arrays.sort. The first two are always empty and single,
    // values run from -50 to 50 so negatives and duplicates are common. Fixed seed for repeatable runs.
    private static void randomTest(int count) {
        Random random = new Random(42);
        int errors = 0;
        for (int t = 0; t < count; t++) {
            int length = t == 0 ? 0 : t == 1 ? 1 : random.nextInt(60);
            int[] nums = new int[length];
            for (int i = 0; i < length; i++) {
                nums[i] = random.nextInt(101) - 50;
            }
            int[] expected = nums.clone();
            Arrays.sort(expected);
            int[] merged = mergeSort(nums);
            int[] insertion = insertionSort(nums);
            if (!Arrays.equals(expected, merged) || !Arrays.equals(expected, insertion)) {
                errors++;
                System.out.println("ERROR  " + Arrays.toString(nums) + "  expected: " + Arrays.toString(expected)
                        + "  merge sort: " + Arrays.toString(merged)
                        + "  insertion sort: " + Arrays.toString(insertion));
            }
        }
        System.out.println();
        System.out.println((errors == 0 ? "PASS" : "FAIL") + "  " + count + " random arrays, "
                + errors + " where a sort differs from Arrays.sort");
    }

    private static void timingTest(int size) {
        Random random = new Random(7);
        int[] nums = new int[size];
        for (int i = 0; i < size; i++) {
            nums[i] = random.nextInt(2_000_001) - 1_000_000;
        }
        int[] expected = nums.clone();
        Arrays.sort(expected);

        long start = System.nanoTime();
        int[] merged = mergeSort(nums);
        long mergeNanos = System.nanoTime() - start;

        start = System.nanoTime();
        int[] insertion = insertionSort(nums);
        long insertionNanos = System.nanoTime() - start;

        boolean ok = Arrays.equals(expected, merged) && Arrays.equals(expected, insertion);
        System.out.println();
        System.out.println("Timing test with " + size + " random numbers:");
        System.out.println((ok ? "PASS" : "FAIL") + "  both results match Arrays.sort");
        System.out.printf("  merge sort:     %.2f ms%n", mergeNanos / 1_000_000.0);
        System.out.printf("  insertion sort: %.2f ms%n", insertionNanos / 1_000_000.0);
    }
}
