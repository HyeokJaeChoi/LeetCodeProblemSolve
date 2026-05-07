/**
 * **Time Complexity: `O(a + b)`** where `a` and `b` are the input counts of `'a'` and `'b'`.
 * - Each loop iteration appends exactly one character, so the loop runs `a + b` times in total
 * - All operations inside the loop (comparisons, append, counter updates) are `O(1)`
 *
 * **Space Complexity: `O(a + b)`**
 * - The `StringBuilder` accumulates `a + b` characters before being converted to the final string
 */
package day17;

public class StringWithoutAAAOrBBB984 {
    class Solution {
        public String strWithout3a3b(int a, int b) {
            final StringBuilder stringBuilder = new StringBuilder();
            int counter = 0;
            char prevSelected = 'a';

            while (a > 0 || b > 0) {
                if (counter == 2) {
                    counter = 0;
                    if (prevSelected == 'a' && b > 0) {
                        stringBuilder.append('b');
                        prevSelected = 'b';
                        b--;

                        continue;
                    }
                    if (prevSelected == 'b' && a > 0) {
                        stringBuilder.append('a');
                        prevSelected = 'a';
                        a--;

                        continue;
                    }
                }

                final int currMax = a >= b ? a : b;
                if ((prevSelected == 'a' && currMax == b) || (prevSelected == 'b' && currMax == a)) {
                    counter = 0;
                }

                    if (currMax == a) {
                        prevSelected = 'a';
                        stringBuilder.append('a');
                        a--;
                    } else {
                        prevSelected = 'b';
                        stringBuilder.append('b');
                        b--;
                    }
                    counter++;
            }

            return stringBuilder.toString();
        }
    }

    private static boolean isPassed(String result, int a, int b) {
        if (result.contains("aaa") || result.contains("bbb")) {
            return false;
        }
        int countA = 0;
        int countB = 0;
        for (char c : result.toCharArray()) {
            if (c == 'a') countA++;
            else if (c == 'b') countB++;
            else return false;
        }
        return countA == a && countB == b;
    }

    public static void main(String[] args) {
        Solution sol = new StringWithoutAAAOrBBB984().new Solution();

        int[][] inputs = {
                {1, 2},
                {4, 1},
                {2, 2},
                {1, 1},
                {3, 3},
                {4, 4},
                {7, 1},
                {1, 7},
                {4, 2},
                {2, 4},
                {5, 3},
                {3, 5},
                {6, 6},
                {1, 4},
                {100, 50}
        };

        for (int i = 0; i < inputs.length; i++) {
            int a = inputs[i][0];
            int b = inputs[i][1];
            String actual = sol.strWithout3a3b(a, b);
            boolean isPassed = isPassed(actual, a, b);
            System.out.println("Test " + (i + 1) + ": a=" + a + ", b=" + b
                    + ", actual=\"" + actual + "\" -> " + (isPassed ? "PASS" : "FAIL"));
        }
    }
}
