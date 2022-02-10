package com.acentura.splashstart.ui.login;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.acentura.splashstart.R;
import com.acentura.splashstart.base.BaseBindingActivity;
import com.acentura.splashstart.databinding.ActivityLoginBinding;
import com.acentura.splashstart.di.module.viewmodelfactory.ViewModelFactory;
import com.acentura.splashstart.service.auth.AuthenticationHelper;
import com.acentura.splashstart.service.auth.GraphHelper;
import com.acentura.splashstart.ui.main.MainActivity;
import com.acentura.splashstart.ui.splash.SplashActivity;

import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalServiceException;
import com.microsoft.identity.client.exception.MsalUiRequiredException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends BaseBindingActivity<ActivityLoginBinding, LoginViewModel> {

    @Inject
    ViewModelFactory viewModelFactory;

    private AuthenticationHelper mAuthHelper = null;
    private String mUserName = null;
    private String mUserEmail = null;
    private String mUserTimeZone = null;
    private boolean mIsSignedIn = false;

    @Override
    protected int layoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel bindViewModel() {
        return new ViewModelProvider(this, viewModelFactory)
                .get(LoginViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        changeStatusBarContrastStyle(this.getWindow(),false);

        getBinding().btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                showProgress();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    AuthenticationHelper.getInstance(getApplicationContext())
                            .thenAccept(authHelper -> {
                                mAuthHelper = authHelper;
                                if (!mIsSignedIn) {
                                    doSilentSignIn(true);
                                } else {
                                    dismissProgress();
                                }
                            })
                            .exceptionally(exception -> {
                                Log.e("AUTH", "Error creating auth helper", exception);
                                return null;
                            });
                }
            }
        });

        getBinding().tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthHelper.signOut();
                mIsSignedIn =false;
            }
        });

        String packageName = getApplicationContext().getPackageName();
        try {
            PackageInfo info = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sha1Singature = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("KeyHash", sha1Singature);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("KeyHash name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("KeyHash exception", e.toString());
        }

        showProgress();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            AuthenticationHelper.getInstance(getApplicationContext())
                    .thenAccept(authHelper -> {
                        mAuthHelper = authHelper;
                        if (!mIsSignedIn) {
                            doSilentSignIn(true);
                        } else {
                            dismissProgress();
                        }
                    })
                    .exceptionally(exception -> {
                        Log.e("AUTH", "Error creating auth helper", exception);
                        return null;
                    });
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static void changeStatusBarContrastStyle(Window window, Boolean lightIcons) {
        View decorView = window.getDecorView();
        if (lightIcons) {
            // Draw light icons on a dark background color
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // Draw dark icons on a light background color
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    // Silently sign in - used if there is already a
// user account in the MSAL cache
    private void doSilentSignIn(boolean shouldAttemptInteractive) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mAuthHelper.acquireTokenSilently()
                    .thenAccept(authenticationResult -> {
                        handleSignInSuccess(authenticationResult);
                    })
                    .exceptionally(exception -> {
                        // Check the type of exception and handle appropriately
                        Throwable cause = exception.getCause();
                        if (cause instanceof MsalUiRequiredException) {
                            Log.d("AUTH", "Interactive login required");
                            if (shouldAttemptInteractive) doInteractiveSignIn();
                        } else if (cause instanceof MsalClientException) {
                            MsalClientException clientException = (MsalClientException)cause;
                            if (clientException.getErrorCode() == "no_current_account" ||
                                    clientException.getErrorCode() == "no_account_found") {
                                Log.d("AUTH", "No current account, interactive login required");
                                if (shouldAttemptInteractive) doInteractiveSignIn();
                            }
                        } else {
                            handleSignInFailure(cause);
                        }
                        dismissProgress();
                        return null;
                    });
        }
    }

    // Prompt the user to sign in
    private void doInteractiveSignIn() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mAuthHelper.acquireTokenInteractively(this)
                    .thenAccept(authenticationResult -> {
                        handleSignInSuccess(authenticationResult);
                    })
                    .exceptionally(exception -> {
                        handleSignInFailure(exception);
                        dismissProgress();
                        return null;
                    });
        }
    }


    // Handles the authentication result
    private void handleSignInSuccess(IAuthenticationResult authenticationResult) {
        // Log the token for debug purposes
        String accessToken = authenticationResult.getAccessToken();
        Log.d("AUTH", String.format("Access token: %s", accessToken));

        // Get Graph client and get user
        GraphHelper graphHelper = GraphHelper.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            graphHelper.getUser()
                    .thenAccept(user -> {
                        mUserName = user.displayName;
                        mUserEmail = user.mail == null ? user.userPrincipalName : user.mail;
//                        mUserTimeZone = user.mailboxSettings.timeZone;

                        runOnUiThread(() -> {
                            dismissProgress();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        });
                    })
                    .exceptionally(exception -> {
                        Log.e("AUTH", "Error getting /me", exception);

                        runOnUiThread(()-> {
                            dismissProgress();
                            showMessage(exception.getMessage());
                        });

                        return null;
                    });
        }
    }

    private void handleSignInFailure(Throwable exception) {
        if (exception instanceof MsalServiceException) {
            // Exception when communicating with the auth server, likely config issue
            Log.e("AUTH", "Service error authenticating", exception);
        } else if (exception instanceof MsalClientException) {
            // Exception inside MSAL, more info inside MsalError.java
            Log.e("AUTH", "Client error authenticating", exception);
        } else {
            Log.e("AUTH", "Unhandled exception authenticating", exception);
        }
    }
}