package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class View_Buses extends AppCompatActivity {

    private RecyclerView myBuses;
    private List<BusesRegistration> buses;
    private BusesAdapter busesAdapter;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_buses);


        progressDialog= new ProgressDialog(View_Buses.this);
        storageReference = FirebaseStorage.getInstance().getReference("bus_logos");
        databaseReference = FirebaseDatabase.getInstance().getReference("buses");

        myBuses = findViewById(R.id.added_buses);
        myBuses.setHasFixedSize(true);
        myBuses.setLayoutManager(new LinearLayoutManager(View_Buses.this));

        buses = new ArrayList<>();
        busesAdapter = new BusesAdapter(View_Buses.this, (ArrayList<BusesRegistration>) buses);
        myBuses.setAdapter(busesAdapter);

            ProgressDialog progressDialog = new ProgressDialog(View_Buses.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Loading Buses");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

        Intent intent=getIntent();
        String userInputBusName = intent.getStringExtra("name");
        databaseReference.orderByChild("company").equalTo(userInputBusName).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    buses.clear();
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        // Check if the snapshot value is a string
                        if (itemSnapshot.getValue() instanceof String) {
                            // Handle the case where the value is a string (it might be a single value, not a BusesRegistration object)
                            String stringValue = (String) itemSnapshot.getValue();
                            // Handle this string value appropriately (perhaps log it for debugging purposes)
                        } else {
                            // Convert the snapshot value to a BusesRegistration object
                            BusesRegistration myBuses = itemSnapshot.getValue(BusesRegistration.class);
                            buses.add(myBuses);
                        }
                    }
                    busesAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }


                @Override
                public void onCancelled(DatabaseError error) {
                    progressDialog.dismiss();
                }

            });


    }
}