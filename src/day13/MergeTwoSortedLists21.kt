/**
 * **Status: `UNSOLVED`**
 * - First attempt allocated a fresh `ListNode` each iteration but never wired
 *   `tail.next` to the new node, so only the first node survived in the returned chain.
 * - Switched to reusing input nodes, but still only reassigned the local `next` pointer
 *   with `next = next?.next` — no node's `.next` field was ever written, so the merged
 *   list was never stitched together.
 * - Then introduced a `dummy` variable but left it as `null`, which made every
 *   `next?.next = picked` a silent no-op via Kotlin's safe-call operator.
 * - Could not arrive at the dummy + tail idiom without guidance.
 *
 * **Core insight**
 * - Maintain a **real sentinel dummy node** (non-null) whose `.next` will ultimately
 *   point to the head of the merged list, plus a **tail pointer** that always
 *   references the last node appended to the merged chain. This removes the
 *   "first-node special case" entirely — every append is the same `tail.next = picked`.
 * - In each iteration pick the smaller head (or whichever side is non-null), attach it
 *   with `tail.next = picked`, then advance `tail` to the attached node and move the
 *   consumed input pointer forward.
 * - Reuse the input nodes directly; the merge is a rewiring of existing nodes, not a
 *   copy. Writing `tail.next = picked` overwrites the previous `.next`, which is what
 *   stitches the two lists together.
 * - Return `dummy.next`; the sentinel itself is discarded.
 *
 * **Time Complexity: `O(n + m)`** where `n = list1 length`, `m = list2 length`
 * - Each iteration consumes one node from either list and performs constant work;
 *   every input node is visited exactly once.
 *
 * **Space Complexity: `O(1)`** (excluding the merged output)
 * - Only a dummy node and a few pointers are allocated; the merged list is built by
 *   rewiring existing input nodes rather than copying them.
 */
package day13

class MergeTwoSortedLists21 {
    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    companion object {
        fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
            if (list1 == null && list2 == null) {
                return null
            }

            val dummy = ListNode(0)
            var next: ListNode? = dummy
            var listOne = list1
            var listTwo = list2

            while (listOne != null || listTwo != null) {
                if (listOne != null && listTwo == null) {
                    next?.next = listOne
                    listOne = listOne.next
                } else if (listOne == null && listTwo != null) {
                    next?.next = listTwo
                    listTwo = listTwo.next
                } else if (listOne != null && listTwo != null) {
                    if (listOne.`val` <= listTwo.`val`) {
                        next?.next = listOne
                        listOne = listOne.next
                    } else {
                        next?.next = listTwo
                        listTwo = listTwo.next
                    }
                }

                next = next?.next
            }

            return dummy.next
        }

        fun buildList(values: List<Int>): ListNode? {
            if (values.isEmpty()) return null
            val dummy = ListNode(0)
            var curr = dummy
            for (v in values) {
                curr.next = ListNode(v)
                curr = curr.next!!
            }
            return dummy.next
        }

        fun toList(head: ListNode?): List<Int> {
            val result = mutableListOf<Int>()
            var curr = head
            while (curr != null) {
                result.add(curr.`val`)
                curr = curr.next
            }
            return result
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                listOf(1, 2, 4) to listOf(1, 3, 4),
                listOf<Int>() to listOf<Int>(),
                listOf<Int>() to listOf(0),
                listOf(5) to listOf(1, 2, 3),
                listOf(1, 2, 3) to listOf(4, 5, 6),
                listOf(-10, -5, 0) to listOf(-7, -3, 1),
                listOf(1, 1, 1) to listOf(1, 1),
            )
            val answers = listOf(
                listOf(1, 1, 2, 3, 4, 4),
                listOf(),
                listOf(0),
                listOf(1, 2, 3, 5),
                listOf(1, 2, 3, 4, 5, 6),
                listOf(-10, -7, -5, -3, 0, 1),
                listOf(1, 1, 1, 1, 1),
            )

            for (i in testCases.indices) {
                val (a, b) = testCases[i]
                val result = toList(mergeTwoLists(buildList(a), buildList(b)))
                val isPassed = result == answers[i]
                println("isPassed = $isPassed / $result")
            }
        }
    }
}