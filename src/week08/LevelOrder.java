// Idea: breadth-first search with a queue; at the start of each round the queue holds exactly one level, so take that many nodes and add their children for the next round.
// Mistake: my for loop checked i < q.size() every time round, but q.size() kept changing as I polled and added children, so levels ran together; [3,9,20,null,null,15,7] gave [[3,9],[20,15],[7]] instead of [[3],[9,20],[15,7]].
package week08;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class LevelOrder {

    static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> levels = new ArrayList<>();
        if (root == null) {
            return levels;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            levels.add(level);
        }
        return levels;
    }

    static void check(Integer[] input, String expected) {
        String actual = levelOrder(TreeNode.fromArray(input)).toString().replace(" ", "");
        System.out.println((expected.equals(actual) ? "PASS" : "FAIL") + "  "
                + TreeNode.show(TreeNode.fromArray(input)) + "  expected " + expected + ", got " + actual);
    }

    public static void main(String[] args) {
        check(new Integer[] {3, 9, 20, null, null, 15, 7}, "[[3],[9,20],[15,7]]");
        check(new Integer[] {1, 2, 3, 4, null, null, 5}, "[[1],[2,3],[4,5]]");
        check(new Integer[] {1}, "[[1]]");
        check(new Integer[] {}, "[]");
    }
}
