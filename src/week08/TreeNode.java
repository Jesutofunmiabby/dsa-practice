package week08;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }

    static TreeNode fromArray(Integer... vals) {
        if (vals.length == 0 || vals[0] == null) return null;
        TreeNode root = new TreeNode(vals[0]);
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < vals.length) {
            TreeNode node = queue.poll();
            if (vals[i] != null) {
                node.left = new TreeNode(vals[i]);
                queue.add(node.left);
            }
            i++;
            if (i < vals.length && vals[i] != null) {
                node.right = new TreeNode(vals[i]);
                queue.add(node.right);
            }
            i++;
        }
        return root;
    }

    static String show(TreeNode root) {
        if (root == null) return "[]";
        List<String> out = new ArrayList<>();
        Queue<TreeNode> q = new LinkedList<>(); // allows nulls
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode n = q.poll();
            if (n == null) { out.add("null"); continue; }
            out.add(String.valueOf(n.val));
            q.add(n.left);
            q.add(n.right);
        }
        while (out.get(out.size() - 1).equals("null")) {
            out.remove(out.size() - 1);
        }
        return "[" + String.join(",", out) + "]";
    }

    public static void main(String[] args) {
        String result = show(fromArray(5, 1, 4, null, null, 3, 6));
        String expected = "[5,1,4,null,null,3,6]";
        System.out.println(result);
        System.out.println(result.equals(expected) ? "PASS" : "FAIL: expected " + expected);
    }
}
