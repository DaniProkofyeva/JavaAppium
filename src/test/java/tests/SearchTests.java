package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
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

        assertTrue(
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
        public void testSearchForEachResults ()
        {
            SearchPageObject searchPageObject  = SearchPageObjectFactory.get(driver);
            String search_line = "Java";
            searchPageObject.initSearchInput();
            searchPageObject.typeSearchLine(search_line);
            List<WebElement> articleTitles = searchPageObject.waitForPresenceOfAllResults();
            String attribute;
            if (Platform.getInstance().isAndroid()) attribute = "text";
            else if (Platform.getInstance().isMW()) attribute = "textContent";
            else attribute = "name";
            articleTitles.stream()
                    .map(webElement -> webElement.getAttribute(attribute).toLowerCase())
                    .forEachOrdered(articleTitle -> assertTrue(
                            "One or more titles do not have expected search text",
                            articleTitle.contains(search_line.toLowerCase())));
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
            searchPageObject.typeSearchLine("Resident Evil");
            List<String> article_titles;
            List<String> article_descriptions;
            if (Platform.getInstance().isAndroid()) {
                article_titles = Arrays.asList("Resident Evil", "Resident Evil (film series)", "Resident Evil 7: Biohazard");
                article_descriptions = Arrays.asList("Media franchise", "Film series", "2017 survival horror video game");
            } else {
                article_titles = Arrays.asList("Resident Evil", "Resident Evil 7: Biohazard", "Resident Evil Village");
                article_descriptions = Arrays.asList("Video game and media franchise", "2017 video game", "2021 video game");
            }
            Map<String, String> expected_results = searchPageObject.setExpectedMapOfArticlesWithTitleAndDescription(article_titles, article_descriptions);
            assertTrue("The count of articles less than expected", searchPageObject.getAmountOfFoundArticles() >= 3);
            expected_results.forEach(searchPageObject::waitForElementByTitleAndDescription);

        }
    }