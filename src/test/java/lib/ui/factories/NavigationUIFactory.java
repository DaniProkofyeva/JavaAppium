package lib.ui.factories;

import lib.Platform;
import lib.ui.NavigationUI;
import lib.ui.android.AndroidArticlePageObject;
import lib.ui.android.AndroidNavigationUI;
import mobile_web.MWNavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationUIFactory {
    public static NavigationUI get(RemoteWebDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigationUI(driver);
        } else {
            return new MWNavigationUI(driver);
        }
    }
}
