package app;

import data.TestData;
import model.Cart;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;

/**
 * Created by ohuzenko on 12/16/2016.
 */
public class Application {


    private WebDriver driver;

    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;
    private Cart cart;

    public Application() {
        driver = new ChromeDriver();

        homePage = new HomePage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        cart = new Cart();
    }

    public void addProductsToCart() {

        for(String product : TestData.products ) {

            openProductDetails(product);
            cart.addNumberOfProductsInCart(productPage.addProductToCart());
            verifyCartCounterFromProductDetailPage();
        }

    }

    private void openProductDetails(String product) {
        homePage.open();
        WebElement duck = homePage.find(product);

        if(duck == null) {
            Assert.assertTrue(false, "There is no such product (" + product + ") in the list of most popular");
        }
        duck.click();
        productPage.waitForDetails();
    }


    private void verifyCartCounterFromProductDetailPage() {

        homePage.getWait().until((WebDriver d) -> {

            int quantity = Integer.parseInt(d.findElement(By
                    .cssSelector("header#header div#cart-wrapper div#cart span.quantity"))
                    .getAttribute("innerText"));
            return quantity == cart.getNumberOfProductsInCart();
        });
    }

    public void removeProductsFromCart() {

        cartPage.open();
        verifyCartQuantityInCart();


       while( cart.getNumberOfProductsInCart() > 0) {

            cart.setNumberOfProductsInCart(cart.getNumberOfProductsInCart() - 1);
            cartPage.removeItem();
            verifyCartQuantityInCart();
        }

    }

    private void verifyCartQuantityInCart() {

        if(cart.getNumberOfProductsInCart() != 0) {
            cartPage.getWait().until((WebDriver d)-> (cart.getNumberOfProductsInCart() == cartPage.getNumberOfRowsInCart()));
        }else{
            cartPage.emptyCardProperty()
                    .getAttribute("innerText")
                    .equals("There are no items in your cart.");

        }


    }

    public void quit() {
        driver.quit();
    }

}
