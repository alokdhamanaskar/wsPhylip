package util;

import java.util.Properties;

/**
 *
 * @author Alok Dhamanaskar
 * @see LICENSE (MIT style license file).
 *
 */
public class GetAbsolutePath {

    public String getPath() {

        //return "/home/alok/NetBeansProjects/wsPhylip/tmp/";
        try {
            Properties prop = new Properties();
            //Loading the properties file as a Input file
            prop.load(getClass().getResourceAsStream("GlobalVar.properties"));
            String value = prop.getProperty("phylipOutputDir");
            return value;
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
        //return getClass().getClassLoader().getResource(".").getPath() + "tmp/";

    }//getValueFromProperty

    public static void main(String[] args) throws ClassNotFoundException {
        GetAbsolutePath p = new GetAbsolutePath();
        System.out.println(p.getPath());

    }//main
}
