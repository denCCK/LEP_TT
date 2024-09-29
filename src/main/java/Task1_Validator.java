import java.io.*;
import java.util.*;

public class Task1_Validator {
    public static boolean isValid(String expression) {
        Stack<Character> stack = new Stack<>();

        Map<Character, Character> bracketPairs = new HashMap<>();
        bracketPairs.put(')', '(');
        bracketPairs.put(']', '[');
        bracketPairs.put('}', '{');

        for (char ch : expression.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            }
            else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty() || stack.pop() != bracketPairs.get(ch)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String fileName = "expressions.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (isValid(line)) {
                    System.out.println(line + " - правильная скобочная последовательность");
                } else {
                    System.out.println(line + " - неправильная скобочная последовательность");
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}