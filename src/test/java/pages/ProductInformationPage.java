package pages;

import helpers.CommonActions;
import helpers.Constants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ProductInformationPage extends CommonActions {

    public ProductInformationPage() {
        PageFactory.initElements(driver, this);
    }

    private final Constants constants = new Constants();

    @FindBy(css = "[class='inventory_details_name large_size']")
    private WebElement productName;

    @FindBy(css = "[class='inventory_details_desc large_size']")
    private WebElement productDescription;

    @FindBy(css = "[class='inventory_details_price']")
    private WebElement productPrice;

    @FindBy(xpath = "//button[text() = 'Add to cart']")
    private WebElement addToCart;

    public void validateProductAddToBasket(String item) {
        setDescriptionAndPrice(item);
        Assert.assertNotNull(description);
        Assert.assertNotNull(price);
        Assert.assertEquals(item, productName.getText(), "Item name is incorrect");
        Assert.assertEquals(description, productDescription.getText(), "Description text is incorrect");
        Assert.assertEquals(price, productPrice.getText(), "Product price is incorrect");

        addToCart.click();
        waitForPageLoad();
        Assert.assertTrue(removeFromCart.isDisplayed(), "Item has not been added to the cart");
    }
}