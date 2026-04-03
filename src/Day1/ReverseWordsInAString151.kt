/**
 * **`reverseWords` — Manual scan approach**
 *
 * **Time Complexity: `O(n)`** where `n = s.length`
 * - The for loop traverses the string once, processing each character in `O(1)`
 * - `reverse.add(0, ...)` inserts at the front of an ArrayList, costing `O(k)` per call (k = current list size).
 *   Total insertion cost across `w` words is `O(w^2)`. Since `w <= n`, worst case is `O(n^2)`,
 *   but in practice the word count is small enough that it behaves close to `O(n)`
 * - `joinToString` iterates all words once, so `O(n)`
 *
 * **Space Complexity: `O(n)`**
 * - `reverse` list stores all words with total character count `O(n)`
 * - `wordHolder` StringBuilder is at most `O(n)` but reused via `clear()`, so concurrent usage is `O(word length)`
 * - `joinToString` result string is `O(n)`
 *
 * ---
 *
 * **`reverseWordsUsingRegExp` — Regex approach**
 *
 * **Time Complexity: `O(n)`** where `n = s.length`
 * - `trim()` scans leading/trailing spaces in `O(n)`
 * - `split(Regex("\\s+"))` scans the entire string once to split by whitespace in `O(n)`
 * - `reversed()` creates a new reversed list of `w` words in `O(w)`
 * - `joinToString` iterates all words once in `O(n)`
 *
 * **Space Complexity: `O(n)`**
 * - `split` produces a list of substrings totaling `O(n)` characters
 * - `reversed()` creates a new list of `w` references in `O(w)`
 * - `joinToString` result string is `O(n)`
 */
package Day1

class ReverseWordsInAString151 {
    companion object {
        fun reverseWords(s: String): String {
            val reverse = mutableListOf<String>()
            val wordHolder = StringBuilder()

            for (char in s) {
                if (char == ' ') {
                    if (wordHolder.isNotEmpty()) {
                        reverse.add(0, wordHolder.toString())
                        wordHolder.clear()
                    }
                } else {
                    wordHolder.append(char)
                }
            }
            if (wordHolder.isNotEmpty()) {
                reverse.add(0, wordHolder.toString())
            }

            return reverse.joinToString(separator = " ")
        }

        fun reverseWordsUsingRegExp(s: String): String {
            return s.trim().split(Regex("\\s+")).reversed().joinToString(" ")
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf<String>(
                "the sky is blue",
                "  hello world  ",
                "a good   example",
            )

            val expectedAnswers = listOf<String>(
                "blue is sky the",
                "world hello",
                "example good a",
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val expectedAnswer = expectedAnswers[i]

                println("isPassed = ${expectedAnswer == reverseWords(testCase)}")
            }
        }
    }
}