package lib.ui;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ArticlePageObject extends MainPageObject
{
    private static final String
    TITLE = "id:org.wikipedia:id/view_page_title_text",
    FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
    OPTION_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
    OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
    ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
    MY_LIST_NAME_INPUT = "id:org.wikipedia:id/text_input",
    MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
    CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']",
    EXISTING_LIST_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER}']";


    //----------TEMPLATE METHODS------------
    private static String getFolderElement(String folder)
    {
        return EXISTING_LIST_TPL.replace("{FOLDER}", folder);
    }
    //----------TEMPLATE METHODS------------

    public ArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article on page",
                15
        );
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTION_BUTTON,
                "Cannot find button to open article options",
                5
        );
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
                "Cannot find input to set name of article folder",
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
                15
        );
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void assertArticleHasTitle() {
        this.assertElementPresent(TITLE,"Article has no title");
    }

    public void addArticleToExistingList(String name_of_folder)
    {
        this.waitForElementAndClick(OPTION_BUTTON, "Cannot find options", 5);
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 7);
        String folderToAddArticleXpath = getFolderElement(name_of_folder);
        this.waitForElementAndClick(folderToAddArticleXpath,"Cannot find existing folder to save article",5);

    }

}
