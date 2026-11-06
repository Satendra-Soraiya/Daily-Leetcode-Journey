class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        // Sort logs by timestamp in ascending order
        Arrays.sort(logs, (a, b) -> a[1] - b[1]);
      
        int queryCount = queries.length;
      
        // Create array of [queryValue, originalIndex] pairs to maintain original query order
        int[][] queriesWithIndex = new int[queryCount][2];
        for (int i = 0; i < queryCount; ++i) {
            queriesWithIndex[i] = new int[] {queries[i], i};
        }
      
        // Sort queries by value in ascending order
        Arrays.sort(queriesWithIndex, (a, b) -> a[0] - b[0]);
      
        // Map to track count of requests per server within current window
        Map<Integer, Integer> serverRequestCount = new HashMap<>();
      
        // Result array to store answers
        int[] result = new int[queryCount];
      
        // Two pointers for sliding window
        int leftPointer = 0;  // Points to start of window
        int rightPointer = 0; // Points to end of window
      
        // Process each query in sorted order
        for (int[] currentQuery : queriesWithIndex) {
            int rightBound = currentQuery[0];
            int originalIndex = currentQuery[1];
            int leftBound = rightBound - x;
          
            // Add all logs with timestamp <= rightBound to the window
            while (rightPointer < logs.length && logs[rightPointer][1] <= rightBound) {
                int serverId = logs[rightPointer][0];
                serverRequestCount.merge(serverId, 1, Integer::sum);
                rightPointer++;
            }
          
            // Remove all logs with timestamp < leftBound from the window
            while (leftPointer < logs.length && logs[leftPointer][1] < leftBound) {
                int serverId = logs[leftPointer][0];
                // Decrement count and remove server if count becomes 0
                if (serverRequestCount.merge(serverId, -1, Integer::sum) == 0) {
                    serverRequestCount.remove(serverId);
                }
                leftPointer++;
            }
          
            // Calculate servers with no requests in window [leftBound, rightBound]
            // Total servers - servers that received at least one request
            result[originalIndex] = n - serverRequestCount.size();
        }
      
        return result;
    }
}
