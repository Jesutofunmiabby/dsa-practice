// Pattern: two pointers, one per sorted array, always copying the smaller current value.
// Time: O(n + m) - each element is copied once. Space: O(n + m) - the merged result array.
package week05;

import java.util.Arrays;

public class MergeSortedArrays {

    public static int[] merge(int[] first, int[] second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Arrays must not be null");
        }

        int[] merged = new int[first.length + second.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < first.length && j < second.length) {
            if (first[i] <= second[j]) {
                merged[k++] = first[i++];
            } else {
                merged[k++] = second[j++];
            }
        }
        while (i < first.length) {
            merged[k++] = first[i++];
        }
        while (j < second.length) {
            merged[k++] = second[j++];
        }
        return merged;
    }

    public static void main(String[] args) {
        check("[1, 3, 5] + [2, 4, 6]", new int[] {1, 3, 5}, new int[] {2, 4, 6}, "[1, 2, 3, 4, 5, 6]");
        check("[1, 2, 7, 9] + [3] (different lengths)", new int[] {1, 2, 7, 9}, new int[] {3}, "[1, 2, 3, 7, 9]");
        check("[1, 2, 3] + [4, 5] (no overlap)", new int[] {1, 2, 3}, new int[] {4, 5}, "[1, 2, 3, 4, 5]");
        check("[1, 2, 2] + [2, 3] (duplicates)", new int[] {1, 2, 2}, new int[] {2, 3}, "[1, 2, 2, 2, 3]");
        check("[-5, 0] + [-3, 8] (negatives)", new int[] {-5, 0}, new int[] {-3, 8}, "[-5, -3, 0, 8]");
        check("[] + [1, 2] (one empty)", new int[] {}, new int[] {1, 2}, "[1, 2]");
        check("[1, 2] + [] (other empty)", new int[] {1, 2}, new int[] {}, "[1, 2]");
        check("[] + [] (both empty)", new int[] {}, new int[] {}, "[]");
        check("[4] + [2] (single elements)", new int[] {4}, new int[] {2}, "[2, 4]");
        check("null + [1]", null, new int[] {1}, "exception");
    }

    private static void check(String label, int[] first, int[] second, String expected) {
        String actual;
        try {
            actual = Arrays.toString(merge(first, second));
        } catch (IllegalArgumentException e) {
            actual = "exception";
        }
        String result = expected.equals(actual) ? "PASS" : "FAIL";
        System.out.println(result + "  " + label + "  expected: " + expected + "  actual: " + actual);
    }
}
