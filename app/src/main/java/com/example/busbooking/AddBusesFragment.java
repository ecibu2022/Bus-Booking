package com.example.busbooking;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AddBusesFragment extends Fragment {
private Button global, tausi, gateway, baby, view_global, view_tausi, view_gateway, view_baby;
    public AddBusesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_buses, container, false);
        global=view.findViewById(R.id.global);
        tausi=view.findViewById(R.id.tausi);
        gateway=view.findViewById(R.id.gateway);
        baby=view.findViewById(R.id.baby);
        view_global=view.findViewById(R.id.view_global);
        view_tausi=view.findViewById(R.id.view_tausi);
        view_gateway=view.findViewById(R.id.view_gateway);
        view_baby=view.findViewById(R.id.view_baby);

        global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Global=new Intent(getActivity(), GlobalBuses.class);
                Global.putExtra("name", "Global");
                startActivity(Global);
            }
        });

        tausi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Tausi=new Intent(getActivity(), GlobalBuses.class);
                Tausi.putExtra("name", "Tausi");
                startActivity(Tausi);
            }
        });

        gateway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gateway=new Intent(getActivity(), GlobalBuses.class);
                Gateway.putExtra("name", "Gateway");
                startActivity(Gateway);
            }
        });

        baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Baby=new Intent(getActivity(), GlobalBuses.class);
                Baby.putExtra("name", "Baby");
                startActivity(Baby);
            }
        });

        view_global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Global=new Intent(getActivity(), View_Buses.class);
                Global.putExtra("name", "Global");
                startActivity(Global);
            }
        });

        view_tausi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Tausi=new Intent(getActivity(), View_Buses.class);
                Tausi.putExtra("name", "Tausi");
                startActivity(Tausi);
            }
        });

        view_gateway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gateway=new Intent(getActivity(), View_Buses.class);
                Gateway.putExtra("name", "Gateway");
                startActivity(Gateway);
            }
        });

        view_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Baby=new Intent(getActivity(), View_Buses.class);
                Baby.putExtra("name", "Baby");
                startActivity(Baby);
            }
        });

        return view;
    }
}