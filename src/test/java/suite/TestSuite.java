package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.SearchTests;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        SearchTests.class
}
)

public class TestSuite {}
