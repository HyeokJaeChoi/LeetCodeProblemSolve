/**
 * **Time Complexity: `O(n)`** where `n = number of nodes`
 * - Every node is visited exactly once via DFS
 *
 * **Space Complexity: `O(h)`** where `h = height of the tree`
 * - Recursion call stack depth equals the tree height
 * - Worst case (skewed tree): `O(n)`, balanced tree: `O(log n)`
 */
package day8

import kotlin.math.max

class MaximumDepthOfBinaryTree104 {
    companion object {
        class TreeNode(var `val`: Int) {
            var left: TreeNode? = null
            var right: TreeNode? = null
        }

        fun maxDepth(root: TreeNode?): Int {
            if (root == null) {
                return 0
            }

            var max = 1
            dfs(root, 1) {
                max = max(max, it)
            }

            return max
        }

        fun dfs(node: TreeNode?, count: Int, onMaxCountCheck: (Int) -> Unit) {
            if (node == null) {
                return
            }

            if (node.left != null) {
                dfs(node.left, count + 1, onMaxCountCheck)
            }
            if (node.right != null) {
                dfs(node.right, count + 1, onMaxCountCheck)
            }

            onMaxCountCheck(count)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            // Example 1: [3,9,20,null,null,15,7] -> 3
            val root1 = TreeNode(3).apply {
                left = TreeNode(9)
                right = TreeNode(20).apply {
                    left = TreeNode(15)
                    right = TreeNode(7)
                }
            }

            // Example 2: [1,null,2] -> 2
            val root2 = TreeNode(1).apply {
                right = TreeNode(2)
            }

            val testCases = listOf(root1, root2)
            val answers = listOf(3, 2)

            for (i in testCases.indices) {
                println("isPassed = ${maxDepth(testCases[i]) == answers[i]}")
            }
        }
    }
}