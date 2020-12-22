package com.example.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private Context mContext;
    private ArrayList<exampleItem> mExampleList;

    public ExampleAdapter(Context context, ArrayList<exampleItem> exampleList){
        mContext=context;
        mExampleList=exampleList;

    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.covid_data_layout,parent,false);
        return  new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        String stateName = mExampleList.get(position).getState();
        int confirmed = mExampleList.get(position).getConfirmed();
        int recovered = mExampleList.get(position).getRecovered();
        int death = mExampleList.get(position).getDeath();

        holder.statetextView.setText(stateName);
        holder.confirmedtextview.setText(String.valueOf(confirmed));
        holder.recoveredTextView.setText(String.valueOf(recovered));
        holder.deathtextView.setText(String.valueOf(death));
        holder.activeTextView.setText(String.valueOf(confirmed-recovered-death));
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends  RecyclerView.ViewHolder{

        public TextView statetextView;
        public TextView confirmedtextview;
        public TextView recoveredTextView;
        public TextView deathtextView;
        public TextView activeTextView;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            statetextView = itemView.findViewById(R.id.state_textView);
            confirmedtextview = itemView.findViewById(R.id.confirmed_textView);
            recoveredTextView= itemView.findViewById(R.id.recovered_textView);
            deathtextView = itemView.findViewById(R.id.death_textView);
            activeTextView= itemView.findViewById(R.id.active_textView);

        }
    }
}
