package Task14;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import java.util.Set;


/**
 * Created by ohuzenko on 12/8/2016.
 */


public class Task14Base_IE {
    static WebDriver driver = null;
    static WebDriverWait wait = null;

    @AfterClass
    public void stop() {

        driver.quit();
        driver = null;
    }

    public ExpectedCondition<String> otherWindowIsOpened(final Set<String> openedWindows) {

        return new ExpectedCondition<String>() {

            public String apply(WebDriver webDriver) {

                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(openedWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    public void loadPage(String pageLink) {

        loadPage(pageLink, null);
    }


    public void loadPage(String pageLink, By controlLocator) {

        if(driver == null) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
            driver = new InternetExplorerDriver(caps);
            wait = new WebDriverWait(driver, 30);
        }

        driver.get(pageLink);
        if(controlLocator != null) {
            wait.until(ExpectedConditions.presenceOfElementLocated(controlLocator));
        }
    }


}
