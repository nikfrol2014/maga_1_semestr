package ru.nngasu.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.nngasu.sedlo.Sedlo;
import ru.nngasu.sedlo.SedloException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("exceptions")
@DisplayName("Sedlo: тесты исключений (ЛР3)")
public class SedloExceptionTest {

    @Nested
    @DisplayName("Конструктор: SedloException")
    class Ctor {

        @Test
        @DisplayName("null -> SedloException с осмысленным сообщением")
        void nullMatrix() {
            SedloException ex = assertThrows(SedloException.class,
                    () -> new Sedlo(null));
            assertTrue(ex.getMessage().toLowerCase().contains("null"),
                    "Сообщение: " + ex.getMessage());
        }

        @Test
        @DisplayName("пустая матрица -> SedloException")
        void emptyMatrix() {
            SedloException ex = assertThrows(SedloException.class,
                    () -> new Sedlo(new int[0][]));
            assertTrue(ex.getMessage().toLowerCase().contains("empty"),
                    "Сообщение: " + ex.getMessage());
        }

        @Test
        @DisplayName("нулевая ширина строки -> SedloException")
        void zeroCols() {
            SedloException ex = assertThrows(SedloException.class,
                    () -> new Sedlo(new int[][]{{}, {}}));
            assertTrue(ex.getMessage().toLowerCase().contains("empty"),
                    "Сообщение: " + ex.getMessage());
        }

        @Test
        @DisplayName("неровная матрица -> SedloException")
        void notRectangular() {
            int[][] bad = {{1, 2}, {3}};
            SedloException ex = assertThrows(SedloException.class,
                    () -> new Sedlo(bad));
            assertTrue(ex.getMessage().toLowerCase().contains("rectangular"),
                    "Сообщение: " + ex.getMessage());
        }

        @Test
        @DisplayName("null-строка матрицы -> SedloException")
        void nullRow() {
            int[][] bad = {{1, 2}, null, {3, 4}};
            SedloException ex = assertThrows(SedloException.class,
                    () -> new Sedlo(bad));
            assertTrue(ex.getMessage().contains("row 1"),
                    "Сообщение: " + ex.getMessage());
        }

        @Test
        @DisplayName("корректная матрица -> без исключений")
        void ok() {
            assertDoesNotThrow(() -> new Sedlo(new int[][]{{1, 2}, {3, 4}}));
        }
    }

    @Nested
    @DisplayName("max/min: IllegalArgumentException при плохом t")
    class BadAxis {

        @Test
        void maxBadAxis() {
            Sedlo s = new Sedlo(new int[][]{{1, 2}, {3, 4}});
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> s.max(new int[][]{{1, 2}, {3, 4}}, 'x'));
            assertTrue(ex.getMessage().contains("'x'"),
                    "Сообщение: " + ex.getMessage());
        }

        @Test
        void minBadAxis() {
            Sedlo s = new Sedlo(new int[][]{{1, 2}, {3, 4}});
            assertThrows(IllegalArgumentException.class,
                    () -> s.min(new int[][]{{1, 2}, {3, 4}}, 'Z'));
        }

        @Test
        @DisplayName("валидные оси не бросают")
        void validAxes() {
            Sedlo s = new Sedlo(new int[][]{{1, 2}, {3, 4}});
            assertDoesNotThrow(() -> s.max(new int[][]{{1, 2}, {3, 4}}, 'r'));
            assertDoesNotThrow(() -> s.max(new int[][]{{1, 2}, {3, 4}}, 'c'));
            assertDoesNotThrow(() -> s.min(new int[][]{{1, 2}, {3, 4}}, 'r'));
            assertDoesNotThrow(() -> s.min(new int[][]{{1, 2}, {3, 4}}, 'c'));
        }

        @Test
        @DisplayName("max/min на null-матрице -> SedloException (не NPE)")
        void nullMatrixInMax() {
            Sedlo s = new Sedlo(new int[][]{{1, 2}, {3, 4}});
            assertThrows(SedloException.class, () -> s.max(null, 'r'));
            assertThrows(SedloException.class, () -> s.min(null, 'r'));
        }
    }
}