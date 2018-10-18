package com.example.zizoj.soleektask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zizoj.soleektask.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {

    EditText EmailLoginEditTXT , PasswodLoginEditTxt ;


    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_);

        initFireBaseAuth();
        initEditTextVal();

    }


    private void initFireBaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void initEditTextVal() {
        EmailLoginEditTXT = findViewById(R.id.EmailLoginEditTxt);
        PasswodLoginEditTxt = findViewById(R.id.PasswordLoginEditTxt);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mFirebaseAuth.getCurrentUser() != null){

            startActivity(new Intent(Login_Activity.this , Home_Activity.class));

            finish();

        }

    }

    public void LoginBtn(View view) {
        setLogin();
    }


    public void setLogin(){

        String Email = EmailLoginEditTXT.getText().toString();
        String Password = PasswodLoginEditTxt.getText().toString();


        if(TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password)){
            EmailLoginEditTXT.setError("Enter Your Email");
            PasswodLoginEditTxt.setError("Enter Your Password");

            return;
        }

        mFirebaseAuth.signInWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            if(mFirebaseAuth.getCurrentUser().isEmailVerified()){
                                finish();
                                startActivity(new Intent(Login_Activity.this , Home_Activity.class));
                            }else {
                                mFirebaseAuth.signOut();
                                Toast.makeText(Login_Activity.this, "Verify Your Email First", Toast.LENGTH_SHORT).show();
                            }
                            
                        }else {

                            Toast.makeText(Login_Activity.this, "your Email or Password Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void singupBtn(View view) {

        startActivity(new Intent(Login_Activity.this , Register_Activity.class));
    }

    public void ForgetPassword(View view) {

        startActivity(new Intent(Login_Activity.this,Forget_Activity.class));

    }
}
