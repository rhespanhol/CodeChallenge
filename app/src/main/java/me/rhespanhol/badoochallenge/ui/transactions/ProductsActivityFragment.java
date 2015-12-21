package me.rhespanhol.badoochallenge.ui.transactions;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.rhespanhol.badoochallenge.R;
import me.rhespanhol.badoochallenge.adapters.ProductsAdapter;
import me.rhespanhol.badoochallenge.core.BadooChallengeApp;
import me.rhespanhol.badoochallenge.graph.Graph;
import me.rhespanhol.badoochallenge.pojo.Rate;
import me.rhespanhol.badoochallenge.pojo.Transaction;
import me.rhespanhol.badoochallenge.ui.transactiondetails.TransactionDetailsActivity;

public class ProductsActivityFragment extends Fragment implements ProductsAdapter.IClickListener {

    private HashMap<String, ArrayList<Transaction>> mTransactions = new HashMap<>();
    private ProductsAdapter mProductsAdapter;


    public ProductsActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parseTransactions();

        BadooChallengeApp.getInstance().setTransactions(mTransactions);

        parseRates();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);

        initRecyclerView(rootView);

        return rootView;
    }

    private void initRecyclerView(View rootView) {

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mProductsAdapter = new ProductsAdapter(mTransactions);

        recyclerView.setAdapter(mProductsAdapter);

        mProductsAdapter.setmClickListener(this);

    }


    private void parseTransactions() {
        Gson gson = new GsonBuilder().create();

        Type listType = new TypeToken<List<Transaction>>() {
        }.getType();
        ArrayList<Transaction> transactions = gson.fromJson(loadJSONFromAsset("transactions.json"), listType);

        for (Transaction transaction : transactions) {
            String transactionSku = transaction.getSku();

            if(!mTransactions.containsKey(transactionSku)) {
                mTransactions.put(transactionSku, new ArrayList<Transaction>());
            }

            mTransactions.get(transactionSku).add(transaction);
        }

    }

    private void parseRates() {
        Gson gson = new GsonBuilder().create();

        Type listType = new TypeToken<List<Rate>>() {
        }.getType();
        ArrayList<Rate> rates = gson.fromJson(loadJSONFromAsset("rates.json"), listType);

        BadooChallengeApp.getInstance().setRates(rates);

        Graph graph = Graph.getGraph(rates);

        BadooChallengeApp.getInstance().setGraph(graph);
    }

    private String loadJSONFromAsset(String jsonFile) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onClick(View view, int position) {
        String sku = mProductsAdapter.getItem(position);
        startActivity(TransactionDetailsActivity.TransactionDetailsActivityIntent(getActivity(), sku));
    }
}
