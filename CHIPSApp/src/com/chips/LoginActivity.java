package com.chips;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
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

        setupLoginFields();
    }
    
    private void setupLoginFields() {
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        
        saveLoginDetailsCheckBox 
            = (CheckBox) findViewById(R.id.saveLoginDetailsCheckBox);
        loginAutomaticallyCheckBox 
            = (CheckBox) findViewById(R.id.loginAutomaticallyCheckBox);
        saveLoginDetailsCheckBox.setChecked(
                PersistentUser.isSaveLoginDetailsEnabled(this));
        loginAutomaticallyCheckBox.setChecked(
                PersistentUser.isLoginAutomaticallyEnabled(this));
        loginAutomaticallyCheckBox.setOnClickListener(
                new LoginAutomaticallyOnClickListener());
        
        if (PersistentUser.isSaveLoginDetailsEnabled(this)) {
            usernameEditText.setText(PersistentUser.getSavedUsername(this));
            passwordEditText.setText(PersistentUser.getSavedPassword(this));
        }
    }
    
    public void loginClicked(View v) {
        if (loginDetailsEntered()) {
            ArrayList<String> loginArguments = new ArrayList<String>();
            loginArguments.add(usernameEditText.getText().toString());
            loginArguments.add(passwordEditText.getText().toString());
            loginClient.setURL(LOGIN_URL, loginArguments);
            
            loginClient.asynchronousLoadClientData();
        } else {
            Toast.makeText(this, "Please enter a username and password", 
                    Toast.LENGTH_LONG).show();
        }
    }
    
    private boolean loginDetailsEntered() {
        return (!usernameEditText.getText().toString().equals("")
                    && !passwordEditText.getText().toString().equals(""));
    }
    
    public void registerClicked(View v) {
        startActivity(registerActivityIntent);
    }
    
    public void update(Observable dataClient, Object data) {
        if (loginClient.lastLoginSuccessful()) {
            PersistentUser.setSessionID(loginClient.getSessionId());
            PersistentUser.setPreferredLoginDetails(this, 
                    usernameEditText.getText().toString(), 
                    passwordEditText.getText().toString());
            PersistentUser.setLoginAutomaticallyEnabled(this, 
                    loginAutomaticallyCheckBox.isChecked());
            PersistentUser.setSaveLoginDetailsEnabled(this, 
                    saveLoginDetailsCheckBox.isChecked());
            
            startActivity(applicationHubActivityIntent);
            finish();
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }
    
    private class LoginAutomaticallyOnClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (loginAutomaticallyCheckBox.isChecked()) {
                saveLoginDetailsCheckBox.setChecked(true);
                saveLoginDetailsCheckBox.setEnabled(false);
            } else {
                saveLoginDetailsCheckBox.setEnabled(true);
            }
        }
        
    }
    
    private LoginClient loginClient;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox saveLoginDetailsCheckBox;
    private CheckBox loginAutomaticallyCheckBox;
    private Intent applicationHubActivityIntent;
    private Intent registerActivityIntent;
}