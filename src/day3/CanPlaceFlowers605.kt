/**
 * **Time Complexity: `O(n)`** where `n = flowerbed.size`
 * - Single pass through the flowerbed array, each element checked in `O(1)`
 * - Early exit via `break` when all `n` flowers are placed
 *
 * **Space Complexity: `O(1)`**
 * - Only a single counter variable `flowers` is used; flowerbed is modified in-place
 */
package day3

class CanPlaceFlowers605 {
    companion object {
        fun canPlaceFlowers(flowerbed: IntArray, n: Int): Boolean {
            var flowers = n

            if (n == 0) {
                return true
            }

            if (flowerbed.size == 1) {
                return flowerbed[0] == 0
            }

            for (i in flowerbed.indices) {
                val flowerItem = flowerbed[i]

                when (i) {
                    0 -> {
                        if (flowerItem == 0 && flowerbed[1] == 0) {
                            flowerbed[i] = 1
                            flowers--
                        }
                    }

                    flowerbed.size - 1 -> {
                        if (flowerItem == 0 && flowerbed[i - 1] == 0) {
                            flowerbed[i] = 1
                            flowers--
                        }
                    }

                    else -> {
                        if (flowerItem == 0 && flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
                            flowerbed[i] = 1
                            flowers--
                        }
                    }
                }

                if (flowers == 0) {
                    break
                }
            }

            return flowers == 0
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(1, 0, 0, 0, 1) to 1,
                intArrayOf(1, 0, 0, 0, 1) to 2,
            )

            val answers = listOf(
                true,
                false,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${canPlaceFlowers(testCase.first, testCase.second) == answer}")
            }
        }
    }
}