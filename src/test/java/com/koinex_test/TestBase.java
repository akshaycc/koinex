package com.koinex_test;


import com.koinex_test.utils.Base;
import com.koinex_test.utils.InitiateDriver;
import com.koinex_test.utils.PropertyReader;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import  org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.Duration;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.testng.*;
import ru.yandex.qatools.allure.Allure;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import ru.yandex.qatools.allure.events.MakeAttachmentEvent;

/**
 * Created by codecraft on 13/09/16.
 */
public class TestBase implements IHookable{
    protected RemoteWebDriver driver;
    public static HashMap<String,String> configProperties;
    public static HashMap<String,String> credentialsProperties;
    public static String windowsPath=System.getProperty("user.dir")+"\\src\\main\\resources\\"; //For Windows Properties path
    public static String linux_MacPath=System.getProperty("user.dir")+"/src/main/resources/";  //For Linux Properties path

    public TestBase() {
        configProperties = PropertyReader.getPropValues("config.properties");
        credentialsProperties = PropertyReader.getPropValues("credentials.properties");
    }

    @BeforeMethod(alwaysRun=true)
    public void setup()
    {
        String url = configProperties.get("URL");
        InitiateDriver initiateDriver = new InitiateDriver();
        driver = initiateDriver.getDriver();
       JavascriptExecutor jse = (JavascriptExecutor)driver;
        String screenWidth = jse.executeScript("return screen.availWidth").toString();
        String screenHeight = jse.executeScript("return screen.availHeight").toString();
        int intScreenWidth = Integer.parseInt(screenWidth);
        int intScreenHeight = Integer.parseInt(screenHeight);
        Dimension d = new Dimension(intScreenWidth, intScreenHeight);
        driver.manage().window().setSize(d);
        driver.manage().timeouts().implicitlyWait(15,TimeUnit.SECONDS);
        Base.driver = driver;
        System.out.println(url);
        driver.get(url);

    }


    //Take Screenshot, whenever test cases Fail. The screenshots taken will be attached to Allure report
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {

        callBack.runTestMethod(testResult);
        if (testResult.getThrowable() != null) {
            try {
                takeScreenShot(testResult.getMethod().getMethodName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Attach Screenshots taken to Allure report
    //@Attachment(value = "Failure in method {0}", type = "image/png")
    private void takeScreenShot(String methodName) throws IOException {
         Allure.LIFECYCLE.fire(new MakeAttachmentEvent(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "Failure in method {0}", "image/png"));

        //return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    @AfterMethod(alwaysRun = true)
    public void teardownAndTakeScreenShot(ITestResult result)
    {
        driver.quit();
    }

    public void sleep(int duration)
    {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(new Duration(duration, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
