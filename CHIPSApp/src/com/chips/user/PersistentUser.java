package com.chips.user;

import android.app.Activity;
import android.content.SharedPreferences;

public class PersistentUser {
    private static final String USER_LOGIN_PREFERENCES 
        = "CHIPSUserLoginPreferences";
    private static final String LOGIN_SETTINGS_EXIST = "loginSettingsExist";
    private static final String DEFAULT_SESSION_ID = "-1";
    private static final String PREFERRED_USERNAME = "username";
    private static final String PREFERRED_PASSWORD = "password";
    private static final String SAVE_LOGIN_DETAILS_ENABLED 
        = "saveLoginDetailsEnabled";
    private static final String LOGIN_AUTOMATICALLY_ENABLED 
        = "loginAutomaticallyEnabled";
    
    // Can't instantiate
    private PersistentUser() {
        
    }
    
    public static boolean loginSettingsExist(Activity activity) {
        SharedPreferences settings 
            = activity.getSharedPreferences(USER_LOGIN_PREFERENCES, 0);
        return settings.getBoolean(LOGIN_SETTINGS_EXIST, false);
    }
    
    public static void setSessionID(String newSessionID) {
        sessionID = newSessionID.trim();
    }
    
    public static String getSessionID() {
        return sessionID;
    }
    
    public static void setPreferredLoginDetails(Activity activity, 
            String username, String password) {
        SharedPreferences settings
            = activity.getSharedPreferences(USER_LOGIN_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(LOGIN_SETTINGS_EXIST, true);
        editor.putString(PREFERRED_USERNAME, username);
        editor.putString(PREFERRED_PASSWORD, password);
        
        editor.commit();
    }
    
    public static String getSavedUsername(Activity activity) {
        SharedPreferences settings 
            = activity.getSharedPreferences(USER_LOGIN_PREFERENCES, 0);
        return settings.getString(PREFERRED_USERNAME, "");
    }
    
    public static String getSavedPassword(Activity activity) {
        SharedPreferences settings 
            = activity.getSharedPreferences(USER_LOGIN_PREFERENCES, 0);
        return settings.getString(PREFERRED_PASSWORD, "");
    }
    
    public static void setSaveLoginDetailsEnabled(Activity activity, 
            boolean saveLoginDetailsEnabled) {
        SharedPreferences settings
            = activity.getSharedPreferences(USER_LOGIN_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(SAVE_LOGIN_DETAILS_ENABLED, saveLoginDetailsEnabled);
        editor.commit();
    }
    
    public static void setLoginAutomaticallyEnabled(Activity activity,
            boolean loginAutomaticallyEnabled) {
        SharedPreferences settings
            = activity.getSharedPreferences(USER_LOGIN_PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(LOGIN_AUTOMATICALLY_ENABLED, 
                loginAutomaticallyEnabled);
        editor.commit();
    }
    
    public static boolean isSaveLoginDetailsEnabled(Activity activity) {
        SharedPreferences settings 
            = activity.getSharedPreferences(USER_LOGIN_PREFERENCES, 0);
        return settings.getBoolean(SAVE_LOGIN_DETAILS_ENABLED, false);
    }
    
    public static boolean isLoginAutomaticallyEnabled(Activity activity) {
        SharedPreferences settings 
            = activity.getSharedPreferences(USER_LOGIN_PREFERENCES, 0);
        return settings.getBoolean(LOGIN_AUTOMATICALLY_ENABLED, false);
    }
    
    private static String sessionID = DEFAULT_SESSION_ID;
}
