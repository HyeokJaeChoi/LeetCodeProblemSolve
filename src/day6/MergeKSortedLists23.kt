package day6

import java.util.PriorityQueue

class MergeKSortedLists23 {
    companion object {
        class ListNode(var `val`: Int) {
            var next: ListNode? = null
        }

        // ListNode 헬퍼 함수: 리스트로 LinkedList 생성
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

        fun mergeKLists(lists: Array<ListNode?>): ListNode? {
            if (lists.isEmpty()) {
                return null
            }

            val heap = PriorityQueue<ListNode>() { a, b ->
                a?.`val`?.compareTo(b?.`val` ?: 0) ?: 0
            }

            for (i in lists.indices) {
                var item = lists[i]
                while (item != null) {
                    heap.offer(ListNode(item.`val`))
                    item = item.next
                }
            }

            val list = mutableListOf<ListNode>()
            while (heap.isNotEmpty()) {
                list.add(heap.poll())
            }

            if (list.isEmpty()) {
                return null
            }

            for (i in 0..<list.size.minus(1)) {
                list[i].next = list[i + 1]
            }

            return list[0]
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val lists1: Array<ListNode?> = arrayOf(
                ListNode(1).apply { next = ListNode(4).apply { next = ListNode(5) } },
                ListNode(1).apply { next = ListNode(3).apply { next = ListNode(4) } },
                ListNode(2).apply { next = ListNode(6) }
            )
            val expected1 = ListNode(1).apply { next = ListNode(1).apply { next = ListNode(2).apply {
                next = ListNode(3).apply { next = ListNode(4).apply { next = ListNode(4).apply {
                    next = ListNode(5).apply { next = ListNode(6) }
                }}}
            }}}

            val lists2: Array<ListNode?> = arrayOf()
            val expected2: ListNode? = null  // []

            val lists3: Array<ListNode?> = arrayOf(null)
            val expected3: ListNode? = null  // []

            var item = mergeKLists(lists1)

            while (item != null) {
                println("item: ${item.`val`}")
                item = item.next
            }
        }
    }
}