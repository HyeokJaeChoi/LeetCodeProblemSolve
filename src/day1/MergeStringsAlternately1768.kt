/**
 * **Time Complexity: `O(max(n, m))`** where `n = word1.length`, `m = word2.length`
 * - The for loop iterates `max(n, m)` times, and each `StringBuilder.append()` call is `O(1)`
 *
 * **Space Complexity: `O(n + m)`**
 * - `StringBuilder(mergeWord)` holds all characters from both word1 and word2, so `O(n + m)`
 */
package day1

import kotlin.math.max

class MergeStringsAlternately1768 {
    companion object {
        fun mergeAlternately(word1: String, word2: String): String {
            val word1Length = word1.length
            val word2Length = word2.length

            val iterationCount = max(word1Length, word2Length)
            val mergeWord = StringBuilder()

            for (i in 0..<iterationCount) {
                if (i < word1Length) {
                    mergeWord.append(word1[i])
                }
                if (i < word2Length) {
                    mergeWord.append(word2[i])
                }
            }

            return mergeWord.toString()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf<Pair<String, String>>(
                "abc" to "pqr",
                "ab" to "pqrs",
                "abcd" to "pq",
            )
            val expectedAnswers = listOf<String>(
                "apbqcr",
                "apbqrs",
                "apbqcd",
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val expectedAnswer = expectedAnswers[i]

                val isPassed = expectedAnswer == mergeAlternately(testCase.first, testCase.second)
                println("isPassed = $isPassed")
            }
        }
    }
}
