package ru.nngasu.sedlo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("fast")
@Tag("core")
@DisplayName("Sedlo: базовые тесты (ЛР1)")
public class SedloTest {

    private static final int[][] M2x3 = {
            {1, 2, 3},
            {4, 5, 6}
    };

    @Nested
    @DisplayName("Конструктор")
    class Ctor {

        @Test
        @DisplayName("создаётся на корректной матрице")
        void ok() {
            assertDoesNotThrow(() -> new ru.nngasu.sedlo.Sedlo(M2x3));
        }

        @Test
        @DisplayName("null -> SedloException")
        void nullMatrix() {
            assertThrows(SedloException.class, () -> new Sedlo(null));
        }

        @Test
        @DisplayName("пустая матрица -> SedloException")
        void empty() {
            assertThrows(SedloException.class, () -> new Sedlo(new int[0][]));
        }

        @Test
        @DisplayName("неровная матрица -> SedloException")
        void notRectangular() {
            int[][] bad = {{1, 2}, {3}};
            assertThrows(SedloException.class, () -> new Sedlo(bad));
        }
    }

    @Nested
    @DisplayName("max()")
    class Max {

        @Test
        void byRows() {
            Sedlo s = new Sedlo(M2x3);
            assertArrayEquals(new int[]{3, 6}, s.max(M2x3, 'r'));
        }

        @Test
        void byCols() {
            Sedlo s = new Sedlo(M2x3);
            assertArrayEquals(new int[]{4, 5, 6}, s.max(M2x3, 'c'));
        }

        @Test
        void badAxis() {
            Sedlo s = new Sedlo(M2x3);
            assertThrows(IllegalArgumentException.class, () -> s.max(M2x3, 'x'));
        }
    }

    @Nested
    @DisplayName("min()")
    class Min {

        @Test
        void byRows() {
            Sedlo s = new Sedlo(M2x3);
            assertArrayEquals(new int[]{1, 4}, s.min(M2x3, 'r'));
        }

        @Test
        void byCols() {
            Sedlo s = new Sedlo(M2x3);
            assertArrayEquals(new int[]{1, 2, 3}, s.min(M2x3, 'c'));
        }
    }

    @Nested
    @DisplayName("sedlo()")
    class SedloPoints {

        @Test
        @DisplayName("классическая седловая точка: min в строке, max в столбце")
        void classic() {
            // 5 в (1,0): min в строке {5,6,7}, max в столбце {1,5,4}
            int[][] m = {
                    {1, 5, 9},
                    {5, 6, 7},
                    {4, 8, 3}
            };
            List<Point> points = new Sedlo(m).sedlo();
            assertTrue(points.stream().anyMatch(p ->
                            p.value() == 5 && p.row() == 1 && p.col() == 0
                                    && p.kind() == Point.Kind.MIN_IN_ROW_MAX_IN_COL),
                    "Ожидали точку (1,0)=5 типа MIN_IN_ROW_MAX_IN_COL, получено: " + points);
        }

        @Test
        @DisplayName("max в строке, min в столбце")
        void reverse() {
            // 5 — max в строке 0 (1,2,5) и min в столбце 2 (5,9)
            int[][] m = {
                    {1, 2, 5},
                    {6, 7, 9}
            };
            List<Point> points = new Sedlo(m).sedlo();
            assertTrue(points.stream().anyMatch(p ->
                    p.value() == 5 && p.row() == 0 && p.col() == 2
                            && p.kind() == Point.Kind.MAX_IN_ROW_MIN_IN_COL));
        }

        @Test
        @DisplayName("нет седловых точек")
        void none() {
            int[][] m = {
                    {1, 2},
                    {2, 1}
            };
            assertTrue(new ru.nngasu.sedlo.Sedlo(m).sedlo().isEmpty());
        }

        @Test
        @DisplayName("квадратная матрица 1x1 — точка одновременно двух типов")
        void oneByOne() {
            int[][] m = {{42}};
            List<Point> points = new Sedlo(m).sedlo();
            assertEquals(2, points.size());
            assertTrue(points.stream().allMatch(p -> p.value() == 42));
        }
    }
}