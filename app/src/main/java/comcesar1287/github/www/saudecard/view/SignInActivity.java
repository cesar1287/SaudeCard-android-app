package comcesar1287.github.www.saudecard.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.User;
import comcesar1287.github.www.saudecard.controller.firebase.FirebaseHelper;
import comcesar1287.github.www.saudecard.controller.util.Utility;
import es.dmoral.toasty.Toasty;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    String Uid, email, password, profile_pic = "";

    SharedPreferences sp;

    EditText etEmail, etPassword;

    ImageButton btLogin;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etEmail = (EditText) findViewById(R.id.sign_in_etEmail);
        etPassword = (EditText) findViewById(R.id.sign_in_etPassword);

        btLogin = (ImageButton) findViewById(R.id.sign_in_btLogin);
        btLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.sign_in_btLogin:
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                loginUser();
                break;
        }
    }

    public void loginUser(){

        if(!Utility.verifyEmptyField(email, password)){
            dialog = ProgressDialog.show(SignInActivity.this,"",
                    SignInActivity.this.getResources().getString(R.string.processing_login), true, false);
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnFailureListener(SignInActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toasty.error(SignInActivity.this, getResources().getString(R.string.error_user_password_incorrect), Toast.LENGTH_SHORT, true).show();
                        }
                    })
                    .addOnSuccessListener(SignInActivity.this, new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();

                            FirebaseUser user = mAuth.getCurrentUser();

                            finishLogin(user);

                            finish();
                        }
                    })
                    .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
        }else{
            Toasty.error(SignInActivity.this, getResources().getString(R.string.error_all_fields_required), Toast.LENGTH_SHORT, true).show();
        }
    }

    public void finishLogin(FirebaseUser user){

        Uid = user.getUid();

        mDatabase.child(FirebaseHelper.FIREBASE_DATABASE_USERS).child(Uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);


                        sp = getSharedPreferences(Utility.LOGIN_SHARED_PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();

                        editor.putString("id", Uid);
                        editor.putString("name", user.name);
                        editor.putString("email", email);
                        editor.putString("profile_pic", profile_pic);
                        editor.putString("phone", user.phone);
                        editor.putString("birth", user.birth);
                        editor.putString("sex", user.sex);
                        editor.apply();
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
