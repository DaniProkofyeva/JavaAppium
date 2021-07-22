package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchTests extends CoreTestCase
{

    @Test
    public void testSearch() throws InterruptedException {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchResultsAndCancel() {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResultsNotEmpty(1);
        if (Platform.getInstance().isAndroid()) {
            searchPageObject.doubleClickCancelSearch();
        } else {
            searchPageObject.clickClearSearch();
        }
        searchPageObject.waitForCancelButtonToDisappear();

    }

        @Test
        public void testSearchForEachResults () {
            SearchPageObject searchPage = SearchPageObjectFactory.get(driver);

            final String searchValue = "JAVA";
            searchPage.searchByValue(searchValue);

            List<WebElement> articleTitles = searchPage.getSearchResultsList();

            for (int i = 0; i < articleTitles.size(); i++) {
                final WebElement titleElement = articleTitles.get(i);
                String articleTitle = "";
                if (Platform.getInstance().isAndroid()) {
                    articleTitle = titleElement.getAttribute("text").toLowerCase();
                } else {
                    Platform.getInstance().isMW();
                    articleTitle = titleElement.getAttribute("textContent").toLowerCase();
                }

                Assert.assertTrue(
                        String.format("\n  Error! In the title of the found article with the index [%d] missing search value '%s'.\n", i, searchValue),
                        articleTitle.contains(searchValue.toLowerCase()));
            }
        }
        @Test
        public void testSearchForPlaceHolderText () {
            SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
            searchPageObject.initSearchInput();
            searchPageObject.assertSearchPlaceHolderText("Search Wikipedia");
        }

        public void testSearchArticleByTitleAndDescription () {
            SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine("Java");
            List<String> article_titles;
            List<String> article_descriptions;
            if (Platform.getInstance().isAndroid()) {
                article_titles = Arrays.asList("Java", "JavaScript", "Java (programming language)");
                article_descriptions = Arrays.asList("Indonesian island", "High-level-programming language", "Object-oriented programming language");
            } else {
                article_titles = Arrays.asList("Java", "Javanese people", "Java (programming language)");
                article_descriptions = Arrays.asList("Indonesian island", "Ethnic group in Indonesia", "Object-oriented programming language");
            }
            Map<String, String> expected_results = searchPageObject.setExpectedMapOfArticlesWithTitleAndDescription(article_titles, article_descriptions);
            Assert.assertTrue("The count of articles less than expected", searchPageObject.getAmountOfFoundArticles() >= 3);
            expected_results.forEach(searchPageObject::waitForElementByTitleAndDescription);

        }
    }