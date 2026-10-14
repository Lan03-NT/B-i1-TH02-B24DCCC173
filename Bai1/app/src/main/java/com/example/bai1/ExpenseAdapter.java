package com.example.bai1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Expense expense);
    }

    private final ArrayList<Expense> expenseList;
    private final OnItemClickListener listener;
    private final DecimalFormat formatter = new DecimalFormat("#,### đ");

    public ExpenseAdapter(ArrayList<Expense> expenseList, OnItemClickListener listener) {
        this.expenseList = expenseList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.bind(expense, listener, formatter);
    }

    @Override
    public int getItemCount() {
        return expenseList != null ? expenseList.size() : 0;
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemTitle, tvItemCategory, tvItemAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemTitle = itemView.findViewById(R.id.tvItemTitle);
            tvItemCategory = itemView.findViewById(R.id.tvItemCategory);
            tvItemAmount = itemView.findViewById(R.id.tvItemAmount);
        }

        public void bind(Expense expense, OnItemClickListener listener, DecimalFormat formatter) {
            tvItemTitle.setText(expense.getTitle());
            tvItemCategory.setText(expense.getCategory());
            tvItemAmount.setText(formatter.format(expense.getAmount()));

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(expense);
                }
            });
        }
    }
}