import java.util.*;

public class Main {

    // Function to count set bits (1s) in a number
    private static int countBits(int num) {
        int count = 0;
        while (num > 0) {
            count += (num & 1); // Add 1 if LSB is 1
            num >>= 1;          // Right shift
        }
        return count;
    }

    // Function to find the next integer with same number of 1s in binary
    private static int nextMatchingBitPattern(int n) {
        int targetBits = countBits(n);
        int next = n + 1;

        while (countBits(next) != targetBits) {
            next++;
        }

        return next;
    }

    // Main function with Scanner input
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int n = sc.nextInt();

        int result = nextMatchingBitPattern(n);
        System.out.println("Next number with same number of binary 1s: " + result);
    }
}

/*
Test Case-1:
input:
2
output:
4

Test Case-2:
input:
6
output:
9

Test Case-3:
input:
15
output:
23

*/
