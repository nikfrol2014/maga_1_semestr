package ru.nngasu.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.nngasu.console.ConsoleApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@Tag("exceptions")
@DisplayName("ConsoleApp: обработка исключений (ЛР3)")
public class ConsoleExceptionTest {

    private static final String NL = System.lineSeparator();

    /** Мини-хелпер: запускает ConsoleApp.run на заданном вводе, возвращает вывод и код. */
    private record Result(String out, int code) {}

    private Result run(String stdin) {
        InputStream origIn = System.in;
        PrintStream origOut = System.out;
        try {
            System.setIn(new ByteArrayInputStream(stdin.getBytes(StandardCharsets.UTF_8)));
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            System.setOut(new PrintStream(buf, true, StandardCharsets.UTF_8));

            int code = ConsoleApp.run(System.in, System.out);
            System.out.flush();
            return new Result(buf.toString(StandardCharsets.UTF_8), code);
        } finally {
            System.setIn(origIn);
            System.setOut(origOut);
        }
    }

    @Test
    @DisplayName("пустой ввод: NumberFormatException пойман -> код 1")
    void emptyInput() {
        Result r = run("");
        assertEquals(1, r.code());
        assertTrue(r.out().startsWith("error:"), r.out());
    }

    @Test
    @DisplayName("некорректный заголовок: пойман -> код 1")
    void badHeader() {
        Result r = run("3" + NL + "1 2 3" + NL);
        assertEquals(1, r.code());
        assertTrue(r.out().contains("rows cols"), r.out());
    }

    @Test
    @DisplayName("короткая строка матрицы: пойман -> код 1")
    void shortRow() {
        Result r = run("2 3" + NL + "1 2 3" + NL + "4 5" + NL);
        assertEquals(1, r.code());
        assertTrue(r.out().contains("row 1"), r.out());
    }

    @Test
    @DisplayName("нечисловое значение: NumberFormatException пойман -> код 1")
    void notANumber() {
        Result r = run("1 1" + NL + "abc" + NL);
        assertEquals(1, r.code());
        assertTrue(r.out().startsWith("error:"), r.out());
    }

    @Test
    @DisplayName("обрыв ввода: unexpected end of input пойман -> код 1")
    void truncated() {
        Result r = run("3 3" + NL + "1 2 3" + NL);
        assertEquals(1, r.code());
        assertTrue(r.out().contains("unexpected end"), r.out());
    }

    @Test
    @DisplayName("SedloException (нулевые размеры) поймана -> код 1")
    void zeroSizeMatrix() {
        Result r = run("0 0" + NL);
        assertEquals(1, r.code());
        assertTrue(r.out().startsWith("error:"), r.out());
    }

    @Test
    @DisplayName("корректный ввод -> код 0, никаких error")
    void happyPath() {
        Result r = run("1 1" + NL + "42" + NL);
        assertEquals(0, r.code());
        assertFalse(r.out().contains("error:"), r.out());
    }
}