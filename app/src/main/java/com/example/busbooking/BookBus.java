package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BookBus extends AppCompatActivity {
    private GridLayout gridLayout;
    private String userName;
    private DatabaseReference usersRef, bookingRef;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private boolean isSeatSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_bus);

        gridLayout=findViewById(R.id.gridLayoutSeats);

        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        bookingRef = FirebaseDatabase.getInstance().getReference("Bookings");
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();

        for (int i = 0; i < 60; i++) {
            CardView cardView = createSeatCard(i + 1);
            gridLayout.addView(cardView);
            // Check if the seat is booked and set the background color accordingly
            checkSeatBookingStatus(cardView, i + 1);
        }
    }

    private CardView createSeatCard(final int seatNumber) {
        CardView cardView = new CardView(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = getResources().getDimensionPixelSize(R.dimen.seat_width);
        params.height = getResources().getDimensionPixelSize(R.dimen.seat_height);
        params.setMargins(6, 6, 6, 6);
        cardView.setLayoutParams(params);
        cardView.setCardBackgroundColor(getResources().getColor(R.color.bg));

        TextView textView = new TextView(this);
        textView.setText(String.valueOf(seatNumber));
        textView.setTextSize(16);
        textView.setTextColor(getResources().getColor(android.R.color.black));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setGravity(Gravity.CENTER);
        cardView.addView(textView);

        String uid = currentUser.getUid();

        usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String firstName = dataSnapshot.child("firstname").getValue(String.class);
                    String lastName = dataSnapshot.child("lastname").getValue(String.class);
                    userName = firstName + " " + lastName;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors if needed
            }
        });

        return cardView;
    }

    public static String getCurrentDate() {
        // Get the current date and time
        Calendar calendar = Calendar.getInstance();

        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public static String getCurrentTime() {
        // Get the current time
        LocalTime currentTime = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentTime = LocalTime.now();
        }

        // Format the time
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return currentTime.format(formatter);
        }
        return null;
    }

    private void updateSeatState(CardView cardView, boolean isSeatSelected) {
        if (isSeatSelected) {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.red));
        } else {
            cardView.setCardBackgroundColor(getResources().getColor(R.color.bg));
        }
    }

    private boolean isSeatBooked(CardView cardView, int seatNumber) {
        // Check if the seat is already booked based on the background color
        ColorStateList backgroundColorStateList = cardView.getCardBackgroundColor();
        int backgroundColor = backgroundColorStateList.getDefaultColor();
        return backgroundColor == getResources().getColor(R.color.red);
    }

    private void checkSeatBookingStatus(CardView cardView, int bookedSeatNumber) {
        bookingRef.child(String.valueOf(bookedSeatNumber)).child("seatNO").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isSeatBooked = dataSnapshot.exists();

                // Update the UI based on seat availability
                updateSeatState(cardView, isSeatBooked);

                // If the seat is booked, disable the onClickListener to restrict booking
                if (isSeatBooked) {
                    cardView.setOnClickListener(null);
                } else {
                    // If the seat is not booked, enable the onClickListener for booking
                    setSeatClickListener(cardView, bookedSeatNumber);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors if needed
            }
        });
    }

    public  void  setSeatClickListener(CardView cardView, int seatNumber){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSeatBooked(cardView, seatNumber)) {
                    bookingRef.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Seat is already booked, display a toast message
                                Toast.makeText(BookBus.this, "Seat is already booked", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle errors if needed
                        }
                    });
                } else {
                    Intent intent = getIntent();
                    String Company=intent.getStringExtra("company");
                    String busName = intent.getStringExtra("name");
                    String route = intent.getStringExtra("route");
                    String Park=intent.getStringExtra("park");
                    String Fee=intent.getStringExtra("fee");
                    String dateNow = getCurrentDate();
                    String timeNow = getCurrentTime();

                    AlertDialog.Builder dialog = new AlertDialog.Builder(BookBus.this);
                    dialog.setTitle("Confirm your Booking");
                    dialog.setMessage("Are you sure you want to continue?");
                    dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Update the UI based on the seat selection state
                            updateSeatState(cardView, true);

                            String userID = currentUser.getUid();

                            BookingBusHelperClass bookingBusHelperClass = new BookingBusHelperClass(userID, userName, Company, busName, Park, route, dateNow, timeNow, String.valueOf(seatNumber), Fee);

                            bookingRef.child(String.valueOf(seatNumber)).setValue(bookingBusHelperClass)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(BookBus.this, "Booked Seat Successfully", Toast.LENGTH_SHORT).show();
                                                Intent payment = new Intent(BookBus.this, BookingPayment.class);
                                                payment.putExtra("company", Company);
                                                payment.putExtra("seatNO", String.valueOf(seatNumber));
                                                startActivity(payment);
                                                finish();
                                            } else {
                                                Toast.makeText(BookBus.this, "Booking Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    });

                    dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(BookBus.this, "Booking Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.show();
                }
            }
        });
    }

}