package ru.nngasu.datadriven;

import ru.nngasu.sedlo.Point;

import java.util.ArrayList;
import java.util.List;

/** Утилиты для парсинга CSV-полей в структуры. */
final class CsvParser {

    private CsvParser() {}

    /** "1 2 3;4 5 6" -> int[2][3] = {{1,2,3},{4,5,6}} */
    static int[][] parseMatrix(String s) {
        String[] rows = s.split(";");
        int[][] m = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] nums = rows[i].trim().split("\\s+");
            m[i] = new int[nums.length];
            for (int j = 0; j < nums.length; j++) {
                m[i][j] = Integer.parseInt(nums[j]);
            }
        }
        return m;
    }

    /** "3 6" -> {3, 6} */
    static int[] parseIntVector(String s) {
        String[] parts = s.trim().split("\\s+");
        int[] v = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            v[i] = Integer.parseInt(parts[i]);
        }
        return v;
    }

    /**
     * "42,0,0,MIN_IN_ROW_MAX_IN_COL;5,1,0,MIN_IN_ROW_MAX_IN_COL" -> список Point.
     * Пустая строка -> пустой список.
     */
    static List<Point> parsePoints(String s) {
        List<Point> result = new ArrayList<>();
        if (s == null || s.isBlank()) {
            return result;
        }
        for (String chunk : s.split(";")) {
            String[] parts = chunk.trim().split(",");
            if (parts.length != 4) {
                throw new IllegalArgumentException("Bad point chunk: " + chunk);
            }
            result.add(new Point(
                    Integer.parseInt(parts[0].trim()),
                    Integer.parseInt(parts[1].trim()),
                    Integer.parseInt(parts[2].trim()),
                    Point.Kind.valueOf(parts[3].trim())
            ));
        }
        return result;
    }
}