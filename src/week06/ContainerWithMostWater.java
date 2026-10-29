// Idea: start with the widest container and always move the shorter wall inward, because moving the taller one can never give more water.
// Mistake: TODO
package week06;

import java.util.Arrays;
import java.util.Random;

public class ContainerWithMostWater {

    public static int maxAreaBruteForce(int[] heights) {
        if (heights == null) {
            throw new IllegalArgumentException("Heights must not be null");
        }
        int best = 0;
        for (int i = 0; i < heights.length; i++) {
            for (int j = i + 1; j < heights.length; j++) {
                int area = Math.min(heights[i], heights[j]) * (j - i);
                best = Math.max(best, area);
            }
        }
        return best;
    }

    public static int maxArea(int[] heights) {
        if (heights == null) {
            throw new IllegalArgumentException("Heights must not be null");
        }
        int left = 0;
        int right = heights.length - 1;
        int best = 0;
        while (left < right) {
            int area = Math.min(heights[left], heights[right]) * (right - left);
            best = Math.max(best, area);
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        check("[1, 8, 6, 2, 5, 4, 8, 3, 7]", new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7}, "49");
        check("[1, 1]", new int[] {1, 1}, "1");
        check("[4, 3, 2, 1, 4] (tall walls at both ends)", new int[] {4, 3, 2, 1, 4}, "16");
        check("[1, 2, 1]", new int[] {1, 2, 1}, "2");
        check("[5] (single wall)", new int[] {5}, "0");
        check("[] (empty)", new int[] {}, "0");
        check("[0, 0, 0] (all zero)", new int[] {0, 0, 0}, "0");
        check("[1, 2, 3, 4, 5] (increasing)", new int[] {1, 2, 3, 4, 5}, "6");
        check("null", null, "exception");

        randomTest(100);
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
            return String.valueOf(brute ? maxAreaBruteForce(input) : maxArea(input));
        } catch (IllegalArgumentException e) {
            return "exception";
        }
    }

    // Fixed seed so a failure can be reproduced by running the program again.
    private static void randomTest(int count) {
        Random random = new Random(42);
        int errors = 0;
        for (int t = 0; t < count; t++) {
            int[] heights = new int[random.nextInt(30)];
            for (int i = 0; i < heights.length; i++) {
                heights[i] = random.nextInt(100);
            }
            int brute = maxAreaBruteForce(heights);
            int fast = maxArea(heights);
            if (brute != fast) {
                errors++;
                System.out.println("ERROR  " + Arrays.toString(heights) + "  brute: " + brute + "  two pointers: " + fast);
            }
        }
        System.out.println();
        System.out.println((errors == 0 ? "PASS" : "FAIL") + "  " + count + " random arrays, "
                + errors + " where brute and two pointers differ");
    }
}
