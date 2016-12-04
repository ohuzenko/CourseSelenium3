import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.util.ArrayList;
import java.util.List;


import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by ohuzenko on 12/3/2016.
 */


/*
Сделайте сценарии, которые проверяют сортировку стран и геозон (штатов) в учебном приложении litecart.

1) на странице http://localhost/litecart/admin/?app=countries&doc=countries
а) проверить, что страны расположены в алфавитном порядке
б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке

2) на странице http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones
зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке

*/

public class Task9 {


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
    public void checkCountriesOrderInAdminCountriesPage() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        List<WebElement> countries = driver.findElements(By.cssSelector("table.dataTable tr.row"));

        compareColumnValuesOrder( countries, 5, " >a", "innerHTML");
    }



    @Test
    public void checkCountriesTomeZonesOrderAdminEditCountryPages() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        List<String> multiZonesLinks  = new ArrayList<>();

        List<WebElement> countries = driver.findElements(By.cssSelector("table.dataTable tr.row"));

        for(WebElement currentCountry : countries) {
            if(getNumberOfZones(currentCountry) > 1) {
                multiZonesLinks.add(getEditCountryPageLink(currentCountry));
            }
        }

        for(String link : multiZonesLinks) {

            checkZoneOrder(link, "", "innerText");
        }

    }



    @Test
    public void checkCountriesTomeZonesOrderInAdminGeoZonesPage() {
       driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
       List<WebElement> countries = driver.findElements(By.cssSelector("table.dataTable tr.row"));
       List<String> multiZonesLinks  = new ArrayList<>();

       for(WebElement country : countries) {
           multiZonesLinks.add(getColumnValueByAttribute(country, 3, ">a", "href"));
       }

       for(String link : multiZonesLinks) {

            checkZoneOrder(link, ">select option[selected]", "innerText");
        }

    }



    private void checkZoneOrder(String link, String suffix, String attribute) {
        driver.get(link);
        List<WebElement> zones = driver.findElements(By.cssSelector("table.dataTable tr:not(.header)"));
        zones.remove(zones.size()-1);
        compareColumnValuesOrder(zones,3, suffix, attribute);
    }

    private void compareColumnValuesOrder(List<WebElement> rows, int column, String suffix, String attribute) {

        String currentValue;
        String tmp = "";
        for(WebElement current : rows) {
            currentValue = getColumnValueByAttribute(current, column, suffix, attribute);
            Assert.assertTrue(tmp.compareTo(currentValue) <= 0 , "The order is wrong: " + tmp + " before " + currentValue );
            tmp = currentValue;

        }

    }

    private String getColumnValueByAttribute(WebElement row, int columnNumber, String suffix, String attribute) {

        return row.findElement(By.cssSelector("td:nth-child(" + columnNumber + ")" + suffix)).getAttribute(attribute);
    }


    private int getNumberOfZones(WebElement currentCountry){
        return Integer.parseInt(getColumnValueByAttribute(currentCountry, 6, "", "innerHTML"));
    }


    private String getEditCountryPageLink(WebElement currentCountry) {
        return getColumnValueByAttribute(currentCountry, 7, " > a", "href");
    }



    private void login(String login, String pwd) {

        driver.findElement(By.name("username")).sendKeys(login);
        driver.findElement(By.name("password")).sendKeys(pwd);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));


    }

}



