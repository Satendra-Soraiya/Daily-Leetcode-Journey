public class Solution {
    public int zigZagArrays(int n, int l, int r) {
        long MOD = 1_000_000_007L;
        int m = r - l + 1;
        
        // Base case: for length n = 1, each single number is valid.
        // We initialize both states with 1 for each number.
        long[] up = new long[m];
        long[] down = new long[m];
        for (int i = 0; i < m; i++) {
            up[i] = 1;
            down[i] = 1;
        }
        
        // Dynamic Programming transitions from length 2 to n
        for (int step = 2; step <= n; step++) {
            long[] nextUp = new long[m];
            long[] nextDown = new long[m];
            
            // Compute prefix sums to answer range sum queries in O(1)
            long[] prefUp = new long[m + 1];
            long[] prefDown = new long[m + 1];
            for (int i = 0; i < m; i++) {
                prefUp[i + 1] = (prefUp[i] + up[i]) % MOD;
                prefDown[i + 1] = (prefDown[i] + down[i]) % MOD;
            }
            
            for (int i = 0; i < m; i++) {
                // To transition to up[i], previous state must be down[j] where j < i
                // Sum of down[0...i-1] is given by prefDown[i]
                nextUp[i] = prefDown[i];
                
                // To transition to down[i], previous state must be up[j] where j > i
                // Sum of up[i+1...m-1] is given by prefUp[m] - prefUp[i+1]
                long sumUp = (prefUp[m] - prefUp[i + 1] + MOD) % MOD;
                nextDown[i] = sumUp;
            }
            
            up = nextUp;
            down = nextDown;
        }
        
        // Accumulate final valid states across all ending values
        long totalArrays = 0;
        for (int i = 0; i < m; i++) {
            totalArrays = (totalArrays + up[i] + down[i]) % MOD;
        }
        
        return (int) totalArrays;
    }
}
