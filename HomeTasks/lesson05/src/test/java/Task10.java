import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;


/**
 * Created by sunny_IT on 12/4/2016.
 */

/*
Сделайте сценарий, который проверяет, что при клике на товар открывается правильная страница товара в учебном приложении litecart.

1) Открыть главную страницу
2) Кликнуть по первому товару в категории Campaigns
3) Проверить, что открывается страница правильного товара

Более точно, проверить, что
а) совпадает текст названия товара
б) совпадает цена (обе цены)

Кроме того, проверить стили цены на главной странице и на странице товара -- первая цена серая, зачёркнутая, маленькая, вторая цена красная жирная, крупная.

*/


public class Task10 {

    private WebDriver driver;
    private WebDriverWait wait;


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
    public void checkCampaignsDuckDetails() throws Exception {

        driver.get("http://localhost/litecart/");

        List<WebElement> campaignsContent = driver.findElements(By.cssSelector("div#box-campaigns.box div.content > ul.listing-wrapper.products > li >a.link"));
        Assert.assertTrue(campaignsContent.size() > 0, "There are no campaigns goods!");
        WebElement firstDuck = campaignsContent.get(0);


        DataDuck mainPageDuck = new DataDuck(getCampaignsGoodsInfoFromMainPage(firstDuck));

        firstDuck.click();

        DataDuck detailedPageDuck = new DataDuck(getCampaignsGoodsInfoFromDetailsPage());

        Assert.assertTrue(mainPageDuck.equals(detailedPageDuck), "Wrong duck details!");

    }

    @Test
    public void styleValuesAreCorrect() throws ParseException {
        driver.get("http://localhost/litecart/");
        List<WebElement> campaignsContent = driver.findElements(By.cssSelector("div#box-campaigns.box div.content > ul.listing-wrapper.products > li >a.link"));
        Assert.assertTrue(campaignsContent.size() > 0, "There are no campaigns goods!");
        WebElement duck = campaignsContent.get(0);

        //check color for prices
        String color = duck.findElement(By.cssSelector("div.price-wrapper > s.regular-price")).getCssValue("color");
        Assert.assertTrue(color.equals("rgba(119, 119, 119, 1)"), "Regular price color is incorrect");

        color = duck.findElement(By.cssSelector("div.price-wrapper > strong.campaign-price")).getCssValue("color");

        Assert.assertTrue(color.equals("rgba(204, 0, 0, 1)"), "Campaign price color is incorrect");

        //check font size for prices
        String sizeCampaign = duck.findElement(By.cssSelector("div.price-wrapper > strong.campaign-price")).getCssValue("fontSize");
        String sizeRegular  = duck.findElement(By.cssSelector("div.price-wrapper > s.regular-price")).getCssValue("fontSize");

        int sC = ((Number) NumberFormat.getInstance().parse(sizeCampaign)).intValue();
        int sR = ((Number) NumberFormat.getInstance().parse(sizeRegular)).intValue();

        Assert.assertTrue(sC > sR, "Font size is incorrect");

        //check font weight for prices
        String weightCampaign = duck.findElement(By.cssSelector("div.price-wrapper > .campaign-price")).getAttribute("tagName");
        String weightRegular = duck.findElement(By.cssSelector("div.price-wrapper > .regular-price")).getAttribute("tagName");

        Assert.assertTrue(weightCampaign.toUpperCase().equals("STRONG"), "Font weight is incorrect for campaign price");
        Assert.assertTrue(weightRegular.toUpperCase().equals("S"), "Font weight is incorrect for regular price");

    }

    private String[] getCampaignsGoodsInfoFromMainPage(WebElement firstDuck) {


        String[] data = new String[5];

        data[0] = firstDuck.findElement(By.cssSelector("div.name")).getAttribute("innerHTML");
        data[1] = firstDuck.findElement(By.cssSelector("div.price-wrapper > s.regular-price")).getAttribute("innerHTML");
        data[2] = firstDuck.findElement(By.cssSelector("div.price-wrapper > strong.campaign-price")).getAttribute("innerHTML");
        data[3] = firstDuck.findElement(By.cssSelector("div.price-wrapper > s.regular-price")).getAttribute("className");
        data[4] = firstDuck.findElement(By.cssSelector("div.price-wrapper > strong.campaign-price")).getAttribute("className");

        return data;

    }



    private String[] getCampaignsGoodsInfoFromDetailsPage() {

        String[] data = new String[5];
        WebElement content = driver.findElement(By.cssSelector("div#box-product"));

        WebElement prices = content.findElement(By.cssSelector("div.content div.information div.price-wrapper"));


        data[0] = content.findElement(By.cssSelector("h1[itemprop=name]")).getAttribute("innerHTML");
        data[1] = prices.findElement(By.cssSelector("s.regular-price")).getAttribute("innerHTML");
        data[2] = prices.findElement(By.cssSelector("strong.campaign-price")).getAttribute("innerHTML");
        data[3] = prices.findElement(By.cssSelector("s.regular-price")).getAttribute("className");
        data[4] = prices.findElement(By.cssSelector("strong.campaign-price")).getAttribute("className");


        return data;

    }




}
