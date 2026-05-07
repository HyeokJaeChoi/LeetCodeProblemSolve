/**
 * **Time Complexity: `O(h)`** where `h` is the height of the BST (`O(log n)` balanced, `O(n)` skewed)
 * - Iterative descent compares the target with the current node and moves to one child per step
 * - Each step halves the search space on a balanced tree, terminating once the value is found or `null` is hit
 *
 * **Space Complexity: `O(1)`**
 * - Only a constant number of pointers (`target`, `answer`) are maintained; no recursion or auxiliary structure is used
 */
package day17

class SearchInABinarySearchTree700 {
    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    companion object {
        fun searchBST(root: TreeNode?, `val`: Int): TreeNode? {
            if (root == null) {
                return null
            }

            var target = root
            var answer: TreeNode? = null

            while (target != null) {
                if (target.`val` > `val`) {
                    target = target.left
                }
                else if (target.`val` < `val`) {
                    target = target.right
                }
                else {
                    answer = target
                    break
                }
            }

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

        private fun toLevelOrder(root: TreeNode?): List<Int?> {
            if (root == null) return emptyList()
            val result = mutableListOf<Int?>()
            val queue = ArrayDeque<TreeNode?>()
            queue.addLast(root)
            while (queue.isNotEmpty()) {
                val node = queue.removeFirst()
                if (node == null) {
                    result.add(null)
                } else {
                    result.add(node.`val`)
                    queue.addLast(node.left)
                    queue.addLast(node.right)
                }
            }
            while (result.isNotEmpty() && result.last() == null) {
                result.removeAt(result.size - 1)
            }
            return result
        }

        private fun isPassed(actual: TreeNode?, expected: List<Int?>): Boolean =
            toLevelOrder(actual) == expected

        @JvmStatic
        fun main(args: Array<String>) {
            data class TC(val tree: List<Int?>, val target: Int, val expected: List<Int?>)

            val testCases = listOf(
                TC(listOf(4, 2, 7, 1, 3), 2, listOf(2, 1, 3)),
                TC(listOf(4, 2, 7, 1, 3), 5, emptyList()),
                TC(listOf(1), 1, listOf(1)),
                TC(listOf(1), 2, emptyList()),
                TC(emptyList(), 1, emptyList()),
                TC(listOf(4, 2, 7, 1, 3), 7, listOf(7)),
                TC(listOf(4, 2, 7, 1, 3), 4, listOf(4, 2, 7, 1, 3)),
                TC(listOf(4, 2, 7, 1, 3), 1, listOf(1)),
                TC(listOf(4, 2, 7, 1, 3), 3, listOf(3)),
                TC(listOf(8, 3, 10, 1, 6, null, 14, null, null, 4, 7, 13), 6, listOf(6, 4, 7)),
                TC(listOf(8, 3, 10, 1, 6, null, 14, null, null, 4, 7, 13), 10, listOf(10, null, 14, 13)),
                TC(listOf(8, 3, 10, 1, 6, null, 14, null, null, 4, 7, 13), 99, emptyList()),
            )

            for ((idx, tc) in testCases.withIndex()) {
                val root = buildTree(tc.tree)
                val actual = searchBST(root, tc.target)
                println(
                    "Test ${idx + 1}: tree=${tc.tree}, target=${tc.target}, " +
                        "actual=${toLevelOrder(actual)}, expected=${tc.expected}, " +
                        "isPassed=${isPassed(actual, tc.expected)}"
                )
            }
        }
    }
}
