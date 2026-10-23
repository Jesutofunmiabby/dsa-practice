// Pattern: single pass keeping two running values (largest and second largest).
// Time: O(n) - one loop over the array. Space: O(1) - only a few variables.
package week05;

public class SecondLargest {

    public static int secondLargest(int[] numbers) {
        if (numbers == null || numbers.length < 2) {
            throw new IllegalArgumentException("Need at least two distinct values");
        }

        int largest = numbers[0];
        int second = 0;
        boolean found = false;

        for (int i = 1; i < numbers.length; i++) {
            int x = numbers[i];
            if (x > largest) {
                second = largest;
                largest = x;
                found = true;
            } else if (x < largest && (!found || x > second)) {
                second = x;
                found = true;
            }
        }

        if (!found) {
            throw new IllegalArgumentException("Need at least two distinct values");
        }
        return second;
    }

    public static void main(String[] args) {
        check("[3, 7, 2, 9, 5]", new int[] {3, 7, 2, 9, 5}, "7");
        check("[5, 5, 3]", new int[] {5, 5, 3}, "3");
        check("[10, 20]", new int[] {10, 20}, "10");
        check("[-4, -1, -7]", new int[] {-4, -1, -7}, "-4");
        check("[9, 1, 9, 8]", new int[] {9, 1, 9, 8}, "8");
        check("[MIN_VALUE, 5]", new int[] {Integer.MIN_VALUE, 5}, String.valueOf(Integer.MIN_VALUE));
        check("[] (empty)", new int[] {}, "exception");
        check("[4] (single element)", new int[] {4}, "exception");
        check("[7, 7, 7] (no distinct second)", new int[] {7, 7, 7}, "exception");
        check("null", null, "exception");
    }

    private static void check(String label, int[] input, String expected) {
        String actual;
        try {
            actual = String.valueOf(secondLargest(input));
        } catch (IllegalArgumentException e) {
            actual = e.getMessage().equals("Need at least two distinct values") ? "exception" : e.getMessage();
        }
        String result = expected.equals(actual) ? "PASS" : "FAIL";
        System.out.println(result + "  " + label + "  expected: " + expected + "  actual: " + actual);
    }
}
