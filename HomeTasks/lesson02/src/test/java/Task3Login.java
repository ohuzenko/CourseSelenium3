import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by ohuzenko on 11/17/2016.
 */
public class Task3Login {
    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeTest
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10000);
    }

    @Test
    public void googleFindTest() {

        driver.get("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }

    @AfterTest
    public void stop() {

        driver.quit();
        driver = null;
    }
}
