package lib.ui;
import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

abstract public class SearchPageObject extends MainPageObject
{
    protected static String
            SEARCH_INIT_ELEMENT,
            SEARCH_INPUT,
            SEARCH_CANCEL_BUTTON,
            SEARCH_CLEAR_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TPL,
            SEARCH_RESULT_LIST_ITEM,
            SEARCH_RESULT_TITLE_TPL,
            SEARCH_RESULT_LIST,
            SEARCH_EMPTY_RESULT_ELEMENT,
            SEARCH_RESULT_LIST_ELEMENT,
            SEARCH_BUTTON,
            SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESCRIPTION_TPL;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    @Step("Open Wikipedia URL for Mobile Web (this method does nothing for Android and iOS")
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    @Step("Get the result search by title '{articleTitle}'")
    private static String getResultSearchElementTitle(String article_title) {
        return SEARCH_RESULT_TITLE_TPL.replace("{TITLE}", article_title);
    }

    @Step("Waiting for the article with title '{article_title}' and description '{article_description}' to appear")
    private static String getArticleWithTitleAndDescription(String article_title, String article_description) {
        return SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESCRIPTION_TPL.replace("{ARTICLE_TITLE}", article_title)
                .replace("{ARTICLE_DESCRIPTION}", article_description);
    }
    /* TEMPLATES METHODS */

    @Step("Initializing the search field")
    public void initSearchInput() {
        this.waitForElementAndClick(
                SEARCH_INIT_ELEMENT,
                "Cannot find and click search init element", 20);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT,
                "Cannot find search input after clicking search init element");
    }

    @Step("Waiting for button to cancel search result")
    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                SEARCH_CANCEL_BUTTON,
                "Cannot find search cancel button",
                20);
    }

    @Step("Waiting for search cancel button to disappear")
    public void waitForCancelButtonToDisappear() {
        if (Platform.getInstance().isMW()) this.waitForElementNotPresent(
                SEARCH_CLEAR_BUTTON,
                "Search clear button is still present",
                20);
        else this.waitForElementNotPresent(
                SEARCH_CANCEL_BUTTON,
                "Search cancel button is still present",
                20);
    }

    @Step("Clicking button to check search result")
    public void waitForClickSearchButton() {
        if (Platform.getInstance().isAndroid()) {

            this.waitForElementAndClick(
                    SEARCH_BUTTON,
                    "Cannot find Search button",
                    20
            );
        } else {
            this.waitForElementAndClick(
                    SEARCH_INIT_ELEMENT,
                    "Cannot find Search button",
                    20
            );
        }
    }

    @Step("Clicking button to cancel search result")
    public void clickCancelSearch() {
        this.waitForElementAndClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                20
        );
    }

    @Step("Clicking button to clean search result")
    public void clickClearSearch() {
        this.waitForElementAndClick(
                SEARCH_CLEAR_BUTTON,
                "Cannot find and click search cancel button",
                20
        );
    }

    @Step("Double clicking button to cancel search result")
    public void doubleClickCancelSearch() {
        this.waitForElementAndDoubleClick(
                SEARCH_CANCEL_BUTTON,
                "Cannot find and click search cancel button",
                20
        );
    }

    @Step("Typing '{search_line}' to the search line")
    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(
                SEARCH_INPUT,
                search_line,
                "Cannot find and type into search input", 20);
    }

    @Step("Waiting for search result")
    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find search result with substring " + substring);
    }

    @Step("Waiting for search result and select an article by substring in article title")
    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(
                search_result_xpath,
                "Cannot find and click search result with substring " + substring,
                20);
    }

    @Step("Getting amount of found articles")
    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                SEARCH_RESULT_LIST_ITEM,
                "Cannot find anything by the request",
                20
        );
        return this.getAmountOfElements(SEARCH_RESULT_LIST_ITEM);
    }

    @Step("Waiting for empty result label")
    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                SEARCH_EMPTY_RESULT_ELEMENT,
                "Cannot find empty result element",
                20
        );
    }

    @Step("Making sure there are no results for the search")
    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(SEARCH_RESULT_LIST, "We supposed not to find any results");
    }

    @Step("Asserting that the search placeholder has text '{placeholder}'")
    public void assertSearchPlaceHolderText(String placeholder) {
        this.waitForElementPresent(
                SEARCH_INIT_ELEMENT,
                "Cannot find search input-field",
                20
        );
        if (Platform.getInstance().isMW())
            this.assertElementHasPlaceHolder(
                    SEARCH_INPUT,
                    placeholder,
                    "The search field doesn't exist correct text");
        else this.assertElementHasText(
                SEARCH_INIT_ELEMENT,
                placeholder,
                "The search field doesn't exist correct text"
        );
    }

    @Step("Waiting for search result not empty")
    public void waitForSearchResultsNotEmpty(int count_of_elements) {
        this.waitForNumberOfElementsToBeMoreThan(
                SEARCH_RESULT_LIST_ITEM,
                count_of_elements,
                "The search results more than " + count_of_elements + " or isn't displayed",
                20
        );
    }

    @Step("Waiting for search result not displayed")
    public void waitForSearchResultsNotDisplayed() {
        this.waitForElementNotPresent(
                SEARCH_RESULT_LIST_ITEM,
                "The search results is still displayed",
                20
        );
    }

    @Step("Waiting for search results list is present on the page")
    public List<WebElement> waitForPresenceOfAllResults() {
        return this.waitForPresenceOfAllElementsLocated(
                SEARCH_RESULT_LIST_ELEMENT,
                "Cannot find any result",
                20);
    }

    @Step("Waiting for search results count more than: '{count_of_results}'")
    public void waitForNumberOfResultsMoreThan(int count_of_results) {
        this.waitForNumberOfElementsToBeMoreThan(
                SEARCH_RESULT_LIST_ITEM,
                count_of_results,
                "Count of results less than expected",
                20);
    }

    @Step("Waiting for clicking by article with substring: '{article_title}'")
    public void waitFotClickByArticleWithTitle(String article_title) {
        String article_title_xpath = getResultSearchElementTitle(article_title);
        this.waitForElementClickableAndClick(
                article_title_xpath,
                "Article with title not clickable",
                20);
    }

    @Step("Waiting for the article with title '{article_title}' and description '{article_description}'")
    public void waitForElementByTitleAndDescription(String article_title, String article_description) {
        String articleWithTitleAndDescriptionXpath = getArticleWithTitleAndDescription(article_title, article_description);
        this.waitForElementPresent(
                articleWithTitleAndDescriptionXpath,
                "Cannot find article with title and description",
                20
        );
    }

    @Step("Making map of results from list of article titles and list of article descriptions")
    public Map<String, String> setExpectedMapOfArticlesWithTitleAndDescription(List<String> article_titles, List<String> article_descriptions) {
        return IntStream.range(0, article_titles.size()).boxed().collect(Collectors.toMap(article_titles::get, article_descriptions::get, (a, b) -> b));
    }

    @Step("Init and type in SearchLine")
    public void searchByValue(String searchLine) {
        initSearchInput();
        typeSearchLine(searchLine);
    }

    @Step("Trying to get results of search")
    public List<WebElement> getSearchResultsList() {
        return this.waitForPresenceOfAllElements(SEARCH_RESULT_LIST_ITEM, "По заданному запросу ничего не найдено.", 15);
    }
}
