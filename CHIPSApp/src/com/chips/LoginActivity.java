package com.chips;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chips.dataclient.LoginClient;
import com.chips.user.PersistentUser;

public class LoginActivity extends Activity implements Observer {
    private static final String LOGIN_URL 
        = "http://cs110chips.phpfogapp.com/index.php/mobile/"
                + "authenticate_credentials";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        registerActivityIntent = new Intent(this, RegisterActivity.class);
        applicationHubActivityIntent 
            = new Intent(this, ApplicationHubActivity.class);
        
        loginClient = new LoginClient();
        loginClient.addObserver(this);
        
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
    }
    
    public void loginClicked(View v) {
        ArrayList<String> loginArguments = new ArrayList<String>();
        loginArguments.add(usernameEditText.getText().toString());
        loginArguments.add(passwordEditText.getText().toString());
        loginClient.setURL(LOGIN_URL, loginArguments);
        
        loginClient.refreshClient();
        //startActivity(applicationHubActivityIntent);
        //finish();
    }
    
    public void registerClicked(View v) {
        startActivity(registerActivityIntent);
    }
    
    public void update(Observable dataClient, Object data) {
        if (loginClient.lastLoginSuccessful()) {
            PersistentUser.setSessionID(loginClient.getSessionId());
            startActivity(applicationHubActivityIntent);
            finish();
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }
    
    private LoginClient loginClient;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Intent applicationHubActivityIntent;
    private Intent registerActivityIntent;
}