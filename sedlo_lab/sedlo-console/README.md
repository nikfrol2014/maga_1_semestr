# sedlo-console — ЛР2: тесты ввода/вывода

Консольное приложение: читает матрицу из stdin, печатает седловые точки.
Тесты покрывают оба потока через подмену `System.in`/`System.out`.

## Содержимое
```text
src/main/java/ru/nngasu/console/
└── ConsoleApp.java

src/test/java/ru/nngasu/console/
├── ConsoleTestSupport.java — AutoCloseable-хелпер подмены потоков
└── ConsoleAppTest.java — 7 тестов
```

## Формат ввода
```text
rows cols
a11 a12 ... a1c
...
ar1 ar2 ... arc
```

## Формат вывода
```text
<value> <row> <col> <KIND>
```

где `KIND` ∈ {`MIN_IN_ROW_MAX_IN_COL`, `MAX_IN_ROW_MIN_IN_COL`}.

Если точек нет — `no saddle points`.
При ошибке ввода — `error: <сообщение>` и код возврата 1.

## Ключевое решение

`main` делегирует всю работу в:

```java
public static int run(InputStream in, PrintStream out)
```
run не вызывает System.exit — возвращает код. Это позволяет тестам
запускать приложение в своей JVM.

## Запуск тестов
```bash
mvn -pl sedlo-console -am test
```
Ручной запуск
```bash
printf "3 3\n1 5 9\n5 6 7\n4 8 3\n" | \
  java -cp "sedlo-console/target/classes:sedlo-core/target/classes" \
       ru.nngasu.console.ConsoleApp
```
(на Windows ; в classpath, на Linux/macOS — :)