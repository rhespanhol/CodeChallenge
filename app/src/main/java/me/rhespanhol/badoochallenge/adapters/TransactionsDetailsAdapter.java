package me.rhespanhol.badoochallenge.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import me.rhespanhol.badoochallenge.R;
import me.rhespanhol.badoochallenge.pojo.Transaction;


public class TransactionsDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Transaction> mTransactions = new ArrayList<>();

    public TransactionsDetailsAdapter(ArrayList<Transaction> transactions) {
        this.mTransactions = transactions;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_detail, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        Transaction transaction = getItem(position);

        viewHolder.originalPrice.setText(transaction.getCurrency() + " " + transaction.getAmount());

        viewHolder.conversionPrice.setText("GBP " + transaction.getGbpPrice());

    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }


    private Transaction getItem(int position) {
        return mTransactions.get(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        final TextView originalPrice;
        final TextView conversionPrice;



        public ItemViewHolder(View itemView) {
            super(itemView);
            this.originalPrice = (TextView) itemView.findViewById(R.id.transaction_original_price);
            this.conversionPrice = (TextView) itemView.findViewById(R.id.transaction_conversion_price);
        }
    }
}
