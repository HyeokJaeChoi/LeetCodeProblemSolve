/**
 * **Time Complexity: `O((n + m) * min(n, m))`** where `n = str1.length`, `m = str2.length`
 * - The while loop works similarly to the Euclidean algorithm, repeatedly removing the shorter string's length from the longer one
 * - Total removal count is `O((n + m) / min(n, m))`, and each iteration performs `substring` + string comparison in `O(min(n, m))`, giving `O(n + m)` overall
 * - However, `StringBuilder.delete(0, k)` internally shifts remaining characters at `O(current length)` cost,
 *   leading to worst-case `O((n + m) * min(n, m))`
 *
 * **Space Complexity: `O(n + m)`**
 * - Two `StringBuilder`s (`str1Builder`, `str2Builder`) each copy the original strings, so `O(n + m)`
 * - Temporary strings like `modString` and `prefix` are `O(min(n, m))`, dominated by `O(n + m)`
 */
package Day1

class GreatestCommonDivisorOfStrings1071 {
    companion object {
        fun gcdOfStrings(str1: String, str2: String): String {
            val str1Builder = StringBuilder().append(str1)
            val str2Builder = StringBuilder().append(str2)

            while (str1Builder.isNotEmpty() && str2Builder.isNotEmpty()) {
                val targetBuilder = if (str1Builder.length > str2Builder.length) str1Builder else str2Builder
                val modString = if (str1Builder.length > str2Builder.length) str2Builder.toString() else str1Builder.toString()
                val prefix = targetBuilder.substring(0, modString.length)

                if (prefix != modString) {
                    return ""
                }

                targetBuilder.delete(0, prefix.length)
            }

            return (str1Builder.ifEmpty { str2Builder }).toString()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf<Pair<String, String>>(
                "ABCABC" to "ABC",
                "ABABAB" to "ABAB",
                "LEET" to "CODE",
                "AAAAAB" to "AAA",
            )
            val expectedAnswers = listOf<String>(
                "ABC",
                "AB",
                "",
                "",
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val expectedAnswer = expectedAnswers[i]

                println("isPassed = ${expectedAnswer == gcdOfStrings(testCase.first, testCase.second)}")
            }
        }
    }
}