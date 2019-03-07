package com.emam8.emam8_universal.Model;

import com.emam8.emam8_universal.utilities.ClientConfigs;

/**
 * used in refresh token request
 */
public class RefreshTokenRequestModel {
    public String client_id;
    public String client_key;
    public String refresh_token;

    public RefreshTokenRequestModel() {
        this.client_id = ClientConfigs.CLIENT_ID;
        this.client_key = ClientConfigs.CLIENT_KEY;
    }
}
