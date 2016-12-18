package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ohuzenko on 12/16/2016.
 */
public class CartPage extends Page {

    public CartPage(WebDriver driver) {

        super(driver);
        PageFactory.initElements(driver, this);
    }

    private By cartLink = By.cssSelector("header#header div#cart-wrapper div#cart a.link") ;
    private By cartEmptyMessage = By.cssSelector("div#checkout-cart-wrapper > p > em");
    private By customerServiceTitle = By.cssSelector("header#header div#customer-service-wrapper span.title");
    private By rowsInOrder = By.cssSelector("table.dataTable.rounded-corners tr:not(.header)");
    private By productShortcuts = By.cssSelector("ul.shortcuts > li.shortcut > a.act");
    private By removeProductButton = By.cssSelector("li.item > form[name=cart_form]  div  p > button[name=remove_cart_item]");



    public CartPage open() {

        find(cartLink).click();
        wait.until((WebDriver d) -> find(customerServiceTitle).getAttribute("innerHTML").equals("Customer Service"));
        return this;
    }

    public void removeItem() {

        List<WebElement> shortcuts = driver.findElements(productShortcuts);
        if(shortcuts.size() > 0) shortcuts.get(0).click();
        driver.findElement(removeProductButton).click();

    }


    public int getNumberOfRowsInCart() {

       return findList(rowsInOrder).size() - 4;
    }

    public WebElement emptyCardProperty() {

        return wait.until(ExpectedConditions.presenceOfElementLocated(cartEmptyMessage));
    }

}


