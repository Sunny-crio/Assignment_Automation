package CawStudiosTest;

import java.io.FileReader;
import java.io.IOException;
import java.security.PublicKey;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//the below imports are to handle the Json data 
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PageClass {
	
	WebDriver driver;
	WebDriverWait wait;
	
	WebElement inputtextbox;
	// I have created an object of JsonArray( JsonArray is an array to hold the Json data)
	JsonArray jsonData;
	
	//Used PageFactory concept to find the elements
	@FindBy(xpath = "//div[@class='centered']/details/summary[text()='Table Data']")
    WebElement tableDataBtn;
	
    @FindBy(id="jsondata")
    WebElement Jsondata_box;
 
    @FindBy(id = "refreshtable")
    WebElement refreshTableButton;
    
    @FindBy(id="dynamictable")
    WebElement tableElement;
        
  //I have created a constructor for the PageClass. It takes a WebDriver as a parameter, initializes the driver
    public PageClass(WebDriver driver)
    {
    	this.driver = driver;
    	wait= new WebDriverWait(driver, Duration.ofSeconds(30));
    	 PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }
    
    // To verify if the user navigated to the expected URL
    public boolean entered_Urlpage()
    {
    	 boolean userentered_thepage=true;
         if(driver.getCurrentUrl().equals("https://testpages.herokuapp.com/styled/tag/dynamic-table.html")){

           return userentered_thepage;
          
         }
         else {
             return !userentered_thepage;
         }
    }
    // Verify if the table data button is displayed and if displayed user should click
    public void click_tabledata() {
    	if(tableDataBtn.isDisplayed()) {
    		
    		tableDataBtn.click();
    		
    	}
    	else System.out.println("TableDataButton is not displayed");
		}
    
    //TO verify after clicking on Table Data Button, if user can visible the json data box
    public void inputtextbox_tovisible() {
    	
    
      inputtextbox=  wait.until(ExpectedConditions.visibilityOf(Jsondata_box));

 
    }
   
    public void get_jsondata() {
    	
    	// created object for FileReader which is used for reading the file for the given path in the constructor
        try (FileReader reader = new FileReader("C:\\MavenProject\\src\\test\\resources\\data.json")) {
        	//here i have parsed the json data to JsonArray and i have assigned it to jsonData
            jsonData = JsonParser.parseReader(reader).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
            driver.quit();
            return;
        }
    	
    }
    // Created a method to enter the json data as a string into the input text box
    public void enterjsondata_into_inputbox() {
    	
    	inputtextbox.clear();
        inputtextbox.sendKeys(jsonData.toString());
    	   	
    }
    // Created a method to click on the refresh table
    public void click_refreshtable() {
    	refreshTableButton.click();
    }
    
    // Created a method to compare json data with the text of the table Element 
    public void comparejsondata_withtable() {
    	
    	String tableText = tableElement.getText();
    	System.out.println( tableText);
    	
    	
    	//Used for loop to iterate the jsonData
    	  for (int i = 0; i < jsonData.size(); i++) {
    		  // here i have used a variable to get the elements in the jsonData as JsonObject
              JsonObject entry = jsonData.get(i).getAsJsonObject();
            //Here i have extracted the value of the "name" key from the JSON object(entry)
              //and stored that value as a string
              String name = entry.get("name").getAsString();
            //Here i have extracted the value of the "age" key from the JSON object(entry)
              //and stored that value as a int 
              int age = entry.get("age").getAsInt();
              //Here i have extracted the value of the "gender" key from the JSON object(entry)
              //and stored that value as a String 
              String gender = entry.get("gender").getAsString();
              // here I have formatted the outcomes for name , age , gender
              String expectedData = String.format("%s %d %s", name, age, gender);
              System.out.println(expectedData);

              if (tableText.contains(expectedData)) {
                  System.out.println("Data for " + name + " matches in the table.");
              } else {
                  System.out.println("Data for " + name + " does not match in the table.");
              }
          }
    }
    
    
}
