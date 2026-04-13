/**
 * **Time Complexity: `O(n)`** where `n = nums.size`
 * - Initial window sum computed in `O(k)`, then each subsequent window slides by removing one element and adding one in `O(1)`
 * - Total iterations: `n - k + 1`, so overall `O(n)`
 *
 * **Space Complexity: `O(1)`**
 * - Only two running variables (`maxSum`, `prevSum`) are used; no additional arrays allocated
 */
package day7

import kotlin.math.max

class MaximumAverageSubarray643 {
    companion object {
        fun findMaxAverage(nums: IntArray, k: Int): Double {
            var maxSum = 0.0
            var prevSum = 0.0

            for (i in 0..(nums.size - k)) {
                if (i == 0) {
                    maxSum = nums.slice(0..<k).sum().toDouble()
                    prevSum = maxSum
                } else {
                    prevSum = prevSum - nums[i - 1] + nums[i + k - 1]
                    if (prevSum > maxSum) {
                        maxSum = prevSum
                    }
                }
            }

            return (maxSum / k)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(1,12,-5,-6,50,3) to 4,
                intArrayOf(5) to 1,
                intArrayOf(0,4,0,3,2) to 1,
                intArrayOf(4,2,1,3,3) to 2
            )
            val answers = listOf(
                12.75,
                5.0,
                4.0,
                3.0,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${findMaxAverage(testCase.first, testCase.second) == answer}")
            }
        }
    }
}