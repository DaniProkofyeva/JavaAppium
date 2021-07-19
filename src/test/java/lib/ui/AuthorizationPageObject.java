package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject{

    public static final String
            LOGIN_BUTTON = "xpath://*[contains(@class,'drawer-container__drawer')]//a[text()='Log in']",
            LOGIN_INPUT = "css:input[name='wpName']",
            PASSWORD_INPUT = "css:input[name='wpPassword']",
            SUBMIT_BUTTON = "id:wpLoginAttempt";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void clickAuthButton() {
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button", 20);
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button", 20);
    }

    public void enterLoginData(String login, String password) {
        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "Cannot find and put a login to the login input.", 5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find and put a password to the login input.", 5);
    }

    public void submitForm() {
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit auth button", 5);
    }
}
