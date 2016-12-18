package pages;

/**
 * Created by ohuzenko on 12/16/2016.
 */


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Page {

        protected WebDriver driver;
        protected WebDriverWait wait;

        public Page(WebDriver driver) {
            this.driver = driver;
            wait = new WebDriverWait(driver, 40);
        }

        public WebDriverWait getWait() {
            return wait;

        }

        protected List<WebElement> findList(By locator) {

            return  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        }

         protected WebElement find(By locator) {

            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

      }

}

