package ru.nngasu.suite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("FAST tests (tag=fast)")
@SelectClasses({
        ru.nngasu.sedlo.SedloTest.class,
        ru.nngasu.console.ConsoleAppTest.class
})
@IncludeTags("fast")
public class FastTestsSuiteTest {
}