package Task17ErrorLog;

import org.openqa.selenium.By;


import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;



/**
 * Created by ohuzenko on 12/15/2016.
 */
public class Task17Base {

    public static ThreadLocal<EventFiringWebDriver> tlDriver = new ThreadLocal<EventFiringWebDriver>();
    static EventFiringWebDriver driver = null;
    static WebDriverWait wait = null;

    @BeforeClass
    public void start() {
        if(tlDriver.get() != null){
            driver = tlDriver.get();
        }else{
            driver = new EventFiringWebDriver(new ChromeDriver());
            driver.register(new LogListener());
            tlDriver.set(driver);
        }

        wait = new WebDriverWait(driver, 30);
    }


    @AfterClass
    public void stop() {
        driver.quit();
        driver = null;
    }


    public void loadPage(String pageLink) {
        loadPage(pageLink, null);
    }


    public void loadPage(String pageLink, By controlLocator) {
        driver.get(pageLink);
        if(controlLocator != null) {
            wait.until(ExpectedConditions.presenceOfElementLocated(controlLocator));
        }
    }



}
