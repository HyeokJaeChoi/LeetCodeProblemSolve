/**
 * **Status: `UNSOLVED`**
 * - Started with an include/exclude DFS and forgot to invoke the recursive helper at all,
 *   so the answer list remained empty.
 * - Even after wiring the recursion, lacked a strategy for suppressing duplicate combinations
 *   introduced by equal values in `candidates`; could not derive the "same slot, same value,
 *   skip" rule without guidance.
 *
 * **Core insight**
 * - After sorting, equal values become adjacent, which turns duplicate-combination suppression
 *   into a simple adjacent-element comparison.
 * - Switch to the for-loop + start-index skeleton so each recursion call represents filling
 *   one slot of the combination, and each loop iteration represents trying one candidate for
 *   that slot.
 * - Within a single recursion call (same slot), only try each distinct value once:
 *   `start > index && candidates[start] == candidates[start - 1]` → `continue`.
 *   The `start > index` guard is critical — the first iteration (`start == index`) is always
 *   allowed, so repeating a value across different slots (e.g. `[1, 1, 6]`) still works
 *   because the deeper recursion starts a fresh loop with a new `index`.
 * - Prune with `sum > target` and stop collecting once `sum == target`; each element is used
 *   at most once because the recursive call advances to `start + 1`, never reusing `start`.
 *
 * **Time Complexity: `O(2^n)`** where `n = candidates.size`
 * - Worst case explores every subset of `candidates` (each element is either chosen or not).
 * - `sum > target` pruning, sibling-duplicate skipping, and `start + 1` advancement reduce
 *   the effective search tree well below the upper bound, but the theoretical ceiling stays
 *   exponential in `n`.
 * - Per successful leaf, copying the current combination costs `O(n)`.
 *
 * **Space Complexity: `O(n)`** (excluding the output list)
 * - Recursion depth is bounded by `n` because each recursive call advances the index.
 * - Each include branch allocates a fresh list of length up to `n`.
 */
package day13

class CombinationSumTwo40 {
    companion object {
        fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
            val answer = mutableListOf<List<Int>>()
            candidates.sort()

            fun dfs(index: Int, current: List<Int>, sum: Int) {
                if (sum == target) {
                    answer.add(current.toList())
                    return
                }

                if (index >= candidates.size || sum > target) {
                    return
                }

                for (start in index..<candidates.size) {
                    if (start > index && candidates[start] == candidates[start - 1]) {
                        continue
                    }

                    val new = mutableListOf<Int>().apply {
                        addAll(current)
                        add(candidates[start])
                    }
                    dfs(start + 1, new, sum + candidates[start])
                    new.removeLast()
                }
            }

            dfs(0, emptyList(), 0)
            return answer
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(10, 1, 2, 7, 6, 1, 5) to 8,
                intArrayOf(2, 5, 2, 1, 2) to 5,
                intArrayOf(1) to 1,
                intArrayOf(1) to 2,
                intArrayOf(1, 1, 1, 1, 1) to 3,
                intArrayOf(2, 3, 5) to 1,
                intArrayOf(3, 1, 3, 5, 1, 1) to 8,
            )
            val answers = listOf(
                listOf(listOf(1, 1, 6), listOf(1, 2, 5), listOf(1, 7), listOf(2, 6)),
                listOf(listOf(1, 2, 2), listOf(5)),
                listOf(listOf(1)),
                listOf(),
                listOf(listOf(1, 1, 1)),
                listOf(),
                listOf(listOf(1, 1, 1, 5), listOf(1, 1, 3, 3), listOf(3, 5)),
            )

            fun normalize(list: List<List<Int>>): List<List<Int>> =
                list.map { it.sorted() }.sortedWith(compareBy({ it.size }, { it.toString() }))

            for (i in testCases.indices) {
                val (candidates, target) = testCases[i]
                val result = combinationSum2(candidates, target)
                val isPassed = normalize(result) == normalize(answers[i])
                println("isPassed = $isPassed / $result")
            }
        }
    }
}