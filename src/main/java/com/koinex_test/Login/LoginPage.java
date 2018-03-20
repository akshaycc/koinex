package com.koinex_test.Login;


import com.koinex_test.utils.Base;

/**
 * Created by codecraft on 11/07/17.
 */
public class LoginPage extends Base {
    private LoginPageObjectRepository lp;

    public LoginPage(){
        lp = new LoginPageObjectRepository(driver);
    }

  public String getVersion(){
        System.out.println(lp.ver.getText());
        return lp.ver.getText();

  }

}
