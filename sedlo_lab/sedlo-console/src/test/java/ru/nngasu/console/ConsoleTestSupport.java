package ru.nngasu.console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Хелпер для тестов, работающих с {@link System#in}/{@link System#out}.
 * Восстанавливает исходные потоки в {@link AutoCloseable#close()}.
 */
final public  class ConsoleTestSupport implements AutoCloseable {

    private final InputStream origIn;
    private final PrintStream origOut;
    private final ByteArrayOutputStream capturedOut;

    ConsoleTestSupport(String stdin) {
        this.origIn = System.in;
        this.origOut = System.out;
        this.capturedOut = new ByteArrayOutputStream();

        System.setIn(new ByteArrayInputStream(stdin.getBytes(StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(capturedOut, true, StandardCharsets.UTF_8));
    }

    String output() {
        System.out.flush();
        return capturedOut.toString(StandardCharsets.UTF_8);
    }

    @Override
    public void close() {
        System.setIn(origIn);
        System.setOut(origOut);
    }
}