package com.example.busbooking;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewBookingsAdapter extends RecyclerView.Adapter<ViewBookingsAdapter.MyViewHolder> {
    private ArrayList<BookingBusHelperClass> bookings;
    private Context context;

    public ViewBookingsAdapter(Context context, ArrayList<BookingBusHelperClass> bookings) {
        this.bookings = bookings;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Notice Design Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookingBusHelperClass myTicket = bookings.get(position);
        holder.id.setText(myTicket.getUserID());
        holder.company.setText(myTicket.getCompany());
        holder.name.setText(myTicket.getBusName());
        holder.park.setText(myTicket.getPark());
        holder.route.setText(myTicket.getRoute());
        holder.seat.setText(myTicket.getSeatNO());
        holder.fee.setText(myTicket.getFee());
        holder.date.setText(myTicket.getDate()+"  "+myTicket.getTime());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, company, name, park, route, seat, fee, date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            company=itemView.findViewById(R.id.company);
            name = itemView.findViewById(R.id.name);
            park = itemView.findViewById(R.id.park);
            route = itemView.findViewById(R.id.route);
            seat=itemView.findViewById(R.id.seat);
            fee = itemView.findViewById(R.id.fee);
            date=itemView.findViewById(R.id.date);
        }
    }

}
