package com.example.chara.helper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UserServiceHelper {

    public static String ACCESS_TOKEN = null;

    static String REST_ID = "client";

    static String REST_SECRET = "secret";

    static String USER_LOGIN = "admin";

    static String USER_PASSWORD = "admin";

    public static String base64RestParams() {
        Base64.Encoder encoder = Base64.getEncoder();
        String restParams = String.format("%s:%s", REST_ID, REST_SECRET);
        return "Basic " + encoder.encodeToString(restParams.getBytes(StandardCharsets.UTF_8));
    }

    public static String userParams() {
        return String
                .format("grant_type=password&username=%s&password=%s",
                        USER_LOGIN,
                        USER_PASSWORD);
    }

}
