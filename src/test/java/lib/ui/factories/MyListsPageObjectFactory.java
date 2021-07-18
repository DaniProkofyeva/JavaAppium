package lib.ui.factories;

import lib.Platform;
import lib.ui.MyListsPageObject;
import lib.ui.android.AndroidArticlePageObject;
import lib.ui.android.AndroidMyListsPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListsPageObjectFactory {

    public static MyListsPageObject get(RemoteWebDriver driver)

        {
            Platform.getInstance().isAndroid();
            return new AndroidMyListsPageObject(driver);
        }

}
