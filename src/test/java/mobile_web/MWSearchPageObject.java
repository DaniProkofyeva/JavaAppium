package mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject
{
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(),'{SUBSTRING}')]";
                SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
                SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
                SEARCH_LENS_ICON = "id:org.wikipedia:id/menu_page_search";
                SEARCH_RESULT_TITLE = "id:org.wikipedia:id/page_list_item_title";
                SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL =
                        "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']" +
                                "[.//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{ARTICLE_TITLE}']]" +
                                "[.//*[@resource-id='org.wikipedia:id/page_list_item_description'][@text='{ARTICLE_DESCRIPTION}']]";

    }
    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}