package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by ohuzenko on 12/16/2016.
 */
public class ProductPage extends Page {
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    private By sizeBy = By.name("options[Size]");
    private By addCartProduct = By.name("add_cart_product");
    private By productQuantity = By.cssSelector("input[name=quantity]");
    private By productTitle = By.cssSelector("div#box-product.box h1.title");

    public int addProductToCart() {
        List<WebElement> sizes =driver.findElements(sizeBy);
        if(sizes.size() > 0) {
            Select size = new Select(sizes.get(0));
            size.selectByIndex(1);
        }

        find(addCartProduct).click();

        return Integer.parseInt(find(productQuantity).getAttribute("value"));

    }

    public void waitForDetails() {
        wait.until(ExpectedConditions.presenceOfElementLocated(productTitle));

    }
}
