package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
public class ForgotPassword extends AppCompatActivity {
    EditText reset_email;
    Button reset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        reset_email = findViewById(R.id.reset_email);
        reset = findViewById(R.id.reset);
        mAuth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = reset_email.getText().toString().trim();
                if(Email.isEmpty()){
                    reset_email.setError("Email is Required for a password reset*");
                    reset_email.requestFocus();
                    return;
                }else{
                    mAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(ForgotPassword.this, "Password reset email sent. Check your email.", Toast.LENGTH_SHORT).show();
                            reset_email.getText().clear();
                        }
                    });
                }
            }
        });

    }
}