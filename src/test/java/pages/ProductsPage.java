package pages;

import helpers.CommonActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class ProductsPage extends CommonActions {

    public ProductsPage() {
        PageFactory.initElements(driver, this);
    }

    private final List<String> lowHighList = Arrays.asList("Sauce Labs Onesie", "Sauce Labs Bike Light",
            "Sauce Labs Bolt T-Shirt", "Test.allTheThings() T-Shirt (Red)", "Sauce Labs Backpack", "Sauce Labs Fleece Jacket");

    @FindBy(css = "[class='product_sort_container']")
    private WebElement filterSelectDropdown;

    @FindBy(css = "[class='inventory_item_name']")
    private List<WebElement> productList;

    public void validateProductsPage() {
        waitForPageLoad();
        Assert.assertTrue(titleHeader.isDisplayed(), "Title header is not displayed");
        Assert.assertEquals(titleHeader.getText(), "PRODUCTS", "Title header text is incorrect");
        Assert.assertTrue(filterSelectDropdown.isDisplayed(), "Filter dropdown is not displayed");
    }

    public void validateHighLowFilterDropdown() {
        selectFromDropdown(filterSelectDropdown, "Price (low to high)");
        waitForPageLoad();
        int i = 0;
        for (WebElement product : productList) {
            Assert.assertEquals(lowHighList.get(i), product.getText(), "Product low high order is incorrect");
            i++;
        }
    }

    public void selectItem(String item) {
        WebElement itemToSelect = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + item + "']"));
        Assert.assertTrue(itemToSelect.isDisplayed(), "Item to select is not visible");
        itemToSelect.click();
        waitForPageLoad();
    }
}