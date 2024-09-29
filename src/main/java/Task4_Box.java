import java.io.*;
import java.util.*;

public class Task4_Box {

    public static boolean checkEdge(int[] edge1, int[] edge2) {
        int A1 = edge1[0], B1 = edge1[1];
        int A2 = edge2[0], B2 = edge2[1];
        return (A1 == A2 || A1 == B2 || B1 == A2 || B1 == B2);
    }

    public static boolean checkParallelepiped(int[][] edges) {
        List<int[]> sortedEdges = new ArrayList<>();
        for (int[] edge : edges) {
            int[] sortedEdge = Arrays.copyOf(edge, edge.length);
            Arrays.sort(sortedEdge);
            sortedEdges.add(sortedEdge);
        }

        HashMap<String, Integer> edgeCount = new HashMap<>();
        boolean isEqualSide = false;
        for (int[] edge : sortedEdges) {
            if (edge[0] == edge[1]) {
                isEqualSide = true;
            }
            String key = Arrays.toString(edge);
            edgeCount.put(key, edgeCount.getOrDefault(key, 0) + 1);
        }

        Map<Integer, List<Integer>> adjacencyEdges = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            adjacencyEdges.put(i, new ArrayList<>());
            for (int j = 0; j < edges.length; j++) {
                if (i != j && checkEdge(edges[i], edges[j])) {
                    adjacencyEdges.get(i).add(j);
                }
            }
        }

        if (edgeCount.size() > 3 || (edgeCount.size() == 3 && isEqualSide)) {
            return false;
        } else if (edgeCount.size() == 1) {
            if (!isEqualSide) {
                return false;
            }
        } else if (edgeCount.size() == 2 || edgeCount.size() == 3) {
            for (List<Integer> connections : adjacencyEdges.values()) {
                if (connections.size() < 5) {
                    return false;
                }
            }
        }

        return true;
    }

    public static List<int[][]> readBoxesFromFile(String fileName) {
        List<int[][]> boxes = new ArrayList<>();
        List<List<Integer>> currentSheets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    if (currentSheets.size() == 6) {
                        boxes.add(convertListToMatrix(currentSheets));
                        currentSheets.clear();
                    } else {
                        System.out.println("Количество листов меньше 6");
                    }
                } else {
                    if (currentSheets.size() < 6) {
                        List<Integer> sheet = new ArrayList<>();
                        String[] dimensions = line.split(" ");
                        for (String part : dimensions) {
                            sheet.add(Integer.parseInt(part));
                        }
                        currentSheets.add(sheet);
                    } else {
                        System.out.println("Количество листов больше 6");
                    }
                }
            }

            if (currentSheets.size() == 6) {
                boxes.add(convertListToMatrix(currentSheets));
            } else {
                System.out.println("Количество листов меньше 6");
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return boxes;
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

    public static void printBox(int[][] box) {
        for (int i = 0; i < box.length; i++) {
            for (int j = 0; j < box[i].length; j++) {
                System.out.print(box[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String fileName = "sheets.txt";
        List<int[][]> boxes = readBoxesFromFile(fileName);
        for (int[][] sheets : boxes) {
            printBox(sheets);
            if (checkParallelepiped(sheets)) {
                System.out.println("Возможно");
            } else {
                System.out.println("Невозможно");
            }
            System.out.println();
        }
    }
}
