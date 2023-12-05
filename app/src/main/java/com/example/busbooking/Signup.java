package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Signup extends AppCompatActivity {
    EditText signupFirstName, signupLastName, signupEmail, signupPassword, signupContact;
    TextView loginRedirectText;
    Button signupButton;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth=FirebaseAuth.getInstance();

        signupFirstName = findViewById(R.id.firstname);
        signupLastName = findViewById(R.id.lastname);
        signupEmail = findViewById(R.id.signup_email);
        signupContact = findViewById(R.id.signup_contact);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);

        progressDialog = new ProgressDialog(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPost();
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });

    }

    private void uploadPost () {
        String uName = signupFirstName.getText().toString().trim();
        String uName2 = signupLastName.getText().toString().trim();
        String uEmail = signupEmail.getText().toString().trim();
        String uContact = signupContact.getText().toString().trim();
        String uPassword = signupPassword.getText().toString().trim();

        if (uName.isEmpty()) {
            signupFirstName.setError("Firstname is required");
            signupFirstName.requestFocus();
            return;
        }
        if (uName2.isEmpty()) {
            signupLastName.setError("Lastname is required");
            signupLastName.requestFocus();
            return;
        }
        if (uEmail.isEmpty()) {
            signupEmail.setError("Email is required");
            signupEmail.requestFocus();
            return;
        }
        if (uContact.isEmpty()) {
            signupContact.setError("Contact is required");
            signupContact.requestFocus();
            return;
        }
        if (uPassword.isEmpty()) {
            signupPassword.setError("Password is required");
            signupPassword.requestFocus();
            return;
        }
        else {
            mAuth.createUserWithEmailAndPassword(signupEmail.getText().toString(), signupPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        UploadToFirebase();
                    }
                }
            });
        }
    }
    private void UploadToFirebase () {
        progressDialog.setTitle("Signing up in progress.. .. ..");
        progressDialog.setMessage("Please wait....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String uName = signupFirstName.getText().toString().trim();
        String uName2 = signupLastName.getText().toString().trim();
        String uEmail = signupEmail.getText().toString().trim();
        String uContact = signupContact.getText().toString().trim();
        String uPassword = signupPassword.getText().toString().trim();
        String Role="User";

        DatabaseReference root = FirebaseDatabase.getInstance().getReference("Users");
        String userID = root.push().getKey();
        // Create a new user object with the data
        HelperClass user = new HelperClass(userID,uName, uName2, uEmail, uContact, uPassword, Role);
        // Get a reference to the "users" node
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();

                            signupFirstName.getText().clear();
                            signupLastName.getText().clear();
                            signupContact.getText().clear();
                            signupEmail.getText().clear();
                            signupPassword.getText().clear();
                            signupFirstName.requestFocus();

                            Toast.makeText(Signup.this, "Signup Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Signup.this, Login.class));
                            finish();

                        }else{
                            Toast.makeText(Signup.this, "Error Failed to register", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}