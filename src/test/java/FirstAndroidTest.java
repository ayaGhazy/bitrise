

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait; import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations. BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class FirstAndroidTest {
    AppiumDriver driver;
    private static By addPlant = By.id("add_plant");
    private static By add = By.id("fab");

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("automation Name", "UiAutomator2");
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("app",System.getenv("BITRISE_APK_PATH"));
        caps.setCapability("uiautomator2ServerInstallTimeout","20000");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);

    }

    @Test

    public void click_App_Button() {
   //   driver.findElementByXPath("//*[@text='GET STARTED']").click();
      //  File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        driver.findElementByAccessibilityId("My garden").click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(addPlant)).click();
        List<MobileElement> listElements = driver.findElements(By.id("plant_item_title"));
        for (MobileElement el : listElements) {
            if (el.getText().equalsIgnoreCase("Avocado")) {
                el.click();

                break;
            }
        }
        wait.until(ExpectedConditions.presenceOfElementLocated(add)).click();
        driver.navigate().back();
        driver.findElementByAccessibilityId("My garden").click();
        Assert.assertTrue(driver.findElementById("plant_name").getAttribute("text")
                .equalsIgnoreCase("Avocado"));
    }

    @AfterTest
    public void tearDown() {

        driver.quit();
    }
}