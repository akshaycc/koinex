package com.koinex_test.Login;

import com.koinex_test.Pages;
import com.koinex_test.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by codecraft on 11/07/17.
 */
public class LoginTest extends TestBase {

    @Test
    public void login(){
        Assert.assertEquals(Pages.LoginPage().getVersion(),"BETA","Invalid version");
    }
}
