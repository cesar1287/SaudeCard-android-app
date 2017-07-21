package comcesar1287.github.www.saudecard.view;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    String Uid, name , email, cpf, password, profile_pic = "";

    SharedPreferences sp;

    EditText etName, etEmail, etCPF, etPassword;

    ImageButton etCreateUser;

    TextWatcher cpfMask;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        etName = (EditText) findViewById(R.id.sign_up_etName);
        etEmail = (EditText) findViewById(R.id.sign_up_etEmail);
        etPassword = (EditText) findViewById(R.id.sign_up_etPassword);

        etCPF = (EditText) findViewById(R.id.sign_up_etCPF);
        cpfMask = Utility.insertMask(getResources().getString(R.string.cpf_mask), etCPF);
        etCPF.addTextChangedListener(cpfMask);

        etCreateUser = (ImageButton) findViewById(R.id.sign_up_btCreateUser);
        etCreateUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch(id){
            case R.id.sign_up_btCreateUser:
                dialog = ProgressDialog.show(SignUpActivity.this,"",
                        SignUpActivity.this.getResources().getString(R.string.creating_user), true, false);
                name = etName.getText().toString();
                email = etEmail.getText().toString();
                cpf = etCPF.getText().toString();
                password = etPassword.getText().toString();
                createUser();
                break;
        }
    }

    public void createUser(){
        if(!Utility.verifyEmptyField(name, email, password, cpf)) {

            cpf = cpf.replaceAll("[.]", "").replaceAll("[-]","");
            if(Utility.isValidCPF(cpf)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnFailureListener(SignUpActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                if (e instanceof FirebaseAuthWeakPasswordException) {
                                    Toast.makeText(SignUpActivity.this, R.string.error_password_too_small,
                                            Toast.LENGTH_LONG).show();
                                    etPassword.setText("");
                                    etPassword.requestFocus();
                                } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(SignUpActivity.this, R.string.error_invalid_email,
                                            Toast.LENGTH_SHORT).show();
                                    etEmail.setText("");
                                    etEmail.requestFocus();
                                } else if (e instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(SignUpActivity.this, R.string.error_failed_signin_email_exists,
                                            Toast.LENGTH_LONG).show();
                                    etEmail.setText("");
                                    etEmail.requestFocus();
                                } else {
                                    Toast.makeText(SignUpActivity.this, R.string.error_unknown_error,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnSuccessListener(SignUpActivity.this, new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignUpActivity.this, R.string.user_created_successfully,
                                        Toast.LENGTH_SHORT).show();

                                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                                FirebaseUser user = mAuth.getCurrentUser();

                                finishLogin(user);
                            }
                        })
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                dialog.dismiss();
                                if (task.isSuccessful()) {
                                    finish();
                                }
                            }
                        });
            }else{
                Toast.makeText(SignUpActivity.this, R.string.error_invalid_cpf,
                        Toast.LENGTH_LONG).show();
                etCPF.setText("");
                etCPF.requestFocus();
            }
        }else{
            Toast.makeText(SignUpActivity.this, R.string.error_all_fields_required,
                    Toast.LENGTH_SHORT).show();
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
                            finish();
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
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(SignUpActivity.this, R.string.error_create_new_account, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
