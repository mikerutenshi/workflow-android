package com.workflow.data.net;

import com.workflow.data.net.services.AuthService;

public class TokenServiceHolder {
    AuthService authService = null;

    public AuthService getAuthService() {
        return authService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
