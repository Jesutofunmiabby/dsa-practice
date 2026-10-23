// Pattern: frequency counting with an int[26] array (add for one word, subtract for the other).
// Time: O(n + m) - one pass over each string. Space: O(1) - the array is always 26 ints.
package week05;

public class IsAnagram {

    public static boolean isAnagram(String first, String second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Inputs must not be null");
        }

        int[] counts = new int[26];
        addCounts(first, counts, 1);
        addCounts(second, counts, -1);

        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    private static void addCounts(String text, int[] counts, int change) {
        for (int i = 0; i < text.length(); i++) {
            char c = Character.toLowerCase(text.charAt(i));
            if (c == ' ') {
                continue;
            }
            if (c < 'a' || c > 'z') {
                throw new IllegalArgumentException("Only letters a-z and spaces are allowed");
            }
            counts[c - 'a'] += change;
        }
    }

    public static void main(String[] args) {
        check("\"listen\", \"silent\"", "listen", "silent", "true");
        check("\"hello\", \"world\"", "hello", "world", "false");
        check("\"Dormitory\", \"Dirty room\" (case and spaces)", "Dormitory", "Dirty room", "true");
        check("\"aab\", \"abb\" (same letters, different counts)", "aab", "abb", "false");
        check("\"abc\", \"abcd\" (different lengths)", "abc", "abcd", "false");
        check("\"\", \"\" (both empty)", "", "", "true");
        check("\"\", \"a\" (one empty)", "", "a", "false");
        check("\"a\", \"A\" (single element)", "a", "A", "true");
        check("\"a\", \"b\" (single element)", "a", "b", "false");
        check("\"  \", \"\" (only spaces)", "  ", "", "true");
        check("null, \"abc\"", null, "abc", "exception");
        check("\"ab1\", \"1ab\" (non-letter)", "ab1", "1ab", "exception");
    }

    private static void check(String label, String first, String second, String expected) {
        String actual;
        try {
            actual = String.valueOf(isAnagram(first, second));
        } catch (IllegalArgumentException e) {
            actual = "exception";
        }
        String result = expected.equals(actual) ? "PASS" : "FAIL";
        System.out.println(result + "  " + label + "  expected: " + expected + "  actual: " + actual);
    }
}
