package task_2;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Registration {
    WebDriver driver;

    @BeforeAll
    public void seTup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); //implicit method
    }

    //@Test
    @DisplayName("Check if sucessful submission message is showing properly")
    public void fillupUserForm() throws InterruptedException {
        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");
        List<WebElement> textFields = driver.findElements(By.tagName("input"));
        textFields.get(4).sendKeys("naila"); //first name
        
        Random random = new Random();
        int rdNumber = random.nextInt(1000);
        String randomEmail = "user" + rdNumber + "@gmail.com";
        textFields.get(5).sendKeys(randomEmail); //email
        
        textFields.get(6).sendKeys("aEfT334#$Aa"); //password
        textFields.get(7).sendKeys("nur"); //last name
        textFields.get(9).click();

        // Locate the date input field
        WebElement dobField = driver.findElement(By.cssSelector("input[data-id='date_box_1665628538']"));

        // Use JavaScript to set the value
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '2000-04-18';", dobField);


        textFields.get(14).sendKeys("1782342222"); //phone
        textFields.get(15).sendKeys("1782342243"); // emergency phone
        textFields.get(16).sendKeys("Bangladeshi"); //nationality

        Select option = new Select(driver.findElement(By.id("country_1665629257")));
        option.selectByVisibleText("Bangladesh");
        Thread.sleep(2000);

        WebElement checkbox = driver.findElement(By.cssSelector("[type='checkbox']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        // Scroll to submit button before clicking
        List<WebElement> submitButtons = driver.findElements(By.tagName("button"));

        WebElement secondSubmitButton = submitButtons.get(4);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", secondSubmitButton);
        secondSubmitButton.click();

        WebElement successMessage = driver.findElement(By.xpath("//ul[contains(text(),'User successfully registered.')]"));

        String actualMessage = successMessage.getText();
        String expectedMessage = "User successfully registered.";
        Assertions.assertEquals(expectedMessage, actualMessage);


    }

    //@AfterAll
    public void tearDown() {
        driver.quit();
    }
}



