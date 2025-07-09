package sel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.Status;

public class sel {
    public static void main(String args[]) {
        // 1️⃣ Set up ExtentReports and attach reporter
        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("D:\\reports\\Spark.html");
        extent.attachReporter(spark);

        // 2️⃣ Start WebDriver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 3️⃣ Create test in ExtentReports
        ExtentTest test = extent.createTest("Open SauceDemo Site");

        // 4️⃣ Perform actions and log
//        try {
//            driver.get("https://www.saucedemo.com/v1/");
//            test.pass("Navigated to SauceDemo site");
//        } catch (Exception e) {
//            test.fail("Failed to navigate: " + e.getMessage());
//        }
        
        driver.get("https://www.saucedemo.com/v1/");
        String expectedTitle="Swag Labssss";
        String actualTitle=driver.getTitle();
        
        if (expectedTitle.equals(actualTitle)) {
            test.pass("Title matched: " + actualTitle);
        } else {
            test.fail("Title mismatch! Expected: " + expectedTitle + " but found: " + actualTitle);
            driver.quit();
            extent.flush();
            // Fail the program with exit code
            throw new AssertionError("Title mismatch! Expected: " + expectedTitle + ", Found: " + actualTitle);
        }
        
        
       


        test.info("Browser closed");

        // 6️⃣ Finalize report
        extent.flush();
    }
}