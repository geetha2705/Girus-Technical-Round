import java.util.*;

public class Main {

    public static String findAlienOrder(String[] dictionary) {
        // Step 1: Initialize graph and in-degree map for all unique chars
        Map<Character, Set<Character>> adjMap = new HashMap<>();
        Map<Character, Integer> degreeMap = new HashMap<>();

        for (String word : dictionary) {
            for (char ch : word.toCharArray()) {
                adjMap.putIfAbsent(ch, new HashSet<>());
                degreeMap.putIfAbsent(ch, 0);
            }
        }

        // Step 2: Build graph edges by comparing adjacent words
        for (int i = 0; i < dictionary.length - 1; i++) {
            String first = dictionary[i];
            String second = dictionary[i + 1];
            int length = Math.min(first.length(), second.length());

            // Edge case: prefix scenario like ["abc", "ab"] is invalid
            if (first.startsWith(second) && first.length() > second.length()) {
                return "";
            }

            // Find first differing character and add edge
            for (int j = 0; j < length; j++) {
                char c1 = first.charAt(j);
                char c2 = second.charAt(j);
                if (c1 != c2) {
                    // Add edge c1 -> c2 if not already added
                    if (!adjMap.get(c1).contains(c2)) {
                        adjMap.get(c1).add(c2);
                        degreeMap.put(c2, degreeMap.get(c2) + 1);
                    }
                    break; // Only first difference matters
                }
            }
        }

        // Step 3: Topological sorting using a queue (Kahn's Algorithm)
        Queue<Character> zeroDegreeQueue = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : degreeMap.entrySet()) {
            if (entry.getValue() == 0) {
                zeroDegreeQueue.offer(entry.getKey());
            }
        }

        StringBuilder alienOrder = new StringBuilder();

        while (!zeroDegreeQueue.isEmpty()) {
            char current = zeroDegreeQueue.poll();
            alienOrder.append(current);

            for (char neighbor : adjMap.get(current)) {
                degreeMap.put(neighbor, degreeMap.get(neighbor) - 1);
                if (degreeMap.get(neighbor) == 0) {
                    zeroDegreeQueue.offer(neighbor);
                }
            }
        }

        // If the result length is less than total unique chars, cycle detected
        if (alienOrder.length() != degreeMap.size()) {
            return "";
        }

        return alienOrder.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of words in alien dictionary: ");
        int n = sc.nextInt();
        sc.nextLine();
        String[] alienWords = new String[n];
        System.out.println("Enter the words in sorted alien dictionary order:");
        for (int i = 0; i < n; i++) 
            alienWords[i] = sc.nextLine().trim();
            
        String alienOrder = findAlienOrder(alienWords);
        if (alienOrder.isEmpty())
            System.out.println("No valid alien dictionary order can be determined.");
        else
            System.out.println("Alien Dictionary Order: " + alienOrder);
    }
}


/*
TestCase-1:

input:
Enter number of words in alien dictionary: 3
Enter the words in sorted alien dictionary order:
abc
abx
bcd

output:
Alien Dictionary Order: acbd

Test Case-2:

input:
Enter number of words in alien dictionary: 3
Enter the words in sorted alien dictionary order:
abc
bca
cab

output:
No valid alien dictionary order can be determined.

*/
