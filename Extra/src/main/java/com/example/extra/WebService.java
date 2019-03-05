package com.example.extra;


public class WebService {
    private static final String HOSTING = "192.168.1.9";
    private static final String RAIZ = "http://"+ HOSTING;
    private static final String url = RAIZ + "/WScomfacesar/index.php";

    public static String getUrl() {
        return url;
    }
    public static String getHOSTING() {
        return HOSTING;
    }

    public static String getRAIZ() {
        return RAIZ;
    }
}
