package pages;

import com.github.javafaker.Faker;
import helpers.CommonActions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckOutPage extends CommonActions {
    public CheckOutPage() {
        PageFactory.initElements(driver, this);
    }

    protected static final Faker FAKER = new Faker(new Locale("en"));

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FindBy(id = "first-name")
    private WebElement firstNameInputField;

    @FindBy(id = "last-name")
    private WebElement lastNameInputField;

    @FindBy(id = "postal-code")
    private WebElement postCodeInputField;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(xpath = "//div[@class='summary_info_label'][1]")
    private WebElement paymentInformationLabel;

    @FindBy(xpath = "//div[@class='summary_value_label'][1]")
    private WebElement paymentCard;

    @FindBy(xpath = "//div[@class='summary_info_label'][2]")
    private WebElement shippingInformationLabel;

    @FindBy(xpath = "//div[@class='summary_value_label'][2]")
    private WebElement shippingDetails;

    @FindBy(css = "[class='summary_subtotal_label']")
    private WebElement itemTotal;

    @FindBy(css = "[class='summary_tax_label']")
    private WebElement itemTax;

    @FindBy(css = "[class='summary_total_label']")
    private WebElement total;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(css = "[class='complete-header']")
    private WebElement thankYouLabel;

    @FindBy(css = "[class='complete-text']")
    private WebElement completeText;

    public void enterCheckOutDetails() {
        String firstName = removeSpecialCharacters(FAKER.name().firstName());
        String lastName = removeSpecialCharacters(FAKER.name().lastName());
        String postCode = FAKER.address().zipCode();

        Assert.assertTrue(titleHeader.isDisplayed(), "Title header is not displayed");
        Assert.assertEquals(titleHeader.getText(), "CHECKOUT: YOUR INFORMATION", "Checkout header is incorrect");
        Assert.assertTrue(firstNameInputField.isDisplayed(), "First name field is not displayed");
        firstNameInputField.click();
        firstNameInputField.clear();
        firstNameInputField.sendKeys(firstName, Keys.TAB);

        Assert.assertTrue(lastNameInputField.isDisplayed(), "Last name field is not displayed");
        lastNameInputField.click();
        lastNameInputField.clear();
        lastNameInputField.sendKeys(lastName, Keys.TAB);

        Assert.assertTrue(postCodeInputField.isDisplayed(), "Postcode field is not displayed");
        postCodeInputField.click();
        postCodeInputField.clear();
        postCodeInputField.sendKeys(postCode, Keys.TAB);

        Assert.assertTrue(cancelButton.isDisplayed(), "Cancel Button is not displayed");
        Assert.assertTrue(continueButton.isDisplayed(), "Continue Button is not displayed");
        continueButton.click();
    }

    private String removeSpecialCharacters(String string) {
        Pattern p = Pattern.compile("[^a-zA-Z0-9 ]");
        Matcher m = p.matcher(string);
        return m.replaceAll("");
    }

    public void validateCheckoutOverview(String item) {
        waitForPageLoad();
        setDescriptionAndPrice(item);
        WebElement itemName = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + item + "']"));
        WebElement itemDesc = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + item + "']/../../div"));
        WebElement itemPrice = driver.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + item + "']/../../div[2]/div\n"));

        Assert.assertEquals(itemName.getText(), item, "Item name is incorrect");
        Assert.assertEquals(itemDesc.getText(), description, "Item description is incorrect");
        Assert.assertEquals(itemPrice.getText(), price, "Item price is incorrect");
        Assert.assertEquals(paymentInformationLabel.getText(), "Payment Information:", "Payment information label is incorrect");
        Assert.assertEquals(paymentCard.getText(), "SauceCard #31337", "Payment card details are incorrect");
        Assert.assertEquals(shippingInformationLabel.getText(), "Shipping Information:", "Shipping Information label is incorrect");
        Assert.assertEquals(shippingDetails.getText(), "FREE PONY EXPRESS DELIVERY!", "Shipping details are incorrect");
        Assert.assertEquals(itemTotal.getText(), "Item total: " + price);
        Assert.assertEquals(itemTax.getText(), "Tax: $" + getTaxValue(item), "Item tax is incorrect");
        String number = price.replace("$", "");
        double totalValue = Double.parseDouble(number) + Double.parseDouble(getTaxValue(item));
        number = String.format("%.2f", totalValue);
        Assert.assertEquals(total.getText(), "Total: $" + number, "Total Value is incorrect");
        Assert.assertTrue(finishButton.isDisplayed(), "Finish button is not displayed");
        finishButton.click();
    }

    public void validateCompleteCheckout() {
        waitForPageLoad();
        Assert.assertTrue(thankYouLabel.isDisplayed(), "Thank you label is not displayed");
        Assert.assertTrue(completeText.isDisplayed(), "Complete label is not displayed");
        Assert.assertEquals(thankYouLabel.getText(), "THANK YOU FOR YOUR ORDER", "Thank you label is incorrect");
        Assert.assertEquals(completeText.getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!", "Complete text is incorrect");
    }

    private String getTaxValue(String item) {
        String number = price.replace("$", "");
        setDescriptionAndPrice(item);
        df.setRoundingMode(RoundingMode.UP);
        return df.format((Double.parseDouble(number) * 0.08));
    }
}