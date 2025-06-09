import java.util.*;

public class Main {

    // Directions for 8-connected neighbors (diagonals included)
    private static final int[] rowDir = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] colDir = {-1, 0, 1, -1, 1, -1, 0, 1};

    // BFS to explore all cells of the current island
    private static void bfs(int[][] grid, boolean[][] visited, int startR, int startC) {
        int rows = grid.length;
        int cols = grid[0].length;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startR, startC});
        visited[startR][startC] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int r = cell[0];
            int c = cell[1];

            for (int d = 0; d < 8; d++) {
                int nr = r + rowDir[d];
                int nc = c + colDir[d];

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols &&
                    grid[nr][nc] == 1 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc});
                }
            }
        }
    }

    // Count number of islands using BFS
    public static int countIslands(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int islandCount = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 1 && !visited[r][c]) {
                    bfs(grid, visited, r, c);
                    islandCount++;
                }
            }
        }
        return islandCount;
    }

    // Main method with user input
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = sc.nextInt();

        int[][] grid = new int[rows][cols];
        System.out.println("Enter matrix (0s and 1s):");
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                grid[i][j] = sc.nextInt();

        int islands = countIslands(grid);
        System.out.println("Number of islands (with diagonals): " + islands);
    }
}

/*
Test Case-1:
input:
3 3
1 0 0
0 1 0
0 0 1
output:
Number of islands (with diagonals): 1

Test Case-2:
input:
4 5
1 0 0 1 1
0 1 0 0 0
1 0 1 0 1
0 0 0 1 1
output:
Number of islands (with diagonals): 3

Test Case-3:
input:
3 4
1 0 1 0
0 1 0 1
1 0 1 0

output:
Number of islands (with diagonals): 6

*/
