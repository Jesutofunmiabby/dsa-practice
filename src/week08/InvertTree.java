// Idea: at every node swap the left and right children, then do the same to each child, either by recursion or by visiting the nodes from a queue.
// Mistake: in my recursive version I wrote root.left = invert(root.right) before saving the old left, so the next line inverted the new left (the old right) a second time and the old left was lost; [4,2,7,1,3,6,9] came out as [4,7,7,9,9,9,9]. My queue version was already right.
package week08;

import java.util.ArrayDeque;
import java.util.Queue;

public class InvertTree {

    static TreeNode invert(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invert(root.left);
        TreeNode right = invert(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    static TreeNode invertWithQueue(TreeNode root) {
        if (root == null) {
            return null;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return root;
    }

    static void check(Integer[] input, String expected) {
        String recursive = TreeNode.show(invert(TreeNode.fromArray(input)));
        String withQueue = TreeNode.show(invertWithQueue(TreeNode.fromArray(input)));
        boolean ok = expected.equals(recursive) && expected.equals(withQueue);
        System.out.println((ok ? "PASS" : "FAIL") + "  " + TreeNode.show(TreeNode.fromArray(input))
                + "  expected " + expected + ", recursive " + recursive + ", queue " + withQueue);
    }

    public static void main(String[] args) {
        check(new Integer[] {4, 2, 7, 1, 3, 6, 9}, "[4,7,2,9,6,3,1]");
        check(new Integer[] {2, 1, 3}, "[2,3,1]");
        check(new Integer[] {1, 2}, "[1,null,2]");
        check(new Integer[] {1}, "[1]");
        check(new Integer[] {}, "[]");
    }
}
