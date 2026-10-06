# sedlo-suite — ЛР5: списки воспроизведения (suites)

Модуль **только с suite-классами** (нет `src/main`). Каждый suite —
это `@Suite`-класс без собственных `@Test`-методов. Он описывает,
**какие тесты из других модулей запустить**.

## Содержимое
```text
src/test/java/ru/nngasu/suite/
├── AllTestsSuiteTest.java — все тесты всех модулей
├── FastTestsSuiteTest.java — только @Tag("fast")
├── DataDrivenSuiteTest.java — только @Tag("datadriven")
└── CoreOnlySuiteTest.java — только SedloTest
```

## Как подключены чужие тесты

`pom.xml` тянет **test-jar'ы** всех 4 модулей:

```xml
<dependency>
    <groupId>ru.nngasu</groupId>
    <artifactId>sedlo-core</artifactId>
    <type>test-jar</type>
    <scope>test</scope>
</dependency>
...
```
Без этого классов SedloTest и т.п. не было бы в classpath.

## Содержимое suite'ов
### AllTestsSuiteTest
```java
@Suite
@SuiteDisplayName("ALL tests (все модули)")
@SelectClasses({
        ru.nngasu.sedlo.SedloTest.class,
        ru.nngasu.console.ConsoleAppTest.class,
        ru.nngasu.exceptions.SedloExceptionTest.class,
        ru.nngasu.exceptions.ConsoleExceptionTest.class,
        ru.nngasu.datadriven.SedloCsvTest.class,
        ru.nngasu.datadriven.SedloMethodSourceTest.class
})
public class AllTestsSuiteTest {}
```
### FastTestsSuiteTest
@SelectClasses({SedloTest, ConsoleAppTest}) + @IncludeTags("fast").

### DataDrivenSuiteTest
@SelectClasses({SedloCsvTest, SedloMethodSourceTest}).

### CoreOnlySuiteTest
@SelectClasses({SedloTest})


### Почему @SelectClasses, а не @SelectPackages
@SelectPackages("ru.nngasu") в multi-module Maven часто не находит классы:
Suite Engine сканирует classpath через Launcher API, а Surefire формирует
classpath фрагментами. @SelectClasses ссылается напрямую — работает всегда,
если классы доступны на компиляции.

Условие: тестовые классы должны быть public — из другого пакета
package-private недоступны.

### Почему имена suite'ов заканчиваются на Test
Surefire по умолчанию ищет только *Test, Test*, *Tests, *TestCase.
Имена AllTestsSuite под маску не подпадают — Surefire их не запускает.
Поэтому *SuiteTest.

### Что значит 0 в HTML-отчёте для ru.nngasu.suite
Surefire группирует тесты по исходному классу, где лежит @Test.
Метод SedloTest.someTest() физически в классе SedloTest —
он и учитывается в ru.nngasu.sedlo, вне зависимости от того, кто его
запустил. В ru.nngasu.suite собственных @Test нет — потому 0.

#### Это нормальное поведение surefire-report-plugin.

## Признаки работающих suite'ов
```text
[INFO] Running ru.nngasu.suite.AllTestsSuiteTest
[INFO] Tests run: 62, Failures: 0

[INFO] Running ru.nngasu.suite.CoreOnlySuiteTest
[INFO] Tests run: 13, Failures: 0

[INFO] Running ru.nngasu.suite.DataDrivenSuiteTest
[INFO] Tests run: 27, Failures: 0

[INFO] Running ru.nngasu.suite.FastTestsSuiteTest
[INFO] Tests run: 20, Failures: 0
```
Плюс XML-файлы sedlo-suite/target/surefire-reports/TEST-ru.nngasu.suite.*.xml
с атрибутом tests="N".

Запуск
```bash
mvn -pl sedlo-suite -am test
```
# Один suite
```bash
mvn -pl sedlo-suite test -Dtest=FastTestsSuiteTest
mvn -pl sedlo-suite test -Dtest=DataDrivenSuiteTest
```
Из IDEA: правый клик по *SuiteTest → Run.
