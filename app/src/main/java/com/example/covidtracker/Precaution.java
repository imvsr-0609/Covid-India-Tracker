package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Precaution extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Precaution.this, Dashboard.class);
        startActivity(a);
        overridePendingTransition(0, 0);
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precaution);


        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setSelectedItemId(R.id.precaution);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashBoard:
                        Intent a = new Intent(Precaution.this, Dashboard.class);
                        startActivity(a);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.listView:
                        Intent b = new Intent(Precaution.this, Listviews.class);
                        startActivity(b);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.precaution:
                        break;

                }
                return false;
            }
        });

    }
}