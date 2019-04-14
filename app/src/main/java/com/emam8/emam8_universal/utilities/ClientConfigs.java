package com.emam8.emam8_universal.utilities;

import com.emam8.emam8_universal.BuildConfig;

/**
 * contain client information such as BASE_URL || client information
 */
public class ClientConfigs {
    //TODO: should get network ip address like 192.168.1.2 and replace inside REST_API_BASE_URL
    public static final String REST_API_BASE_URL = BuildConfig.Apikey_BaseUrl;
    public static final String App_name = "emam8_universal";
    //TODO: create new Client with postman in http://localshot:/api/v1/client with body {"name":"android client app"} and set these values with client_id and client_key
    public static final String CLIENT_ID = "";
    public static final String CLIENT_KEY = "";
}
