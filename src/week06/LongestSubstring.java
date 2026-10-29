// Idea: slide a window over the string and, when a character repeats inside it, jump the left edge to just past where that character was last seen.
// Mistake: TODO
package week06;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstring {

    public static int lengthOfLongestSubstringBruteForce(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String must not be null");
        }
        int best = 0;
        for (int start = 0; start < s.length(); start++) {
            for (int end = start; end < s.length(); end++) {
                if (allUnique(s, start, end)) {
                    best = Math.max(best, end - start + 1);
                }
            }
        }
        return best;
    }

    private static boolean allUnique(String s, int start, int end) {
        Set<Character> seen = new HashSet<>();
        for (int i = start; i <= end; i++) {
            if (!seen.add(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String must not be null");
        }
        Map<Character, Integer> lastSeen = new HashMap<>();
        int left = 0;
        int best = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (lastSeen.containsKey(c) && lastSeen.get(c) >= left) {
                left = lastSeen.get(c) + 1;
            }
            lastSeen.put(c, right);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        check("\"abcabcbb\"", "abcabcbb", "3");
        check("\"bbbbb\" (all the same)", "bbbbb", "1");
        check("\"pwwkew\" (answer in the middle)", "pwwkew", "3");
        check("\"\" (empty)", "", "0");
        check("\"abba\" (old repeat left of the window)", "abba", "2");
        check("\"dvdf\"", "dvdf", "3");
        check("\"a\" (single character)", "a", "1");
        check("\"abcdef\" (no repeats)", "abcdef", "6");
        check("\"a b\" (space counts as a character)", "a b", "3");
        check("null", null, "exception");
    }

    private static void check(String label, String input, String expected) {
        String brute = run(input, true);
        String fast = run(input, false);
        String result = expected.equals(brute) && expected.equals(fast) ? "PASS" : "FAIL";
        System.out.println(result + "  " + label + "  expected: " + expected
                + "  brute: " + brute + "  sliding window: " + fast);
    }

    private static String run(String input, boolean brute) {
        try {
            return String.valueOf(brute ? lengthOfLongestSubstringBruteForce(input) : lengthOfLongestSubstring(input));
        } catch (IllegalArgumentException e) {
            return "exception";
        }
    }
}
