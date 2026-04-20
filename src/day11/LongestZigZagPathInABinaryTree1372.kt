/**
 * **Status: `UNSOLVED`**
 * - Tried multiple top-down formulations with a `direction` flag and single `currentSum`,
 *   but every variant either over-counted by teleporting across non-zigzag edges or
 *   under-counted by dropping the edge just taken.
 * - Could not arrive at the two-parameter formulation without guidance.
 *
 * **Core insight**
 * - Push two independent counters down the tree via DFS:
 *   `left`  = length of the ZigZag path that arrived at this node via a LEFT edge,
 *   `right` = length of the ZigZag path that arrived at this node via a RIGHT edge.
 * - Recurrence (the next edge of a ZigZag must alternate direction):
 *   going to `node.left`  → child receives `(right + 1, 0)` (continue if previous was RIGHT, else fresh)
 *   going to `node.right` → child receives `(0, left + 1)` (continue if previous was LEFT, else fresh)
 * - The longest ZigZag can begin at any node, so the running `max` must be updated at
 *   every visit.
 *
 * **Time Complexity: `O(n)`** where `n = number of nodes`
 * - Every node is visited exactly once in a single DFS; constant work per visit.
 *
 * **Space Complexity: `O(h)`** where `h = tree height`
 * - Recursion stack bounded by the tree height; worst case `O(n)` for a skewed tree.
 */
package day11

import kotlin.math.max

class LongestZigZagPathInABinaryTree1372 {
    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    companion object {
        fun longestZigZag(root: TreeNode?): Int {
            if (root == null) {
                return 0
            }

            var maxLen = 0

            fun dfs(node: TreeNode?, left: Int, right: Int) {
                if (node == null) {
                    return
                }

                maxLen = max(maxLen, max(left, right))

                dfs(node.left, right + 1, 0)
                dfs(node.right, 0, left + 1)
            }

            dfs(root, 0, 0)

            return maxLen
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

        @JvmStatic
        fun main(args: Array<String>) {
            // Example 1: [1,null,1,1,1,null,null,1,1,null,1,null,null,null,1,null,1] -> 3
            val root1 = buildTree(
                listOf(1, null, 1, 1, 1, null, null, 1, 1, null, 1, null, null, null, 1, null, 1)
            )

            // Example 2: [1,1,1,null,1,null,null,1,1,null,1] -> 4
            val root2 = buildTree(
                listOf(1, 1, 1, null, 1, null, null, 1, 1, null, 1)
            )

            // Example 3: [1] -> 0
            val root3 = buildTree(listOf(1))

            val testCases = listOf(root1, root2, root3)
            val answers = listOf(3, 4, 0)

            for (i in testCases.indices) {
                val result = longestZigZag(testCases[i])
                println("isPassed = ${result == answers[i]}")
            }
        }
    }
}
