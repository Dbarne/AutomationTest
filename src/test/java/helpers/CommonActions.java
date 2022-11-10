package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class CommonActions {
    private final Logger LOG = LogManager.getLogger(CommonActions.class);

    protected static final String environment = "TECHNICAL_TEST_URL";
    protected static String browserName;
    public static WebDriver driver;
    protected static File file;
    protected static Properties properties = new Properties();

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
}
