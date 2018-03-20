package com.koinex_test.utils;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by codecraft on 25/11/16.
 */
public class ObjectsBase {

    public ObjectsBase(RemoteWebDriver driver){
        PageFactory.initElements(driver, this);
    }

}
