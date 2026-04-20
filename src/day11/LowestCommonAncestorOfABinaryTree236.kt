/**
 * **Status: `SOLVED with guidance`**
 * - Initial attempt used `return if (left != null && right != null) node else null`,
 *   which dropped the marker when only one side found a target — failed the ancestor-descendant case (p is an ancestor of q).
 * - Fixed by changing the fallback to `left ?: right` so a single non-null result propagates upward.
 *
 * **Core insight (Pure Return, conditional propagation)**
 * - `dfs(node)` returns a value with a **dual meaning**:
 *   (a) a marker — "this subtree contains at least one of p / q", or
 *   (b) the confirmed LCA — "both targets meet under this node".
 * - The caller does NOT need to distinguish the two. Correctness is guaranteed by:
 *   (1) problem precondition that p and q both exist in the tree,
 *   (2) invariant that once the LCA is fixed, every ancestor above sees only one non-null child and propagates it up unchanged.
 * - Self-hit short-circuit (`node === p || node === q → return node`) handles the ancestor-descendant case for free: if the other target lies below, the current node is already the LCA; if it lies elsewhere, the marker travels up until a common ancestor sees both sides non-null.
 *
 * **Recurrence (post-order combine)**
 * - `left == null && right == null → null` (neither target in this subtree)
 * - `left != null && right != null → node` (split point — this node is the LCA)
 * - `otherwise → left ?: right` (one side found something — propagate as marker or final LCA)
 *
 * **Time Complexity: `O(n)`** where `n = number of nodes`
 * - Every node is visited at most once; constant work per visit after children return.
 *
 * **Space Complexity: `O(h)`** where `h = tree height`
 * - Recursion stack bounded by tree height; worst case `O(n)` for a skewed tree.
 */
package day11

class LowestCommonAncestorOfABinaryTree236 {
    class TreeNode(var `val`: Int = 0) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    companion object {
        fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
            fun dfs(node: TreeNode?): TreeNode? {
                if (node == null) {
                    return null
                }

                if (node.`val` == p?.`val`) {
                    return node
                }

                if (node.`val` == q?.`val`) {
                    return node
                }

                val left = dfs(node.left)
                val right = dfs(node.right)

                return if (left != null && right != null) node else left ?: right
            }

            val answer = dfs(root)
            return answer
        }

        private fun buildTree(values: List<Int?>): TreeNode? {
            if (values.isEmpty() || values[0] == null) return null
            val root = TreeNode(values[0]!!)
            val queue = ArrayDeque<TreeNode>()
            queue.addLast(root)
            var i = 1
            while (queue.isNotEmpty() && i < values.size) {
                val node = queue.removeFirst()
                if (i < values.size) {
                    values[i]?.let {
                        val leftNode = TreeNode(it)
                        node.left = leftNode
                        queue.addLast(leftNode)
                    }
                    i++
                }
                if (i < values.size) {
                    values[i]?.let {
                        val rightNode = TreeNode(it)
                        node.right = rightNode
                        queue.addLast(rightNode)
                    }
                    i++
                }
            }
            return root
        }

        private fun findNode(root: TreeNode?, target: Int): TreeNode? {
            if (root == null) return null
            if (root.`val` == target) return root
            return findNode(root.left, target) ?: findNode(root.right, target)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            // Example 1: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1 -> 3
            val root1 = buildTree(listOf(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4))
            val p1 = findNode(root1, 5)
            val q1 = findNode(root1, 1)

            // Example 2: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4 -> 5
            val root2 = buildTree(listOf(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4))
            val p2 = findNode(root2, 5)
            val q2 = findNode(root2, 4)

            // Example 3: root = [1,2], p = 1, q = 2 -> 1
            val root3 = buildTree(listOf(1, 2))
            val p3 = findNode(root3, 1)
            val q3 = findNode(root3, 2)

            val testCases = listOf(
                Triple(root1, p1, q1),
                Triple(root2, p2, q2),
                Triple(root3, p3, q3),
            )
            val answers = listOf(3, 5, 1)

            for (i in testCases.indices) {
                val (root, p, q) = testCases[i]
                val result = lowestCommonAncestor(root, p, q)
                println("isPassed = ${result?.`val` == answers[i]} / ${result?.`val`}")
            }
        }
    }
}