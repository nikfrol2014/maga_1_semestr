package ru.nngasu.console;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("fast")
@Tag("console")
@DisplayName("ConsoleApp: тесты ввода/вывода (ЛР2)")
public class ConsoleAppTest {

    private static final String NL = System.lineSeparator();

    @Test
    @DisplayName("печатает одну седловую точку")
    void printsOnePoint() {
        String input = "3 3" + NL
                + "1 5 9" + NL
                + "5 6 7" + NL
                + "4 8 3" + NL;

        try (ConsoleTestSupport cs = new ConsoleTestSupport(input)) {
            int code = ConsoleApp.run(System.in, System.out);
            assertEquals(0, code);
            String out = cs.output();
            assertTrue(out.contains("5 1 0 MIN_IN_ROW_MAX_IN_COL"),
                    "Ожидали точку (1,0)=5, получено:\n" + out);
        }
    }

    @Test
    @DisplayName("нет седловых точек -> 'no saddle points'")
    void noPoints() {
        String input = "2 2" + NL
                + "1 2" + NL
                + "2 1" + NL;

        try (ConsoleTestSupport cs = new ConsoleTestSupport(input)) {
            int code = ConsoleApp.run(System.in, System.out);
            assertEquals(0, code);
            assertEquals("no saddle points", cs.output().trim());
        }
    }

    @Test
    @DisplayName("матрица 1x1 -> две записи (оба типа)")
    void oneByOne() {
        String input = "1 1" + NL
                + "42" + NL;

        try (ConsoleTestSupport cs = new ConsoleTestSupport(input)) {
            int code = ConsoleApp.run(System.in, System.out);
            assertEquals(0, code);
            String out = cs.output();
            assertTrue(out.contains("42 0 0 MIN_IN_ROW_MAX_IN_COL"), out);
            assertTrue(out.contains("42 0 0 MAX_IN_ROW_MIN_IN_COL"), out);
        }
    }

    @Test
    @DisplayName("пустой ввод -> error, код 1")
    void emptyInput() {
        try (ConsoleTestSupport cs = new ConsoleTestSupport("")) {
            int code = ConsoleApp.run(System.in, System.out);
            assertEquals(1, code);
            assertTrue(cs.output().startsWith("error:"), cs.output());
        }
    }

    @Test
    @DisplayName("неправильный заголовок -> error")
    void badHeader() {
        String input = "3" + NL + "1 2 3" + NL;
        try (ConsoleTestSupport cs = new ConsoleTestSupport(input)) {
            int code = ConsoleApp.run(System.in, System.out);
            assertEquals(1, code);
            assertTrue(cs.output().contains("rows cols"), cs.output());
        }
    }

    @Test
    @DisplayName("в строке меньше чисел, чем заявлено -> error")
    void shortRow() {
        String input = "2 3" + NL
                + "1 2 3" + NL
                + "4 5" + NL;
        try (ConsoleTestSupport cs = new ConsoleTestSupport(input)) {
            int code = ConsoleApp.run(System.in, System.out);
            assertEquals(1, code);
            assertTrue(cs.output().contains("row 1"), cs.output());
        }
    }

    @Test
    @DisplayName("нечисловое значение -> error")
    void notANumber() {
        String input = "1 1" + NL + "abc" + NL;
        try (ConsoleTestSupport cs = new ConsoleTestSupport(input)) {
            int code = ConsoleApp.run(System.in, System.out);
            assertEquals(1, code);
            assertTrue(cs.output().startsWith("error:"), cs.output());
        }
    }
}