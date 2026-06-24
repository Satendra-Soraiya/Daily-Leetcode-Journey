class Solution {
    private static final long MOD = 1_000_000_007L;
    private int size;

    public int zigZagArrays(int n, int l, int r) {
        // Base case: if array length is 1, any standalone element in [l, r] is valid
        if (n == 1) {
            return r - l + 1;
        }

        int m = r - l + 1;
        this.size = 2 * m;

        // Transition Matrix
        // State 0 to m-1: Current element is (l + i), and the LAST move was UP (next must be DOWN)
        // State m to 2m-1: Current element is (l + i), and the LAST move was DOWN (next must be UP)
        long[][] T = new long[size][size];

        for (int i = 0; i < m; i++) {
            // From UP state (i): Next element must be strictly smaller -> goes to DOWN state
            for (int j = 0; j < i; j++) {
                T[i][j + m] = 1;
            }
            // From DOWN state (i + m): Next element must be strictly larger -> goes to UP state
            for (int j = i + 1; j < m; j++) {
                T[i + m][j] = 1;
            }
        }

        // Multiply transitions for the remaining (n - 1) steps
        long[][] T_pow = power(T, n - 1);

        // Sum up all possible matching configurations
        long totalArrays = 0;
        for (int start = 0; start < size; start++) {
            for (int end = 0; end < size; end++) {
                totalArrays = (totalArrays + T_pow[start][end]) % MOD;
            }
        }

        return (int) totalArrays;
    }

    // Helper method to perform modular matrix multiplication
    private long[][] multiply(long[][] A, long[][] B) {
        long[][] C = new long[size][size];
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < size; k++) {
                if (A[i][k] == 0) continue; 
                for (int j = 0; j < size; j++) {
                    C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }
        return C;
    }

    // Helper method for Binary Exponentiation on the state matrix
    private long[][] power(long[][] A, int p) {
        long[][] res = new long[size][size];
        for (int i = 0; i < size; i++) {
            res[i][i] = 1; 
        }
        long[][] base = A;
        while (p > 0) {
            if ((p & 1) == 1) {
                res = multiply(res, base);
            }
            base = multiply(base, base);
            p >>= 1;
        }
        return res;
    }
}
