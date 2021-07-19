package tests;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class MyListsTests extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "ProkofevaDD",
            password = "&UJM,ki8";

    @Test
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
            auth.clickAuthButton();
            auth.enterLoginData(login, password);
            auth.submitForm();
            articlePageObject.waitForTitleElement();

            assertEquals("Cannot find search article", article_title, articlePageObject.getArticleTitle());
            navigationUI.openNavigation();
        }
        navigationUI.clickMyLists();

        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) myListsPageObject.openFolderByName(name_of_folder);

        myListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testSaveTwoArticlesDeleteOne()
    {
        String name_of_folder = "Learning programming";
        String first_search = "Java";
        String first_search_substring = "bject-oriented programming language";
        String second_search = "JavaScript";
        String second_search_substring = "Programming language";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(first_search);
        SearchPageObject.clickByArticleWithSubstring(first_search_substring);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String first_article_title = ArticlePageObject.getArticleTitle();

        ArticlePageObject.addArticleToMyList(name_of_folder);

        SearchPageObject.clickSearchIcon();

        SearchPageObject.typeSearchLine(second_search);
        SearchPageObject.clickByArticleWithSubstring(second_search_substring);
        ArticlePageObject.waitForTitleElement();

        String second_article_title = ArticlePageObject.getArticleTitle();

        ArticlePageObject.addArticleToExistingList(name_of_folder);

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        MyListsPageObject.openFolderByName(name_of_folder);

        MyListsPageObject.swipeByArticleToDelete(first_article_title);

        MyListsPageObject.waitForArticleToDisappearByTitle(first_article_title);

        MyListsPageObject.isArticlePresent(second_article_title);

        MyListsPageObject.waitAndClickArticleByTitle(second_article_title);

        assertEquals("Article title not equal saved exam", second_article_title, ArticlePageObject.getArticleTitle());



    }

}
