package suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.ArticleTests;
import tests.ChangeAppConditionTests;
import tests.MyListsTests;
import tests.SearchTests;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        SearchTests.class,
        MyListsTests.class,
        ChangeAppConditionTests.class,
        ArticleTests.class
}
)

public class TestSuite {}
