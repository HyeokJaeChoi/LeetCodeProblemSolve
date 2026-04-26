/**
 * **Status: `UNSOLVED`**
 * - Tried to encode the choices with a hand-rolled DFS that tracked only `(start, end)` as a
 *   2-element pair, which collapsed the general "fill k slots" problem into a flat 2-pointer
 *   walk and could not generalize beyond `k = 2`.
 * - Missed the canonical for-loop + start-index backtracking skeleton, so failed to model
 *   "this recursion call fills one slot, each loop iteration tries one candidate for that slot".
 * - Did not arrive at the shared-mutable `path` + `add` / `removeAt` pattern; without explicit
 *   undo, the recursion cannot reuse a single buffer and the snapshot copy at the leaf becomes
 *   meaningless.
 *
 * **Core insight**
 * - One recursion frame == one slot of the combination. The `for i in start..n` loop enumerates
 *   the candidate placed in that slot.
 * - `start` is the duplicate killer: by never going back below the current floor, permutations
 *   like `[2, 1]` that are equivalent to `[1, 2]` are pruned at the source. Pass `i + 1`
 *   downward so the same element is never picked twice.
 * - Backtracking trio: `path.add(i)` → `backtrack(i + 1, ...)` → `path.removeAt(last)`. The
 *   single shared `path` acts as a stack, which is why `result.add(ArrayList(path))` must copy
 *   — pushing the live reference would let later `removeAt` calls mutate already-recorded
 *   answers.
 * - Termination is purely on `path.size == k`; no sum / target bookkeeping is needed because
 *   the search tree itself is shaped by the slot count.
 * - `for i in start..n` naturally prunes branches that cannot reach `k` elements: when
 *   `n - start + 1 < k - path.size`, the loop simply does not iterate enough times to ever hit
 *   the base case, so those subtrees die without producing output.
 *
 * **Time Complexity: `O(k * C(n, k))`** where `C(n, k) = n! / (k! * (n - k)!)`
 * - The recursion tree has exactly `C(n, k)` leaves, one per valid combination.
 * - Each leaf copies the current `path` of length `k` into `result`, costing `O(k)`.
 * - Internal node work is bounded by the same factor since every node sits on the path to some
 *   leaf and the per-node operations (`add`, `removeAt`) are `O(1)`.
 *
 * **Space Complexity: `O(k)`** (excluding the output list)
 * - Recursion depth is bounded by `k` because each call advances `start` and grows `path` by 1.
 * - The shared `path` buffer holds at most `k` elements at any time.
 */
package day14


class Combinations77 {
    companion object {
        fun combine(n: Int, k: Int): MutableList<MutableList<Int>> {
            val result: MutableList<MutableList<Int>> = ArrayList<MutableList<Int>>()
            backtrack(1, n, k, ArrayList<Int>(), result)
            return result
        }

        private fun backtrack(
            start: Int, n: Int, k: Int, path: MutableList<Int>, result: MutableList<MutableList<Int>>
        ) {
            if (path.size == k) {
                result.add(ArrayList<Int>(path))
                return
            }

            for (i in start..n) {
                path.add(i)
                backtrack(i + 1, n, k, path, result)
                path.removeAt(path.size - 1)
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                4 to 2,
                1 to 1,
                3 to 1,
                3 to 3,
                5 to 3,
                4 to 3,
                2 to 2,
            )
            val answers = listOf(
                listOf(listOf(1, 2), listOf(1, 3), listOf(1, 4), listOf(2, 3), listOf(2, 4), listOf(3, 4)),
                listOf(listOf(1)),
                listOf(listOf(1), listOf(2), listOf(3)),
                listOf(listOf(1, 2, 3)),
                listOf(
                    listOf(1, 2, 3), listOf(1, 2, 4), listOf(1, 2, 5),
                    listOf(1, 3, 4), listOf(1, 3, 5), listOf(1, 4, 5),
                    listOf(2, 3, 4), listOf(2, 3, 5), listOf(2, 4, 5),
                    listOf(3, 4, 5),
                ),
                listOf(listOf(1, 2, 3), listOf(1, 2, 4), listOf(1, 3, 4), listOf(2, 3, 4)),
                listOf(listOf(1, 2)),
            )

            fun normalize(list: List<List<Int>>): List<List<Int>> =
                list.map { it.sorted() }.sortedWith(compareBy({ it.size }, { it.toString() }))

            for (i in testCases.indices) {
                val (n, k) = testCases[i]
                val result = combine(n, k)
                val isPassed = normalize(result) == normalize(answers[i])
                println("isPassed = $isPassed / $result")
            }
        }
    }
}