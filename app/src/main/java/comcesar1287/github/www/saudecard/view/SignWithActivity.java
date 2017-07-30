package comcesar1287.github.www.saudecard.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.User;
import comcesar1287.github.www.saudecard.controller.firebase.FirebaseHelper;
import comcesar1287.github.www.saudecard.controller.util.Utility;
import es.dmoral.toasty.Toasty;

public class SignWithActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 9001;

    CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;

    private DatabaseReference mDatabase;

    String Uid, name , email, profile_pic;

    SharedPreferences sp;

    ImageButton btLogin, btCreate, btFacebook, btGoogle;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        dialog = ProgressDialog.show(SignWithActivity.this,"",
                                SignWithActivity.this.getResources().getString(R.string.processing_login), true, false);

                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toasty.error(SignWithActivity.this, getResources().getString(R.string.error_facebook_login_canceled), Toast.LENGTH_SHORT, true).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toasty.error(SignWithActivity.this, getResources().getString(R.string.error_facebook_login_unknown_error), Toast.LENGTH_SHORT, true).show();
                    }
                });

        setContentView(R.layout.activity_sign_with);

        btCreate = (ImageButton) findViewById(R.id.sign_with_create_account);
        btCreate.setOnClickListener(this);

        btLogin = (ImageButton) findViewById(R.id.sign_with_login);
        btLogin.setOnClickListener(this);

        btFacebook = (ImageButton) findViewById(R.id.sign_with_facebook);
        btFacebook.setOnClickListener(this);

        btGoogle = (ImageButton) findViewById(R.id.sign_with_google);
        btGoogle.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                dialog = ProgressDialog.show(SignWithActivity.this,"",
                        SignWithActivity.this.getResources().getString(R.string.processing_login), true, false);
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                Toasty.error(SignWithActivity.this, getResources().getString(R.string.error_failed_signin_google_account), Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.sign_with_create_account:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            case R.id.sign_with_login:
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.sign_with_facebook:
                LoginManager.getInstance().logInWithReadPermissions(this,
                        Arrays.asList("public_profile","user_friends","email"));
                break;
            case R.id.sign_with_google:
                signIn();
                break;
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnFailureListener(SignWithActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        if(e instanceof FirebaseAuthUserCollisionException){
                            Toasty.error(SignWithActivity.this, getResources().getString(R.string.error_failed_signin_email_exists), Toast.LENGTH_LONG, true).show();
                        }else{
                            Toasty.error(SignWithActivity.this, getResources().getString(R.string.error_unknown_error), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                })
                .addOnSuccessListener(SignWithActivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        FirebaseUser user = mAuth.getCurrentUser();

                        finishLogin(user);
                    }
                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            startActivity(new Intent(SignWithActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnFailureListener(SignWithActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        if(e instanceof FirebaseAuthUserCollisionException){
                            Toasty.error(SignWithActivity.this, getResources().getString(R.string.error_failed_signin_email_exists), Toast.LENGTH_LONG, true).show();
                        }else{
                            Toasty.error(SignWithActivity.this, getResources().getString(R.string.error_unknown_error), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                })
                .addOnSuccessListener(SignWithActivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        FirebaseUser user = mAuth.getCurrentUser();

                        finishLogin(user);
                    }
                })
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            startActivity(new Intent(SignWithActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    public void finishLogin(FirebaseUser user){

        Uid = user.getUid();
        name = user.getDisplayName();
        email = user.getEmail();
        profile_pic = user.getPhotoUrl().toString();

        mDatabase.child(FirebaseHelper.FIREBASE_DATABASE_USERS).child(Uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {

                            FirebaseHelper.writeNewUser(mDatabase, Uid, name, email, "", "", "", "", profile_pic);

                            sp = getSharedPreferences(Utility.LOGIN_SHARED_PREF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();

                            editor.putString("id", Uid);
                            editor.putString("name", name);
                            editor.putString("email", email);
                            editor.putString("profile_pic", profile_pic);
                            editor.apply();
                        } else {

                            sp = getSharedPreferences(Utility.LOGIN_SHARED_PREF_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();

                            editor.putString("id", Uid);
                            editor.putString("name", name);
                            editor.putString("email", email);
                            editor.putString("profile_pic", profile_pic);
                            editor.putString("phone", user.phone);
                            editor.putString("birth", user.birth);
                            editor.putString("sex", user.sex);
                            editor.apply();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toasty.error(SignWithActivity.this, getResources().getString(R.string.error_signin), Toast.LENGTH_SHORT, true).show();
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Toasty.error(SignWithActivity.this, getResources().getString(R.string.error_google_play_services), Toast.LENGTH_SHORT, true).show();
    }
}
