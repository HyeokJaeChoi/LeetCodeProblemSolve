/**
 * **Time Complexity: `O(n)`** where `n = s.length`
 * - Each character is pushed onto the deque at most once and popped at most once
 *
 * **Space Complexity: `O(n)`**
 * - The deque holds up to `n` non-star characters before being converted to the result string
 */
package day15

class RemoveStartsFromAString2390 {
    companion object {
        fun removeStars(s: String): String {
            val queue = ArrayDeque<Char>()

            for (char in s) {
                if (char == '*') {
                    queue.removeLastOrNull()
                } else {
                    queue.add(char)
                }
            }

            return StringBuilder().apply { append(queue.toCharArray()) }.toString()
        }
        private fun isPassed(actual: String, expected: String): Boolean = actual == expected

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                "leet**cod*e" to "lecoe",
                "erase*****" to "",
                "abc" to "abc",
                "*" to "",
                "a*b*c*" to "",
                "ab**cd**ef" to "ef",
                "a**b" to "b",
                "abcde" to "abcde",
                "x*y*z" to "z",
            )

            for ((idx, tc) in testCases.withIndex()) {
                val (input, expected) = tc
                val actual = removeStars(input)
                println("Test ${idx + 1}: input=\"$input\", actual=\"$actual\", expected=\"$expected\", isPassed=${isPassed(actual, expected)}")
            }
        }
    }
}