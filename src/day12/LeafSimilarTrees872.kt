/**
 * **Time Complexity: `O(n1 + n2)`** where `n1` and `n2` are the node counts of the two trees
 * - Each tree is traversed once via DFS to collect its leaf sequence
 * - Comparing the two leaf lists is linear in their combined length
 *
 * **Space Complexity: `O(n1 + n2)`**
 * - Two leaf lists store up to `O(n1)` and `O(n2)` leaf values
 * - DFS recursion stack is bounded by each tree's height, at most `O(n1 + n2)` in the skewed case
 */
package day12

class LeafSimilarTrees872 {
    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    companion object {
        fun leafSimilar(root1: TreeNode?, root2: TreeNode?): Boolean {
            fun dfs(node: TreeNode?, list: MutableList<Int>) {
                if (node != null && node.left == null && node.right == null) {
                    list.add(node.`val`)
                    return
                }

                if (node?.left != null) {
                    dfs(node.left, list)
                }
                if (node?.right != null) {
                    dfs(node.right, list)
                }
            }

            val root1List = mutableListOf<Int>()
            val root2List = mutableListOf<Int>()

            dfs(root1, root1List)
            dfs(root2, root2List)

            return root1List == root2List
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
            // Example 1: root1 = [3,5,1,6,2,9,8,null,null,7,4],
            //            root2 = [3,5,1,6,7,4,2,null,null,null,null,null,null,9,8] -> true
            val r1a = buildTree(listOf(3, 5, 1, 6, 2, 9, 8, null, null, 7, 4))
            val r1b = buildTree(
                listOf(3, 5, 1, 6, 7, 4, 2, null, null, null, null, null, null, 9, 8)
            )

            // Example 2: root1 = [1,2,3], root2 = [1,3,2] -> false
            val r2a = buildTree(listOf(1, 2, 3))
            val r2b = buildTree(listOf(1, 3, 2))

            // Example 3: root1 = [1], root2 = [1] -> true
            val r3a = buildTree(listOf(1))
            val r3b = buildTree(listOf(1))

            // Example 4: root1 = [1,2,3], root2 = [1,2,3] -> true (same leaves: 2,3)
            val r4a = buildTree(listOf(1, 2, 3))
            val r4b = buildTree(listOf(1, 2, 3))

            val testCases = listOf(
                r1a to r1b,
                r2a to r2b,
                r3a to r3b,
                r4a to r4b,
            )
            val answers = listOf(true, false, true, true)

            for (i in testCases.indices) {
                val (a, b) = testCases[i]
                val result = leafSimilar(a, b)
                println("isPassed = ${result == answers[i]} / $result")
            }
        }
    }
}