package Task17ErrorLog;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by ohuzenko on 12/15/2016.
 */

public class Task17 extends Task17Base {


    private String adminLink = "http://192.168.1.41/litecart/admin/";
    private String adminLogin = "admin";
    private String adminPassword = "admin";
    private String productListLink = "http://192.168.1.41/litecart/admin/?app=catalog&doc=catalog&category_id=1";


    @Test
    public void getLogsOnProductLoading() {
        openAdminPageAndLogin();
        loadPage(productListLink);
        List<String> productPagesLinks = getAllProductLinks();

        for(String link : productPagesLinks) {
            loadPage(link, By.cssSelector("button[name=delete]"));
        }
    }


    private void openAdminPageAndLogin(){
        loadPage(adminLink);
        driver.findElement(By.name("username")).sendKeys(adminLogin);
        driver.findElement(By.name("password")).sendKeys(adminPassword);
        driver.findElement(By.name("login")).click();
        wait.until(titleIs("My Store"));
    }

    public List<String> getAllProductLinks() {

        List<WebElement> productElement =  driver.findElements(By.cssSelector("table.dataTable tr.row a"));
        List<String> productLinks = new ArrayList<String>();
        for(WebElement link : productElement ){
            String tmp = link.getAttribute("href");
            if(tmp.contains("product_id=")) {
                productLinks.add(tmp);
            }
        }

        return productLinks;
    }
}
