/**
 * **Time Complexity: `O(n)`** where `n = candies.size`
 * - One pass to find the maximum candy count via `candies.max()` in `O(n)`
 * - One pass through each candy to check if `candy + extraCandies >= maxCandy` in `O(n)`
 *
 * **Space Complexity: `O(n)`**
 * - `result` list holds one boolean per kid, so `O(n)`
 */
package day2

class KidsWithTheGreatestNumberOfCandies1431 {
    companion object {
        fun kidsWithCandies(candies: IntArray, extraCandies: Int): List<Boolean> {
            val result = mutableListOf<Boolean>()
            val maxCandy = candies.max()

            for (candy in candies) {
                val maxToHaveCandies = candy + extraCandies

                if (maxToHaveCandies >= maxCandy) {
                    result.add(true)
                } else {
                    result.add(false)
                }
            }

            return result
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf<Pair<IntArray, Int>>(
                intArrayOf(2, 3, 5, 1, 3) to 3,
                intArrayOf(4, 2, 1, 1, 2) to 1,
                intArrayOf(12, 1, 12) to 10,
            )
            val answers = listOf<List<Boolean>>(
                listOf(true, true, true, false, true),
                listOf(true, false, false, false, false),
                listOf(true, false, true)
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${kidsWithCandies(testCase.first, testCase.second) == answer}")
            }
        }
    }
}
