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

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by ohuzenko on 12/2/2016.
 */


public class Task8 {

    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeTest
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get("http://localhost/litecart/");

    }


    @AfterTest
    public void stop() {

        driver.quit();
        driver = null;
    }

    @Test
    public void checkTheStickerIsOnlyOne() {

        List<WebElement> mainPageGoods = driver.findElements(By.cssSelector("ul.listing-wrapper.products>li>a.link>div.image-wrapper"));

        for(WebElement item : mainPageGoods) {
            Assert.assertEquals( item.findElements(By.cssSelector("div[class ^= sticker]")).size(),1);
        }
    }


}
