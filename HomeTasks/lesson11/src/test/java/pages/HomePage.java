package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by ohuzenko on 12/16/2016.
 */
public class HomePage extends Page {



    private String url = "http://localhost/litecart/";


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(url);
        return this;
    }


    public WebElement find(String product) {

        wait.until(titleIs("Online Store | My Store"));

        return find(driver.findElements(By
                .cssSelector("div#box-most-popular div.content ul li div.name")), "innerText", product);

    }

    public WebElement find(List<WebElement> source, String attribute, String expectedValue) {

        for(WebElement item : source) {
            if(item.getAttribute(attribute).equals(expectedValue)) return item;
        }
        return null;
    }



}
