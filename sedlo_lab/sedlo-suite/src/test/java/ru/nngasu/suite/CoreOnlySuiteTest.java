package ru.nngasu.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("CORE tests only")
@SelectClasses({
        ru.nngasu.sedlo.SedloTest.class
})
public class CoreOnlySuiteTest {
}