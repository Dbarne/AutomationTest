package pages;

import helpers.CommonActions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage extends CommonActions {
    public LoginPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    public WebElement usernameInputField;

    @FindBy(id = "password")
    public WebElement passwordInputField;

    @FindBy(id = "login-button")
    public WebElement loginButton;

    public void login(String username, String password) {
        waitForPageLoad();
        sendKeys(usernameInputField, username);
        sendKeys(passwordInputField, password);

        int count = 0;
        try {
            while (loginButton.isDisplayed() & count <= 5) {
                loginButton.click();
                count++;
            }
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        waitForPageLoad();
    }

    private void sendKeys(WebElement element, String keys) {
        Assert.assertTrue(element.isDisplayed(), "Element to send keys is not displayed");
        element.click();
        element.clear();
        element.sendKeys(keys, Keys.TAB);
    }
}