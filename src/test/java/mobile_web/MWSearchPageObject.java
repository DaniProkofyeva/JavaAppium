package mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject
{
    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:.header-action>button.cancel";
        SEARCH_CLEAR_BUTTON = "css:button.clear";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_LIST_ITEM = "css:ul.page-list>li.page-summary";
        SEARCH_RESULT_TITLE_TPL = "xpath://a[contains(@class,'title')]/h3[normalize-space(.)='{TITLE}']";
        SEARCH_RESULT_LIST = "css:ul.page-list";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SEARCH_RESULT_LIST_ELEMENT = "css:a.title h3";
        SEARCH_RESULT_BY_SUBSTRING_TITLE_AND_DESCRIPTION_TPL = "xpath://*[contains(@class,'page-list')]/*[@class='page-summary'][" +
                ".//h3[contains(normalize-space(),'{ARTICLE_TITLE}')] and " +
                ".//*[@class='wikidata-description' and contains(normalize-space(),'{ARTICLE_DESCRIPTION}')]]";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}