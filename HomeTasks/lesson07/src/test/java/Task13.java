import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by ohuzenko on 12/5/2016.
 */


/*
Сделайте сценарий для добавления товаров в корзину и удаления товаров из корзины.
Сценарий должен состоять из следующих частей:
1) открыть страницу какого-нибудь товара
2) добавить его в корзину
3) подождать, пока счётчик товаров в корзине обновится
4) вернуться на главную страницу, повторить предыдущие шаги ещё два раза, чтобы в общей сложности в корзине было 3 единицы товара
5) открыть корзину (в правом верхнем углу кликнуть по ссылке Checkout)
6) удалить все товары из корзины один за другим, после каждого удаления подождать, пока внизу обновится таблица
*/

public class Task13 {
    private WebDriver driver;
    private WebDriverWait wait;

    private String[] products = {"Green Duck", "Red Duck", "Yellow Duck"};
    private int cartPositionsNumber = 0;
    private int numberOfProductsInCart = 0;

    @BeforeTest
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
    }


    @AfterTest
    public void stop() {

       driver.quit();
       driver = null;
    }

    @Test
    public void addRemoveProductsFromCart() {
            addProductsToCart();
            removeProductsFromCart();
    }



    private void addProductsToCart() {

            for(String product : products ) {

                    openProductDetails(product);
                    addProductToCart();
                    verifyCartCounterFromProductDetailPage();
            }

    }

    private void openHomePage() {

        driver.get("http://localhost/litecart/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button[name=login]")));
    }


    private void openProductDetails(String product) {
        openHomePage();
        WebElement duck = find(product);

        if(duck == null) {
            Assert.assertTrue(false, "There is no such product (" + product + ") in the list of most popular");
        }

        duck.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#box-product.box h1.title")));

    }


    private void addProductToCart() {

        List<WebElement> sizes = driver.findElements(By.name("options[Size]"));
        if(sizes.size() > 0) {
            Select size = new Select(sizes.get(0));
            size.selectByIndex(1);
        }


        driver.findElement(By.name("add_cart_product")).click();

        int positionAmount = Integer.parseInt(driver.findElement(By
                .cssSelector("input[name=quantity]"))
                .getAttribute("value"));
        cartPositionsNumber += positionAmount;
   }


    private void verifyCartCounterFromProductDetailPage() {

        wait.until((WebDriver d) -> {

            int quantity = Integer.parseInt(d.findElement(By
                    .cssSelector("header#header div#cart-wrapper div#cart span.quantity"))
                    .getAttribute("innerText"));
            if(quantity != cartPositionsNumber) return false;

            return true;
        });
    }


    private void removeProductsFromCart() {

        openCart();
        verifyCartQuantityInCart();

        while(removeItems()) {
            verifyCartQuantityInCart();
        }
        verifyCartQuantityInCart();

    }

    private void openCart() {

        driver.findElement(By.cssSelector("header#header div#cart-wrapper div#cart a.link")).click();
        wait.until((WebDriver d) -> d.findElement(By
                .cssSelector("header#header div#customer-service-wrapper span.title"))
                .getAttribute("innerHTML")
                .equals("Customer Service"));

       numberOfProductsInCart = getNumberOfRowsInCart();
    }

    private int getNumberOfRowsInCart() {
        List<WebElement> rowsOrderSummary = driver.findElements(By
                .cssSelector("table.dataTable.rounded-corners tr:not(.header)"));
        return rowsOrderSummary.size() - 4;
    }

    private void verifyCartQuantityInCart() {


        if(numberOfProductsInCart == 0) {
            wait.until((WebDriver d) ->
                 d.findElement(By.cssSelector("div#checkout-cart-wrapper > p > em"))
                        .getAttribute("innerText")
                        .equals("There are no items in your cart.")
            );

            return;
        }

        int quantity = getNumberOfRowsInCart();
        Assert.assertEquals(quantity, numberOfProductsInCart,  "incorrect total product amount in cart");

    }



    private boolean removeItems() {

        if(numberOfProductsInCart == 0)
        {
            return false;
        }

        List<WebElement> shortcuts = driver.findElements(By.cssSelector("ul.shortcuts > li.shortcut > a.act"));
        if(shortcuts.size() > 0) shortcuts.get(0).click();


        numberOfProductsInCart--;

        driver.findElement(By
                .cssSelector("li.item > form[name=cart_form]  div  p > button[name=remove_cart_item]"))
                .click();

        if(numberOfProductsInCart != 0) {
            wait.until((WebDriver d)-> (numberOfProductsInCart == getNumberOfRowsInCart()));
        }else{
            wait.until((WebDriver d) -> d.findElement(By.cssSelector("div#checkout-cart-wrapper > p > em"))
                        .getAttribute("innerText")
                        .equals("There are no items in your cart.")
            );

        }
    return true;
    }


    private WebElement find(String product) {

        wait.until(titleIs("Online Store | My Store"));

        return find(driver.findElements(By
                .cssSelector("div#box-most-popular div.content ul li div.name")), "innerText", product);

    }

    private WebElement find(List<WebElement> source, String attribute, String expectedValue) {

        for(WebElement item : source) {
            if(item.getAttribute(attribute).equals(expectedValue)) return item;
        }
        return null;
    }


}