package tests;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for device interactions and activity")
public class ChangeAppConditionTests extends CoreTestCase
{
    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "AppCondition")})
    @DisplayName("Check search results by setting different device orientations")
    @Description("Search for the article, rotate the device and check search results, then return device back to the portrait orientation and check them again")
    @Step("Starting test 'testChangeScreenOrientationOnSearchResults'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testChangeScreenOrientationOnSearchResults() {

        if (Platform.getInstance().isMW()){
            return;
        }
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String title_before_rotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String title_after_rotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article title have been changes after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();
        String title_after_second_rotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article title have been changes after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "AppCondition")})
    @DisplayName("Check search results after sending the app to the background")
    @Description("Search for the article, then send the currently active app to the background, return after 2 sec and check search results")
    @Step("Starting test 'testCheckSearchArticleInBackground'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCheckSearchArticleInBackground() {

        if (Platform.getInstance().isMW()){
            return;
        }

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(5);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }
}
