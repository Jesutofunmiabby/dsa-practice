// Idea: at every step at least one half around the middle is sorted, so check whether the target sits inside that sorted half and throw away the other half.
// Mistake: in the branch where the right half is sorted I wrote hi = mid + 1 instead of hi = mid - 1, so on [5,1,3] with target 5 hi kept being reset to 2, mid stayed at 1 and the loop never ended.
package week07;

import java.util.Arrays;
import java.util.Random;

public class RotatedSearch {

    public static int search(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[lo] <= nums[mid]) {
                // Left half lo..mid is sorted.
                if (nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else {
                // Right half mid..hi is sorted.
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }

    // The slow version to compare against: checks every element.
    public static int searchSlow(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        check(new int[] {4, 5, 6, 7, 0, 1, 2}, 0, 4);
        check(new int[] {4, 5, 6, 7, 0, 1, 2}, 3, -1);
        check(new int[] {1}, 0, -1);
        check(new int[] {1}, 1, 0);
        check(new int[] {3, 1}, 1, 1);
        check(new int[] {5, 1, 3}, 5, 0);
        check(new int[] {}, 5, -1);
        check(new int[] {1, 2, 3, 4, 5} /* not rotated */, 4, 3);
        check(new int[] {2, 3, 4, 5, 1} /* smallest at the end */, 1, 4);
        check(new int[] {6, 7, 1, 2, 3, 4, 5} /* target in the sorted right half */, 5, 6);

        randomTest(200);
    }

    private static void check(int[] nums, int target, int expected) {
        int fast = search(nums, target);
        int slow = searchSlow(nums, target);
        String result = fast == expected && slow == expected ? "PASS" : "FAIL";
        System.out.println(result + "  " + Arrays.toString(nums) + ", " + target + "  expected: " + expected
                + "  binary search: " + fast + "  slow: " + slow);
    }

    // Builds sorted arrays of different values, rotates them by a random amount, and compares with the slow version.
    // Fixed seed so a failure can be reproduced by running the program again.
    private static void randomTest(int count) {
        Random random = new Random(42);
        int errors = 0;
        for (int t = 0; t < count; t++) {
            int n = random.nextInt(30);
            int[] sorted = new int[n];
            int value = -50;
            for (int i = 0; i < n; i++) {
                value += 1 + random.nextInt(5);
                sorted[i] = value;
            }
            int shift = n == 0 ? 0 : random.nextInt(n);
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sorted[(i + shift) % n];
            }
            int target = random.nextInt(100) - 50;
            int fast = search(nums, target);
            int slow = searchSlow(nums, target);
            if (fast != slow) {
                errors++;
                System.out.println("ERROR  " + Arrays.toString(nums) + ", " + target
                        + "  binary search: " + fast + "  slow: " + slow);
            }
        }
        System.out.println();
        System.out.println((errors == 0 ? "PASS" : "FAIL") + "  " + count + " random rotated arrays, "
                + errors + " where binary search and the slow loop differ");
    }
}
