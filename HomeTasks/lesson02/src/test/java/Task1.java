import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by oleksandra_huzenko on 11/16/2016.
 */
public class Task1 {

    private WebDriver driver;
    private WebDriverWait wait;


    @BeforeTest
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10000);
    }

    @Test
    public void googleFindTest() {

        driver.get("http://google.com/");
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("btnG")).click();
        wait.until(titleIs("Google"));
    }

    @AfterTest
    public void stop() {

        driver.quit();
        driver = null;
    }
}
