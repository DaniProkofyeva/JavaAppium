package mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject
{
    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container]",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
            SEARCH_LENS_ICON ="org.wikipedia:id/menu_page_search",
            SEARCH_RESULT_TITLE = "org.wikipedia:id/page_list_item_title",
            SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL =
                    "//*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                            "[.//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{ARTICLE_TITLE}']]" +
                            "[.//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{ARTICLE_DESCRIPTION}']]";


    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}