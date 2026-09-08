class Solution {
public:
    int countCommas(int n) {
        int result = 0;
        for (int num = 1; num <= n; num++) {
            if (num > 999) {
                result += 1;
            }
        }

        return result;
    }
};
