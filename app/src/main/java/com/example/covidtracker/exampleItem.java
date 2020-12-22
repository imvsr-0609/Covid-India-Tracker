package com.example.covidtracker;

public class exampleItem {
    private String mState;
    private int mConfirmed;
    private int mRecovered;
    private int mDeath;

    public  exampleItem(String state, int confirmed,int recovered,int death){
        mState=state;
        mConfirmed=confirmed;
        mRecovered=recovered;
        mDeath=death;
    }

    public String getState(){
        return mState;
    }
    public int getConfirmed(){
        return mConfirmed;
    }

    public int getRecovered(){
        return mRecovered;
    }

    public  int getDeath(){
        return mDeath;
    }
}
