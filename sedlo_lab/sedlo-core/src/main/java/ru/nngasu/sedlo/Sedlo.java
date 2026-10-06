package ru.nngasu.sedlo;

import java.util.ArrayList;
import java.util.List;

/**
 * Поиск седловых точек матрицы и векторов максимумов/минимумов
 * по строкам или столбцам.
 *
 * <p>Седловая точка — элемент, который одновременно:
 * <ul>
 *   <li>минимум в своей строке и максимум в своём столбце, либо</li>
 *   <li>максимум в своей строке и минимум в своём столбце.</li>
 * </ul>
 */
public class Sedlo {

    /** Исходная матрица. */
    private final int[][] m;

    /**
     * @param m исходная матрица (не {@code null}, непустая, прямоугольная)
     * @throws SedloException если матрица {@code null}, пустая или непрямоугольная
     */
    public Sedlo(int[][] m) {
        validateMatrix(m);
        this.m = deepCopy(m);
    }

    /**
     * Вектор максимальных элементов матрицы по строкам ({@code t='r'})
     * или по столбцам ({@code t='c'}).
     *
     * @param m матрица (проверяется так же, как в конструкторе)
     * @param t {@code 'r'} — по строкам, {@code 'c'} — по столбцам
     * @return массив максимумов
     * @throws SedloException если матрица некорректна
     * @throws IllegalArgumentException если {@code t} не {@code 'r'} и не {@code 'c'}
     */
    public int[] max(int[][] m, char t) {
        return reduce(m, t, true);
    }

    /**
     * Вектор минимальных элементов матрицы по строкам ({@code t='r'})
     * или по столбцам ({@code t='c'}).
     *
     * @param m матрица
     * @param t {@code 'r'} — по строкам, {@code 'c'} — по столбцам
     * @return массив минимумов
     * @throws SedloException если матрица некорректна
     * @throws IllegalArgumentException если {@code t} не {@code 'r'} и не {@code 'c'}
     */
    public int[] min(int[][] m, char t) {
        return reduce(m, t, false);
    }

    /**
     * Список всех седловых точек матрицы (оба типа).
     *
     * @return список точек; пустой, если седловых точек нет
     */
    public List<Point> sedlo() {
        int rows = m.length;
        int cols = m[0].length;

        int[] rowMin = min(m, 'r');
        int[] rowMax = max(m, 'r');
        int[] colMin = min(m, 'c');
        int[] colMax = max(m, 'c');

        List<Point> result = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int v = m[i][j];
                if (v == rowMin[i] && v == colMax[j]) {
                    result.add(new Point(v, i, j, Point.Kind.MIN_IN_ROW_MAX_IN_COL));
                }
                if (v == rowMax[i] && v == colMin[j]) {
                    result.add(new Point(v, i, j, Point.Kind.MAX_IN_ROW_MIN_IN_COL));
                }
            }
        }
        return result;
    }

    // ---------- internals ----------

    private int[] reduce(int[][] matrix, char t, boolean findMax) {
        validateMatrix(matrix);
        if (t != 'r' && t != 'c') {
            throw new IllegalArgumentException(
                    "Parameter t must be 'r' or 'c', but was: '" + t + "'");
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] result;

        if (t == 'r') {
            result = new int[rows];
            for (int i = 0; i < rows; i++) {
                int acc = matrix[i][0];
                for (int j = 1; j < cols; j++) {
                    acc = findMax ? Math.max(acc, matrix[i][j])
                            : Math.min(acc, matrix[i][j]);
                }
                result[i] = acc;
            }
        } else {
            result = new int[cols];
            for (int j = 0; j < cols; j++) {
                int acc = matrix[0][j];
                for (int i = 1; i < rows; i++) {
                    acc = findMax ? Math.max(acc, matrix[i][j])
                            : Math.min(acc, matrix[i][j]);
                }
                result[j] = acc;
            }
        }
        return result;
    }

    private static void validateMatrix(int[][] matrix) {
        if (matrix == null) {
            throw new SedloException("Matrix must not be null");
        }
        if (matrix.length == 0) {
            throw new SedloException("Matrix must not be empty");
        }
        int cols = matrix[0].length;
        if (cols == 0) {
            throw new SedloException("Matrix rows must not be empty");
        }
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null) {
                throw new SedloException("Matrix row " + i + " must not be null");
            }
            if (matrix[i].length != cols) {
                throw new SedloException(
                        "Matrix must be rectangular: row 0 has " + cols
                                + " cols, row " + i + " has " + matrix[i].length);
            }
        }
    }

    private static int[][] deepCopy(int[][] src) {
        int[][] copy = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = src[i].clone();
        }
        return copy;
    }
}