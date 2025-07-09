package sel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ScreenshotExtentTest {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setup() {
        // Setup Extent
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("D:\\reports\\Spark.html");
        extent.attachReporter(spark);

        // Start WebDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testWithScreenshotOnFailure() throws IOException {
        test = extent.createTest("Verify Title and Capture Screenshot on Failure");

        driver.get("https://www.saucedemo.com/v1/");
        String actualTitle = driver.getTitle();
        String expectedTitle = "Swag Labs FAIL TEST"; // Purposefully wrong

        try {
            Assert.assertEquals(actualTitle, expectedTitle);
            test.pass("Title matched: " + actualTitle);
        } catch (AssertionError e) {
            String screenshotPath = captureScreenshot("D:\\reports\\failure_screenshot.png");
            test.fail("Title did not match. Expected: " + expectedTitle + ", Actual: " + actualTitle)
                .addScreenCaptureFromPath(screenshotPath);
            throw e; // rethrow to mark test failed
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }

    // Utility method to take screenshot
    public String captureScreenshot(String path) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        Files.copy(src.toPath(), Paths.get(path));
        return path;
    }
}