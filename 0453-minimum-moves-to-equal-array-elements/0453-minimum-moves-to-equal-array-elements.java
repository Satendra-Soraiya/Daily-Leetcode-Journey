class Solution {
    public int minMoves(int[] nums) {
        // Calculate the sum of all elements in the array
        int sum = Arrays.stream(nums).sum();
      
        // Find the minimum element in the array
        int minValue = Arrays.stream(nums).min().getAsInt();
      
        // The minimum number of moves equals:
        // (sum of all elements) - (minimum element * array length)
        // This works because incrementing n-1 elements is equivalent to
        // decrementing 1 element. To make all elements equal, we need to
        // bring all elements down to the minimum value.
        int minMoves = sum - minValue * nums.length;
      
        return minMoves;
    }
}
