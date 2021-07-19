package lib.ui;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String
            MY_LISTS_BUTTON_LINK,
            TAB_BAR_ELEMENT,
            OPEN_NAVIGATION_BUTTON,
            LISTS_PAGE_TOOLBAR_TITLE;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }
    public void openNavigation() {
        if (Platform.getInstance().isMW())
            this.waitForElementClickableAndClick(
                    OPEN_NAVIGATION_BUTTON,
                    "Cannot find navigation button or it disappeared",
                    5);
        else
            System.out.println("Method openNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());
    }

    public void clickMyLists() {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(MY_LISTS_BUTTON_LINK, "Cannot find button navigation", 5);
        } else {
            this.waitForElementLocated(TAB_BAR_ELEMENT, "Cannot find tab bar panel", 15);
            this.waitForElementAndClick(
                    MY_LISTS_BUTTON_LINK,
                    "Cannot find navigation button to My List",
                    5);
        }
        this.waitForElementPresent(
                LISTS_PAGE_TOOLBAR_TITLE,
                "Cannot find list folders with articles",
                15);
    }
    }