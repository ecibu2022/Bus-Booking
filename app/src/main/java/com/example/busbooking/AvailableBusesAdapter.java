package com.example.busbooking;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AvailableBusesAdapter extends RecyclerView.Adapter<AvailableBusesAdapter.MyViewHolder> {
    private ArrayList<BusesRegistration> buses;
    private Context context;
    private String busName;

    public AvailableBusesAdapter(Context context, ArrayList<BusesRegistration> buses, String busName) {
        this.buses = buses;
        this.context = context;
        this.busName=busName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Notice Design Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_buses, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BusesRegistration myBuses = buses.get(position);
        holder.company.setText(myBuses.getCompany());
        holder.name.setText(myBuses.getBusName());
        holder.park.setText(myBuses.getPark());
        holder.route.setText(myBuses.getRoute());
        holder.tel.setText(myBuses.getTelephone());
        holder.fee.setText(myBuses.getFee());

        Glide.with(context).load(myBuses.getImage()).into(holder.imageView);

        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentBusName = busName;
                Intent bookBus=new Intent(context, BookBus.class);
                bookBus.putExtra("company", myBuses.getCompany());
                bookBus.putExtra("name", myBuses.getBusName());
                bookBus.putExtra("route", myBuses.getRoute());
                bookBus.putExtra("park", myBuses.getPark());
                bookBus.putExtra("fee", myBuses.getFee());
                context.startActivity(bookBus);
            }
        });

    }

    @Override
    public int getItemCount() {
        return buses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView company, name, park, route, tel, fee;
        private ImageView imageView;
        private Button book;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            company=itemView.findViewById(R.id.company);
            name = itemView.findViewById(R.id.name);
            park = itemView.findViewById(R.id.park);
            route=itemView.findViewById(R.id.route);
            tel = itemView.findViewById(R.id.tel);
            fee=itemView.findViewById(R.id.fee);
            imageView = itemView.findViewById(R.id.image);
            book=itemView.findViewById(R.id.book);
        }
    }

}