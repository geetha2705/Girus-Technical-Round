import java.util.*;

public class Main {
    static int rows, cols;
    static char[][] grid;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) {
        // Example input grid
        grid = new char[][] {
             {'.', '#', '#', '#', '#'},
    {'#', '.', '.', '.', '.'},
    {'#', '.', '#', '#', '.'},
    {'#', '.', '#', '#', '.'},
    {'#', '.', '.', '.', '.'}
        };

        rows = grid.length;
        cols = grid[0].length;

        int shortestPath = findShortestPathWithTeleport();
        System.out.println("Shortest path length with one teleport allowed: " + shortestPath);
    }

    // BFS to compute shortest distance from (sr, sc) to all reachable cells
    static int[][] bfs(int sr, int sc) {
        int[][] dist = new int[rows][cols];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);
        dist[sr][sc] = 0;

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{sr, sc});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i], nc = c + dc[i];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols &&
                    grid[nr][nc] != '#' && dist[nr][nc] == Integer.MAX_VALUE) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        return dist;
    }

    static int findShortestPathWithTeleport() {
        // If start or end blocked, no path
        if (grid[0][0] == '#' || grid[rows-1][cols-1] == '#') return -1;

        int[][] distStart = bfs(0, 0);
        int[][] distEnd = bfs(rows - 1, cols - 1);

        int directPath = distStart[rows-1][cols-1];
        if (directPath == Integer.MAX_VALUE) directPath = -1;

        int minPath = directPath;

        // Collect all empty cells for teleport consideration
        List<int[]> emptyCells = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '#') emptyCells.add(new int[]{r, c});
            }
        }

        // Try teleport from a to b for all pairs (a != b)
        for (int i = 0; i < emptyCells.size(); i++) {
            int r1 = emptyCells.get(i)[0], c1 = emptyCells.get(i)[1];
            if (distStart[r1][c1] == Integer.MAX_VALUE) continue;

            for (int j = 0; j < emptyCells.size(); j++) {
                if (i == j) continue;
                int r2 = emptyCells.get(j)[0], c2 = emptyCells.get(j)[1];
                if (distEnd[r2][c2] == Integer.MAX_VALUE) continue;

                int candidate = distStart[r1][c1] + 1 + distEnd[r2][c2]; // 1 for teleport
                if (minPath == -1 || candidate < minPath) {
                    minPath = candidate;
                }
            }
        }

        return minPath;
    }
}

/*
Test Case-1:
input:
char[][] grid = {
    {'.', '.', '.'},
    {'.', '.', '.'},
    {'.', '.', '.'}
};

output:
Shortest path length with one teleport allowed: 2

Test case-2:
input:
char[][] grid = {
    {'.', '#', '#'},
    {'#', '#', '#'},
    {'#', '#', '.'}
};
output:
Shortest path length with one teleport allowed: -1

*/
