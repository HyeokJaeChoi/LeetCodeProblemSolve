/**
 * **Time Complexity: `O(n)`** where `n = t.length`
 * - Two pointers advance through `s` and `t`; `targetPointer` moves every iteration so the loop runs at most `n` times
 * - Each character comparison is `O(1)`
 *
 * **Space Complexity: `O(1)`**
 * - Only two pointer variables (`stringPointer`, `targetPointer`) are used
 */
package day5

class IsSubsequence392 {
    companion object {
        fun isSubsequence(s: String, t: String): Boolean {
            var stringPointer = 0
            var targetPointer = 0

            while (stringPointer < s.length && targetPointer < t.length) {
                val source = s[stringPointer]
                val target = t[targetPointer]

                if (source == target) {
                    stringPointer++
                }
                targetPointer++
            }
            return stringPointer == s.length
        }
        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                "abc" to "ahbgdc",
                "axc" to "ahbgdc",
            )

            val answers = listOf(
                true,
                false,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${isSubsequence(testCase.first, testCase.second) == answer}")
            }
        }
    }
}