package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Route;

public class GlobalBuses extends AppCompatActivity {
    private EditText company, name,park, tel, route, fee;
    private ImageView imageURL;
    private Button add;
    private Uri imageURI;

    public static final int RESULT_OK = -1;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_buses);

        company=findViewById(R.id.company);
        name=findViewById(R.id.name);
        park=findViewById(R.id.park);
        tel=findViewById(R.id.tel);
        route=findViewById(R.id.route);
        fee=findViewById(R.id.fee);
        imageURL=findViewById(R.id.imageView2);
        add=findViewById(R.id.add_btn);
        progressDialog= new ProgressDialog(GlobalBuses.this);
        storageReference = FirebaseStorage.getInstance().getReference("bus_logos");
        databaseReference = FirebaseDatabase.getInstance().getReference("buses");

        Intent busName=getIntent();
        String Name=busName.getStringExtra("name");
        company.setText(Name);
        company.setEnabled(false);

//        Choosing Image
        imageURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageURI != null) {
                    uploadBus();
                } else {
                    Toast.makeText(GlobalBuses.this, "Please select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void uploadBus() {
        if(imageURI!=null) {
            String Company=company.getText().toString().trim();
            String busName=name.getText().toString().trim();
            String Park=park.getText().toString().trim();
            String Tel=tel.getText().toString().trim();
            String Route=route.getText().toString().trim();
            String Fee=fee.getText().toString().trim();

            if (busName.isEmpty()){
                name.setError("Bus name can not be empty");
                name.requestFocus();
                return;
            }else if (Park.isEmpty()){
                park.setError("Park can not be empty");
                park.requestFocus();
                return;
            }else if (Tel.isEmpty()){
                tel.setError("Telephone number required");
                tel.requestFocus();
                return;
            }else if (Route.isEmpty()){
                tel.setError("Route Taken required");
                tel.requestFocus();
                return;
            }else if (Fee.isEmpty()){
                fee.setError("Enter Fee for the bus");
                fee.requestFocus();
                return;
            }else if(!busName.isEmpty() && !Park.isEmpty() && !Tel.isEmpty() && !Route.isEmpty() && !Fee.isEmpty()){
                uploadBusToFirebase(imageURI);
            }else {
                Toast.makeText(GlobalBuses.this, "Select Bus Image", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void uploadBusToFirebase(Uri imageUri) {
        if (imageUri == null) {
            Toast.makeText(GlobalBuses.this, "Please Select Bus Image", Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.setTitle("Bus Upload in Progress");
            progressDialog.setMessage("Please wait....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            // Get the file extension from the imageUri
            ContentResolver contentResolver = getApplication().getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));

            final StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + extension);
            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String Company=company.getText().toString().trim();
                            String Name=name.getText().toString().trim();
                            String Park=park.getText().toString().trim();
                            String Tel=tel.getText().toString().trim();
                            String Route=route.getText().toString().trim();
                            String Fee=fee.getText().toString().trim();

                                    BusesRegistration busesRegistration=new BusesRegistration(Company, Name,Park,Tel, Route, Fee, uri.toString());

                                    databaseReference.child(databaseReference.push().getKey()).setValue(busesRegistration).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                Toast.makeText(GlobalBuses.this, "Bus Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                park.setText(null);
                                                tel.setText(null);
                                                imageURL.setImageURI(null);
                                                // In your activity
                                                FragmentManager fragmentManager = getSupportFragmentManager();
                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                AddBusesFragment myFragment = new AddBusesFragment();
                                                fragmentTransaction.replace(R.id.flFragment, myFragment);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
                                            } else {
                                                Toast.makeText(GlobalBuses.this, "Bus Upload Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(GlobalBuses.this, "Bus Upload Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            });

                        }
                    });
                }
            }



    //    Choosing Image
    private void chooseImage() {
        // Open gallery to select image
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageURI = data.getData();
            imageURL.setImageURI(imageURI);
        }
    }

}