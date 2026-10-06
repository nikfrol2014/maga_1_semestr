package ru.nngasu.datadriven;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.nngasu.sedlo.Point;
import ru.nngasu.sedlo.Sedlo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("datadriven")
@DisplayName("Sedlo: data-driven тесты (CSV) (ЛР4)")
public class SedloCsvTest {

    @ParameterizedTest(name = "[{index}] {0} (t={2})")
    @CsvFileSource(resources = "/sedlo-matrix-cases.csv", numLinesToSkip = 1)
    @DisplayName("max из CSV")
    void maxFromCsv(String caseName, String matrix, String t,
                    String expectedMax, String expectedMin) {
        int[][] m = CsvParser.parseMatrix(matrix);
        Sedlo s = new Sedlo(m);
        int[] expected = CsvParser.parseIntVector(expectedMax);

        assertArrayEquals(expected, s.max(m, t.charAt(0)),
                "case=" + caseName + " expected=" + expectedMax);
    }

    @ParameterizedTest(name = "[{index}] {0} (t={2})")
    @CsvFileSource(resources = "/sedlo-matrix-cases.csv", numLinesToSkip = 1)
    @DisplayName("min из CSV")
    void minFromCsv(String caseName, String matrix, String t,
                    String expectedMax, String expectedMin) {
        int[][] m = CsvParser.parseMatrix(matrix);
        Sedlo s = new Sedlo(m);
        int[] expected = CsvParser.parseIntVector(expectedMin);

        assertArrayEquals(expected, s.min(m, t.charAt(0)),
                "case=" + caseName + " expected=" + expectedMin);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "/sedlo-points-cases.csv", numLinesToSkip = 1)
    @DisplayName("sedlo() из CSV")
    void sedloFromCsv(String caseName, String matrix, String expectedPoints) {
        int[][] m = CsvParser.parseMatrix(matrix);
        List<Point> expected = CsvParser.parsePoints(expectedPoints);
        List<Point> actual = new Sedlo(m).sedlo();

        assertEquals(expected.size(), actual.size(),
                "case=" + caseName + " expected=" + expected + " actual=" + actual);
        assertTrue(actual.containsAll(expected),
                "case=" + caseName + " expected=" + expected + " actual=" + actual);
    }
}