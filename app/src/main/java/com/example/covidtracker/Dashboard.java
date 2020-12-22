package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.StringTokenizer;

public class Dashboard extends AppCompatActivity {

    TextView Confirmed, Active, Deceased, Recovered, refreshed;
    PieChart piechart;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshed.setText("Last Updated : ");
                piechart.clearChart();
                fetchdata();
                pullToRefresh.setRefreshing(false);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navView);
        bottomNavigationView.setSelectedItemId(R.id.dashBoard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashBoard:
                        break;
                    case R.id.listView:
                        Intent a = new Intent(Dashboard.this, Listviews.class);
                        startActivity(a);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                    case R.id.precaution:
                        Intent b = new Intent(Dashboard.this, Precaution.class);
                        startActivity(b);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
                return false;
            }
        });

        Confirmed = findViewById(R.id.confirmedCases);
        Active = findViewById(R.id.activeCases);
        Recovered = findViewById(R.id.recoveredCases);
        Deceased = findViewById(R.id.deceasedCases);
        refreshed = findViewById(R.id.refreshtextView);
        piechart = findViewById(R.id.pieChart);
        fetchdata();
    }


    private void fetchdata() {
        String url = "https://api.rootnet.in/covid19-in/stats/latest";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    JSONObject summary = jsonObject.getJSONObject("summary");
                    DecimalFormat formatter = new DecimalFormat("#,##,###");
                    Confirmed.setText(formatter.format(summary.getInt("total")));
                    Recovered.setText(formatter.format(summary.getInt("discharged")));
                    Deceased.setText(formatter.format(summary.getInt("deaths")));
                    Active.setText(formatter.format(summary.getInt("total") - summary.getInt("discharged") - summary.getInt("deaths")));
                    String refresh = response.getString("lastRefreshed");
                    StringTokenizer stringTokenizer = new StringTokenizer(refresh, "T");
                    refreshed.append(stringTokenizer.nextToken());
                    StringTokenizer dot = new StringTokenizer(stringTokenizer.nextToken(), ".");
                    refreshed.append("  " + dot.nextToken());


                    piechart.addPieSlice(new PieModel("Confirmed", summary.getInt("total"), Color.parseColor("#FE2B57")));
                    piechart.addPieSlice(new PieModel("Recovered", summary.getInt("discharged"), Color.parseColor("#66C17A")));
                    piechart.addPieSlice(new PieModel("Active", summary.getInt("total") - summary.getInt("discharged") - summary.getInt("deaths"), Color.parseColor("#1284FE")));
                    piechart.addPieSlice(new PieModel("Deceased", summary.getInt("deaths"), Color.parseColor("#777F87")));
                    piechart.startAnimation();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Dashboard.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}