package ru.nngasu.sedlo;

/**
 * Исключение для ошибок работы с матрицей в классе {@link Sedlo}.
 * Unchecked — чтобы не засорять сигнатуры методов.
 */
public class SedloException extends RuntimeException {

    public SedloException(String message) {
        super(message);
    }

    public SedloException(String message, Throwable cause) {
        super(message, cause);
    }
}