package Task17ErrorLog;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.util.List;

/**
 * Created by ohuzenko on 12/15/2016.
 */
public class LogListener extends AbstractWebDriverEventListener {
    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        getBrowserLogs(driver, url, false);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        getBrowserLogs(driver, url, true);

    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        System.out.println(throwable);
    }


    public void getBrowserLogs(WebDriver driver, String url, boolean after) {

        List<LogEntry> log =  driver.manage().logs().get("browser").getAll();
        if(log.size() == 0) {
            System.out.println("No Log messages " + (after? "after":"before") + " navigation to " + url);
            return;
        }

        log.forEach((l) -> System.out.println(l));

    }

}
