package com.example.nettypractice.auth;

import com.example.nettypractice.protocol.Message;

public class AuthMessageFactory {
    public Message createMessage(byte actionTypeCode) {
        AuthAction action = AuthAction.fromCode(actionTypeCode);
        // We don't have concrete message classes yet.
        // This will throw an exception for now.
        switch (action) {
            // case LOGIN_REQUEST: return new LoginRequestMessage();
            // case LOGIN_RESPONSE: return new LoginResponseMessage();
            default:
                throw new IllegalArgumentException("Unsupported auth action type: " + action);
        }
    }
}
