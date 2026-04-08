/**
 * **Time Complexity: `O(n³)`** where `n = nums.size`
 * - Brute force: try every combination of three indices (i < j < k) and check if nums[i] < nums[j] < nums[k]
 * - Three nested loops each iterate up to `n` times in the worst case
 * - Early termination when a valid triplet is found, but worst case remains `O(n³)`
 *
 * **Space Complexity: `O(1)`**
 * - Only three local variables (`first`, `second`, `third`) are used
 *
 *
 * IMPROVED.
 * **Time Complexity: `O(n)`** where `n = nums.size`
 * - Greedy approach: scan the array once while greedily tracking the smallest (`first`) and second smallest (`second`) values seen so far
 * - Updating `first` and `second` to the smallest possible values never invalidates an already-found pair,
 *   because `second` having a finite value guarantees that a number smaller than `second` existed before it
 * - As soon as a number larger than both `first` and `second` is found, a valid increasing triplet exists
 * - Single pass through the array, each element compared against `first` and `second` in `O(1)`
 *
 * **Space Complexity: `O(1)`**
 * - Only two tracking variables (`first`, `second`) are used
 */
package day4

class IncreasingTripletSubsequence334 {
    companion object {
        fun increasingTriplet(nums: IntArray): Boolean {
            if (nums.size < 3) {
                return false
            }

            val min = nums.min()
            val max = nums.max()

            if (max - min < 2) {
                return false
            }

            for (i in 0..<nums.size - 2) {
                val first = nums[i]

                for (j in (i+1)..<nums.size - 1) {
                    val second = nums[j]

                    if (first - second < 0) {
                        for (k in (j+1..<nums.size)) {
                            val third = nums[k]

                            if (second - third < 0) {
                                return true
                            }
                        }
                    }
                }
            }

            return false
        }

        fun increasingTripletWithTimeComplexityN(nums: IntArray): Boolean {
            var first = Int.MAX_VALUE
            var second = Int.MAX_VALUE

            for (num in nums) {
                when {
                    num <= first -> first = num
                    num <= second -> second = num
                    else -> return true
                }
            }

            return false
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(1,2,3,4,5),
                intArrayOf(5,4,3,2,1),
                intArrayOf(2,1,5,0,4,6)
            )

            val answers = listOf(
                true,
                false,
                true,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${increasingTripletWithTimeComplexityN(testCase) == answer}")
            }
        }
    }
}