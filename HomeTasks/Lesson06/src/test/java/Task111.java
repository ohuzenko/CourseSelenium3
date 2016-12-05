/**
 * Created by ohuzenko on 12/4/2016.
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


/*

Сделайте сценарий для регистрации нового пользователя в учебном приложении litecart (не в админке, а в клиентской части магазина).
Сценарий должен состоять из следующих частей:
1) регистрация новой учётной записи с достаточно уникальным адресом электронной почты (чтобы не конфликтовало с ранее созданными пользователями),
2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
3) повторный вход в только что созданную учётную запись,
4) и ещё раз выход.

Можно оформить сценарий либо как тест, либо как отдельный исполняемый файл.
Проверки можно никакие не делать, только действия -- заполнение полей, нажатия на кнопки и ссылки. Если сценарий дошёл до конца, то есть созданный пользователь смог выполнить вход и выход -- значит создание прошло успешно.

 */
public class Task111 {

    private WebDriver driver;
    private WebDriverWait wait;
    private String logoutLink = "http://localhost/litecart/en/logout";

    @BeforeTest
    public void start() {

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 30);
    }


    @AfterTest
    public void stop() {

        driver.quit();
        driver = null;
    }

    @Test
    public void verifyNewAccountCreation() {

        UserAccount user = new UserAccount();

        createNewAccount(user);
        logout();
        login(user.getEmail(), user.getPassword());
        logout();

    }



    private void createNewAccount(UserAccount user) {

        openCreateAccountPage();
        fillCreateAccountForm(user);
        submitCreateAccountForm();

    }

    private void openCreateAccountPage() {
        logout();
        driver.findElement(By.cssSelector("form[name=login_form] table tr td a")).click();
        wait.until(titleIs("Create Account | My Store"));
    }

    private void fillCreateAccountForm(UserAccount user) {

        driver.findElement(By.name("firstname")).sendKeys(user.getFirstName());
        driver.findElement(By.name("lastname")).sendKeys(user.getLastName());
        driver.findElement(By.name("address1")).sendKeys(user.getAddress1());
        driver.findElement(By.name("postcode")).sendKeys(user.getPostCode());
        driver.findElement(By.name("city")).sendKeys(user.getCity());

        Select country = new Select(driver.findElement(By.name("country_code")));
        country.selectByVisibleText(user.getCountry());

        driver.findElement(By.name("email")).sendKeys(user.getEmail());
        driver.findElement(By.name("phone")).sendKeys(user.getPhone());
        driver.findElement(By.name("password")).sendKeys(user.getPassword());
        driver.findElement(By.name("confirmed_password")).sendKeys(user.getPassword());

    }

    private void submitCreateAccountForm() {
        driver.findElement(By.cssSelector("form[name=customer_form] table tr td button[name=create_account]")).click();

    }


    private void login(String login, String pwd) {

        driver.findElement(By.name("email")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(pwd);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("Online Store | My Store"));

    }

    private void logout() {
        driver.get(logoutLink);
    }

}
