package steps;

import helpers.CommonActions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;

public class AppTestBase extends CommonActions {
    private static final Logger LOG = LogManager.getLogger(CommonActions.class);
    private static boolean scenarioResult;

    @Before
    public void startApp(Scenario scenario) throws MalformedURLException {
        LOG.info("***********************************************************");
        LOG.info("** Starting New TestCase -> " + scenario.getName() + "  **");
        LOG.info("***********************************************************");
        scenario.getSourceTagNames().forEach(System.out::println);
        browserName = System.getProperty("browser");

        file = new File("target/ExportDownloads");
        try {
            Files.createDirectories(Path.of("target/ExportDownloads"));
        } catch (IOException e) {
            e.printStackTrace();
            LOG.info("dir is not created!");
        }

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-blink-features");
            chromeOptions.addArguments("--disable-app-list-dismiss-on-blur");
            chromeOptions.addArguments("--disable-core-animation-plugins");
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            capabilities.setCapability("applicationCacheEnabled", false);
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", file.getAbsolutePath());
            chromeOptions.setExperimentalOption("prefs", chromePrefs);
            driver = new ChromeDriver(chromeOptions);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            System.setProperty(FirefoxDriver.Capability.MARIONETTE, "true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "target/logs.txt");
            driver = new FirefoxDriver(firefoxOptions);
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions edgeOptions = new EdgeOptions();
            driver = new EdgeDriver(edgeOptions);
        }
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        LOG.info("Testing on :" + browserName + "\n");
    }

    @After
    public void closeApp(Scenario scenario) {
        scenarioResult = scenario.isFailed();
        byte[] screenshot;
        if (scenario.isFailed()) {
            try {
                LOG.info("Failed scenario ----->> " + scenario.getSourceTagNames());
                LOG.info(("Failed scenario ----->> " + scenario.getStatus()));
                LOG.info("Browser failed on " + browserName);
                screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Test failed on : " + browserName);
            } catch (WebDriverException screenShot) {
                System.err.println(screenShot
                        .getMessage());
            }
        }
        driver.quit();
        LOG.info("***********************************************************");
        LOG.info("** TestCase Completed -> " + scenario.getName() + "  **");
        LOG.info("***********************************************************");
    }

    @AfterMethod
    public void aSynch() {
        driver.close();
    }
}
