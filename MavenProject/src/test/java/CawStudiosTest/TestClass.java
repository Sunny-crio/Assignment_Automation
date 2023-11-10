package CawStudiosTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestClass {

	static PageClass pageclass;
	static WebDriver driver ;
	static String url = "https://testpages.herokuapp.com/styled/tag/dynamic-table.html";
	

	public static void main(String[] args) {
        try {
            initializeWebDriver();
            navigateToURL();
            performTestSteps();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    private static void initializeWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
       
        driver = new ChromeDriver(chromeOptions);
    }

    private static void navigateToURL() {
        driver.get(url);
        driver.manage().window().maximize();
        
    }

    private static void performTestSteps() {
        pageclass = new PageClass(driver);
        pageclass.entered_Urlpage();
        pageclass.click_tabledata();
        pageclass.inputtextbox_tovisible();
        pageclass.get_jsondata();
        pageclass.enterjsondata_into_inputbox();
        pageclass.click_refreshtable();
        pageclass.comparejsondata_withtable();
    }
}
