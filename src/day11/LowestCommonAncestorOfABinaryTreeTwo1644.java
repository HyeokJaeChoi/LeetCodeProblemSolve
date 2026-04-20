/**
 * ## Approach: Post-order DFS + Existence Set
 *
 * Variant of LC 236 where `p` or `q` may be missing from the tree.
 * A pure LC 236 solution is unsafe: its pre-order short-circuit
 * (`if (node == p || node == q) return node`) returns as soon as one
 * target is hit, without proving the other target actually exists.
 *
 * ## Key Idea
 * - Recurse fully into both children **before** checking `node == p` / `node == q`.
 *   Post-order placement guarantees the subtree is explored even when the
 *   current node is one of the targets, so a missing counterpart cannot be
 *   masked by an early return.
 * - A shared `Set<TreeNode>` records every hit on `p` or `q`. After dfs
 *   finishes, the candidate LCA is accepted only if both targets were seen.
 *
 * ## Variables
 * - `set`    : records which of `{p, q}` were actually encountered in the tree
 * - `left`   : LCA candidate from the left subtree (or `null`)
 * - `right`  : LCA candidate from the right subtree (or `null`)
 * - `answer` : LCA candidate returned by dfs; validated against `set`
 *
 * ## Recurrence
 * 1. Base: `node == null` → return `null`.
 * 2. Recurse left and right first (post-order).
 * 3. If `node == p` or `node == q`, record it in `set` and return `node`
 *    (self is a valid ancestor of itself when the other target lies in its subtree).
 * 4. Else if both `left` and `right` are non-null → `node` is the split point → return `node`.
 * 5. Else propagate the non-null side upward (`left != null ? left : right`).
 * 6. After dfs: return `answer` only if `set` contains both `p` and `q`; otherwise `null`.
 *
 * ## Why Post-order (vs LC 236's Pre-order short-circuit)
 * LC 236 assumes both targets exist, so hitting one is sufficient evidence
 * of its subtree's relevance. LC 1644 has no such guarantee — existence
 * must be *proven*, which requires continuing the traversal past a self-hit.
 *
 * **Time Complexity: `O(n)`** where `n = number of nodes`
 * - Each node is visited exactly once by dfs
 *
 * **Space Complexity: `O(n)`**
 * - Recursion stack up to `O(h)` (worst-case `O(n)` on a skewed tree)
 * - `set` stores at most 2 entries (`p`, `q`), i.e., `O(1)` auxiliary
 */
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
