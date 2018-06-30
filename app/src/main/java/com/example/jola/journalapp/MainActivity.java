package com.example.jola.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    SignInButton googleSignIn;
    Button logIn;
    TextView registerText;
    EditText emailText;
    EditText passwordText;
    TextView statusMessageText;
    private static final int RC_SIGN_IN = 20;
    private  static  final String TAG = "Exception Handler";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        googleSignIn = (SignInButton)findViewById(R.id.sign_in_with_google);
        logIn = (Button) findViewById(R.id.login_button);
        registerText = (TextView) findViewById(R.id.register_link);
        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        statusMessageText = (TextView)findViewById(R.id.status_message);
        statusMessageText.setVisibility(View.INVISIBLE);
        googleSignIn.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    @Override
    protected  void onStart(){
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account == null){
            updateUI(false,"User not logged in!");
        }
        else{
            updateUI(true,"User logged in!");
        }
        return;
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.sign_in_with_google:
                signIn();
                break;
        }
    }
    private void signIn(){
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent,RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
             if(completedTask.isSuccessful()){
                 account = completedTask.getResult(ApiException.class);
                 String name = account.getDisplayName();
                 String email = account.getEmail();
                 // Signed in successfully, show authenticated UI.
                 updateUI(true,String.format("{0} Logged in successfully!",email));
             }
        }
        catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(false,"Error occurred while signing in!");
        }
    }

    private  void updateUI(boolean loginStatus,String statusMessage){
        try{
            if(loginStatus){
                emailText.setVisibility(View.INVISIBLE);
                passwordText.setVisibility(View.INVISIBLE);
                statusMessageText.setVisibility(View.VISIBLE);
                statusMessageText.setText(statusMessage);
                googleSignIn.setVisibility(View.INVISIBLE);
                logIn.setVisibility(View.INVISIBLE);
                registerText.setVisibility(View.INVISIBLE);
            }
            else{
                emailText.setVisibility(View.VISIBLE);
                passwordText.setVisibility(View.VISIBLE);
                statusMessageText.setVisibility(View.VISIBLE);
                googleSignIn.setVisibility(View.VISIBLE);
                logIn.setVisibility(View.VISIBLE);
                registerText.setVisibility(View.VISIBLE);
                statusMessageText.setText(statusMessage);
            }
        }
        catch (Exception ex){
            Log.e(TAG, "Error occurred=" + ex.toString());
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
