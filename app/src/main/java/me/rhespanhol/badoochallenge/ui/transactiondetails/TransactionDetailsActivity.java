package me.rhespanhol.badoochallenge.ui.transactiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import me.rhespanhol.badoochallenge.R;
import me.rhespanhol.badoochallenge.adapters.TransactionsDetailsAdapter;
import me.rhespanhol.badoochallenge.core.BadooChallengeApp;
import me.rhespanhol.badoochallenge.graph.DijkstraAlgorithm;
import me.rhespanhol.badoochallenge.graph.Graph;
import me.rhespanhol.badoochallenge.pojo.Transaction;

public class TransactionDetailsActivity extends AppCompatActivity {

    private static final String TRANSACTION_SKU = "extra.transaction.sku";
    private ArrayList<Transaction> mTransactions;
    private String mSku;

    float mTotal = 0f;
    private DijkstraAlgorithm mDijkstraAlgorithm;
    private Graph mGraph;


    public static Intent TransactionDetailsActivityIntent(Context context, String transactionSku) {
        Intent intent = new Intent();

        intent.putExtra(TRANSACTION_SKU, transactionSku);

        intent.setClass(context, TransactionDetailsActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mGraph = BadooChallengeApp.getInstance().getGraph();
        mDijkstraAlgorithm = new DijkstraAlgorithm(mGraph);

        parseExtras(getIntent());

        setToolbar(toolbar, mSku);

        initTotal();

        initRecyclerView();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setToolbar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(title);
        }
    }

    private void parseExtras(Intent intent) {

        mSku = intent.getStringExtra(TRANSACTION_SKU);

        mTransactions = BadooChallengeApp.getInstance().getTransactions(mSku);
    }

    private void initTotal() {

        for(Transaction transaction: mTransactions) {

            if(transaction.getCurrency().equals("GBP")) transaction.setGbpPrice(Float.parseFloat(transaction.getAmount()));

            if(transaction.getGbpPrice() != 0f) {
                mTotal += transaction.getGbpPrice();
            } else {

                float transactionAmount = Float.parseFloat(transaction.getAmount());

                float transactionGbpPrice;

                Float rate = BadooChallengeApp.getInstance().getGbpRate(transaction.getCurrency());

                if(rate == null) {
                    mDijkstraAlgorithm.execute(transaction.getCurrency());
                    LinkedList<String> path = mDijkstraAlgorithm.getPath("GBP");

                    rate = mGraph.getConversionRate(path);

                    BadooChallengeApp.getInstance().setRate(transaction.getCurrency(), rate);
                }

                transactionGbpPrice = transactionAmount * rate;
                transaction.setGbpPrice(transactionGbpPrice);

                mTotal += transactionGbpPrice;

            }
        }

        ((TextView) findViewById(R.id.total)).setText("Total: GBP" + mTotal);

    }

    private void initRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        TransactionsDetailsAdapter transactionsDetailsAdapter = new TransactionsDetailsAdapter(mTransactions);

        recyclerView.setAdapter(transactionsDetailsAdapter);

    }

}
