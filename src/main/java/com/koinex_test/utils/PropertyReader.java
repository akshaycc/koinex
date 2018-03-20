package com.koinex_test.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * Created by codecraft on 08/11/16.
 */
public class PropertyReader {
    public static HashMap<String,String> getPropValues(String file) {
        InputStream inputStream;
        HashMap<String,String> result = new HashMap<String, String>();

        try {
            Properties prop = new Properties();
            String propFileName = file;
            inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(propFileName);
            //inputStream = new FileInputStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            // get the property values
            Set propNames = prop.stringPropertyNames();
            Iterator<String> iterator = propNames.iterator();
            while (iterator.hasNext())
            {
                String key = iterator.next();
                result.put(key , prop.getProperty(key));
            }

            inputStream.close();


        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return result;
    }

    public static void setPropsValue(String file,String key,String value)
    {
        Properties prop = new Properties();
        try{
            prop.load(new InputStreamReader(new FileInputStream(file)));
        }catch (FileNotFoundException e){
            System.out.println("File Not Found");
        }catch (IOException e){
            System.out.println("Failed to write to credentials.properties");
        }

        try {
            prop.remove(key);
            prop.setProperty(key, value);
            prop.store(new FileOutputStream(file,false), null);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}