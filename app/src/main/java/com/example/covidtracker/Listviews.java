package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Listviews extends AppCompatActivity {

   TextView refreshed;
    private RecyclerView mRecyclerview;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<exampleItem> mExampleList;
    RequestQueue requestQueue;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Listviews.this, Dashboard.class);
        startActivity(a);
        overridePendingTransition(0, 0);
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviews);


        mRecyclerview = findViewById(R.id.recycler_view);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();


        requestQueue = Volley.newRequestQueue(this);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshed.setText("Last Updated : ");
                fetchdata();
                pullToRefresh.setRefreshing(false);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setSelectedItemId(R.id.listView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashBoard:
                        Intent a = new Intent(Listviews.this, Dashboard.class);
                        startActivity(a);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.listView:
                        break;
                    case R.id.precaution:
                        Intent b = new Intent(Listviews.this, Precaution.class);
                        startActivity(b);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
                return false;
            }
        });


        refreshed=findViewById(R.id.refreshtextView);

        fetchdata();



    }
    public  void fetchdata(){
        String url = "https://api.rootnet.in/covid19-in/stats/latest";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject data = response.getJSONObject("data");
                    DecimalFormat formatter = new DecimalFormat("#,##,###");
                    String refresh = response.getString("lastRefreshed");
                    StringTokenizer stringTokenizer = new StringTokenizer(refresh, "T");
                    refreshed.append(stringTokenizer.nextToken());
                    StringTokenizer dot = new StringTokenizer(stringTokenizer.nextToken(), ".");
                    refreshed.append("  " + dot.nextToken());
                    JSONArray regionalArray = data.getJSONArray("regional");
                    for (int i = 0; i < regionalArray.length(); i++) {
                        JSONObject statewise = regionalArray.getJSONObject(i);
                        String statename = statewise.getString("loc");
                        int totalconfirmed = statewise.getInt("totalConfirmed");
                        int discharged = statewise.getInt("discharged");
                        int death = statewise.getInt("deaths");
                        mExampleList.add(new exampleItem(statename,totalconfirmed,discharged,death));
                        Log.i("list",statename+totalconfirmed+discharged+death);



                    }
                    Log.i("lists",mExampleList.toString());
                    mExampleAdapter = new ExampleAdapter(Listviews.this, mExampleList);
                    mRecyclerview.setAdapter(mExampleAdapter);



                } catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Listviews.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


}