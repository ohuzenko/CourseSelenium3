import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by ohuzenko on 12/2/2016.
 */
public class Task7 {

    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeTest
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get("http://localhost/litecart/admin/");
        login("admin", "admin");
    }


    @AfterTest
    public void stop() {

        driver.quit();
        driver = null;
    }


    @Test
    public void mainMenuPassing() {

        WebElement currentItem;

        int menuItemsCount = driver.findElements(By.cssSelector("li#app-")).size();

        for(int i = 1; i <= menuItemsCount; i++ ) {
            currentItem = driver.findElement(By.cssSelector("li#app-:nth-child(" + i + ")"));
            clickItems(currentItem);

        }
    }

    private void clickItems(WebElement currentItem) {

        currentItem.click();
        currentItem = driver.findElement(By.cssSelector("li#app-.selected"));
        int subMenuItemsCount = currentItem.findElements(By.cssSelector("ul.docs>li[id^=doc]")).size();
        for(int i = 1; i <= subMenuItemsCount; i++ ) {
            driver.findElement(By.cssSelector("li#app-.selected")).findElement(By.cssSelector("ul.docs>li[id^=doc]:nth-child(" + i + ")")).click();
            wait.until(titleContains("My Store"));
        }
    }


    private void login(String login, String pwd) {

        driver.findElement(By.name("username")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(pwd);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));


    }




}
