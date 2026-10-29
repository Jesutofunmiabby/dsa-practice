// Idea: push every opening bracket onto a stack, and every closing bracket must match the bracket popped from the top.
// Mistake: TODO
package week06;

import java.util.ArrayDeque;
import java.util.Deque;

public class MatchingBrackets {

    public static boolean isValid(String s) {
        if (s == null) {
            throw new IllegalArgumentException("String must not be null");
        }
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char open = stack.pop();
                if (open != matchingOpen(c)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static char matchingOpen(char close) {
        return switch (close) {
            case ')' -> '(';
            case ']' -> '[';
            case '}' -> '{';
            default -> throw new IllegalArgumentException("Not a bracket: " + close);
        };
    }

    public static void main(String[] args) {
        check("\"()\"", "()", "true");
        check("\"()[]{}\"", "()[]{}", "true");
        check("\"{[]}\" (nested)", "{[]}", "true");
        check("\"(]\" (wrong type)", "(]", "false");
        check("\"([)]\" (wrong order)", "([)]", "false");
        check("\"((\" (never closed)", "((", "false");
        check("\"]\" (close with nothing open)", "]", "false");
        check("\"\" (empty)", "", "true");
        check("\"{[()()]}\" (deep nesting)", "{[()()]}", "true");
        check("\")(\" (close before open)", ")(", "false");
        check("\"(\" (single open)", "(", "false");
        check("null", null, "exception");
    }

    private static void check(String label, String input, String expected) {
        String actual;
        try {
            actual = String.valueOf(isValid(input));
        } catch (IllegalArgumentException e) {
            actual = "exception";
        }
        String result = expected.equals(actual) ? "PASS" : "FAIL";
        System.out.println(result + "  " + label + "  expected: " + expected + "  actual: " + actual);
    }
}
