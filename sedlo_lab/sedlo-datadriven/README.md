# sedlo-datadriven — ЛР4: data-driven тесты

Модуль **только с тестами**. Два подхода к параметризации:

- `@CsvFileSource` — данные из внешнего CSV;
- `@CsvSource` / `@MethodSource` — данные из кода.

## Содержимое
```text
src/test/java/ru/nngasu/datadriven/
├── CsvParser.java — парсит строки CSV в int[][], int[], List<Point>
├── SedloCsvTest.java — @CsvFileSource, 20 запусков
└── SedloMethodSourceTest.java — @CsvSource + @MethodSource, 7 запусков

src/test/resources/
├── sedlo-matrix-cases.csv — 8 кейсов для max/min
└── sedlo-points-cases.csv — 4 кейса для sedlo()
```

## Формат CSV

### sedlo-matrix-cases.csv
```text
caseName,matrix,t,expectedMax,expectedMin
2x3_rows,"1 2 3;4 5 6",r,"3 6","1 4"
```


- `matrix` — строки через `;`, элементы через пробел;
- `t` — `'r'` или `'c'`;
- `expected*` — значения через пробел.

### sedlo-points-cases.csv
```text
caseName,matrix,expectedPoints
one_by_one,"42","42,0,0,MIN_IN_ROW_MAX_IN_COL;42,0,0,MAX_IN_ROW_MIN_IN_COL"
```

- `expectedPoints` — точки через `;`, формат `value,row,col,kind`;
- пусто = точек нет.

## Почему CSV в src/test/resources

Это **тестовые данные**. Maven копирует их в `target/test-classes`, оттуда —
в `test-jar`, оттуда — в classpath `sedlo-suite`.

## Запуск

```bash
mvn -pl sedlo-datadriven -am test
Каждый кейс CSV = отдельный запуск с именем [N] caseName (t=r).
```
