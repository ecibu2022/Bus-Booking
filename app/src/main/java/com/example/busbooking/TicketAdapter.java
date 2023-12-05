package com.example.busbooking;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {
    private ArrayList<BookingBusHelperClass> bookings;
    private Context context;

    public TicketAdapter(Context context, ArrayList<BookingBusHelperClass> bookings) {
        this.bookings = bookings;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Notice Design Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_seats, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookingBusHelperClass myTicket = bookings.get(position);
        holder.company.setText(myTicket.getCompany());
        holder.name.setText(myTicket.getBusName());
        holder.park.setText(myTicket.getPark());
        holder.route.setText(myTicket.getRoute());
        holder.seat.setText(myTicket.getSeatNO());
        holder.fee.setText(myTicket.getFee());
        holder.date.setText(myTicket.getDate()+"  "+myTicket.getTime());

        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Pay btn clicked", Toast.LENGTH_SHORT).show();
            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Cancelling your Booking");
                dialog.setMessage("Are you sure you want to cancel?");
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference ticketRef = FirebaseDatabase.getInstance().getReference("Bookings").child(user.getUid());

                        ticketRef.removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Ticket cancelled successfully", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed to cancel ticket", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

                dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Failed to cancel ticket", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView company, name, park, route, seat, fee, date;
        Button pay, cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            company=itemView.findViewById(R.id.company);
            name = itemView.findViewById(R.id.name);
            park = itemView.findViewById(R.id.park);
            route = itemView.findViewById(R.id.route);
            seat=itemView.findViewById(R.id.seat);
            fee = itemView.findViewById(R.id.fee);
            date=itemView.findViewById(R.id.date);
            pay=itemView.findViewById(R.id.pay);
            cancel=itemView.findViewById(R.id.cancel);
        }
    }

}
