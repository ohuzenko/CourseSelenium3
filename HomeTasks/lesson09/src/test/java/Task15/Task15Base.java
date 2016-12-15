package Task15;

import com.sun.jndi.toolkit.url.Uri;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Set;

/**
 * Created by sunny_IT on 12/12/2016.
 */
public class Task15Base {

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

    public void loadPage(String pageLink) throws MalformedURLException {

        loadPage(pageLink, null);
    }


    public void loadPage(String pageLink, By controlLocator) throws MalformedURLException {

        if(driver == null) {
            DesiredCapabilities capability = DesiredCapabilities.chrome();
            capability.setCapability("platform", Platform.WINDOWS);
            driver = new RemoteWebDriver(new URL("http://192.168.1.41:4444/wd/hub"),capability);
            wait = new WebDriverWait(driver, 30);
        }

        driver.get(pageLink);
        if(controlLocator != null) {
            wait.until(ExpectedConditions.presenceOfElementLocated(controlLocator));
        }
    }



}
