import java.util.*;

public class Main {

    /**
     * Helper method to check if a group of 9 characters contains unique digits 1–9 (ignoring '.')
     */
    private static boolean isValidGroup(List<Character> values) {
        Set<Character> seen = new HashSet<>();
        for (char ch : values) {
            if (ch != '.') {
                if (seen.contains(ch)) return false;
                seen.add(ch);
            }
        }
        return true;
    }

    /**
     * Method to validate a standard 9x9 Sudoku board with additional custom zones.
     */
    public static boolean isValidSudoku(char[][] board, List<List<int[]>> customZones) {
        // Validate all rows and columns
        for (int i = 0; i < 9; i++) {
            List<Character> row = new ArrayList<>();
            List<Character> column = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(board[i][j]);
                column.add(board[j][i]);
            }
            if (!isValidGroup(row)) {
                System.out.println("Invalid Row at index " + i + ": " + row);
                return false;
            }
            if (!isValidGroup(column)) {
                System.out.println("Invalid Column at index " + i + ": " + column);
                return false;
            }
        }

        // Validate each 3x3 box
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                List<Character> box = new ArrayList<>();
                for (int r = row; r < row + 3; r++) {
                    for (int c = col; c < col + 3; c++) {
                        box.add(board[r][c]);
                    }
                }
                if (!isValidGroup(box)) {
                    System.out.println("Invalid 3x3 Box at top-left corner (" + row + "," + col + "): " + box);
                    return false;
                }
            }
        }

        // Validate custom zones
        for (int z = 0; z < customZones.size(); z++) {
            List<int[]> zone = customZones.get(z);
            List<Character> zoneValues = new ArrayList<>();
            for (int[] coord : zone) {
                zoneValues.add(board[coord[0]][coord[1]]);
            }
            if (!isValidGroup(zoneValues)) {
                System.out.println("Invalid Custom Zone " + z + ": " + zoneValues);
                return false;
            }
        }

        return true;
    }

    /**
     * Main method to test the Sudoku validation with standard and custom zones.
     */
    public static void main(String[] args) {
        // Hardcoded Sudoku board
        char[][] board = {
            
            {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
            {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
            {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
            {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
            {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
            {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
            {'3', '4', '5', '2', '8', '6', '1', '7', '9'},
            {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
            {'5', '3', '4', '6', '7', '8', '9', '1', '2'}
        }; //output - Sudoku is valid

        // Define custom zones (same as 3x3 boxes in this example)
        List<List<int[]>> customZones = new ArrayList<>();
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                List<int[]> zone = new ArrayList<>();
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        zone.add(new int[]{blockRow * 3 + r, blockCol * 3 + c});
                    }
                }
                customZones.add(zone);
            }
        }

        // Validate the board using the custom validator
        boolean valid = isValidSudoku(board, customZones);
        System.out.println(valid ? "Sudoku is valid." : "Sudoku is invalid.");
    }
}


/*
Test case-2
Input:
  {'9','6','1','5','3','7','2','8','4'},
  {'6','7','5','1','9','5','3','4','8'}, // duplicate 5 here
  {'1','9','8','3','4','2','5','6','7'},
  {'8','5','9','7','6','1','4','2','3'},
  {'4','2','6','8','5','3','7','9','1'},
  {'5','3','4','6','7','8','9','1','2'},
  {'7','1','3','9','2','4','8','5','6'},
  {'2','8','7','4','1','9','6','3','5'},
  {'3','4','5','2','8','6','1','7','9'}
  
Output:
Sudoku is invalid

Test Case-3"
Input:
  {'2','8','7','4','1','9','6','3','5'},
  {'5','3','4','6','7','8','9','1','2'},
  {'6','7','5','1','9','5','3','4','8'},
  {'1','9','8','3','4','2','5','6','7'},
  {'8','5','9','7','6','1','4','2','3'},
  {'4','2','6','8','5','3','7','9','1'},
  {'7','1','3','6','2','4','8','5','6'}, //duplicate in 6
  {'9','6','1','5','3','7','2','8','4'},
  {'3','4','5','2','8','6','1','7','9'}
 
Output:
Sudoku is invalid
*/
