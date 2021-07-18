package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SearchPageObject extends MainPageObject
{
    private static final String
    SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]",
    SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]",
    SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
    SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
    SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container]",
    SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='No results found']",
    SEARCH_LENS_ICON ="id:org.wikipedia:id/menu_page_search",
    SEARCH_RESULT_TITLE = "id:org.wikipedia:id/page_list_item_title",
    SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL =
                    "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                            "[.//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{ARTICLE_TITLE}']]" +
                            "[.//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{ARTICLE_DESCRIPTION}']]";

    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */

    public void initSearchInput()
    {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element",
                15
        );
        this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                "Cannot find Search input after clicking search init element",
                15
        );
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find Search cancel button",
                15
        );
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                15
        );
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click Search cancel button",
                15
        );
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input",
                200
        );
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with substring " + substring,
                20
        );
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                10
        );
    }

    public int getAmountOfFoundArticles()
    {
       this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
       return this.getAmountOfElements(
               SEARCH_RESULT_ELEMENT
       );
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPreset(
                SEARCH_RESULT_ELEMENT,
                "We supposed not to find any results"
        );
    }

    public String getSearchlineHint() {
        return this.waitForElementAndGetAttribute(SEARCH_INPUT,"text", "Cannot find search input", 5);
    }

    public void assertAllResultsHasText(String search_word) {
        this.testTextSearchResults(SEARCH_RESULT_TITLE,search_word,"Not all results has text: " + search_word);
    }

    public void clickSearchIcon()
    {
        this.waitForElementAndClick(SEARCH_LENS_ICON, "Cannot find and click search icon", 5);
    }

    private static String getArticleWithTitleAndDescription(String articleTitle, String articleDescription) {
        return SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL
                .replace("{ARTICLE_TITLE}", articleTitle)
                .replace("{ARTICLE_DESCRIPTION}", articleDescription);
    }

    public void waitForElementByTitleAndDescription(String title, String description) {
        String articleWithTitleAndDescriptionXpath = getArticleWithTitleAndDescription(title, description);
        this.waitForElementPresent(
                articleWithTitleAndDescriptionXpath,
                String.format("We no found name with '%s' and description '%s'.", title, description),
                15
        );
    }
}
