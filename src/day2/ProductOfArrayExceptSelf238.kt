package day2

/**
 * UNSOLVED.
 * Solutions(Time Complexity: O(n) / Space Complexity: O(n)) :
 *
 * Initialize two empty arrays, L and R where for a given index i, L[i] would contain the product of all the numbers to the left of i and R[i] would contain the product of all the numbers to the right of i.
 * We would need two different loops to fill in values for the two arrays. For the array L, L[0] would be 1 since there are no elements to the left of the first element. For the rest of the elements, we simply use L[i]=L[i−1]∗nums[i−1]. Remember that L[i] represents product of all the elements to the left of element at index i.
 * For the other array, we do the same thing but in reverse i.e. we start with the initial value of 1 in R[length−1] where length is the number of elements in the array, and keep updating R[i] in reverse. Essentially, R[i]=R[i+1]∗nums[i+1]. Remember that R[i] represents product of all the elements to the right of element at index i.
 * Once we have the two arrays set up properly, we simply iterate over the input array one element at a time, and for each element at index i, we find the product except self as L[i]∗R[i].
 *
 * Solutions(Time Complexity: O(n) / Space Complexity: O(1)) :
 *
 * Initialize the empty answer array where for a given index i, answer[i] would contain the product of all the numbers to the left of i.
 * We construct the answer array the same way we constructed the L array in the previous approach. These two algorithms are exactly the same except that we are trying to save up on space.
 * The only change in this approach is that we don't explicitly build the R array from before.
 * Instead, we simply use a variable to keep track of the running product of elements to the right and we keep updating the answer array by doing answer[i]=answer[i]∗R.
 * For a given index i, answer[i] contains the product of all the elements to the left and R would contain product of all the elements to the right. We then update R as R=R∗nums[i]
 */

class ProductOfArrayExceptSelf238 {
    companion object {
        fun productExceptSelf(nums: IntArray): IntArray {
            val length = nums.size
            val left = IntArray(length)
            val right = IntArray(length)
            val answer = IntArray(length)

            left[0] = 1
            for (i in 1..<length) {
                left[i] = nums[i - 1] * left[i - 1]
            }

            right[length - 1] = 1
            for (i in length - 2 downTo 0) {
                right[i] = nums[i + 1] * right[i + 1]
            }

            for (i in 0..<length) {
                answer[i] = left[i] * right[i]
            }

            return answer
        }
        fun productExceptSelfWithSpaceComplexityInOne(nums: IntArray): IntArray {
            val length: Int = nums.size
            val answer = IntArray(length)

            answer[0] = 1
            for (i in 1..<length) {
                answer[i] = nums[i - 1] * answer[i - 1]
            }

            var right = 1
            for (i in length - 1 downTo 0) {
                answer[i] = answer[i] * right
                right *= nums[i]
            }

            return answer
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val testCases = listOf(
                intArrayOf(1,2,3,4),
                intArrayOf(-1,1,0,-3,3)
            )
            val answers = listOf(
                intArrayOf(24,12,8,6),
                intArrayOf(0,0,9,0,0)
            )

            for (i in testCases.indices) {
                val testCase = testCases[i]
                val answer = answers[i]

                println("isPassed = ${productExceptSelf(testCase).contentEquals(answer)}\n")
            }
        }
    }
}