package com.koinex_test.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.Platform;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import java.util.HashMap;

/**
 * Created by codecraft on 13/09/16.
 */
public class InitiateDriver {

    private RemoteWebDriver driver;
    private AppiumDriver appiumDriver;
    private HashMap<String,String> getProperties;

    public InitiateDriver()
    {
        try
        {
            getProperties = PropertyReader.getPropValues("config.properties");
            String runOn = System.getProperty("runOn") == null ? getProperties.get("RUN_ON") : System.getProperty("runOn");
            String platform = System.getProperty("platform") == null ? getProperties.get("PLATFORM") : System.getProperty("platform");
            String url = System.getProperty("url") == null ? getProperties.get("SELENIUMSERVERURL") : System.getProperty("url");
            String mUrl =System.getProperty("mUrl") == null ? getProperties.get("mURL") : System.getProperty("mUrl");
            String browser = null;

            if(platform.equalsIgnoreCase("WINDOWS"))
            {
                if (runOn.equalsIgnoreCase("WEBSITE"))
                {
                    browser = System.getProperty("browser") == null ? getProperties.get("BROWSER") : System.getProperty("browser");
                    System.out.println(url);
                    driver = new RemoteWebDriver(new URL(url), getBrowserCapabilities(browser, runOn));
                    System.out.println("Driver is: "+driver);
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }
            }
            else if(platform.equalsIgnoreCase("ANDROID"))
            {
                if (runOn.equalsIgnoreCase("ANDROID_SITE"))
                {
                    browser = System.getProperty("mBrowser") == null ? getProperties.get("MOBILE_BROSWER") : System.getProperty("mBrowser");
                    appiumDriver = new AndroidDriver(new URL(mUrl), getBrowserCapabilities(browser, runOn));
                    appiumDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }
                else if (runOn.equalsIgnoreCase("ANDROID_APP")) {
                    appiumDriver = new AndroidDriver(new URL(getProperties.get("AndroidAppURL")), getBrowserCapabilities(browser, runOn));
                    appiumDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }

            }
            else if(platform.equalsIgnoreCase("IOS"))
            {
                if (runOn.equalsIgnoreCase("IOS_SITE"))
                {
                    browser = System.getProperty("mBrowser") == null ? getProperties.get("MOBILE_BROSWER") : System.getProperty("mBrowser");
                    appiumDriver = new IOSDriver(new URL(mUrl), getBrowserCapabilities(browser, runOn));
                    appiumDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }
                else if (runOn.equalsIgnoreCase("IOS_APP")) {
                    appiumDriver = new IOSDriver(new URL(getProperties.get("iOSAppURL")), getBrowserCapabilities(browser, runOn));
                    appiumDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public RemoteWebDriver getDriver()
    {
        if(driver==null)
            throw new RuntimeException("Driver has not been Instantiated");

        return driver;
    }

    public AppiumDriver getAppiumDriver()
    {
        if(appiumDriver==null)
            throw new RuntimeException("Driver has not been instantiated");

        return appiumDriver;
    }

    private DesiredCapabilities getBrowserCapabilities(String browser, String runOn)
    {
        DesiredCapabilities capabilities = null;
        String appName = System.getProperty("appName") == null ? getProperties.get("AppName") : System.getProperty("appName");
        String appPath =System.getProperty("appPath") == null ? getProperties.get("AppPath") : System.getProperty("appPath");

        try
        {
            if (runOn.equalsIgnoreCase("WEBSITE") && browser.equalsIgnoreCase("Firefox"))
            {
                capabilities = DesiredCapabilities.firefox();
                capabilities.setBrowserName("firefox");
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setPlatform(Platform.ANY);
            }
            else if (runOn.equalsIgnoreCase("WEBSITE") && browser.equalsIgnoreCase("IE"))
            {
                //InternetExplorerDriverManager.getInstance().setup();
                //System.setProperty("webdriver.ie.driver", "../../../../resources/IEDriverServer.exe");
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setBrowserName("internet explorer");
                capabilities.setPlatform(Platform.WINDOWS);
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability("ie.ensureCleanSession", true);
            }
            else if (runOn.equalsIgnoreCase("WEBSITE") && browser.equalsIgnoreCase("Chrome"))
            {
                //ChromeDriverManager.getInstance().setup();
                //System.setProperty("webdriver.chrome.driver","/home/" + System.getProperty("user.name") + "/Documents/chromedriver");
                capabilities = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                //options.addArguments("user-data-dir=/Users/codecraft/Library/Application Support/Google/Chrome");
                //options.addArguments("user-data-dir=/Users/codecraft/Library/Application Support/Google/Chrome/Profile 2");
                options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-component-update")); //To Play DRM Content
                options.addArguments("disable-extensions");
                options.addArguments("start-maximized");
                //options.addArguments("--start-fullscreen");
                capabilities.setBrowserName("chrome");
                capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                capabilities.setPlatform(Platform.ANY);
            }
            else if (runOn.equalsIgnoreCase("ANDROID_SITE"))
            {
                capabilities = DesiredCapabilities.android();
                //capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "chrome");
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "");
            }
            else if (runOn.equalsIgnoreCase("IOS_SITE"))
            {
                capabilities = DesiredCapabilities.iphone();
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.3");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, browser);
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "");
            }
            else if (runOn.equalsIgnoreCase("ANDROID_APP"))
            {

                File appDir = new File(appPath);
                File app = new File(appDir, appName);
                capabilities=new DesiredCapabilities();
                capabilities.setCapability("deviceName", "a");
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("app", app.getAbsolutePath());
                capabilities.setCapability("appPackage", "com.glosi.glosiapp");
                capabilities.setCapability("appActivity", "com.greenlaneinnovationsllc.glosi.activity.SplashScreenActivity");
            }
            else if (runOn.equalsIgnoreCase("IOS_APP"))
            {
                File appDir = new File(appPath);
                File app = new File(appDir, appName);
                capabilities=new DesiredCapabilities();

                //  capabilities.setCapability("autoAcceptAlerts", true);
                capabilities.setCapability("platformVersion", "9.1");
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("deviceName", "iPhone");
                capabilities.setCapability("udid", "517ea6a9384dedd07fe34f3c9f6b7d70e5c57ff6");
                capabilities.setCapability("app", app.getPath());


            }
            else
            {
                // default is firefox
                capabilities = DesiredCapabilities.firefox();
                capabilities.setBrowserName("firefox");
                capabilities.setPlatform(Platform.ANY);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return capabilities;
    }
}
