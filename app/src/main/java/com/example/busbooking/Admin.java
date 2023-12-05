package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Admin extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bottomNavigationView=findViewById(R.id.bottomNavigationView);

//        Makes Home active when back button is clicked either in settings or profile it goes back to home
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.book);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    HomeFragment homeFragment = new HomeFragment();
    AddBusesFragment addBusesFragment = new AddBusesFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.book:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, homeFragment)
                        .addToBackStack(null)
                        .commit();
                return true;

            case R.id.buses:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, addBusesFragment)
                        .addToBackStack(null)
                        .commit();
                return true;

            case R.id.logout:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Are you sure you want to log out?")
                        .setTitle("Log Out")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mAuth.signOut();
                                Intent intent = new Intent(Admin.this, Login.class);
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

                return true;

        }

        return false;
    }

}