/**
 * **Time Complexity: `O(n)`** where `n = nums.size`
 * - Single pass with two pointers: `right` scans every element, `left` tracks the next non-zero position
 * - Each element is visited exactly once and swapped at most once
 *
 * **Space Complexity: `O(1)`**
 * - In-place swap using only a `temp` variable and two pointer indices
 */
package day4

class MoveZeroes283 {
    companion object {
        fun moveZeroes(nums: IntArray) {
            var left = 0

            for (right in nums.indices) {
                if (nums[right] != 0) {
                    val temp = nums[right]
                    nums[right] = nums[left]
                    nums[left] = temp
                    left++
                }
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(0,1,0,3,12),
                intArrayOf(0),
                intArrayOf(1,0)
            )

            for (testCase in testCases) {
                moveZeroes(testCase)
                println("isPassed = ${testCase.joinToString(separator = ", ")}")
            }
        }
    }
}