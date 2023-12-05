package com.example.busbooking;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Client extends AppCompatActivity {
    private ImageSlider imageSlider;
    CardView book, buses, logout,route;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private Button view_global, view_tausi, view_gateway, view_baby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        view_global=findViewById(R.id.view_global);
        view_tausi=findViewById(R.id.view_tausi);
        view_gateway=findViewById(R.id.view_gateway);
        view_baby=findViewById(R.id.view_baby);

        imageSlider = findViewById(R.id.imageSlider);
        book = findViewById(R.id.book);
        logout = findViewById(R.id.logout);
        buses = findViewById(R.id.buses);
        route = findViewById(R.id.route);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.logo2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.gateway, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.tausi, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Client.this, "Bookings Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        buses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Client.this, "Buses Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Client.this, "Routes Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Client.this);
                builder1.setMessage("Are you sure you want to log out?")
                        .setTitle("Log Out")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mAuth.signOut();
                                Intent intent = new Intent(Client.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder1.create();
                alert.show();
            }
        });

        view_global.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Global=new Intent(Client.this, Available_Buses.class);
                Global.putExtra("name", "Global");
                startActivity(Global);
            }
        });

        view_tausi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Tausi=new Intent(Client.this, Available_Buses.class);
                Tausi.putExtra("name", "Tausi");
                startActivity(Tausi);
            }
        });

        view_gateway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Gateway=new Intent(Client.this, Available_Buses.class);
                Gateway.putExtra("name", "Gateway");
                startActivity(Gateway);
            }
        });

        view_baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Baby=new Intent(Client.this, Available_Buses.class);
                Baby.putExtra("name", "Baby");
                startActivity(Baby);
            }
        });



    }
}