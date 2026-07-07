class Solution {
    public long sumAndMultiply(int n) {
        long x = 0;
        long sum = 0;
        
        // Convert to string to process sequentially
        for (char ch : String.valueOf(n).toCharArray()) {
            if (ch != '0') {
                int digit = ch - '0';
                sum += digit;
                x = x * 10 + digit;
            }
        }
        return x * sum;
    }
}
