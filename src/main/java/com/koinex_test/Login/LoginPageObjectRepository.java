package com.koinex_test.Login;

import com.koinex_test.utils.ObjectsBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

/**
 * Created by codecraft on 11/07/17.
 */
public class LoginPageObjectRepository extends ObjectsBase {

    public LoginPageObjectRepository(RemoteWebDriver driver){
        super(driver);
    }

    @FindBy(xpath = "//div[@class='container-fluid']/descendant::*/div[@class='version']")
    protected WebElement ver;

}
