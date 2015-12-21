package me.rhespanhol.badoochallenge.core;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import me.rhespanhol.badoochallenge.graph.Graph;
import me.rhespanhol.badoochallenge.pojo.Rate;
import me.rhespanhol.badoochallenge.pojo.Transaction;

/**
 * Created by rhespanhol on 21/12/15.
 */
public class BadooChallengeApp extends Application {

    private static BadooChallengeApp sInstance;

    private HashMap<String, ArrayList<Transaction>> mTransactions;
    private ArrayList<Rate> mRates;
    private HashMap<String, Float> mGbpRates = new HashMap<>();

    private Graph mGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        if (sInstance == null) sInstance = this;
    }

    public static BadooChallengeApp getInstance() {
        return sInstance;
    }

    public Context getContext() {
        return sInstance.getApplicationContext();

    }

    public void setRates(ArrayList<Rate> rates) {
        mRates = rates;
    }

    public void setTransactions(HashMap<String, ArrayList<Transaction>> transactions) {
        mTransactions = transactions;
    }

    public ArrayList<Transaction> getTransactions(String sku) {
        return mTransactions.get(sku);
    }

    public void setGraph(Graph graph) {
        mGraph = graph;
    }

    public Graph getGraph() {
        return mGraph;
    }

    public Float getGbpRate(String currency) {
        return mGbpRates.get(currency);
    }

    public void setRate(String currency, float converstionRate) {
        mGbpRates.put(currency, converstionRate);
    }
}
