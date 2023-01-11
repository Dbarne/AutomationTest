package steps.technicalTestSteps;

import helpers.CommonActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.*;

public class technicalTestSteps extends CommonActions {

    private final LoginPage loginPage = new LoginPage();
    private final ProductsPage productsPage = new ProductsPage();
    private final ProductInformationPage productInformationPage = new ProductInformationPage();
    private final ShoppingCartPage shoppingCartPage = new ShoppingCartPage();
    private final CheckOutPage checkOutPage = new CheckOutPage();


    @Given("I have opened the SwagLabs login page")
    public void openSwagLabs() {
        driver.get(readPropertiesFile(environment));
        waitForPageLoad();
    }

    @And("I login to SwagLab with valid credentials")
    public void iLoginValid() {
        loginPage.login(validUser, validPassword);
    }

    @And("I validate the Product page")
    public void iValidateTheProductPage() {
        productsPage.validateProductsPage();
        productsPage.validateHighLowFilterDropdown();
    }

    @And("I select an {string} from the Product page")
    public void iSelectAnFromTheProductPage(String item) {
        productsPage.selectItem(item);
    }

    @When("I add {string} the to the cart")
    public void iAddTheToTheCart(String item) {
        productInformationPage.validateProductAddToBasket(item);
    }

    @And("I Navigate to the shopping cart")
    public void iNavigateToTheShoppingCart() {
        productInformationPage.selectCart();
    }

    @And("I validate the {string} in Shopping Cart")
    public void iValidateTheShoppingCart(String item) {
        shoppingCartPage.validateCartLabels();
        shoppingCartPage.validateItemShoppingCart(item);
    }

    @And("I enter checkout credentials")
    public void iEnterCheckoutCredentials() {
        checkOutPage.enterCheckOutDetails();
    }

    @And("I validate the {string} checkout details")
    public void iValidateTheCheckoutDetails(String item) {
        checkOutPage.validateCheckoutOverview(item);
    }

    @Then("I verify order has been completed")
    public void iVerifyOrderHasBeenCompleted() {
        checkOutPage.validateCompleteCheckout();
    }
}