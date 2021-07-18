package lib.ui.android;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AndroidNavigationUI extends NavigationUI {
    static {
        MY_LISTS_LINK = "xpath://lib.ui.android.widget.FrameLayout[@content-desc='My lists']";
    }

    public AndroidNavigationUI(RemoteWebDriver driver){
        super(driver);
    }

}
