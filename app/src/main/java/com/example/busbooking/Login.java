package com.example.busbooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView signupRedirectText,forgot;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail= findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        forgot = findViewById(R.id.forgot);

        progressDialog = new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(Login.this,Signup.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = loginEmail.getText().toString().trim();
                String Password = loginPassword.getText().toString().trim();

                if(Email.isEmpty()){
                    loginEmail.setError("Email is Required*");
                    loginEmail.requestFocus();
                    return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    loginEmail.setError("Valid Email Required*");
                    loginEmail.requestFocus();
                    return;
                }else if(Password.isEmpty()){
                    loginPassword.setError("Password Required*");
                    loginPassword.requestFocus();
                    return;
                }else if (Password.length()<8) {
                    loginPassword.setError("Password Should be at least 8 characters*");
                    loginPassword.requestFocus();
                    return;
                }
                else{
                    mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                                progressDialog.setTitle("Logging in progress.. .. ..");
                                progressDialog.setMessage("Please wait....");
                                progressDialog.setCanceledOnTouchOutside(false);
                                progressDialog.show();

                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String role = snapshot.child("role").getValue(String.class);
                                        if(role.equals("Admin")){
                                            progressDialog.dismiss();
                                            Intent admin = new Intent(Login.this, Admin.class);
                                            startActivity(admin);
                                            finish();
                                        }
                                        else if (role.equals("User")) {
                                            progressDialog.dismiss();
                                            Intent user = new Intent(Login.this, Client.class);
                                            startActivity(user);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(Login.this, "Login Failed try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }else{
                                Toast.makeText(Login.this, "Login Failed try again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });


    }
}