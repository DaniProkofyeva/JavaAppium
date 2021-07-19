package mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {
    static {
        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:#page-actions #ca-watch[class*='mw-ui-icon-wikimedia-star'][role='button']";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#page-actions #ca-watch.watched[class*='mw-ui-icon-wikimedia-unStar'][role='button']";
        ARTICLE_BANNER_BY_TITLE_TPL = "xpath://*[contains(@class, 'pre-content') and contains(normalize-space(), '{TITLE}')]";
    }
    public MWArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }
}
