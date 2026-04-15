/**
 * **Time Complexity: `O(n)`** where `n = s.length`
 * - Initial window of size `k` is computed in `O(k)`, then each subsequent window slides by removing one character and adding one in `O(1)`
 * - Total iterations: `n - k + 1`, so overall `O(n)`
 *
 * **Space Complexity: `O(1)`**
 * - Only two counter variables (`maxVowelsInWindows`, `currentVowelsInWindows`) are used
 */
package day8

class MaximumNumberOfVowelsInASubStringOfGivenLength1456 {
    companion object {
        fun Char.isVowel(): Boolean {
            return this == 'a' || this == 'e' || this == 'i' || this == 'o' || this == 'u'
        }

        fun maxVowels(s: String, k: Int): Int {
            var maxVowelsInWindows = 0
            var currentVowelsInWindows = 0

            for (i in 0..s.length - k) {
                if (i ==0) {
                    for (j in 0..<k) {
                        val char = s[j]

                        if (char.isVowel()) {
                            currentVowelsInWindows++
                        }
                    }
                    maxVowelsInWindows = currentVowelsInWindows
                } else {
                    val toBeRemoved = s[i - 1]
                    val toBeAppended = s[i + k - 1]
                    currentVowelsInWindows = currentVowelsInWindows - (if (toBeRemoved.isVowel()) 1 else 0) + (if (toBeAppended.isVowel()) 1 else 0)

                    if (currentVowelsInWindows > maxVowelsInWindows) {
                        maxVowelsInWindows = currentVowelsInWindows
                    }
                }
            }

            return maxVowelsInWindows
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                "abciiidef" to 3,
                "aeiou" to 2,
                "leetcode" to 3,
                "weallloveyou" to 7,
            )
            val answers = listOf(
                3,
                2,
                2,
                4,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${maxVowels(testCase.first, testCase.second) == answer}")
            }
        }
    }
}