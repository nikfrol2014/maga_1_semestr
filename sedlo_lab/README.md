# Sedlo Lab — Средства компьютерного тестирования

Лабораторный проект по дисциплине «Средства компьютерного тестирования»
(направление 09.04.02 Информационные системы и технологии, ННГАСУ).

Java 17 · Maven · JUnit 5.10.2.

## Модули

| Модуль | ЛР | Содержит |
|---|---|---|
| `sedlo-core` | ЛР1 | Класс `Sedlo`, `Point`, `SedloException` + базовые тесты |
| `sedlo-console` | ЛР2 | `ConsoleApp` + тесты ввода/вывода |
| `sedlo-exceptions` | ЛР3 | Тесты обработки исключений |
| `sedlo-datadriven` | ЛР4 | Параметризованные тесты + CSV |
| `sedlo-suite` | ЛР5 | Списки воспроизведения (`@Suite`) |

## Предметная область

`Sedlo` ищет **седловые точки** в целочисленной матрице:

- `MIN_IN_ROW_MAX_IN_COL` — минимум в строке и максимум в столбце;
- `MAX_IN_ROW_MIN_IN_COL` — максимум в строке и минимум в столбце.

```java
Sedlo s = new Sedlo(matrix);
int[] maxR = s.max(matrix, 'r');
int[] maxC = s.max(matrix, 'c');
int[] minR = s.min(matrix, 'r');
int[] minC = s.min(matrix, 'c');
List<Point> pts = s.sedlo();
Point — record: (int value, int row, int col, Kind kind).
```


## Сборка и тесты
``` bash
mvn clean install                          # всё: clean + build + tests

mvn -pl sedlo-core       -am test
mvn -pl sedlo-console    -am test
mvn -pl sedlo-exceptions -am test
mvn -pl sedlo-datadriven -am test
mvn -pl sedlo-suite      -am test
```
-am (--also-make) собирает модули, от которых зависит указанный.

HTML-отчёт о тестах
```bash
mvn test surefire-report:report -Daggregate=true
start target/site/surefire-report.html          # Windows
open  target/site/surefire-report.html          # macOS
xdg-open target/site/surefire-report.html       # Linux
Сырые отчёты: <module>/target/surefire-reports/.
```
Структура
```text
sedlo-lab/
├── pom.xml
├── README.md
├── sedlo-core/
├── sedlo-console/
├── sedlo-exceptions/
├── sedlo-datadriven/
└── sedlo-suite/
```
## Как связаны модули
sedlo-core — единственный модуль с бизнес-логикой.

sedlo-console → зависит от sedlo-core.

sedlo-exceptions, sedlo-datadriven → зависят от sedlo-core/sedlo-console.

sedlo-suite → тянет test-jar'ы всех четырёх модулей.

## Теги

|Тег	| Где|
|----|---|
core	| SedloTest
fast	| SedloTest, ConsoleAppTest
console |	ConsoleAppTest
exceptions	| SedloExceptionTest, ConsoleExceptionTest
datadriven	| SedloCsvTest, SedloMethodSourceTest
----