// Idea: binary search twice, and when the middle equals the target don't stop: keep searching left to find the first copy and right to find the last copy.
// Mistake: when I found the target I set hi = mid instead of hi = mid - 1, so once lo, mid and hi were the same index the range never shrank, and [2,2] with 2, [1] with 1 and [5,7,7,8,8,10] with 8 all looped forever.
package week07;

import java.util.Arrays;
import java.util.Random;

public class SearchRange {

    // Plain binary search: returns any index of target, or -1.
    public static int binarySearch(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return -1;
    }

    public static int[] searchRange(int[] nums, int target) {
        return new int[] {leftmost(nums, target), rightmost(nums, target)};
    }

    private static int leftmost(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        int ans = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                ans = mid;
                hi = mid - 1;
            } else if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ans;
    }

    private static int rightmost(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;
        int ans = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) {
                ans = mid;
                lo = mid + 1;
            } else if (nums[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return ans;
    }

    // The slow answer to compare against: walks the whole array.
    public static int[] searchRangeSlow(int[] nums, int target) {
        int first = -1;
        int last = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                if (first == -1) {
                    first = i;
                }
                last = i;
            }
        }
        return new int[] {first, last};
    }

    public static void main(String[] args) {
        check(new int[] {5, 7, 7, 8, 8, 10}, 8, "[3, 4]");
        check(new int[] {5, 7, 7, 8, 8, 10}, 6, "[-1, -1]");
        check(new int[] {2, 2}, 2, "[0, 1]");
        check(new int[] {1}, 1, "[0, 0]");
        check(new int[] {}, 0, "[-1, -1]");
        check(new int[] {8, 8, 8, 8} /* every element is the target */, 8, "[0, 3]");
        check(new int[] {1, 2, 3} /* target at the left end */, 1, "[0, 0]");
        check(new int[] {1, 2, 3} /* target at the right end */, 3, "[2, 2]");
        check(new int[] {1, 2, 3} /* smaller than everything */, 0, "[-1, -1]");
        check(new int[] {1, 2, 3} /* bigger than everything */, 4, "[-1, -1]");

        randomTest(200);
    }

    private static void check(int[] nums, int target, String expected) {
        String fast = Arrays.toString(searchRange(nums, target));
        String slow = Arrays.toString(searchRangeSlow(nums, target));
        int any = binarySearch(nums, target);
        boolean anyOk = expected.equals("[-1, -1]") ? any == -1 : nums[any] == target;
        String result = expected.equals(fast) && expected.equals(slow) && anyOk ? "PASS" : "FAIL";
        System.out.println(result + "  " + Arrays.toString(nums) + ", " + target + "  expected: " + expected
                + "  binary twice: " + fast + "  slow: " + slow + "  plain binary search: " + any);
    }

    // Fixed seed so a failure can be reproduced by running the program again.
    private static void randomTest(int count) {
        Random random = new Random(42);
        int errors = 0;
        for (int t = 0; t < count; t++) {
            int[] nums = new int[random.nextInt(30)];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = random.nextInt(21) - 10;
            }
            Arrays.sort(nums);
            int target = random.nextInt(25) - 12;
            int[] fast = searchRange(nums, target);
            int[] slow = searchRangeSlow(nums, target);
            int any = binarySearch(nums, target);
            boolean anyOk = slow[0] == -1 ? any == -1 : any >= slow[0] && any <= slow[1];
            if (!Arrays.equals(fast, slow) || !anyOk) {
                errors++;
                System.out.println("ERROR  " + Arrays.toString(nums) + ", " + target + "  binary twice: "
                        + Arrays.toString(fast) + "  slow: " + Arrays.toString(slow) + "  plain: " + any);
            }
        }
        System.out.println();
        System.out.println((errors == 0 ? "PASS" : "FAIL") + "  " + count + " random sorted arrays, "
                + errors + " where binary search and the slow scan differ");
    }
}
