class Solution {
    // Grid dimensions
    private int rows;
    private int cols;
    private int[][] grid;

    /**
     * Counts the number of closed islands in the grid.
     * A closed island is an island that doesn't touch the border of the grid.
     * 
     * @param grid 2D array where 0 represents land and 1 represents water
     * @return the number of closed islands
     */
    public int closedIsland(int[][] grid) {
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.grid = grid;
      
        int closedIslandCount = 0;
      
        // Traverse the entire grid to find unvisited land cells
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 0) {  // Found unvisited land
                    // Check if this island is closed (doesn't touch border)
                    closedIslandCount += dfs(row, col);
                }
            }
        }
      
        return closedIslandCount;
    }

    /**
     * Performs DFS to explore the island and determine if it's closed.
     * Marks visited land cells as water (1) to avoid revisiting.
     * 
     * @param row current row position
     * @param col current column position
     * @return 1 if the island is closed, 0 otherwise
     */
    private int dfs(int row, int col) {
        // Check if current cell is not on the border (closed island condition)
        // If on border, this island touches the edge and is not closed
        int isClosed = (row > 0 && row < rows - 1 && col > 0 && col < cols - 1) ? 1 : 0;
      
        // Mark current land cell as visited by changing it to water
        grid[row][col] = 1;
      
        // Direction vectors for moving up, right, down, left
        int[] directions = {-1, 0, 1, 0, -1};
      
        // Explore all 4 adjacent cells
        for (int k = 0; k < 4; k++) {
            int nextRow = row + directions[k];
            int nextCol = col + directions[k + 1];
          
            // Check if the adjacent cell is within bounds and is unvisited land
            if (nextRow >= 0 && nextRow < rows && 
                nextCol >= 0 && nextCol < cols && 
                grid[nextRow][nextCol] == 0) {
              
                // Continue DFS and update closed status using bitwise AND
                // If any part of the island touches border, entire island is not closed
                isClosed &= dfs(nextRow, nextCol);
            }
        }
      
        return isClosed;
    }
}
