package day8

import kotlin.math.max

class LongestSubArrayOfOnesAfterDeletingOneElement1493 {
    companion object {
        fun longestSubarray(nums: IntArray): Int {
            var left = 0
            var zeroCount = 0
            var answer = 0

            for (right in nums.indices) {
                if (nums[right] == 0) {
                    zeroCount++
                }

                while (zeroCount > 1) {
                    if (nums[left] == 0) {
                        zeroCount--
                    }

                    left++
                }

                answer = max(answer, right - left)
            }

            return answer
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(1, 1, 0, 1),
                intArrayOf(0, 1, 1, 1, 0, 1, 1, 0, 1),
                intArrayOf(1, 1, 1),
            )
            val answers = listOf(
                3,
                5,
                2,
            )

            for (i in testCases.indices) {
                val result = longestSubarray(testCases[i])
                println("isPassed = ${result == answers[i]}")
            }
        }
    }
}