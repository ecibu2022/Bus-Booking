package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingPayment extends AppCompatActivity {
    private RecyclerView ticket;
    private List<BookingBusHelperClass> bookings;
    private TicketAdapter ticketAdapter;

    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_payment);

        ticket=findViewById(R.id.ticket);

        progressDialog= new ProgressDialog(BookingPayment.this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Bookings");

        ticket.setHasFixedSize(true);
        ticket.setLayoutManager(new LinearLayoutManager(BookingPayment.this));

        bookings = new ArrayList<>();
        ticketAdapter = new TicketAdapter(BookingPayment.this, (ArrayList<BookingBusHelperClass>) bookings);
        ticket.setAdapter(ticketAdapter);

        ProgressDialog progressDialog = new ProgressDialog(BookingPayment.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading Your Tickets");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference.orderByChild("userID").equalTo(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                bookings.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    // Check if the snapshot value is a string
                    if (itemSnapshot.getValue() instanceof String) {
                        // Handle the case where the value is a string (it might be a single value, not a BusesRegistration object)
                        String stringValue = (String) itemSnapshot.getValue();
                        // Handle this string value appropriately (perhaps log it for debugging purposes)
                    } else {
                        // Convert the snapshot value to a BusesRegistration object
                        BookingBusHelperClass myBookings = itemSnapshot.getValue(BookingBusHelperClass.class);
                        bookings.add(myBookings);
                    }
                }
                ticketAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }


            @Override
            public void onCancelled(DatabaseError error) {
                progressDialog.dismiss();
            }

        });

    }
}