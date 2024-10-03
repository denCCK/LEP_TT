import java.io.*;
import java.util.*;

public class Task3_CaseConverter {

    static String fileName = "cases.txt";

    public static String convertIdentifier(String identifier) {
        if (isSnakeCase(identifier)) {
            return snakeToCamel(identifier);
        } else if (isCamelCase(identifier)) {
            return camelToSnake(identifier);
        } else {
            return "Error!";
        }
    }

    public static boolean isSnakeCase(String identifier) {
        String snakePattern = "^[a-z]+(_[a-z]+)*$";
        return identifier.matches(snakePattern);
    }

    public static boolean isCamelCase(String identifier) {
        String camelPattern = "^[a-z]+[a-zA-Z]*$";
        return identifier.matches(camelPattern);
    }

    public static String snakeToCamel(String identifier) {
        StringBuilder result = new StringBuilder();
        boolean toUpperCase = false;
        for (char ch : identifier.toCharArray()) {
            if (ch == '_') {
                toUpperCase = true;
            } else {
                if (toUpperCase) {
                    result.append(Character.toUpperCase(ch));
                    toUpperCase = false;
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    public static String camelToSnake(String identifier) {
        StringBuilder result = new StringBuilder();
        for (char ch : identifier.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                result.append("_").append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line + " -> " + convertIdentifier(line));
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
