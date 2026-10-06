package ru.nngasu.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

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
public class AllTestsSuiteTest {
}