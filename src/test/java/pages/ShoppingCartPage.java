package pages;

import helpers.CommonActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ShoppingCartPage extends CommonActions {
    public ShoppingCartPage() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkOutButton;

    @FindBy(css = "[class='cart_quantity_label']")
    private WebElement quantityLabel;

    @FindBy(css = "[class='cart_desc_label']")
    private WebElement descriptionLabel;

    public void validateItemShoppingCart(String item) {
        waitForPageLoad();
        setDescriptionAndPrice(item);
        WebElement itemName = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + item + "']"));
        WebElement itemDesc = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + item + "']/../../div"));
        WebElement itemPrice = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + item + "']/../../div[2]/div\n"));

        Assert.assertEquals(itemName.getText(), item, "Item name is incorrect");
        Assert.assertEquals(itemDesc.getText(), description, "Item description is incorrect");
        Assert.assertEquals(itemPrice.getText(), price, "Item price is incorrect");
        checkOutButton.click();
        waitForPageLoad();
    }

    public void validateCartLabels() {
        Assert.assertTrue(titleHeader.isDisplayed(), "Title is not displayed");
        Assert.assertEquals(titleHeader.getText(), "YOUR CART", "Cart Header text is incorrect");
        Assert.assertEquals(quantityLabel.getText(), "QTY", "Quantity label is incorrect");
        Assert.assertEquals(descriptionLabel.getText(), "DESCRIPTION", "Description label is incorrect");
    }
}