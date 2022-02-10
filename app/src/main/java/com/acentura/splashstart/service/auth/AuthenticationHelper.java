package com.acentura.splashstart.service.auth;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.acentura.splashstart.R;
import com.microsoft.graph.authentication.BaseAuthenticationProvider;
import com.microsoft.identity.client.AuthenticationCallback;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.IPublicClientApplication;
import com.microsoft.identity.client.ISingleAccountPublicClientApplication;
import com.microsoft.identity.client.PublicClientApplication;
import com.microsoft.identity.client.exception.MsalException;

import java.net.URL;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nonnull;

// Singleton class - the app only needs a single instance
// of PublicClientApplication
public class AuthenticationHelper extends BaseAuthenticationProvider {
    private static AuthenticationHelper INSTANCE = null;
    private ISingleAccountPublicClientApplication mPCA = null;
    private String[] mScopes = { "User.Read", "MailboxSettings.Read", "Calendars.ReadWrite" };

    private AuthenticationHelper(Context ctx, final IAuthenticationHelperCreatedListener listener) {
        PublicClientApplication.createSingleAccountPublicClientApplication(ctx, R.raw.msal_config,
                new IPublicClientApplication.ISingleAccountApplicationCreatedListener() {
                    @Override
                    public void onCreated(ISingleAccountPublicClientApplication application) {
                        mPCA = application;
                        listener.onCreated(INSTANCE);
                    }

                    @Override
                    public void onError(MsalException exception) {
                        Log.e("AUTHHELPER", "Error creating MSAL application", exception);
                        listener.onError(exception);
                    }
                });
    }

    public static synchronized CompletableFuture<AuthenticationHelper> getInstance(Context ctx) {

        if (INSTANCE == null) {
            CompletableFuture<AuthenticationHelper> future = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                future = new CompletableFuture<>();
            }
            CompletableFuture<AuthenticationHelper> finalFuture = future;
            INSTANCE = new AuthenticationHelper(ctx, new IAuthenticationHelperCreatedListener() {
                @Override
                public void onCreated(AuthenticationHelper authHelper) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        finalFuture.complete(authHelper);
                    }
                }

                @Override
                public void onError(MsalException exception) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        finalFuture.completeExceptionally(exception);
                    }
                }
            });

            return future;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return CompletableFuture.completedFuture(INSTANCE);
            }
            else return null;
        }
    }

    // Version called from fragments. Does not create an
    // instance if one doesn't exist
    public static synchronized AuthenticationHelper getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException(
                    "AuthenticationHelper has not been initialized from MainActivity");
        }

        return INSTANCE;
    }

    public CompletableFuture<IAuthenticationResult> acquireTokenInteractively(Activity activity) {
        CompletableFuture<IAuthenticationResult> future = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            future = new CompletableFuture<>();
        }
        mPCA.signIn(activity, null, mScopes, getAuthenticationCallback(future));

        return future;
    }

    public CompletableFuture<IAuthenticationResult> acquireTokenSilently() {
        // Get the authority from MSAL config
        String authority = mPCA.getConfiguration()
                .getDefaultAuthority().getAuthorityURL().toString();

        CompletableFuture<IAuthenticationResult> future = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            future = new CompletableFuture<>();
        }
        mPCA.acquireTokenSilentAsync(mScopes, authority, getAuthenticationCallback(future));
        return future;
    }

    public void signOut() {
        mPCA.signOut(new ISingleAccountPublicClientApplication.SignOutCallback() {
            @Override
            public void onSignOut() {
                Log.d("AUTHHELPER", "Signed out");
            }

            @Override
            public void onError(@NonNull MsalException exception) {
                Log.d("AUTHHELPER", "MSAL error signing out", exception);
            }
        });
    }

    private AuthenticationCallback getAuthenticationCallback(
            CompletableFuture<IAuthenticationResult> future) {
        return new AuthenticationCallback() {
            @Override
            public void onCancel() {
                future.cancel(true);
            }

            @Override
            public void onSuccess(IAuthenticationResult authenticationResult) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    future.complete(authenticationResult);
                }
            }

            @Override
            public void onError(MsalException exception) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    future.completeExceptionally(exception);
                }
            }
        };
    }

    @Nonnull
    @Override
    public CompletableFuture<String> getAuthorizationTokenAsync(@Nonnull URL requestUrl) {
        if (shouldAuthenticateRequestWithUrl(requestUrl) == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return acquireTokenSilently()
                        .thenApply(result -> result.getAccessToken());
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return CompletableFuture.completedFuture(null);
        }
        else return null;
    }
}
