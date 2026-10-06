package ru.nngasu.sedlo;

/**
 * Седловая точка матрицы.
 *
 * @param value значение элемента
 * @param row   индекс строки (0-based)
 * @param col   индекс столбца (0-based)
 * @param kind  тип седловой точки
 */
public record Point(int value, int row, int col, Kind kind) {

    /** Тип седловой точки. */
    public enum Kind {
        /** Минимум в строке и максимум в столбце. */
        MIN_IN_ROW_MAX_IN_COL,
        /** Максимум в строке и минимум в столбце. */
        MAX_IN_ROW_MIN_IN_COL
    }
}