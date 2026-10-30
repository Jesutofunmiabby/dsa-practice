// Idea: sort the numbers, fix each one in turn, and use two pointers on the rest to find pairs that cancel it out, skipping repeated values.
// Mistake: after finding a triplet I didn't skip equal values, so [-2,0,0,2,2] listed [-2,0,2] twice.
package week06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ThreeSum {

    public static List<List<Integer>> threeSumBruteForce(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        Set<List<Integer>> found = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        int[] triple = {nums[i], nums[j], nums[k]};
                        Arrays.sort(triple);
                        found.add(List.of(triple[0], triple[1], triple[2]));
                    }
                }
            }
        }
        List<List<Integer>> result = new ArrayList<>(found);
        result.sort(ThreeSum::compareTriples);
        return result;
    }

    private static int compareTriples(List<Integer> a, List<Integer> b) {
        for (int i = 0; i < 3; i++) {
            int cmp = Integer.compare(a.get(i), b.get(i));
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Array must not be null");
        }
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < sorted.length - 2; i++) {
            if (i > 0 && sorted[i] == sorted[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = sorted.length - 1;
            while (left < right) {
                int sum = sorted[i] + sorted[left] + sorted[right];
                if (sum == 0) {
                    result.add(List.of(sorted[i], sorted[left], sorted[right]));
                    left++;
                    right--;
                    while (left < right && sorted[left] == sorted[left - 1]) {
                        left++;
                    }
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        check("[-1, 0, 1, 2, -1, -4]", new int[] {-1, 0, 1, 2, -1, -4}, "[[-1, -1, 2], [-1, 0, 1]]");
        check("[0, 1, 1] (no triple)", new int[] {0, 1, 1}, "[]");
        check("[0, 0, 0]", new int[] {0, 0, 0}, "[[0, 0, 0]]");
        check("[-2, 0, 0, 2, 2] (duplicates)", new int[] {-2, 0, 0, 2, 2}, "[[-2, 0, 2]]");
        check("[] (empty)", new int[] {}, "[]");
        check("[0, 0, 0, 0] (more than three zeros)", new int[] {0, 0, 0, 0}, "[[0, 0, 0]]");
        check("[1, 2] (fewer than three)", new int[] {1, 2}, "[]");
        check("null", null, "exception");

        randomTest(200);
        timingTest(1_500);
    }

    private static void check(String label, int[] input, String expected) {
        String brute = run(input, true);
        String fast = run(input, false);
        String result = expected.equals(brute) && expected.equals(fast) ? "PASS" : "FAIL";
        System.out.println(result + "  " + label + "  expected: " + expected
                + "  brute: " + brute + "  two pointers: " + fast);
    }

    private static String run(int[] input, boolean brute) {
        try {
            return String.valueOf(brute ? threeSumBruteForce(input) : threeSum(input));
        } catch (IllegalArgumentException e) {
            return "exception";
        }
    }

    // Small values (-5 to 5) so the arrays are full of duplicates and zero-sum triples.
    private static void randomTest(int count) {
        Random random = new Random(42);
        int errors = 0;
        for (int t = 0; t < count; t++) {
            int[] nums = randomArray(random, random.nextInt(12), 5);
            List<List<Integer>> brute = threeSumBruteForce(nums);
            List<List<Integer>> fast = threeSum(nums);
            if (!brute.equals(fast)) {
                errors++;
                System.out.println("ERROR  " + Arrays.toString(nums) + "  brute: " + brute + "  two pointers: " + fast);
            }
        }
        System.out.println();
        System.out.println((errors == 0 ? "PASS" : "FAIL") + "  " + count + " small random arrays, "
                + errors + " where brute and two pointers differ");
    }

    private static void timingTest(int size) {
        Random random = new Random(7);
        int[] nums = randomArray(random, size, 1_000);

        // Warm-up runs so the JVM has compiled both methods before we time them.
        int[] warmUp = randomArray(random, 300, 1_000);
        threeSumBruteForce(warmUp);
        threeSum(warmUp);

        long start = System.nanoTime();
        List<List<Integer>> brute = threeSumBruteForce(nums);
        long bruteNanos = System.nanoTime() - start;

        start = System.nanoTime();
        List<List<Integer>> fast = threeSum(nums);
        long fastNanos = System.nanoTime() - start;

        System.out.println();
        System.out.println("Timing test with " + size + " random numbers from -1000 to 1000:");
        System.out.println((brute.equals(fast) ? "PASS" : "FAIL") + "  both found "
                + brute.size() + " and " + fast.size() + " triples");
        System.out.printf("  brute force:  %.2f ms%n", bruteNanos / 1_000_000.0);
        System.out.printf("  two pointers: %.2f ms%n", fastNanos / 1_000_000.0);
    }

    private static int[] randomArray(Random random, int length, int maxAbs) {
        int[] nums = new int[length];
        for (int i = 0; i < length; i++) {
            nums[i] = random.nextInt(-maxAbs, maxAbs + 1);
        }
        return nums;
    }
}
