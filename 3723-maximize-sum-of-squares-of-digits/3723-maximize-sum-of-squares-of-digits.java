class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int left = sum;
        for (int i = 0; i < num; i++) {
            int d = Math.min(9, left);
            sb.append(d);
            left -= d;
        }
        return sb.toString();
    }
}