package com.example.busbooking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView viewBookings;
    private List<BookingBusHelperClass> bookings;
    private ViewBookingsAdapter bookingsAdapter;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog= new ProgressDialog(getContext());
        databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");
        viewBookings=view.findViewById(R.id.bookings);
        viewBookings.setHasFixedSize(true);
        viewBookings.setLayoutManager(new LinearLayoutManager(getContext()));

        bookings = new ArrayList<>();
        bookingsAdapter = new ViewBookingsAdapter(getContext(), (ArrayList<BookingBusHelperClass>) bookings);
        viewBookings.setAdapter(bookingsAdapter);

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading Bookings");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                bookings.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    // Check if the snapshot value is a string
                    if (itemSnapshot.getValue() instanceof String) {
                        String stringValue = (String) itemSnapshot.getValue();
                    } else {
                        // Convert the snapshot value to a BusesRegistration object
                        BookingBusHelperClass myBookings = itemSnapshot.getValue(BookingBusHelperClass.class);
                        bookings.add(myBookings);
                    }
                }
                bookingsAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
            }

        });

        return view;
    }

}