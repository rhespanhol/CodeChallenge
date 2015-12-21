package me.rhespanhol.badoochallenge.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import me.rhespanhol.badoochallenge.R;
import me.rhespanhol.badoochallenge.pojo.Transaction;


public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private HashMap<String, ArrayList<Transaction>> mTransactions = new HashMap<>();

    private ArrayList<String> mTransactionsSet = new ArrayList<>();

    private IClickListener mClickListener;

    public ProductsAdapter(HashMap<String, ArrayList<Transaction>> transactions) {
        this.mTransactions = transactions;

        mTransactionsSet.addAll(transactions.keySet());
    }

    public interface IClickListener{
        void onClick(View view, int position);
    }

    public void setmClickListener(IClickListener clickListener){
        this.mClickListener = clickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction, viewGroup, false);

        return new ItemViewHolder(view, new ItemViewHolder.IClickListener() {
            @Override
            public void onClick(View v, int position) {
                if(mClickListener != null){
                    mClickListener.onClick(v, position);
                }

            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        String transaction = getItem(position);

        viewHolder.transactionName.setText(transaction);
        viewHolder.transactionNumber.setText(mTransactions.get(transaction).size() + " transactions");

    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }


    public String getItem(int position) {
        return mTransactionsSet.get(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView transactionName;
        final TextView transactionNumber;

        private IClickListener clickListener;

        interface IClickListener {

            /**
             * Called when the view is clicked.
             *
             * @param v        view that is clicked
             * @param position of the clicked item
             */

            void onClick(View v, int position);
        }


        public ItemViewHolder(View itemView, IClickListener clickListener) {
            super(itemView);
            this.transactionName = (TextView) itemView.findViewById(R.id.transaction_name);
            this.transactionNumber = (TextView) itemView.findViewById(R.id.transaction_number);
            this.clickListener = clickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // If not long clicked, pass last variable as false.
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}
