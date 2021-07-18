package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class SearchTests extends CoreTestCase
{
    @Test
    public void testSearch() {

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
    public void testSearchFieldText()
    {
        String search_hint = "Search…";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();

        assertEquals("Search hint is not: "+ search_hint,search_hint, SearchPageObject.getSearchlineHint());
    }

    @Test
    public void testSearchSeveralArtAndCancel()
    {
        String search_line = "Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_line);

        assertTrue("Few articles found", SearchPageObject.getAmountOfFoundArticles()>1);
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchResultsText()
    {
        String search_word="Java";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(search_word);
        SearchPageObject.assertAllResultsHasText(search_word);
    }

    public void testSearchArticleByTitleAndDescription() {
        Map<String, String> searchResults = new HashMap<>();
        searchResults.put("Java", "Island of Indonesia");
        searchResults.put("JavaScript", "Programming language");
        searchResults.put("Java (programming language)", "Object-oriented programming language");

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");

        int amountOfSearchResults = searchPageObject.getAmountOfFoundArticles();

        assertTrue(
                String.format("\n  Error! We found less names: %d.\n", amountOfSearchResults),
                amountOfSearchResults >= 3);

        searchResults.forEach(searchPageObject::waitForElementByTitleAndDescription);
    }

}
