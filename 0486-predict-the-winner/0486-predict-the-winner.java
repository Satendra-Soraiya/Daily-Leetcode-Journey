class Solution {
    public boolean predictTheWinner(int[] nums) {
      
        Integer[][] memo = new Integer[nums.length][nums.length];
        
      
        int finalScoreDifference = calculateMaxDifference(nums, 0, nums.length - 1, memo);
        
        return finalScoreDifference >= 0;
    }
    private int calculateMaxDifference(int[] nums, int left, int right, Integer[][] memo) {

        if (left == right) {
            return nums[left];
        }
        if (memo[left][right] != null) {
            return memo[left][right];
        }

        int takeLeft = nums[left] - calculateMaxDifference(nums, left + 1, right, memo);
        

        int takeRight = nums[right] - calculateMaxDifference(nums, left, right - 1, memo);
     
        int bestChoice = Math.max(takeLeft, takeRight);
        
        // Write this answer down in the notebook before returning it.
        memo[left][right] = bestChoice;
        
        return bestChoice;
    }
}