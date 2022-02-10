package com.acentura.splashstart.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.acentura.splashstart.R;
import com.acentura.splashstart.base.BaseBindingActivity;
import com.acentura.splashstart.databinding.ActivityMainBinding;
import com.acentura.splashstart.di.module.viewmodelfactory.ViewModelFactory;
import com.acentura.splashstart.service.auth.AuthenticationHelper;
import com.acentura.splashstart.service.auth.GraphHelper;
import com.acentura.splashstart.ui.login.LoginActivity;
import com.acentura.splashstart.ui.main.fragments.EventFragment;
import com.acentura.splashstart.ui.main.fragments.HomeFragment;
import com.acentura.splashstart.ui.main.fragments.MainFragment;
import com.acentura.splashstart.util.HelperUtil;
import com.acentura.splashstart.util.reponse.ConnectionErrorDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.microsoft.identity.client.IAuthenticationResult;
import com.microsoft.identity.client.exception.MsalClientException;
import com.microsoft.identity.client.exception.MsalServiceException;
import com.microsoft.identity.client.exception.MsalUiRequiredException;

import javax.inject.Inject;

import timber.log.Timber;

@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseBindingActivity<ActivityMainBinding, MainViewModel>
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject
    ViewModelFactory viewModelFactory;

    private static final String SAVED_IS_SIGNED_IN = "isSignedIn";
    private static final String SAVED_USER_NAME = "userName";
    private static final String SAVED_USER_EMAIL = "userEmail";
    private static final String SAVED_USER_TIMEZONE = "userTimeZone";

    private View mHeaderView;
    private boolean mIsSignedIn = false;
    private String mUserName = null;
    private String mUserEmail = null;
    private String mUserTimeZone = null;
    private AuthenticationHelper mAuthHelper = null;

    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel bindViewModel() {
        return new ViewModelProvider(this, viewModelFactory)
                .get(MainViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observeViewModel();
        init(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_IS_SIGNED_IN, mIsSignedIn);
        outState.putString(SAVED_USER_NAME, mUserName);
        outState.putString(SAVED_USER_EMAIL, mUserEmail);
        outState.putString(SAVED_USER_TIMEZONE, mUserTimeZone);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
//                addFragment(R.id.fragmentContainer,
//                        HomeFragment.createInstance(mUserName), false);
                addFragment(R.id.fragmentContainer,
                        new MainFragment(), false);
                getBinding().navigationView.setCheckedItem(R.id.nav_home);

                break;
            case R.id.nav_create_event:
                addFragment(R.id.fragmentContainer,
                        EventFragment.createInstance(mUserName), false);
                getBinding().navigationView.setCheckedItem(R.id.nav_home);
                break;
            case R.id.nav_signin:
                signIn();
                break;
            case R.id.nav_signout:
                signOut();
                break;
        }
        getBinding().drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getBinding().drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getBinding().drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void init(Bundle savedInstanceState){
        getBinding().toolbar.setTitle("Dashboard");

      //  getViewModel().getPosts();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            AuthenticationHelper.getInstance(getApplicationContext())
                    .thenAccept(authHelper -> {
                        mAuthHelper = authHelper;
                        mIsSignedIn = true;
                    })
                    .exceptionally(exception -> {
                        Log.e("AUTH", "Error creating auth helper", exception);
                        return null;
                    });
        }

        setSupportActionBar(getBinding().toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                getBinding().drawerLayout,
                getBinding().toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getBinding().drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set user name and email
        mHeaderView = getBinding().navigationView.getHeaderView(0);
        setSignedInState(mIsSignedIn);

        // Listen for item select events on menu
        getBinding().navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            // Load the home fragment by default on startup
//            addFragment(R.id.fragmentContainer,
//                    HomeFragment.createInstance(mUserName), false);
            addFragment(R.id.fragmentContainer,
                    new MainFragment(), false);
            getBinding().navigationView.setCheckedItem(R.id.nav_home);
        } else {
            // Restore state
            mIsSignedIn = savedInstanceState.getBoolean(SAVED_IS_SIGNED_IN);
            mUserName = savedInstanceState.getString(SAVED_USER_NAME);
            mUserEmail = savedInstanceState.getString(SAVED_USER_EMAIL);
            mUserTimeZone = savedInstanceState.getString(SAVED_USER_TIMEZONE);
            setSignedInState(mIsSignedIn);
        }
    }

    private void observeViewModel() {
        getViewModel().getResponseLiveData().observe(this, response -> {
            dismissProgress();
            if (response == null) return;

            switch (response.status) {
                case LOADING:
                    showProgress();
                    break;
                case SUCCESS:
                    if (HelperUtil.isDataAvailable(response.data)) {
                        Timber.v(" Data %s", new Gson().toJson(response.data));
                        showMessage(response.data.get(0).toString());
                    }
                    else{
                        Timber.v(" Data  NULL");
                    }
                    break;
                case ERROR: {
                    Timber.d("Response ERROR | %s", response.message);
                    showMessage(response.message);
                    break;
                }
                case NOT_CONNECTED: {
                    Timber.d("Response NOT_CONNECTED");
                    ConnectionErrorDialog dialog = new ConnectionErrorDialog(MainActivity.this,
                            ConnectionErrorDialog.TYPE_INTERNET_NOT_CONNECTED);
                    dialog.setDialogClickListener(new ConnectionErrorDialog.DialogClickListener() {
                        @Override
                        public void onPositiveClicked(Dialog dialog) {

                        }

                        @Override
                        public void onNegativeClicked(Dialog dialog) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    break;
                }
            }
        });
    }


    // Update the menu and get the user's name and email
    @SuppressLint("SetTextI18n")
    private void setSignedInState(boolean isSignedIn) {
        mIsSignedIn = isSignedIn;

        getBinding().navigationView.getMenu().clear();
        getBinding().navigationView.inflateMenu(R.menu.drawer_menu);

        Menu menu = getBinding().navigationView.getMenu();

        // Hide/show the Sign in, Calendar, and Sign Out buttons
        if (isSignedIn) {
            menu.removeItem(R.id.nav_signin);
        } else {
            menu.removeItem(R.id.nav_home);
            menu.removeItem(R.id.nav_create_event);
            menu.removeItem(R.id.nav_signout);
        }

        // Set the user name and email in the nav drawer
        TextView userName = mHeaderView.findViewById(R.id.tvUserName);
        TextView userEmail = mHeaderView.findViewById(R.id.tvUserEmail);

        if (isSignedIn) {
            // For testing

            userName.setText(mUserName);
            userEmail.setText(mUserEmail);
        } else {
            mUserName = null;
            mUserEmail = null;
            mUserTimeZone = null;

            userName.setText("Please sign in");
            userEmail.setText("");
        }
    }

    private void signIn() {
        showProgress();
        doSilentSignIn(true);

    }

    private void signOut() {

        mAuthHelper.signOut();

        setSignedInState(false);
        addFragment(R.id.fragmentContainer,
                HomeFragment.createInstance(mUserName), false);
        getBinding().navigationView.setCheckedItem(R.id.nav_home);
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
                        mUserName = user.displayName == null ? "User" :user.displayName;
                        mUserEmail = user.mail == null ? user.userPrincipalName : user.mail;
//                        mUserTimeZone = user.mailboxSettings.timeZone;

                        Log.d("AUTH", String.format("Access: %s  %s  %s", mUserName, mUserEmail, mUserTimeZone));

                        runOnUiThread(() -> {
                            dismissProgress();

                            setSignedInState(true);
                            addFragment(R.id.fragmentContainer,
                                    HomeFragment.createInstance(mUserName), false);
                            getBinding().navigationView.setCheckedItem(R.id.nav_home);
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
}