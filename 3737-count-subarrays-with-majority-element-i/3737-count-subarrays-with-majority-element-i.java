class Solution {
    public int countMajoritySubarrays(int[] nums, int target) {
        int count = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int freq = 0, len = 0;
            for (int j = i; j < n; j++) {
                if (nums[j] == target) freq++;
                len++;
                if (freq * 2 > len) count++;
            }
        }
        return count;
    }
}