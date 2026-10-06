# sedlo-exceptions — ЛР3: тесты обработки исключений

Модуль **только с тестами** (нет `src/main`). Проверяет:

1. `Sedlo` корректно бросает исключения на плохих данных;
2. `ConsoleApp.run` ловит их и возвращает код 1 + `error: ...`.

## Содержимое
```text
src/test/java/ru/nngasu/exceptions/
├── SedloExceptionTest.java — 9 тестов на assertThrows
└── ConsoleExceptionTest.java — 7 тестов на обработку исключений в ConsoleApp
```

## Что проверяется

### SedloExceptionTest

- `new Sedlo(null)` → `SedloException` (сообщение содержит `null`);
- пустая матрица → `SedloException` (`empty`);
- нулевая ширина → `SedloException`;
- неровная матрица → `SedloException` (`rectangular`);
- null-строка → `SedloException` (`row 1`);
- корректная матрица → `assertDoesNotThrow`;
- неверный `t` → `IllegalArgumentException`;
- `max/min` на null → `SedloException` (не NPE);
- валидные оси → без исключений.

### ConsoleExceptionTest

- пустой ввод → код 1 + `error:`;
- плохой заголовок → `error: ... rows cols ...`;
- короткая строка → `error: ... row 1 ...`;
- нечисловое значение → `error:`;
- обрыв ввода → `error: ... unexpected end ...`;
- нулевые размеры → `SedloException` поймана, код 1;
- корректный ввод → код 0, без `error:`.

## Запуск

```bash
mvn -pl sedlo-exceptions -am test
```