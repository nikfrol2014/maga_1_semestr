package ru.nngasu.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("DATA-DRIVEN tests (tag=datadriven)")
@SelectClasses({
        ru.nngasu.datadriven.SedloCsvTest.class,
        ru.nngasu.datadriven.SedloMethodSourceTest.class
})
public class DataDrivenSuiteTest {
}