/**
 * **Time Complexity: `O(n)`** where `n = height.size`
 * - Two pointers start at both ends and move inward, each index visited at most once
 * - The shorter side is moved inward (greedy choice), because keeping it can never produce a larger area
 *
 * **Space Complexity: `O(1)`**
 * - Only pointer variables (`left`, `right`) and a running maximum (`maxWaterCanContain`) are used
 */
package day7

import kotlin.math.min

class ContainerWithMostWater11 {
    companion object {
        fun maxArea(height: IntArray): Int {
            var left = 0
            var right = height.size - 1
            var maxWaterCanContain = 0

            while (left < right) {
                val leftSide = height[left]
                val rightSide = height[right]
                val currentWaterCanContain = min(leftSide, rightSide) * (right - left)

                if (currentWaterCanContain > maxWaterCanContain) {
                    maxWaterCanContain = currentWaterCanContain
                }
                if (leftSide <= rightSide) {
                    left++
                } else {
                    right--
                }
            }

            return maxWaterCanContain
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(1,8,6,2,5,4,8,3,7),
                intArrayOf(1,1),
            )
            val answers = listOf(
                49,
                1,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${maxArea(testCase) == answer}")
            }
        }
    }
}