// Idea: pass an allowed range (low, high) down the tree; going left the node's value becomes the new upper limit, going right it becomes the new lower limit, and null means "no limit".
// Mistake: I only compared each node with its own children, so [5,4,6,null,null,3,7] passed even though 3 sits in 5's right subtree and is smaller than 5; my version said true instead of false.
package week08;

public class ValidBST {

    static boolean isValidBST(TreeNode root) {
        return isValid(root, null, null);
    }

    static boolean isValid(TreeNode node, Integer low, Integer high) {
        if (node == null) {
            return true;
        }
        if (low != null && node.val <= low) {
            return false;
        }
        if (high != null && node.val >= high) {
            return false;
        }
        return isValid(node.left, low, node.val) && isValid(node.right, node.val, high);
    }

    static void check(Integer[] input, boolean expected) {
        boolean actual = isValidBST(TreeNode.fromArray(input));
        System.out.println((expected == actual ? "PASS" : "FAIL") + "  "
                + TreeNode.show(TreeNode.fromArray(input)) + "  expected " + expected + ", got " + actual);
    }

    public static void main(String[] args) {
        check(new Integer[] {2, 1, 3}, true);
        check(new Integer[] {5, 1, 4, null, null, 3, 6}, false);
        check(new Integer[] {5, 4, 6, null, null, 3, 7}, false);
        check(new Integer[] {1, 1}, false);
        check(new Integer[] {2147483647}, true);
        check(new Integer[] {}, true);
        // Extra edge cases: Integer.MIN_VALUE and MAX_VALUE as real children must still work.
        check(new Integer[] {0, -2147483648, 2147483647}, true);
        check(new Integer[] {-2147483648, null, -2147483648}, false);
    }
}
