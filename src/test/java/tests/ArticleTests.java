package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for articles")
public class ArticleTests extends CoreTestCase
{
    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Compare article title with expected one")
    @Description("Open 'Java Object-oriented programming language' article and make sure the title is expected")
    @Step("Starting test 'testCompareArticleTitle'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();

  //      ArticlePageObject.takeScreenshot("article_page");

        Assert.assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Swipe article to the footer")
    @Description("Open an article and swipe it to the footer")
    @Step("Starting test 'testSwipeArticle'")
    @Severity(value = SeverityLevel.MINOR)
    public void testSwipeArticle() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();

    }


    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article")})
    @DisplayName("Check that article title present on the page")
    @Description("Open an article and verify the article title present on the page")
    @Step("Starting test 'testArticleTitlePresence'")
    @Severity(value = SeverityLevel.MINOR)
    public void testArticleTitlePresence() {

        String search_line = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForNumberOfResultsMoreThan(0);
        SearchPageObject.waitFotClickByArticleWithTitle(search_line);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.assertArticleTitlePresence();

    }

}
