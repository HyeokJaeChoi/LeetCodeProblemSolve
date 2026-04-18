/**
 * **Status: `UNSOLVED`**
 * - Reached the idea of using prefix sum independently,
 *   but could not derive the hash map reformulation without guidance.
 *
 * **Core insight**
 * - Subarray sum `[i..j]` equals `prefix[j] - prefix[i-1]`.
 * - Setting this equal to `k` and isolating the unknown gives `prefix[i-1] = prefix[j] - k`.
 * - While standing at `j`, count how many past prefix sums equal `prefix[j] - k`;
 *   each occurrence corresponds to a distinct valid subarray ending at `j`.
 * - Seed the map with `{0 -> 1}` so subarrays starting at index `0` are counted naturally.
 *
 * **Time Complexity: `O(n)`** where `n = nums.size`
 * - Single pass over the array; each hash map lookup and update is amortized `O(1)`.
 *
 * **Space Complexity: `O(n)`**
 * - Hash map stores up to `n + 1` distinct prefix sum entries in the worst case.
 */
package day10


class SubarraySumEqualsK560 {
    companion object {
        fun subarraySum(nums: IntArray, k: Int): Int {
            var count = 0
            var sum = 0
            val map = mutableMapOf<Int, Int>().apply {
                put(0, 1)
            }

            for (i in nums.indices) {
                sum += nums[i]
                if (map.containsKey(sum - k)) count += map.getOrDefault(sum - k, 0)
                map[sum] = map.getOrDefault(sum, 0) + 1
            }
            return count
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(1, 1, 1) to 2,
                intArrayOf(1, 2, 3) to 3,
                intArrayOf(1, -1, 0) to 0,
                intArrayOf(3, 4, 7, 2, -3, 1, 4, 2) to 7,
                intArrayOf(1) to 0,
            )
            val answers = listOf(
                2,
                2,
                3,
                4,
                0,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${subarraySum(testCase.first, testCase.second) == answer}")
            }
        }
    }
}