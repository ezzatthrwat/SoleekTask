package com.example.zizoj.soleektask.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zizoj.soleektask.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_Activity extends AppCompatActivity {


    EditText EmailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forget_);

        
        EmailEditText = findViewById(R.id.EmailToForgetPassEditTxt);

    }

    public void sendBtn(View view) {


        FirebaseAuth.getInstance().sendPasswordResetEmail(EmailEditText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(Forget_Activity.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
