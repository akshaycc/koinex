package com.koinex_test;



import com.koinex_test.Login.LoginPage;

import com.koinex_test.utils.Base;

/**
 * Created by codecraft on 13/09/16.
 */
public class Pages {

    private static <T extends Base> T getPage(Class<T> pageType)  {
        T page;
        try {
            page = pageType.newInstance();
        } catch (InstantiationException e) { //make sure you use JDK 1.7
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return page;
    }

    public static LoginPage LoginPage(){return getPage(LoginPage.class);}


}
