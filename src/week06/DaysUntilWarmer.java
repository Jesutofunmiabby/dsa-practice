// Idea: keep a stack of days still waiting for a warmer day, and when a warmer day arrives, pop and answer every colder day on top.
// Mistake: my first try compared the temperature with an index on the stack, and then I used if instead of while, so one warm day only settled one waiting day: [60,50,40,70] gave [0,0,1,0] instead of [3,2,1,0].
package week06;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class DaysUntilWarmer {

    public static int[] daysUntilWarmerBruteForce(int[] temps) {
        if (temps == null) {
            throw new IllegalArgumentException("Temperatures must not be null");
        }
        int[] answer = new int[temps.length];
        for (int i = 0; i < temps.length; i++) {
            for (int j = i + 1; j < temps.length; j++) {
                if (temps[j] > temps[i]) {
                    answer[i] = j - i;
                    break;
                }
            }
        }
        return answer;
    }

    public static int[] daysUntilWarmer(int[] temps) {
        if (temps == null) {
            throw new IllegalArgumentException("Temperatures must not be null");
        }
        int[] answer = new int[temps.length];
        Deque<Integer> waiting = new ArrayDeque<>();
        for (int i = 0; i < temps.length; i++) {
            while (!waiting.isEmpty() && temps[i] > temps[waiting.peek()]) {
                int day = waiting.pop();
                answer[day] = i - day;
            }
            waiting.push(i);
        }
        return answer;
    }

    public static void main(String[] args) {
        check("[73, 74, 75, 71, 69, 72, 76, 73]", new int[] {73, 74, 75, 71, 69, 72, 76, 73}, "[1, 1, 4, 2, 1, 1, 0, 0]");
        check("[30, 40, 50, 60] (always warmer)", new int[] {30, 40, 50, 60}, "[1, 1, 1, 0]");
        check("[30, 60, 90]", new int[] {30, 60, 90}, "[1, 1, 0]");
        check("[90, 80, 70] (never warmer)", new int[] {90, 80, 70}, "[0, 0, 0]");
        check("[] (empty)", new int[] {}, "[]");
        check("[50] (single day)", new int[] {50}, "[0]");
        check("[70, 70, 70] (equal is not warmer)", new int[] {70, 70, 70}, "[0, 0, 0]");
        check("[60, 50, 40, 70] (one day answers many)", new int[] {60, 50, 40, 70}, "[3, 2, 1, 0]");
        check("null", null, "exception");
    }

    private static void check(String label, int[] input, String expected) {
        String brute = run(input, true);
        String fast = run(input, false);
        String result = expected.equals(brute) && expected.equals(fast) ? "PASS" : "FAIL";
        System.out.println(result + "  " + label + "  expected: " + expected
                + "  brute: " + brute + "  stack: " + fast);
    }

    private static String run(int[] input, boolean brute) {
        try {
            return Arrays.toString(brute ? daysUntilWarmerBruteForce(input) : daysUntilWarmer(input));
        } catch (IllegalArgumentException e) {
            return "exception";
        }
    }
}
