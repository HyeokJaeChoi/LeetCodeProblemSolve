/**
 * **Time Complexity: `O(N ^ (T / M))`** where `N = candidates.size`, `T = target`, `M = min(candidates)`
 * - Each recursion node branches twice: reuse the current candidate (stay at `index`) or skip to `index + 1`
 * - The include chain is bounded by `T / M` since the smallest candidate caps depth before the sum exceeds the target
 * - Every terminal combination copies the current path into the answer list, costing `O(T / M)` per hit
 *
 * **Space Complexity: `O(T / M)`** (excluding the output list)
 * - Recursion stack depth is bounded by the path length `T / M`
 * - `newCandidates` allocates a fresh list per include branch, each of length at most `T / M`
 */
package day13

class CombinationSum39 {
    companion object {
        fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
            val answer = mutableListOf<List<Int>>()
            fun dfs(index: Int, current: List<Int>, sum: Int) {
                if (sum == target) {
                    answer.add(current.toList())
                    return
                }

                if (index >= candidates.size || sum > target) {
                    return
                }

                val newCandidates = mutableListOf<Int>().apply {
                    addAll(current)
                    add(candidates[index])
                }
                dfs(index, newCandidates, sum + candidates[index])
                newCandidates.removeLast()
                dfs(index + 1, newCandidates, sum)
            }
            dfs(0, emptyList(), 0)

            return answer
        }

        fun combinationSumUsingDP(candidates: IntArray, target: Int): List<List<Int>> {
            val dp = Array(target + 1) { mutableListOf<List<Int>>() }
            dp[0].add(emptyList())

            for (c in candidates) {
                for (s in c..target) {
                    for (prev in dp[s - c]) {
                        dp[s].add(prev + c)
                    }
                }
            }
            return dp[target]
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(2, 3, 6, 7) to 7,
                intArrayOf(2, 3, 5) to 8,
                intArrayOf(2) to 1,
                intArrayOf(1) to 1,
                intArrayOf(1) to 2,
                intArrayOf(2, 3, 5) to 2,
                intArrayOf(8, 7, 4, 3) to 11,
            )
            val answers = listOf(
                listOf(listOf(2, 2, 3), listOf(7)),
                listOf(listOf(2, 2, 2, 2), listOf(2, 3, 3), listOf(3, 5)),
                listOf(),
                listOf(listOf(1)),
                listOf(listOf(1, 1)),
                listOf(listOf(2)),
                listOf(listOf(3, 4, 4), listOf(3, 8), listOf(4, 7)),
            )

            fun normalize(list: List<List<Int>>): List<List<Int>> =
                list.map { it.sorted() }.sortedWith(compareBy({ it.size }, { it.toString() }))

            for (i in testCases.indices) {
                val (candidates, target) = testCases[i]
                val result = combinationSum(candidates, target)
                val isPassed = normalize(result) == normalize(answers[i])
                println("isPassed = $isPassed / $result")
            }
        }
    }
}