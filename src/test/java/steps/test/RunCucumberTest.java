package steps.test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import helpers.CucumberReports;
import org.testng.annotations.AfterSuite;


@CucumberOptions(features = "src/test/resources/features/",
        plugin = {"pretty", "html:target/cucumber-report",
                "json:target/cucumber-parallel/cucumber.json", "junit:target/cucumber.xml"},
        glue = {"steps"},
        publish = false,
        tags = "@all and not @ignore")

public class RunCucumberTest extends AbstractTestNGCucumberTests {
    @AfterSuite
    public void reports() {
        CucumberReports.generateReports();
    }
}