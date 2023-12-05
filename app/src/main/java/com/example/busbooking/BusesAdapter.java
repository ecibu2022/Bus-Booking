package com.example.busbooking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BusesAdapter extends RecyclerView.Adapter<BusesAdapter.MyViewHolder> {
    private ArrayList<BusesRegistration> buses;
    private Context context;

    public BusesAdapter(Context context, ArrayList<BusesRegistration> buses) {
        this.buses = buses;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Notice Design Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.added_buses, parent, false);
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

    }

    @Override
    public int getItemCount() {
        return buses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView company, name, park, route, tel, fee;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            company=itemView.findViewById(R.id.company);
            name = itemView.findViewById(R.id.name);
            park = itemView.findViewById(R.id.park);
            route=itemView.findViewById(R.id.route);
            tel = itemView.findViewById(R.id.tel);
            fee=itemView.findViewById(R.id.fee);
            imageView = itemView.findViewById(R.id.image);
        }
    }

}