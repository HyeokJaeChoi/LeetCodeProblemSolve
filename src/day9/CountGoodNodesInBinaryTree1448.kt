/**
 * **Time Complexity: `O(n)`** where `n = number of nodes`
 * - Every node is visited exactly once via DFS
 *
 * **Space Complexity: `O(h)`** where `h = height of the tree`
 * - Recursion call stack depth equals the tree height
 * - Worst case (skewed tree): `O(n)`, balanced tree: `O(log n)`
 */
package day9

import kotlin.math.max

class CountGoodNodesInBinaryTree1448 {
    companion object {
        class TreeNode(var `val`: Int) {
            var left: TreeNode? = null
            var right: TreeNode? = null
        }

        fun dfs(node: TreeNode?, maxValue: Int): Int {
            if (node == null) {
                return 0
            }

            val newMaxValue = max(node.`val`, maxValue)

            return (if (node.`val` >= maxValue) 1 else 0) + dfs(node.left, newMaxValue) + dfs(node.right, newMaxValue)
        }

        fun goodNodes(root: TreeNode?): Int {
            if (root == null) {
                return 0
            }

            return dfs(root, root.`val`)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            // Example 1: [3,1,4,3,null,1,5] -> 4
            val root1 = TreeNode(3).apply {
                left = TreeNode(1).apply {
                    left = TreeNode(3)
                }
                right = TreeNode(4).apply {
                    left = TreeNode(1)
                    right = TreeNode(5)
                }
            }

            // Example 2: [3,3,null,4,2] -> 3
            val root2 = TreeNode(3).apply {
                left = TreeNode(3).apply {
                    left = TreeNode(4)
                    right = TreeNode(2)
                }
            }

            // Example 3: [1] -> 1
            val root3 = TreeNode(1)

            val testCases = listOf(root1, root2, root3)
            val answers = listOf(4, 3, 1)

            for (i in testCases.indices) {
                println("isPassed = ${goodNodes(testCases[i]) == answers[i]}")
            }
        }
    }
}