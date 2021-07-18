package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class NavigationUI extends MainPageObject{

    private static final String
    MY_LISTS_LINK = "xpath://lib.ui.android.widget.FrameLayout[@content-desc='My lists']";


    public NavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void clickMyList()
    {
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find navigation button to My list",
                5
        );
    }
}
