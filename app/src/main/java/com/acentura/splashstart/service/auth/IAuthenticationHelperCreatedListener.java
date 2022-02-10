package com.acentura.splashstart.service.auth;


import com.microsoft.identity.client.exception.MsalException;

public interface IAuthenticationHelperCreatedListener {
    void onCreated(final AuthenticationHelper authHelper);
    void onError(final MsalException exception);
}
