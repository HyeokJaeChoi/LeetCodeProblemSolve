/**
 * **Time Complexity: `O(n log n + k log n)`** where `n = nums.length` and `k` is the rank.
 * - Inserting `n` elements one-by-one into the max-heap costs `O(n log n)`
 * - Polling `k` times costs `O(k log n)`
 * - Since `k <= n`, this simplifies to `O(n log n)`
 *
 * **Space Complexity: `O(n)`**
 * - The priority queue holds all `n` elements before polling begins
 */
package day16;

import java.util.*;

public class KthLargestElementInAnArray215 {
    class Solution {
        public int findKthLargest(int[] nums, int k) {
            final PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
            for (int num : nums) {
                queue.add(num);
            }

            Integer result = null;
            while (k > 0) {
                result = queue.poll();
                k--;
            }

            return result;
        }
    }

    public static void main(String[] args) {
        Solution sol = new KthLargestElementInAnArray215().new Solution();

        int[][] inputs = {
                {3, 2, 1, 5, 6, 4},
                {3, 2, 3, 1, 2, 4, 5, 5, 6},
                {1},
                {1, 2},
                {2, 1},
                {7, 7, 7, 7, 7, 7},
                {-1, -1},
                {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
                {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
                {5, 2, 4, 1, 3, 6, 0}
        };
        int[] ks = {2, 4, 1, 1, 2, 3, 2, 4, 7, 2};
        int[] expected = {5, 4, 1, 2, 1, 7, -1, 7, 4, 5};

        for (int i = 0; i < inputs.length; i++) {
            int actual = sol.findKthLargest(inputs[i].clone(), ks[i]);
            boolean isPassed = actual == expected[i];
            System.out.println("Test " + (i + 1) + ": expected=" + expected[i]
                    + ", actual=" + actual + " -> " + (isPassed ? "PASS" : "FAIL"));
        }
    }
}
