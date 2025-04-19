package task_3;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StockExchange {
    WebDriver driver;

    @BeforeAll
    public void seTup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); //implicit method
    }

    //@Test
    @DisplayName("Check if the table values are stored in the txt file")
    public void scrapData() {
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");
        List<WebElement> tableElem = driver.findElements(By.tagName("tbody"));

        Set<String> uniqueRows = new HashSet<>();

        try (FileWriter writer = new FileWriter("./src/test/resources/values.txt", true)) {

            for (WebElement tabledata : tableElem) {
                List<WebElement> rowElem = tabledata.findElements(By.tagName("tr"));

                for (WebElement rowdata : rowElem) {
                    List<WebElement> cellData = rowdata.findElements(By.tagName("td"));

                    StringBuilder rowBuilder = new StringBuilder();

                    for (WebElement cells : cellData) {
                        String cellTxt = cells.getText();
                        rowBuilder.append(cellTxt).append("\t");
                    }

                    String rowString = rowBuilder.toString().trim();

                    // filtering
                    if (rowString.matches("^\\d+\\s.*") && uniqueRows.add(rowString)) {
                        writer.write(rowString + "\n");
                        System.out.println(rowString);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error while writing to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
