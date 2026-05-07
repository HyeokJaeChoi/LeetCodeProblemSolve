/**
 * **Time Complexity: `O(n + m)`** where `n = s.length` and `m = t.length`
 * - First loop iterates over `s` to build the character count map in `O(n)`
 * - Second loop iterates over `t` to decrement counts and remove zero-count entries in `O(m)`
 *
 * **Space Complexity: `O(k)`** where `k = number of distinct characters in `s`
 * - The hash map stores at most one entry per distinct character of `s`
 */
package day17

class ValidAnagram242 {
    companion object {
        fun isAnagram(s: String, t: String): Boolean {
            val map = mutableMapOf<Char, Int>()

            for (item in s) {
                if (map.contains(item)) {
                    map[item]!! += 1
                } else {
                    map[item]?.putIfAbsent(1)
                }
            }

            for (item in length) {
                if (map.contains(item)) {
                    map[item]!! -= 1
                }

                if (map[item] == 0) {
                    map.remove(item)
                }
            }

            return map.isEmpty()
        }

        private fun isPassed(actual: Boolean, expected: Boolean): Boolean = actual == expected

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                Triple("anagram", "nagaram", true),
                Triple("rat", "car", false),
                Triple("", "", true),
                Triple("a", "a", true),
                Triple("a", "b", false),
                Triple("ab", "a", false),
                Triple("listen", "silent", true),
                Triple("hello", "world", false),
                Triple("aabb", "abab", true),
                Triple("aabb", "abbb", false),
                Triple("aacc", "ccac", false),
                Triple("abcd", "dcba", true),
            )

            for ((idx, tc) in testCases.withIndex()) {
                val (s, t, expected) = tc
                val actual = isAnagram(s, t)
                println("Test ${idx + 1}: s=\"$s\", t=\"$t\", actual=$actual, expected=$expected, isPassed=${isPassed(actual, expected)}")
            }
        }
    }
}
