package tests;
import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

@Epic("Tests for my lists")
public class MyListsTests extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "ProkofevaDD",
            password = "&UJM,ki8";

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"),  @Feature(value = "MyLists"),  @Feature(value = "Navigation")})
    @DisplayName("Save the first found article to my new list")
    @Description("Add the first found article to the new reading list and then remove it from the list")
    @Step("Starting test 'testSaveFirstArticleToMyList'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSaveFirstArticleToMyList() throws InterruptedException {

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String article_title = articlePageObject.getArticleTitle();
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addArticleToMyList(name_of_folder);
            articlePageObject.closeArticle();
        } else {
            articlePageObject.addArticleToMySavedList();
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            Thread.sleep(3000);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();
            articlePageObject.waitForTitleElement();

            Assert.assertEquals("Cannot find search article", article_title, articlePageObject.getArticleTitle());
            navigationUI.openNavigation();
        }
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) myListsPageObject.openFolderByName(name_of_folder);

        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    @Features(value = {@Feature(value = "Search"), @Feature(value = "Article"),  @Feature(value = "MyLists"),  @Feature(value = "Navigation")})
    @DisplayName("Delete one of two articles from the reading list")
    @Description("Add two articles to the reading list and then check remove one article from two saved")
    @Step("Starting test 'testSaveTwoArticlesToMyListAndDeleteAny'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSaveTwoArticlesToMyListAndDeleteAny() throws InterruptedException {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        String first_search_line = "Java";
        searchPageObject.typeSearchLine(first_search_line);
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        articlePageObject.waitForTitleElement();
        String first_article_title = articlePageObject.getArticleTitle();
        if (Platform.getInstance().isAndroid()) articlePageObject.addArticleToMyList(name_of_folder);
        else {
            articlePageObject.addArticleToMySavedList();
            AuthorizationPageObject auth = new AuthorizationPageObject(driver);
            Thread.sleep(3000);
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();
            articlePageObject.waitForTitleElement();
            Assert.assertEquals("Cannot find search article", first_article_title, articlePageObject.getArticleTitle());
        }
        String second_article_title;
        if (Platform.getInstance().isAndroid()) second_article_title = "Programming language";
        else second_article_title = "High-level programming language";
        Thread.sleep(3000);
        searchPageObject.waitForClickSearchButton();
        String second_search_line = "JavaScript";
        searchPageObject.typeSearchLine(second_search_line);
        searchPageObject.clickByArticleWithSubstring(second_article_title);
        articlePageObject.waitForTitleElement();
        Assert.assertEquals("Actual title not equals expected", second_search_line, articlePageObject.getArticleTitle());
        NavigationUI navigationUI = NavigationUIFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            articlePageObject.addSecondArticleToMyList(name_of_folder);
            articlePageObject.closeArticle();
        } else {
            articlePageObject.addArticleToMySavedList();
            navigationUI.openNavigation();
        }
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) myListsPageObject.openFolderByName(name_of_folder);
        searchPageObject.waitForSearchResultsNotEmpty(1);
        myListsPageObject.swipeByArticleToDelete(first_article_title);
        if (Platform.getInstance().isAndroid()) {
            searchPageObject.clickByArticleWithSubstring(second_article_title.toLowerCase());
            articlePageObject.waitForTitleElement();
            String actual_second_search_line = articlePageObject.getArticleTitle();
            Assert.assertEquals("Actual title not equals expected", second_search_line, actual_second_search_line);
        } else {
            myListsPageObject.waitForArticleToDisappearByTitle(second_article_title);
            myListsPageObject.openArticleBySubstring(second_search_line);
            Assert.assertEquals("Cannot find search article", second_search_line, articlePageObject.getArticleTitle());

            articlePageObject.waitForBannerElement(second_search_line);
        }
    }
}
