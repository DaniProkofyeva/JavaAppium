package lib.ui;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            SYNC_SAVED_ARTICLES,
            CLOSE_SYNC_BUTTON,
            LIST_ELEMENT_CONTAINER,
            REMOVE_FROM_SAVED_LIST_BUTTON_TPL;

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }


    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }


    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }


    private static String getRemoveButtonByTitle(String articleTitle) {
        return REMOVE_FROM_SAVED_LIST_BUTTON_TPL.replace("{TITLE}", articleTitle);
    }


    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementLocated(
                LIST_ELEMENT_CONTAINER,
                "Cannot find any folder",
                15);
        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                5
        );
    }


    public void waitForArticleToDisappearByTitle(String articleTitle) {
        String articleTitleXpath = getSavedArticleXpathByTitle(articleTitle);
        if (Platform.getInstance().isMW()) {
            long finish = System.currentTimeMillis() + 5000; // end time
            while (isAnyElementPresent(articleTitleXpath) && (System.currentTimeMillis() < finish)) {
                driver.navigate().refresh();
            }
        } else {
            this.waitForElementNotPresent(articleTitleXpath, "Cannot find saved article by title ", 15);
        }
    }


    public void waitForArticleToAppearByTitle(String article_title) {
        final String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                15
        );
    }


    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        final String article_xpath = getSavedArticleXpathByTitle(article_title);
        Platform.getInstance().isAndroid();
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find saved article"
            );
            String remove_btn = getRemoveButtonByTitle(article_title);
            this.waitForElementClickableAndClick(remove_btn, "Cannot find delete article button", 10);

        Platform.getInstance().isAndroid();
        this.waitForArticleToDisappearByTitle(article_title);

    }


    public void openArticleBySubstring(String article_title) {
        final String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                article_xpath,
                "Cannot find article by name " + article_title,
                5
        );
    }

    public void closeSyncSavedArticlesPopUpWindow() {
        if (isElementPresent(SYNC_SAVED_ARTICLES))
            this.waitForElementClickableAndClick(
                    CLOSE_SYNC_BUTTON,
                    "Cannot find 'Sync your saved articles popup?' close button",
                    10);
    }
}
