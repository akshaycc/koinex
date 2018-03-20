package com.koinex_test.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

/**
 * Created by codecraft on 13/09/16.
 */
public class Base {
    public static long wait=60;
    public static RemoteWebDriver driver;

    public static boolean isElementVisible(WebElement element)
    {
        try{
            if(element.isDisplayed())
                return true;
            return false;
        }
        catch (org.openqa.selenium.NoSuchElementException e)
        {
            return false;
        }

    }

    public static void waitForElementToBeInvisible(WebElement element)
    {
        WebDriverWait wwait = new WebDriverWait(driver,wait);
        wwait.until(invisibilityOfWebElementLocated(element));
    }

    public static void waitUntilElementIsVisible(WebElement element){
        WebDriverWait wwait = new WebDriverWait(driver, wait);
        wwait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilElementHasText(WebElement element, String text)
    {
        WebDriverWait wwait = new WebDriverWait(driver, wait);
        wwait.until(ExpectedConditions.textToBePresentInElement(element,text));
    }

    private static ExpectedCondition<Boolean> invisibilityOfWebElementLocated(final WebElement element)
    {
        return new ExpectedCondition<Boolean>() {
            //@Override
            public Boolean apply(WebDriver driver) {
                try
                {
                    if (element.isDisplayed())
                        return false;
                    return true;
                }
                catch (Exception e)
                {
                    return true;
                }
            }
        };
    }

    public static void waitUntilElementsAttributeHasChanged(WebElement element, String attribute, String initialValue){
        WebDriverWait wwait = new WebDriverWait(driver, wait);
        wwait.until(attributeValueOfElementChanged(element,attribute,initialValue));
    }

    private static ExpectedCondition<Boolean> attributeValueOfElementChanged(final WebElement element, final String attribute, final String initialValue){
        return new ExpectedCondition<Boolean>() {
            //@Override
            public Boolean apply(WebDriver driver) {
                try
                {
                    if (element.getAttribute(attribute).equalsIgnoreCase(initialValue))
                        return false;
                    return true;
                }
                catch (Exception e)
                {
                    return true;
                }
            }
        };

    }


    /**
     * get current location of the browser
     * @return
     */
    public static String getCurrentLocation()
    {
        driver.executeScript("return document.readyState;").equals("complete");
        return driver.getCurrentUrl();
    }

    /**
     * execute java script
     * @param script
     * @return
     */
    public Object executeScript(String script)
    {
        return driver.executeScript(script);
    }

    public Object executeScript(String script,WebElement element)
    {
        return  ((JavascriptExecutor) driver).executeScript(script, element);

    }

    /**
     * get page source
     * @return
     */
    public String getPageSource()
    {
        return driver.getPageSource();
    }

    /**
     * return current window handles
     */
    public static Set<String> getWindowHandles()
    {
        return driver.getWindowHandles();
    }


    /**
     * return current window handle
     */
    public static String getWindowHandle()
    {
        return driver.getWindowHandle();
    }

    /**
     * return targert locator
     * @return
     */
    public static WebDriver.TargetLocator switchTo()
    {
        return driver.switchTo();
    }

    /**
     * navigate to new page
     * @return
     */
    public static WebDriver.Navigation navigateTo()
    {
        return driver.navigate();
    }

    public static void back(){   driver.navigate().back();}

    public static void forward() { driver.navigate().forward();}

    public static void refreshPage(){
        driver.navigate().refresh();
    }

    /**
     * get title of page
     * @return
     */
    public String getTitle()
    {
        return driver.getTitle();
    }

    /**
     * opens passed Url
     * @param url
     */
    public void openUrl(String url){
        driver.get(url);
    }

    /**
     * delete all cookies
     */
    public void deleteAllCookies()
    {

        driver.manage().deleteAllCookies();
    }

    /**
     * zoom out browser
     * @param numberOfTimes
     */
    public  void zoomOut(int numberOfTimes)
    {
        try
        {
            for(int i=0; i<numberOfTimes; i++) {
                driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SUBTRACT));
            }
            Thread.sleep(1000);
        }catch (Exception e)
        {}
    }

    /**
     * reset zoom
     */
    public void resetZoom()
    {
        try {
            driver.findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
            Thread.sleep(1000);
        }catch (Exception e)
        {}
    }

    /**
     * Generic method to Switch to Pop Up, enter element as parameter by which clicking on it pop up opens
     *
     * @param popup
     */
    public String switchtoPopup(WebElement popup) {
        String mainWindowHandle = driver.getWindowHandle();
        popup.click();
        Set s = driver.getWindowHandles();
        Iterator ite = s.iterator();
        while (ite.hasNext()) {
            String popupHandle = ite.next().toString();
            if (!popupHandle.contains(mainWindowHandle)) {
                driver.switchTo().window(popupHandle);
            }
        }

        return mainWindowHandle;
    }

    /**
     * Switch to Parent window with parameter value of Parent window
     *
     * @param Parentwindow
     */
    public void switchtoParentfromPopUp(String Parentwindow) {
        driver.switchTo().window(Parentwindow);
    }

    public void closeChildWindowofPopUp(String childwindow) {
        driver.switchTo().window(childwindow).close();
    }
    /**
     * This function is used to return currentwindow
     *
     * @return
     */
    public String returnCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    /**
     * wait for page to load having given URL
     *
     * @param pageURL
     */
    public static boolean waitForPageToLoad(final String pageURL) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 45);
            return wait.until(new ExpectedCondition<Boolean>() {
                                  public Boolean apply(WebDriver d) {
                                      return (driver.getCurrentUrl().toLowerCase().contains(pageURL.toLowerCase())) && driver.executeScript("return document.readyState;").equals("complete");
                                  }
                              }
            );
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static void getElementintoView(WebElement element){
        Coordinates coordinates=((Locatable)element).getCoordinates();
        System.out.println(coordinates.inViewPort());
        sleep(3000);
    }

    public static void sleep(int timeout){
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollToElement(WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollVerticallWithCords(int startValue, int endValue) {
        try {
            ((JavascriptExecutor) driver).executeScript("scroll(" + startValue + "," + endValue + " );");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Scroll Down
    public static void scrollDownVertical(){
        try{
            JavascriptExecutor js = ((JavascriptExecutor) driver);

            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void mouseHoverAndClickElement(WebElement srcElement, WebElement targetElement){
        Actions action = new Actions(driver);
        action.moveToElement(srcElement).perform();
        action.moveToElement(targetElement);
        action.click();
        action.perform();
    }

    public static void mouseHoverOnElement(WebElement srcElement){
        Actions action = new Actions(driver);
        action.moveToElement(srcElement).perform();
    }

    public static void slideThroughSlider(WebElement element, int value){
        Actions move = new Actions(driver);
        Action action = move.dragAndDropBy(element, value, 0).build();
        action.perform();
    }

    public static void dragOnAnotherElement(WebElement element1, WebElement element2){
        Actions move = new Actions(driver);
        Actions action = move.dragAndDrop(element1,element2);
        action.perform();
    }

    //Should work on any Input Type
    // @Parameters: WebElement, String
    // Returns: boolean
    public static boolean enterTextInTextField(WebElement element, String text){
        element.sendKeys(text);
        if (getValueOfTextField(element).equalsIgnoreCase(text))
            return true;

        return false;
    }

    public static String getValueOfTextField(WebElement element){
        return element.getAttribute("value");
    }

    // Generic Methods

    public static String generateRandomString(int length){
        String randomString = new String();
        randomString = RandomStringUtils.randomAlphabetic(length);
        return randomString;
    }

    public static String generateAlphaNumericString(int length){
        String randomAlphaNumericString = new String();
        randomAlphaNumericString  = RandomStringUtils.randomAlphanumeric(length);
        return randomAlphaNumericString;
    }

    public static String generateRandomMobileNumber(){
        String mobileNumber = new String();
        mobileNumber = RandomStringUtils.random(1,"789");
        mobileNumber=mobileNumber+RandomStringUtils.randomNumeric(9);
        return mobileNumber;
    }

    public static String generateRandomEmail(){
        String email = RandomStringUtils.randomAlphanumeric(12).toLowerCase();
        return email+"@mailinator.com";
    }

    public static String generateRandomNumber(){
        return RandomStringUtils.randomNumeric(13);
    }

    public static void waitForElementToBeClickable(WebElement element)
    {
        WebDriverWait wwait = new WebDriverWait(driver,wait);
        wwait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static String getFirstSelectedValueInDropDown(WebElement element){
        Select select=new Select(element);
        return  select.getFirstSelectedOption().getText();
    }
}
