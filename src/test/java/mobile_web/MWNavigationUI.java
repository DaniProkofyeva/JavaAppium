package mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {

    static {
        MY_LISTS_BUTTON_LINK = "css:[data-event-name='menu.unStar']";
        OPEN_NAVIGATION_BUTTON = "id:mw-mf-main-menu-button";
        LISTS_PAGE_TOOLBAR_TITLE = "xpath://*[@class='page-heading']//h1[normalize-space(text())='Watchlist']";
    }

    public MWNavigationUI(RemoteWebDriver driver){
        super(driver);
    }
}
