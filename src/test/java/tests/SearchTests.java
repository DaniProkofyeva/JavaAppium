package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
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

@Epic("Tests for searching")
public class SearchTests extends CoreTestCase
{

    @Test
    @Feature(value = "Search")
    @DisplayName("Simple search for the article")
    @Description("Search for the article and check that its present in search results by substring")
    @Step("Starting test 'testSearch'")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Cancel search for the article")
    @Description("Search for the article and then cancel the search")
    @Step("Starting test 'testCancelSearch'")
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Search for not empty results")
    @Description("Search for the article and then check that search results are not empty")
    @Step("Starting test 'testAmountOfNotEmptySearch'")
    @Severity(value = SeverityLevel.NORMAL)
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
    @Feature(value = "Search")
    @DisplayName("Search for empty results")
    @Description("Search for the article and then check that search results are empty")
    @Step("Starting test 'testAmountOfEmptySearch'")
    @Severity(value = SeverityLevel.NORMAL)
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Feature(value = "Search")
    @DisplayName("Clear the search input field")
    @Description("Search for the article and then clear the search input field")
    @Step("Starting test 'testSearchResultsAndCancel'")
    @Severity(value = SeverityLevel.NORMAL)
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
        @Feature(value = "Search")
        @DisplayName("Check search results")
        @Description("Search for the article and then check that each displayed search result contains the search value")
        @Step("Starting test 'testSearchForEachResults'")
        @Severity(value = SeverityLevel.NORMAL)
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
        @Feature(value = "Search")
        @DisplayName("Check search placeholder")
        @Description("We init search and check the placeholder in the search input field")
        @Step("Starting test 'testSearchForPlaceHolderText'")
        @Severity(value = SeverityLevel.TRIVIAL)
        public void testSearchForPlaceHolderText () {
            SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
            searchPageObject.initSearchInput();
            searchPageObject.assertSearchPlaceHolderText("Search Wikipedia");
        }

    @Test
    @Feature(value = "Search")
    @DisplayName("Search for the article by title and description")
    @Description("Search for the article and then check that first three search results contain the article with the specified title and description")
    @Step("Starting test 'testSearchArticleByTitleAndDescription'")
    @Severity(value = SeverityLevel.BLOCKER)
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