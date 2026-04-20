package day11;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LowestCommonAncestorOfABinaryTreeTwo1644 {
    Set<TreeNode> set = new HashSet<>();

    private TreeNode dfs(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) {
            return null;
        }

        TreeNode left = dfs(node.left, p, q);
        TreeNode right = dfs(node.right, p, q);

        if (node == p) {
            set.add(p);
            return node;
        }
        if (node == q) {
            set.add(q);
            return node;
        }

        if (left != null && right != null) {
            return node;
        }

        return left != null ? left : right;
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode answer = dfs(root, p, q);

        if (set.contains(p) && set.contains(q)) {
            return answer;
        }

        return null;
    }

    private static TreeNode buildTree(Integer[] values) {
        if (values.length == 0 || values[0] == null) return null;
        TreeNode root = new TreeNode(values[0]);
        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            if (i < values.length) {
                if (values[i] != null) {
                    node.left = new TreeNode(values[i]);
                    queue.add(node.left);
                }
                i++;
            }
            if (i < values.length) {
                if (values[i] != null) {
                    node.right = new TreeNode(values[i]);
                    queue.add(node.right);
                }
                i++;
            }
        }
        return root;
    }

    private static TreeNode findNode(TreeNode root, int target) {
        if (root == null) return null;
        if (root.val == target) return root;
        TreeNode l = findNode(root.left, target);
        return l != null ? l : findNode(root.right, target);
    }

    public static void main(String[] args) {
        // Example 1: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1 -> 3
        TreeNode root1 = buildTree(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        TreeNode p1 = findNode(root1, 5);
        TreeNode q1 = findNode(root1, 1);

        // Example 2 (p is ancestor of q): p = 5, q = 4 -> 5
        TreeNode root2 = buildTree(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        TreeNode p2 = findNode(root2, 5);
        TreeNode q2 = findNode(root2, 4);

        // Example 3 (q missing): p = 5, q = 10 (not in tree) -> null
        TreeNode root3 = buildTree(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        TreeNode p3 = findNode(root3, 5);
        TreeNode q3 = new TreeNode(10); // 트리에 없는 참조 (== 동일성에서 절대 매칭되지 않음)

        // Example 4 (both missing): p = 11, q = 12 -> null
        TreeNode root4 = buildTree(new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4});
        TreeNode p4 = new TreeNode(11);
        TreeNode q4 = new TreeNode(12);

        TreeNode[] roots = {root1, root2, root3, root4};
        TreeNode[] ps = {p1, p2, p3, p4};
        TreeNode[] qs = {q1, q2, q3, q4};
        Integer[] answers = {3, 5, null, null};

        for (int i = 0; i < roots.length; i++) {
            LowestCommonAncestorOfABinaryTreeTwo1644 solver = new LowestCommonAncestorOfABinaryTreeTwo1644();
            TreeNode result = solver.lowestCommonAncestor(roots[i], ps[i], qs[i]);
            Integer actual = (result == null) ? null : result.val;
            boolean isPassed = Objects.equals(actual, answers[i]);
            System.out.println("isPassed = " + isPassed + " / " + actual);
        }
    }
}
