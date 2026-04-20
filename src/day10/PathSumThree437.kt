/**
 * **Status: `UNSOLVED`**
 * - Recognized that the prefix sum trick from LeetCode 560 should extend from arrays to
 *   root-to-node paths in a tree, but could not derive the DFS with backtracking map
 *   without referring to the editorial.
 *
 * **Core insight**
 * - For any ancestor `A` and descendant `B` on the same root-to-leaf path,
 *   the downward path sum from `A`'s child to `B` equals `prefixSum(B) - prefixSum(A)`.
 * - Setting this equal to `targetSum` gives `prefixSum(A) = prefixSum(B) - targetSum`.
 * - While standing at `B` during DFS, count how many ancestors on the current path
 *   recorded a prefix sum of `currSum - targetSum`; each occurrence yields a distinct
 *   valid downward path ending at `B`.
 * - Seed the map with `{0L -> 1}` so paths that start from the root itself are counted.
 * - On leaving a node (backtrack), decrement its prefix sum entry so sibling subtrees
 *   do not observe prefix sums from nodes that are not their ancestors.
 * - Use `Long` for the running sum because node values up to `10^9` combined along a path
 *   of length `1000` can overflow `Int`.
 *
 * **Time Complexity: `O(n)`** where `n = number of nodes`
 * - Every node is visited exactly once; each map lookup and update is amortized `O(1)`.
 *
 * **Space Complexity: `O(n)`**
 * - Recursion stack is `O(h)` where `h` is tree height (worst case `O(n)` for a skewed tree),
 *   and the prefix sum map holds at most `O(h)` entries along the current DFS path.
 */
package day10

class PathSumThree437 {
    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    companion object {
        fun pathSum(root: TreeNode?, targetSum: Int): Int {
            var count = 0
            val prefixSumCount = HashMap<Long, Int>().apply { put(0L, 1) }

            fun dfs(node: TreeNode?, currSum: Long) {
                if (node == null) return

                val newSum = currSum + node.`val`

                count += prefixSumCount.getOrDefault(newSum - targetSum, 0)

                prefixSumCount[newSum] = prefixSumCount.getOrDefault(newSum, 0) + 1

                dfs(node.left, newSum)
                dfs(node.right, newSum)

                prefixSumCount[newSum] = prefixSumCount.getValue(newSum) - 1
            }

            dfs(root, 0L)
            return count
        }

        @JvmStatic
        fun main(args: Array<String>) {
            // Example 1: [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8 -> 3
            val root1 = TreeNode(10).apply {
                left = TreeNode(5).apply {
                    left = TreeNode(3).apply {
                        left = TreeNode(3)
                        right = TreeNode(-2)
                    }
                    right = TreeNode(2).apply {
                        right = TreeNode(1)
                    }
                }
                right = TreeNode(-3).apply {
                    right = TreeNode(11)
                }
            }

            // Example 2: [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22 -> 3
            val root2 = TreeNode(5).apply {
                left = TreeNode(4).apply {
                    left = TreeNode(11).apply {
                        left = TreeNode(7)
                        right = TreeNode(2)
                    }
                }
                right = TreeNode(8).apply {
                    left = TreeNode(13)
                    right = TreeNode(4).apply {
                        left = TreeNode(5)
                        right = TreeNode(1)
                    }
                }
            }

            // Example 3: empty tree, targetSum = 0 -> 0
            val root3: TreeNode? = null

            // Example 4: single node [1], targetSum = 1 -> 1
            val root4 = TreeNode(1)

            val testCases = listOf(
                root1 to 8,
                root2 to 22,
                root3 to 0,
                root4 to 1,
            )
            val answers = listOf(3, 3, 0, 1)

            for (i in testCases.indices) {
                val (root, targetSum) = testCases[i]
                println("isPassed = ${pathSum(root, targetSum) == answers[i]}")
            }
        }
    }
}
