package com.example.jola.journalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    SignInButton googleSignIn;
    Button logIn;
    FirebaseAuth mAuth;
    TextView registerText;
    EditText emailText;
    EditText passwordText;
    ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(this);
        registerText = (TextView) findViewById(R.id.register_link);
        registerText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://accounts.google.com/signup/v2/webcreateaccount?hl=en&flowName=GlifWebSignIn&flowEntry=SignUp"));
                startActivity(intent);
            }
        });
        googleSignIn.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.signout,menu);
        return  true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.sign_out:
                signOut();
                return  true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    @Override
    protected  void onStart(){
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        getCurrentUser();
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

    private GoogleSignInAccount getCurrentUser(){
        try {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            //FirebaseUser currentUser = mAuth.getCurrentUser();
            if (account == null) {
                updateUI(false, "You are not logged in!");
                return null;
            }
            return account;
        }
        catch (Exception ex){
            Log.w(TAG, String.format("ErrorOccurred:Failure",ex.toString()));
            updateUI(false,"Failed to get the current user!");
            return  null;
        }

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
             if(completedTask.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                 Log.d(TAG, "SignInWithCredential:Success");
                 updateUI(true,"Login successful!");
                 account = completedTask.getResult(ApiException.class);
                 firebaseAuthWithGoogle(account);
             }
             else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "SignInWithCredential:Failure", completedTask.getException());
                updateUI(false,"Login failed!");
            }
        }
        catch (Exception e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "ErrorOccurred:" + e.toString());
            updateUI(false,"Error Occurred While Signing In!");
        }
    }

    private void updateUI(boolean loginStatus,String statusMessage){
        try{
            Toast.makeText(this, statusMessage, Toast.LENGTH_SHORT).show();
            if(loginStatus){
                Intent intent = new Intent(MainActivity.this, ViewEntriesActivity.class);
                intent.putExtra("key", "Started From The MainActivity"); //Optional parameters
                MainActivity.this.startActivity(intent);
            }
        }
        catch (Exception ex){
            Log.e(TAG, "ErrorOccurred:" + ex.toString());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "FirebaseAuthWithGoogle:" + acct.getId());
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential);
    }

    private void signOut() {
        // Google sign out
        if(getCurrentUser()!= null) {
            mGoogleSignInClient.signOut().addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                updateUI(false, "Signed out successfully!");
                                // Firebase sign out
                                mAuth.signOut();
                            } else {
                                Log.w(TAG, "SignOutFailed", task.getException());
                                updateUI(false, "Sign out failed!");
                            }
                        }
                    });
        }
        else{
            updateUI(false, "You are not logged in");
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
