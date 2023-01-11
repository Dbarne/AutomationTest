package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class CommonActions {
    public CommonActions() {
        PageFactory.initElements(driver, this);
    }

    private final Logger LOG = LogManager.getLogger(CommonActions.class);
    private final Constants constants = new Constants();

    protected static final String environment = "TECHNICAL_TEST_URL";
    protected static String browserName;
    public static WebDriver driver;
    protected static File file;
    protected static Properties properties = new Properties();
    protected static String validUser = "standard_user";
    protected static String validPassword = "secret_sauce";
    protected static String price;
    protected static String description;


    @FindBy(xpath = "//button[text() = 'Remove']")
    public WebElement removeFromCart;

    @FindBy(css = "[class='title']")
    public WebElement titleHeader;

    @FindBy(css = "[class='shopping_cart_link']")
    public WebElement shoppingCart;

    public String readPropertiesFile(String data) {
        try {
            FileInputStream inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);
            data = properties.getProperty(data);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info(e.getMessage());
        }
        return data;
    }

    protected void waitForPageLoad() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(driver -> {
            LOG.info("Current Window State       : "
                    + ((JavascriptExecutor) driver).executeScript("return document.readyState"));
            return String
                    .valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                    .equals("complete");
        });
    }

    protected void selectFromDropdown(WebElement dropdown, String selection) {
        Select select = new Select(dropdown);
        select.selectByVisibleText(selection);
    }


    protected void setDescriptionAndPrice(String item) {
        switch (item) {
            case "Sauce Labs Backpack" -> {
                description = constants.backPackDesc;
                price = constants.backpackPrice;
            }
            case "Sauce Labs Bike Light" -> {
                description = constants.bikeLightDesc;
                price = constants.bikeLightPrice;
            }
            case "Sauce Labs Bolt T-Shirt" -> {
                description = constants.boltDesc;
                price = constants.boltPrice;
            }
            case "Sauce Labs Fleece Jacket" -> {
                description = constants.fleeceDesc;
                price = constants.fleecePrice;
            }
            case "Sauce Labs Onesie" -> {
                description = constants.onesieDesc;
                price = constants.onesiePrice;
            }
            case "Test.allTheThings() T-Shirt (Red)" -> {
                description = constants.allThingsDesc;
                price = constants.allThingsPrice;
            }
        }
    }

    public void selectCart() {
        waitForClickable(shoppingCart, 10);
        shoppingCart.click();
    }

    public void waitForClickable(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
