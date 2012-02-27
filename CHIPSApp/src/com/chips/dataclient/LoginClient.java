package com.chips.dataclient;

import com.chips.xmlhandler.LoginHandler;

public class LoginClient extends XMLDataClient {

    @Override
    protected void reloadData() {
        LoginHandler handler = new LoginHandler();
        parse(handler);
        lastLoginSuccessful = handler.lastLoginAttemptSuccessful();
        sessionId = handler.getSessionId();
    } 
    
    public boolean lastLoginSuccessful() {
        return lastLoginSuccessful;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public boolean lastLoginSuccessful;
    public String sessionId;
}
