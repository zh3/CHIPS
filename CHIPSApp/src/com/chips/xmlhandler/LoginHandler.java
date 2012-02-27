package com.chips.xmlhandler;

import java.util.Arrays;
import java.util.EnumMap;

import org.xml.sax.SAXException;

import com.chips.xmlhandler.TagStateEnum.LoginTagState;

public class LoginHandler extends SAXHandler<LoginTagState> {
    
    public LoginHandler() {
        buffers = new EnumMap<LoginTagState, StringBuilder>(
                LoginTagState.class);
        values = Arrays.asList(LoginTagState.values());
        
        sessionId = "";
        lastLoginAttemptSuccessful = false;
    }

    @Override
    protected LoginTagState valueOf(String s) {
        return LoginTagState.valueOf(normalize(s));
    }
    
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if (localName.equals("login")) {
            lastLoginAttemptSuccessful 
                = getString(LoginTagState.SUCCESS).trim().equals("1");
            sessionId = getString(LoginTagState.SESSIONID);

            clearTagBuffers();
        }
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public boolean lastLoginAttemptSuccessful() {
        return lastLoginAttemptSuccessful;
    }
    
    private boolean lastLoginAttemptSuccessful;
    private String sessionId;
}
