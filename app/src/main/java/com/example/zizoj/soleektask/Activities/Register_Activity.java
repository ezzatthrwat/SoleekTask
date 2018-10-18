package com.example.zizoj.soleektask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zizoj.soleektask.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Activity extends AppCompatActivity {

    EditText EmailEditTxt, PasswordEditTxt, ConfirmPasswordEditTxt;


    private FirebaseAuth mFirebaseAuth;

    private FirebaseDatabase mFirebasedatabase;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register_);

        initFirebase();
        initEditTextVal();

    }

    private void initFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebasedatabase.getReference().child("Users");
    }

    private void initEditTextVal() {
        EmailEditTxt = findViewById(R.id.EmialEditTxt);
        PasswordEditTxt = findViewById(R.id.PasswordEditTxt);
        ConfirmPasswordEditTxt = findViewById(R.id.ConfirmPasswordEditTxt);
    }


    public void RegisterBtn(View view) {

        setRegister();

    }


    public void setRegister() {

        final String Email = EmailEditTxt.getText().toString();
        String Password = PasswordEditTxt.getText().toString();
        String ConfirmPassword = ConfirmPasswordEditTxt.getText().toString();

        if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password)) {
            EmailEditTxt.setError("Enter Your Email");
            PasswordEditTxt.setError("Enter Your Password");

            return;
        }
        if (!ConfirmPassword.equals(Password)) {
            ConfirmPasswordEditTxt.setError("Password not Match");
            return;
        }


        mFirebaseAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            databaseReference.push().setValue(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        mFirebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()){
                                                    mFirebaseAuth.signOut();
                                                    finish();
                                                    Toast.makeText(Register_Activity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                                                }else {

                                                    Toast.makeText(Register_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                    }
                                }
                            });

                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                PasswordEditTxt.setError("Password should be at least 6 characters.");
                                Toast.makeText(Register_Activity.this, "Password should be at least 6 characters.", Toast.LENGTH_SHORT).show();
                                Log.d("firebase", e.getMessage());
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                EmailEditTxt.setError("Invalid email address.");
                                Toast.makeText(Register_Activity.this, "Invalid email address.", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException e) {
                                EmailEditTxt.setError("Email already exists.");
                                Toast.makeText(Register_Activity.this, "Email already exists.", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.d("firebase", e.getMessage());
                                Toast.makeText(Register_Activity.this, "Something wrong happened. Please try again later.", Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(Register_Activity.this, "could Not Register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    public void loginTxt(View view) {

        startActivity(new Intent(Register_Activity.this, Login_Activity.class));

        finish();
    }
}
