# sedlo-core — ЛР1: unit-тесты класса Sedlo

Бизнес-логика поиска седловых точек + базовые unit-тесты.

## Содержимое
```text
src/main/java/ru/nngasu/sedlo/
├── Point.java — record (value, row, col, kind)
├── Sedlo.java — основной класс
└── SedloException.java — unchecked-исключение

src/test/java/ru/nngasu/sedlo/
└── SedloTest.java — 13 тестов в @Nested-группах
```

## API

```java
public class Sedlo {
    public Sedlo(int[][] m);
    public int[] max(int[][] m, char t);
    public int[] min(int[][] m, char t);
    public List<Point> sedlo();
}

public record Point(int value, int row, int col, Kind kind) {
    public enum Kind {
        MIN_IN_ROW_MAX_IN_COL,
        MAX_IN_ROW_MIN_IN_COL
    }
}
```
## Исключения
```text
SedloException — null/пустая/неровная матрица, null-строка.

IllegalArgumentException — t не 'r' и не 'c'.```
```
## Тесты
* SedloTest содержит 4 @Nested-класса:

* Ctor — конструктор и валидация;

* Max — максимумы по строкам/столбцам;

* Min — минимумы;

* SedloPoints — сценарии поиска седловых точек, включая 1×1.

## Запуск
```bash
mvn -pl sedlo-core -am test
```
