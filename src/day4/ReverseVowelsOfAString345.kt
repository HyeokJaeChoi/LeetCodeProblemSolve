/**
 * **Time Complexity: `O(n)`** where `n = s.length`
 * - First pass collects all vowel positions and characters into a list in `O(n)`
 * - Second pass reassigns vowels in reverse order by iterating the vowels list in `O(v)` where `v <= n`
 *
 * **Space Complexity: `O(n)`**
 * - `StringBuilder` copies the entire string in `O(n)`
 * - `vowels` list stores up to `n` pairs in the worst case (all vowels)
 *
 *
 * **Time Complexity: `O(n)`** where `n = s.length`
 * - Two pointers move inward from both ends, each index visited at most once
 * - Vowel check via `isVowel()` is `O(1)` per character
 *
 * **Space Complexity: `O(n)`**
 * - `StringBuilder` copies the entire string in `O(n)`
 * - Only two pointer variables (`left`, `right`) are used beyond that
 */
package day4

class ReverseVowelsOfAString345 {
    companion object {
        fun Char.isVowel(): Boolean {
            return this == 'a' || this == 'e' || this == 'i' || this == 'o' || this == 'u' ||
                    this == 'A' || this == 'E' || this == 'I' || this == 'O' || this == 'U'
        }

        fun reverseVowels(s: String): String {
            val strBuilder = StringBuilder().append(s)
            val vowels = mutableListOf<Pair<Int, Char>>()

            for (i in s.indices) {
                val char = s[i]
                if (char.isVowel()) {
                    vowels.add(i to char)
                }
            }

            for (i in vowels.indices) {
                val targetVowel = vowels[i]
                val sourceVowel = vowels[vowels.size - 1 - i]

                strBuilder[targetVowel.first] = sourceVowel.second
            }

            return strBuilder.toString()
        }

        fun reverseVowelsUsingTwoPointer(s: String): String {
            if (s.length == 1) {
                return s
            }
            val strBuilder = StringBuilder().append(s)

            var left = 0
            var right = s.length - 1

            while (left < right) {
                val leftChar = s[left]
                if (leftChar.isVowel()) {
                    while (right > left) {
                        val rightChar = s[right]
                        if (rightChar.isVowel()) {
                            strBuilder[left] = rightChar
                            strBuilder[right] = leftChar
                            right--
                            break
                        }
                        right--
                    }
                }

                left++
            }

            return strBuilder.toString()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                "IceCreAm",
                "leetcode",
            )
            val answers = listOf(
                "AceCreIm",
                "leotcede"
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${reverseVowelsUsingTwoPointer(testCase) == answer}")
            }
        }
    }
}