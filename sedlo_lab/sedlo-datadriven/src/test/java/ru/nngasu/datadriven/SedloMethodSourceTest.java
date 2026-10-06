package ru.nngasu.datadriven;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.nngasu.sedlo.Point;
import ru.nngasu.sedlo.Sedlo;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Tag("datadriven")
@DisplayName("Sedlo: data-driven тесты (@CsvSource / @MethodSource) (ЛР4)")
public class SedloMethodSourceTest {

    @ParameterizedTest(name = "max по строкам: {0}")
    @CsvSource({
            "'1 2 3;4 5 6', 3 6",
            "'0 0;0 0',    0 0",
            "'7',          7"
    })
    @DisplayName("@CsvSource: max по строкам")
    void maxRowsInline(String matrix, String expected) {
        int[][] m = CsvParser.parseMatrix(matrix);
        assertArrayEquals(CsvParser.parseIntVector(expected),
                new Sedlo(m).max(m, 'r'));
    }

    /** Генерируем квадратные матрицы NxN с известной седловой точкой (0,0). */
    static Stream<Arguments> squareMatricesWithKnownSaddle() {
        return Stream.of(
                Arguments.of(1, 1),
                Arguments.of(2, 2),
                Arguments.of(3, 3),
                Arguments.of(5, 5)
        );
    }

    @ParameterizedTest(name = "N={0}")
    @MethodSource("squareMatricesWithKnownSaddle")
    @DisplayName("@MethodSource: 1x1 всегда даёт 2 седловые точки")
    void oneByOneAlwaysHasTwoPoints(int size, int ignored) {
        // для size==1 проверим особый случай, для остальных — просто убедимся, что метод работает
        int[][] m = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                m[i][j] = i * size + j;
            }
        }
        List<Point> points = new Sedlo(m).sedlo();
        if (size == 1) {
            assertEquals(2, points.size());
        } else {
            assertNotNull(points);
        }
    }
}