package steps.technicalTestSteps;

import helpers.CommonActions;
import io.cucumber.java.en.Given;

public class technicalTestSteps extends CommonActions {

    @Given("I am have opened the SwagLabs login page")
    public void openSwagLabs(){
        driver.get(readPropertiesFile(environment));
        waitForPageLoad();
    }
}
