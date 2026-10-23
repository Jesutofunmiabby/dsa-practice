// Pattern: brute force nested loops vs a HashMap of value -> index (look up the complement).
// Time: brute O(n^2), HashMap O(n). Space: brute O(1), HashMap O(n) for the map.
package week05;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TwoSum {

    public static int[] twoSumBruteForce(int[] numbers, int target) {
        if (numbers == null) {
            throw new IllegalArgumentException("No two numbers add up to the target");
        }
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        throw new IllegalArgumentException("No two numbers add up to the target");
    }

    public static int[] twoSumHashMap(int[] numbers, int target) {
        if (numbers == null) {
            throw new IllegalArgumentException("No two numbers add up to the target");
        }
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            int complement = target - numbers[i];
            if (seen.containsKey(complement)) {
                return new int[] {seen.get(complement), i};
            }
            seen.put(numbers[i], i);
        }
        throw new IllegalArgumentException("No two numbers add up to the target");
    }

    public static void main(String[] args) {
        check("[2, 7, 11, 15], target 9", new int[] {2, 7, 11, 15}, 9, "[0, 1]");
        check("[3, 2, 4], target 6 (can't reuse index 0)", new int[] {3, 2, 4}, 6, "[1, 2]");
        check("[3, 3], target 6 (duplicate values)", new int[] {3, 3}, 6, "[0, 1]");
        check("[-3, 4, 3, 90], target 0 (negatives)", new int[] {-3, 4, 3, 90}, 0, "[0, 2]");
        check("[0, 4, 3, 0], target 0 (zeros)", new int[] {0, 4, 3, 0}, 0, "[0, 3]");
        check("[1, 2, 3], target 100 (no pair)", new int[] {1, 2, 3}, 100, "exception");
        check("[] (empty)", new int[] {}, 5, "exception");
        check("[5] (single element)", new int[] {5}, 10, "exception");
        check("null", null, 5, "exception");

        timingTest(20_000);
    }

    private static void check(String label, int[] input, int target, String expected) {
        String brute = run(() -> twoSumBruteForce(input, target));
        String hash = run(() -> twoSumHashMap(input, target));
        String result = expected.equals(brute) && expected.equals(hash) ? "PASS" : "FAIL";
        System.out.println(result + "  " + label + "  expected: " + expected
                + "  brute: " + brute + "  hashmap: " + hash);
    }

    private static String run(Supplier<int[]> solver) {
        try {
            return Arrays.toString(solver.get());
        } catch (IllegalArgumentException e) {
            return "exception";
        }
    }

    // The only pair that hits the target is the last two elements, so the brute force
    // has to try almost every pair (its worst case).
    private static void timingTest(int size) {
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = i;
        }
        int target = (size - 2) + (size - 1);
        String expected = "[" + (size - 2) + ", " + (size - 1) + "]";

        // Warm-up runs so the JVM has compiled both methods before we time them.
        twoSumBruteForce(numbers, target);
        twoSumHashMap(numbers, target);

        long start = System.nanoTime();
        int[] bruteResult = twoSumBruteForce(numbers, target);
        long bruteNanos = System.nanoTime() - start;

        start = System.nanoTime();
        int[] hashResult = twoSumHashMap(numbers, target);
        long hashNanos = System.nanoTime() - start;

        String brute = Arrays.toString(bruteResult);
        String hash = Arrays.toString(hashResult);
        String result = expected.equals(brute) && expected.equals(hash) ? "PASS" : "FAIL";
        System.out.println();
        System.out.println("Timing test with " + size + " numbers:");
        System.out.println(result + "  expected: " + expected + "  brute: " + brute + "  hashmap: " + hash);
        System.out.printf("  brute force: %.2f ms%n", bruteNanos / 1_000_000.0);
        System.out.printf("  hashmap:     %.2f ms%n", hashNanos / 1_000_000.0);
    }
}
