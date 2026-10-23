// Pattern: frequency counting with a HashMap of word -> count, then one pass to pick the winner.
// Time: O(n) - n is the length of the text. Space: O(n) - the words and the map.
package week05;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MostFrequentWord {

    public static String mostFrequentWord(String text) {
        if (text == null) {
            throw new IllegalArgumentException("No words");
        }

        String cleaned = text.toLowerCase(Locale.ROOT).replaceAll("[.,!?]", " ");
        Map<String, Integer> counts = new HashMap<>();
        for (String word : cleaned.split("\\s+")) {
            if (!word.isEmpty()) {
                counts.put(word, counts.getOrDefault(word, 0) + 1);
            }
        }

        if (counts.isEmpty()) {
            throw new IllegalArgumentException("No words");
        }

        String best = null;
        int bestCount = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String word = entry.getKey();
            int count = entry.getValue();
            if (count > bestCount || (count == bestCount && word.compareTo(best) < 0)) {
                best = word;
                bestCount = count;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        check("\"the cat and the hat\"", "the cat and the hat", "the");
        check("\"Hello! hello? HELLO. world\" (case and punctuation)", "Hello! hello? HELLO. world", "hello");
        check("\"banana apple Banana Apple cherry\" (tie)", "banana apple Banana Apple cherry", "apple");
        check("\"dog cat\" (all tied once)", "dog cat", "cat");
        check("\"hi,there hi\" (punctuation between words)", "hi,there hi", "hi");
        check("\"  spaced   out   spaced  \" (extra spaces)", "  spaced   out   spaced  ", "spaced");
        check("\"word\" (single word)", "word", "word");
        check("\"\" (empty)", "", "exception");
        check("\"   \" (only spaces)", "   ", "exception");
        check("\"!?., !!\" (only punctuation)", "!?., !!", "exception");
        check("null", null, "exception");
    }

    private static void check(String label, String text, String expected) {
        String actual;
        try {
            actual = mostFrequentWord(text);
        } catch (IllegalArgumentException e) {
            actual = e.getMessage().equals("No words") ? "exception" : e.getMessage();
        }
        String result = expected.equals(actual) ? "PASS" : "FAIL";
        System.out.println(result + "  " + label + "  expected: " + expected + "  actual: " + actual);
    }
}
