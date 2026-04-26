/**
 * **Time Complexity: `O(n + m)`** where `n = nums1.size` and `m = nums2.size`
 * - Building each hash set takes `O(n)` and `O(m)` respectively
 * - Iterating both arrays with `O(1)` set lookups and removals adds another `O(n + m)`
 *
 * **Space Complexity: `O(n + m)`**
 * - Two hash sets and two mutable result sets each hold up to `n` or `m` distinct elements
 */
package day15

class FindTheDifferenceOfTwoArrays2215 {
    companion object {
        fun findDifference(nums1: IntArray, nums2: IntArray): List<List<Int>> {
            val nums1Hash = nums1.toSet()
            val nums2Hash = nums2.toSet()
            val nums1List = nums1.toMutableSet()
            val nums2List = nums2.toMutableSet()

            for (num1 in nums1) {
                if (nums2Hash.contains(num1)) {
                    nums1List.remove(num1)
                }
            }
            for (num2 in nums2) {
                if (nums1Hash.contains(num2)) {
                    nums2List.remove(num2)
                }
            }

            return listOf(
                nums1List.toList(),
                nums2List.toList(),
            )
        }

        private fun isPassed(actual: List<List<Int>>, expected: List<List<Int>>): Boolean {
            if (actual.size != expected.size) return false
            for (i in actual.indices) {
                if (actual[i].toSet() != expected[i].toSet()) return false
            }
            return true
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                Triple(intArrayOf(1, 2, 3), intArrayOf(2, 4, 6), listOf(listOf(1, 3), listOf(4, 6))),
                Triple(intArrayOf(1, 2, 3, 3), intArrayOf(1, 1, 2, 2), listOf(listOf(3), emptyList())),
                Triple(intArrayOf(1, 1, 1), intArrayOf(1, 1, 1), listOf(emptyList(), emptyList())),
                Triple(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), listOf(listOf(1, 2, 3), listOf(4, 5, 6))),
                Triple(intArrayOf(-1, -2, -3), intArrayOf(-2, -3, -4), listOf(listOf(-1), listOf(-4))),
                Triple(intArrayOf(1), intArrayOf(2), listOf(listOf(1), listOf(2))),
                Triple(intArrayOf(1, 2, 2, 3, 3), intArrayOf(2, 3, 4), listOf(listOf(1), listOf(4))),
            )

            for ((idx, tc) in testCases.withIndex()) {
                val (nums1, nums2, expected) = tc
                val actual = findDifference(nums1, nums2)
                println("Test ${idx + 1}: actual=$actual, expected=$expected, isPassed=${isPassed(actual, expected)}")
            }
        }
    }
}