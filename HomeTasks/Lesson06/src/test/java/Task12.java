import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
Сделайте сценарий для добавления нового товара (продукта) в учебном приложении litecart (в админке).
Для добавления товара нужно открыть меню Catalog, в правом верхнем углу нажать кнопку "Add New Product", заполнить поля с информацией о товаре и сохранить.
Достаточно заполнить только информацию на вкладках General, Information и Prices. Скидки (Campains) на вкладке Prices можно не добавлять.
После сохранения товара нужно убедиться, что он появился в каталоге (в админке). Клиентскую часть магазина можно не проверять.
 */

public class Task12 {

    private WebDriver driver;
    private WebDriverWait wait;
    private String adminLink = "http://localhost/litecart/admin/";
    private String catalogLink = "http://localhost/litecart/admin/?app=catalog&doc=catalog";

    @BeforeTest
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get(adminLink);
        login("admin", "admin");
    }


    @AfterTest
    public void stop() {

        driver.quit();
        driver = null;
    }

    @Test
    public void createNewProductVerification() {

        openCatalog();
        switchToAddNewProductPage();
        String duckName = fillNewProductData();
        submitNewProductData();
        Assert.assertTrue(findNewProductInCatalog(duckName));


    }

    private void openCatalog() {

        driver.get(catalogLink);
        wait.until(titleIs("Catalog | My Store"));
    }

    private void switchToAddNewProductPage() {
        driver.findElement(By.cssSelector("td#content div a.button[href $=edit_product")).click();
        wait.until(titleIs("Add New Product | My Store"));
    }

    private String fillNewProductData() {

        long sfx = System.currentTimeMillis();
        String duckName = "duck_" + sfx;

        driver.findElement(By.cssSelector("#tab-general > table > tbody > tr:nth-child(1) > td > label:nth-child(3) > input[type=\"radio\"]")).click();
        driver.findElement(By.cssSelector("#tab-general > table > tbody  input[name=\"name[en]\"]")).sendKeys(duckName);
        driver.findElement(By.cssSelector("#tab-general > table > tbody  input[name=code]")).sendKeys(""+sfx);
        driver.findElement(By.cssSelector("#tab-general > table > tbody  input[data-name=\"Rubber Ducks\"]")).click();
        driver.findElement(By.cssSelector("#tab-general > table > tbody  input[name=\"product_groups[]\"")).click();
        driver.findElement(By.cssSelector("#tab-general > table > tbody  input[name=quantity]")).sendKeys("10");

        driver.findElement(By.cssSelector("form > div > ul > li > a[href$=tab-information]")).click();
        driver.findElement(By.cssSelector("#tab-information > table > tbody input[name ^= short_description]")).sendKeys(duckName);

        driver.findElement(By.cssSelector("form > div > ul > li > a[href$=tab-prices]")).click();
        driver.findElement(By.cssSelector("#tab-prices > table:nth-child(2) > tbody > tr > td > input[name=purchase_price]")).sendKeys("10");

        return duckName;
    }

    private void submitNewProductData() {
        driver.findElement(By.cssSelector("#content > form > p > span > button[name=save]")).click();
        wait.until(titleIs("Catalog | My Store"));
    }


    private boolean findNewProductInCatalog(String duckName) {


        List<WebElement> ducksInCatalog = driver.findElements(By.cssSelector("#content > form > table > tbody > tr.row > td:nth-child(3) > a"));

        for(WebElement product : ducksInCatalog) {
            if(product.getAttribute("innerText").equals(duckName)) return true;
        }
        return false;
    }


    private void login(String login, String pwd) {

        driver.findElement(By.name("username")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(pwd);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }
}
