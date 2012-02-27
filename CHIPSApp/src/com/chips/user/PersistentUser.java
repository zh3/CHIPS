package com.chips.user;

import android.app.Activity;
import android.content.SharedPreferences;

public class PersistentUser {
    private static final int DEFAULT_ID = 0;
    private static final String USER_LOGIN_PREFERENCES 
        = "CHIPSUserLoginPreferences";
    private static final String LOGIN_SETTINGS_EXIST = "loginSettingsExist";
    private static final String DEFAULT_SESSION_ID = "-1";
    
    // Can't instantiate
    private PersistentUser() {
        
    }
    
    public static int getCurrentSessionId() {
        return DEFAULT_ID;
    }
    
    public static boolean loginSettingsExist(Activity activity) {
        SharedPreferences settings 
            = activity.getSharedPreferences(USER_LOGIN_PREFERENCES , 0);
        return settings.getBoolean(LOGIN_SETTINGS_EXIST, false);
    }
    
    public static void setSessionID(String newSessionID) {
        sessionID = newSessionID.trim();
    }
    
    public String getSessionID() {
        return sessionID;
    }
    
    private static String sessionID = DEFAULT_SESSION_ID;
}
