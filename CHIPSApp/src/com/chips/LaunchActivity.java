package com.chips;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chips.dataclient.LoginClient;
import com.chips.user.PersistentUser;

public class LaunchActivity extends Activity implements Observer {
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String LOGIN_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/"
                + "authenticate_credentials";
    
    @Override
    protected void onCreate(Bundle state){
       super.onCreate(state);

       applicationHubActivityIntent = new Intent(this, ApplicationHubActivity.class);
       loginActivityIntent = new Intent(this, LoginActivity.class);
       
       if (PersistentUser.loginSettingsExist(this) 
               && PersistentUser.isLoginAutomaticallyEnabled(this)) {
           loginClient = new LoginClient();
           loginClient.addObserver(this);
           
           // Login with cached details
           ArrayList<String> loginArguments = new ArrayList<String>();
           loginArguments.add(PersistentUser.getSavedUsername(this));
           loginArguments.add(PersistentUser.getSavedPassword(this));
           loginClient.setURL(LOGIN_URL, loginArguments);
           
           loginClient.refreshClient();
       } else {
           startActivity(loginActivityIntent);
       }
       
       finish();
    }
    
    public void update(Observable dataClient, Object data) {
        if (loginClient.lastLoginSuccessful()) {
            PersistentUser.setSessionID(loginClient.getSessionId());
            startActivity(applicationHubActivityIntent);
            finish();
        } else {
            Toast.makeText(this, "Login with saved details failed", 
                    Toast.LENGTH_LONG).show();
            startActivity(loginActivityIntent);
        }
    }
    
    private LoginClient loginClient;
    private Intent applicationHubActivityIntent;
    private Intent loginActivityIntent;
        
}
