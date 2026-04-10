/**
 * UNSOLVED.
 * Two-pointer in-place compression: use `i` to read consecutive groups of the same character,
 * and `res` to write the compressed result back into the original array.
 * For each group, write the character once, then write the digit(s) of the count only if count > 1.
 *
 * **Time Complexity: `O(n)`** where `n = chars.size`
 * - Each character is read exactly once by the inner and outer while loops combined
 * - Writing count digits is at most `O(log₁₀(n))` per group, totaling `O(n)` across all groups
 *
 * **Space Complexity: `O(1)`**
 * - All compression is done in-place; only a few variables (`i`, `res`, `count`) are used
 */
package day5

class StringCompression443 {
    companion object {
        fun compress(chars: CharArray): Int {
            if (chars.size == 1) {
                return chars.size
            }

            var i = 0
            var res = 0

            while (i < chars.size) {
                var count = 1
                while (i + count < chars.size && chars[i + count] == chars[i]) {
                    count++
                }

                chars[res++] = chars[i]
                if (count > 1) {
                    val countStr = count.toString()
                    for (char in countStr) {
                        chars[res++] = char
                    }
                }

                i+=count
            }

            return res
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                charArrayOf('a','a','b','b','c','c','c'),
                charArrayOf('a','a','a','b','b','a','a'),
                charArrayOf('a'),
                charArrayOf('a','b','b','b','b','b','b','b','b','b','b','b','b'),
            )
            val answers = listOf(
                6,
                6,
                1,
                4,
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]
                val modified = compress(testCase)

                println("modified = ${testCase.joinToString(",")}")
                println("isPassed = ${modified == answer}")
            }
        }
    }
}