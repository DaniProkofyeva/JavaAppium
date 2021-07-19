package lib.ui;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import lib.Platform;

import java.util.List;

abstract public class ArticlePageObject extends MainPageObject {
    protected static String
            TITLE,
            LIST_TITLES,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_MENU,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_NAME_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            BACK_BUTTON,
            CREATED_FOLDER,
            ARTICLE_BANNER_BY_TITLE_TPL;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }


    private static String getCreatedFolderTitle(String name_of_folder) {
        return CREATED_FOLDER.replace("{NAME_OF_FOLDER}", name_of_folder);
    }


    private static String getBannerTitle(String substring) {
        return ARTICLE_BANNER_BY_TITLE_TPL.replace("{TITLE}", substring);
    }


    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title on page!",
                15);
    }


    public List<WebElement> waitForAllTitlesElements() {
        return this.waitForPresenceOfAllElementsLocated(
                LIST_TITLES,
                "Cannot find any article title",
                15);
    }


    public WebElement waitForBannerElement(String substring) {
        String banner_xpath = getBannerTitle(substring);
        return this.waitForElementLocated(
                banner_xpath,
                "Cannot find banner by substring " + substring,
                15);
    }


    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        screenshot(this.takeScreenshot("article_title"));
        if (Platform.getInstance().isAndroid()) return title_element.getAttribute("text");
        else if (Platform.getInstance().isAndroid()) return title_element.getAttribute("name");
        else return title_element.getText();
    }


    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        }
    }


    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );
        this.waitForElementLocated(
                OPTIONS_MENU,
                "Cannot find context menu",
                15);
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );
        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
                5
        );
        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );
        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }


    public void addSecondArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );
        this.waitForElementLocated(
                OPTIONS_MENU,
                "Cannot find context menu",
                15);
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );
        String created_folder = getCreatedFolderTitle(name_of_folder);
        this.waitForElementAndClick(
                created_folder,
                "Cannot find created folder",
                5
        );
    }


    public void addArticleToMySavedList() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementClickableAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);
    }


    public void closeArticle() {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
    }


    public void assertArticleTitlePresence() {
        this.assertElementPresent(
                TITLE,
                "Article not found"
        );
    }


    public void removeArticleFromSavedIfItAdded() {
        if (this.isAnyElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    1
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before"
            );
        }
    }

    public void closeArticleAndReturnToTheMainPage() {
        this.waitForElementClickableAndClick(BACK_BUTTON, "Cannot find refresh button", 5);
    }
}
