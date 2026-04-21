/**
 * **Time Complexity: `O(n)`** where `n = nums.size`
 * - Two linear passes build the left and right prefix sum arrays in a single loop
 * - A final linear scan compares `leftPrefixSum[i - 1]` with `rightPrefixSum[i + 1]` to locate the pivot
 *
 * **Space Complexity: `O(n)`**
 * - Two auxiliary prefix sum arrays of length `n` are allocated
 */
package day12

class FindPivotIndex724 {
    companion object {
        fun pivotIndex(nums: IntArray): Int {
            if (nums.size == 1) {
                return 0
            }

            val leftPrefixSum = IntArray(nums.size) {
                0
            }
            val rightPrefixSum = IntArray(nums.size) {
                0
            }
            leftPrefixSum[0] = nums[0]
            rightPrefixSum[nums.size - 1] = nums[nums.size - 1]

            for (i in 1..<nums.size) {
                leftPrefixSum[i] = leftPrefixSum[i - 1] + nums[i]
                rightPrefixSum[nums.size - 1 - i] = rightPrefixSum[nums.size - i] + nums[nums.size - 1 - i]
            }

            for (i in rightPrefixSum.indices) {
                if (i == 0) {
                    if (rightPrefixSum[1] == 0) {
                        return 0
                    } else {
                        continue
                    }
                }

                if (i == rightPrefixSum.size - 1) {
                    if (leftPrefixSum[i - 1] == 0) {
                        return i
                    } else {
                        return -1
                    }
                }

                if (leftPrefixSum[i - 1] == rightPrefixSum[i + 1]) {
                    return i
                }
            }

            return -1
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(1, 7, 3, 6, 5, 6) to 3,
                intArrayOf(1, 2, 3) to -1,
                intArrayOf(2, 1, -1) to 0,
                intArrayOf(-1, -1, -1, -1, -1, 0) to 2,
                intArrayOf(1) to 0,
                intArrayOf(1, -1, 0) to 2,
                intArrayOf(0, 0, 0, 0) to 0,
                intArrayOf(-1, -1, 0, 1, 1, 0) to 5,
                intArrayOf(-1, -1, 1, 1, 0, 0) to 4,
            )
            val answers = listOf(
                3,
                -1,
                0,
                2,
                0,
                2,
                0,
                5,
                4,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]
                val result = pivotIndex(testCase.first)
                println("isPassed = ${result == answer} / $result")
            }
        }
    }
}