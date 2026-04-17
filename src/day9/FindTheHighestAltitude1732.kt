/**
 * **Time Complexity: `O(n)`** where `n = gain.size`
 * - Single pass through `gain` accumulating the running altitude
 *
 * **Space Complexity: `O(1)`**
 * - Only two scalar variables (`max`, `currentSum`) regardless of input size
 */
package day9

import kotlin.math.max

class FindTheHighestAltitude1732 {
    companion object {
        fun largestAltitude(gain: IntArray): Int {
            var max = 0
            var currentSum = 0

            for (item in gain) {
                currentSum += item
                max = max(max, currentSum)
            }

            return max
        }

        @JvmStatic
        fun main(args: Array<String>) {
            // Example 1: gain = [-5,1,5,0,-7] -> 1
            val gain1 = intArrayOf(-5, 1, 5, 0, -7)

            // Example 2: gain = [-4,-3,-2,-1,4,3,2] -> 0
            val gain2 = intArrayOf(-4, -3, -2, -1, 4, 3, 2)

            val testCases = listOf(gain1, gain2)
            val answers = listOf(1, 0)

            for (i in testCases.indices) {
                println("isPassed = ${largestAltitude(testCases[i]) == answers[i]}")
            }
        }
    }
}