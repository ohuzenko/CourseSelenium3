package Task14;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


/**
 * Created by ohuzenko on 12/7/2016.
 */
public class Task14_IE extends Task14Base_IE {



    private String adminLink = "http://localhost/litecart/admin/";
    private String adminLogin = "admin";
    private String adminPassword = "admin";
    private String editCountryLink ="http://localhost/litecart/admin/?app=countries&doc=edit_country";


    @Test
    public void verifyCountryLinksAreOpenedInTheNewWindow() {
        openAdminPageAndLogin();
        loadPage(editCountryLink, By.cssSelector("span.button-set button[name=save]"));
        verifyExternalLinks();
     }

    private void verifyExternalLinks() {

        List<WebElement> links = getExternalLinks();
        String currentWindow = driver.getWindowHandle();
        Set<String>  openedWindows = driver.getWindowHandles();

        for(WebElement link : links) {
            checkLink(currentWindow, openedWindows, link);
        }

    }

    private void checkLink(String currentWindow, Set<String> openedWindows, WebElement link) {
        link.click();
        String newWindow = wait.until(otherWindowIsOpened(openedWindows));

        driver.switchTo().window(newWindow);
        driver.close();
        driver.switchTo().window(currentWindow);
        Assert.assertTrue(driver.getCurrentUrl().equals(editCountryLink), "cannot switch to the 'edit country' page");
    }


    private void openAdminPageAndLogin() {
        loadPage(adminLink);
        driver.findElement(By.name("username")).sendKeys(adminLogin);
        driver.findElement(By.name("password")).sendKeys(adminPassword);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }


    public List<WebElement> getExternalLinks() {

        return wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By
                        .xpath("//tr/td/a[.//i[contains(@class,\"fa-external-link\") and contains(@class, \"fa\")]]")));
    }
}
