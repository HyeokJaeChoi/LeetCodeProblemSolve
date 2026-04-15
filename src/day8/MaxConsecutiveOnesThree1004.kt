/**
 * UNSOLVED
 *
 * ## Approach: Sliding Window
 *
 * The key insight is to reframe the problem:
 * "flip at most k zeros" == "a window may contain at most k zeros"
 *
 * Two pointers define a window [left, right].
 * - `right` always moves forward, expanding the window one element at a time.
 * - `left` advances only when the window becomes invalid (curr > k).
 * Both pointers move strictly forward, so the total time is O(n).
 *
 * ## Variables
 * - `left`  : left boundary of the sliding window
 * - `curr`  : number of 0s currently inside the window [left, right]
 *             A window is valid when curr <= k.
 * - `ans`   : maximum valid window size seen so far
 *
 * ## Loop logic
 * 1. Expand: move `right` forward.
 *    - If nums[right] == 0 → curr++ (one more zero entered the window)
 *    - If nums[right] == 1 → curr unchanged
 * 2. Shrink: while curr > k, shrink from the left.
 *    - If nums[left] == 0 → curr-- (that zero leaves the window)
 *    - Always left++ to move the boundary forward.
 *    (`while` instead of `if` is the canonical sliding window pattern,
 *    though here right moves one step at a time so curr exceeds k by at most 1.)
 * 3. Update: after the window is valid again,
 *    ans = max(ans, right - left + 1)
 *    (+1 converts the index difference to a length)
 *
 * **Time Complexity: `O(n)`** where `n = nums.length`
 * - `right` and `left` each traverse the array at most once (both move strictly forward)
 *
 * **Space Complexity: `O(1)`**
 * - Only three integer variables (`left`, `curr`, `ans`) are used; no extra data structures
 */
package day8

import kotlin.math.max

class MaxConsecutiveOnesThree1004 {
    companion object {
        fun longestOnes(nums: IntArray, k: Int): Int {
            var left = 0
            var curr = 0
            var ans = 0
            for ((right, element) in nums.withIndex()) {
                if (element == 0) {
                    curr++
                }

                while (curr > k) {
                    if (nums[left] == 0) {
                        curr--
                    }

                    left++
                }

                ans = max(ans, right - left + 1)
            }

            return ans
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(1,1,1,0,0,0,1,1,1,1,0) to 2,
                intArrayOf(0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1) to 3,
            )
            val answers = listOf(
                6,
                10,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${longestOnes(testCase.first, testCase.second) == answer}")
            }
        }
    }
}