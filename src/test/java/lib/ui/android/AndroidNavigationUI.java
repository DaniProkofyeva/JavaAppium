package lib.ui.android;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUI extends NavigationUI {
    static {
        MY_LISTS_BUTTON_LINK = "xpath://android.widget.FrameLayout[@content-desc='My lists']";
        TAB_BAR_ELEMENT = "id:org.wikipedia:id/fragment_main_nav_tab_layout";
        LISTS_PAGE_TOOLBAR_TITLE = "xpath://*[@resource-id='org.wikipedia:id/single_fragment_toolbar']/*[@text='My lists']";
    }

    public AndroidNavigationUI(RemoteWebDriver driver){
        super(driver);
    }

}
