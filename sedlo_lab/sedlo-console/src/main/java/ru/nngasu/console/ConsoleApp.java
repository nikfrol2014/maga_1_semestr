package ru.nngasu.console;

import ru.nngasu.sedlo.Point;
import ru.nngasu.sedlo.Sedlo;
import ru.nngasu.sedlo.SedloException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Консольное приложение: читает матрицу, печатает её седловые точки.
 *
 * <p>Формат ввода:
 * <pre>
 *   rows cols
 *   a11 a12 ... a1c
 *   ...
 *   ar1 ar2 ... arc
 * </pre>
 *
 * <p>Формат вывода: одна седловая точка на строку в виде
 * {@code value row col kind} (row/col — 0-based).
 * Если точек нет — печатается {@code no saddle points}.
 */
public class ConsoleApp {

    public static void main(String[] args) {
        int exitCode = run(System.in, System.out);
        if (exitCode != 0) {
            System.exit(exitCode);
        }
    }

    /**
     * Тестируемая точка входа. Не трогает {@link System#exit}.
     *
     * @return 0 — успех, 1 — ошибка ввода/данных
     */
    public static int run(java.io.InputStream in, PrintStream out) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {

            int[][] matrix = readMatrix(reader);
            List<Point> points = new Sedlo(matrix).sedlo();

            if (points.isEmpty()) {
                out.println("no saddle points");
            } else {
                for (Point p : points) {
                    out.printf("%d %d %d %s%n",
                            p.value(), p.row(), p.col(), p.kind());
                }
            }
            return 0;

        } catch (SedloException e) {
            out.println("error: " + e.getMessage());
            return 1;
        } catch (IOException | NumberFormatException e) {
            out.println("error: " + e.getMessage());
            return 1;
        }
    }

    private static int[][] readMatrix(BufferedReader reader) throws IOException {
        String header = reader.readLine();
        if (header == null) {
            throw new NumberFormatException("empty input");
        }
        String[] parts = header.trim().split("\\s+");
        if (parts.length != 2) {
            throw new NumberFormatException(
                    "first line must be 'rows cols', got: " + header);
        }
        int rows = Integer.parseInt(parts[0]);
        int cols = Integer.parseInt(parts[1]);

        int[][] m = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = reader.readLine();
            if (line == null) {
                throw new NumberFormatException(
                        "unexpected end of input at row " + i);
            }
            String[] nums = line.trim().split("\\s+");
            if (nums.length != cols) {
                throw new NumberFormatException(
                        "row " + i + " must contain " + cols
                                + " numbers, got: " + nums.length);
            }
            for (int j = 0; j < cols; j++) {
                m[i][j] = Integer.parseInt(nums[j]);
            }
        }
        return m;
    }
}