package com.emam8.emam8_universal.Model;

import java.util.Date;

/**
 * Token Model used in sign in, sign up and refresh token response
 */
public class TokenModel {
    public String access_token;
    public String expire_in_sec;
    public String expire_at;
    public String refresh_token;
    public String app_id;
}
