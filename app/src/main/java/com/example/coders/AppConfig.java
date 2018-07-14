package com.example.coders;

/**
 * Created by Mustafa on 14/07/2018.
 */

public class AppConfig {
    private final static String App_URL = "http://192.168.8.100:8000/api";
    final static String loginUrl = App_URL + "/auth/login";
    final static String showUrl = App_URL + "/auth/user";
    final static String logoutUrl = App_URL + "/auth/logout";
}
