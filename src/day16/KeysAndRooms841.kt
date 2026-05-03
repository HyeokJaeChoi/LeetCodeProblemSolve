/**
 * **Time Complexity: `O(n + k)`** where `n = rooms.size` and `k = total number of keys across all rooms`
 * - Each room is marked visited at most once via BFS starting from room 0
 * - Every key encountered is enqueued and dequeued once; visited keys are skipped on dequeue
 *
 * **Space Complexity: `O(n + k)`**
 * - `visited` boolean array holds `n` entries and the BFS queue can hold up to `O(k)` keys before deduplication
 */
package day16

import kotlin.collections.emptyList

class KeysAndRooms841 {
    companion object {
        fun canVisitAllRooms(rooms: List<List<Int>>): Boolean {
            val visited = BooleanArray(rooms.size) {
                false
            }
            val queue = ArrayDeque<Int>()

            queue.addAll(rooms[0])
            visited[0] = true

            while (queue.isNotEmpty()) {
                val item = queue.first()
                if (visited[item]) {
                    queue.removeFirst()
                    continue
                }
                visited[item] = true
                queue.removeFirst()

                val nextItem = rooms[item]
                for (i in nextItem) {
                    queue.addLast(i)
                }
            }

            return !visited.any { !it }
        }

        private fun isPassed(actual: Boolean, expected: Boolean): Boolean = actual == expected

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                listOf(listOf(1), listOf(2), listOf(3), emptyList()) to true,
                listOf(listOf(1, 3), listOf(3, 0, 1), listOf(2), listOf(0)) to false,
                listOf(emptyList<Int>()) to true,
                listOf(listOf(1), emptyList<Int>()) to true,
                listOf(emptyList(), emptyList<Int>()) to false,
                listOf(listOf(1, 2, 3), emptyList(), emptyList(), emptyList<Int>()) to true,
                listOf(listOf(2), emptyList(), listOf(1)) to false,
                listOf(listOf(1), listOf(2), emptyList<Int>()) to true,
                listOf(listOf(1, 2), listOf(2, 0, 1), listOf(0, 1)) to true,
                listOf(listOf(2, 3), emptyList(), listOf(1), emptyList<Int>()) to false,
                listOf(listOf(1, 2), listOf(0), listOf(0)) to true,
                listOf(listOf(1), listOf(0), listOf(3), listOf(2)) to false,
            )

            for ((idx, tc) in testCases.withIndex()) {
                val (rooms, expected) = tc
                val actual = canVisitAllRooms(rooms)
                println("Test ${idx + 1}: rooms=$rooms, actual=$actual, expected=$expected, isPassed=${isPassed(actual, expected)}")
            }
        }
    }
}