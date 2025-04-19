package task_1;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;
import java.io.File;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FormFillUp {
    WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); //implicit method
    }



    //@Test
    @DisplayName("Check if sucessful submission message is showing properly")
    public void fillupUserForm() throws InterruptedException {
        driver.get("https://www.digitalunite.com/practice-webform-learners");

        try {
            WebElement cookieBtn = driver.findElement(By.id("onetrust-accept-btn-handler"));
            if (cookieBtn.isDisplayed()) {
                cookieBtn.click();
            }
        } catch (Exception e) {

        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");

        List<WebElement> txtFields = driver.findElements(By.tagName("input"));
        txtFields.get(0).sendKeys("naila nur");
        txtFields.get(1).sendKeys("01782342222");
        txtFields.get(2).sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        txtFields.get(2).sendKeys("04/19/2025");
        txtFields.get(2).sendKeys(Keys.ENTER);
        txtFields.get(3).sendKeys("nur123@gmail.com");

        //JavascriptExecutor js = (JavascriptExecutor) driver;
        //js.executeScript("window.scrollBy(0,1500)");

        driver.findElement(By.id("edit-tell-us-a-bit-about-yourself-"))
                .sendKeys("Hello, I'm Naila Nur");

        // Upload file
        WebElement fileInput = driver.findElement(By.id("edit-uploadocument-upload"));
        File file = new File("src/test/resources/demo.png");
        String absolutePath = file.getAbsolutePath();
        fileInput.sendKeys(absolutePath);

        WebElement checkbox = driver.findElement(By.cssSelector("[type='checkbox']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkbox);
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);

        // Scroll to submit button before clicking
        WebElement submitButton = driver.findElement(By.cssSelector("[value='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        Thread.sleep(1000); // allow time to scroll
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        String submitMsg = driver.findElement(By.tagName("h1")).getText();
        String actualMsg = "Thank you for your submission!";
        Assertions.assertTrue(submitMsg.contains(actualMsg));



    }



    //@AfterAll
    public void tearDown() {
        driver.quit();
    }
}
