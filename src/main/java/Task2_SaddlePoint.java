import java.io.*;
import java.util.*;

public class Task2_SaddlePoint {
    public static void findSaddlePoint(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean found = false;

        for (int i = 0; i < rows; i++) {
            int minRowValue = matrix[i][0];
            int minColIndex = 0;
            for (int j = 1; j < cols; j++) {
                if (matrix[i][j] < minRowValue) {
                    minRowValue = matrix[i][j];
                    minColIndex = j;
                }
            }

            boolean isSaddlePoint = true;
            for (int k = 0; k < rows; k++) {
                if (k != i && matrix[k][minColIndex] >= minRowValue) {
                    isSaddlePoint = false;
                    break;
                }
            }

            if (isSaddlePoint) {
                System.out.println("Седловая точка найдена: " + minRowValue + " в строке " + (i + 1) + " и столбце " + (minColIndex + 1));
                found = true;
                printMatrix(matrix, minRowValue, minColIndex);
            }
        }

        if (!found) {
            System.out.println("Седловая точка не найдена.");
        }
    }

    public static void printMatrix(int[][] matrix, int saddleRow, int saddleCol) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static List<int[][]> readMatricesFromFile(String fileName) {
        List<int[][]> matrices = new ArrayList<>();
        List<List<Integer>> currentMatrix = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    if (!currentMatrix.isEmpty()) {
                        matrices.add(convertListToMatrix(currentMatrix));
                        currentMatrix.clear();
                    }
                } else {
                    List<Integer> row = new ArrayList<>();
                    String[] parts = line.split("\\s+");
                    for (String part : parts) {
                        row.add(Integer.parseInt(part));
                    }
                    currentMatrix.add(row);
                }
            }

            if (!currentMatrix.isEmpty()) {
                matrices.add(convertListToMatrix(currentMatrix));
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return matrices;
    }

    public static int[][] convertListToMatrix(List<List<Integer>> list) {
        int rows = list.size();
        int cols = list.get(0).size();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = list.get(i).get(j);
            }
        }
        return matrix;
    }

    public static void main(String[] args) {
        String fileName = "matrices.txt";

        List<int[][]> matrices = readMatricesFromFile(fileName);

        for (int i = 0; i < matrices.size(); i++) {
            System.out.println("Матрица " + (i + 1) + ":");
            findSaddlePoint(matrices.get(i));
            System.out.println();
        }
    }

}
