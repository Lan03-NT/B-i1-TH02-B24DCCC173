package com.example.bai1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvTotalAmount;
    private ExpenseAdapter adapter;
    private final DecimalFormat formatter = new DecimalFormat("#,### đ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        RecyclerView rvExpenses = findViewById(R.id.rvExpenses);
        AppCompatButton btnAddExpense = findViewById(R.id.btnAddExpense);

        // Khởi tạo danh sách & Adapter
        ArrayList<Expense> expenseList = ExpenseManager.getInstance().getAllExpenses();
        adapter = new ExpenseAdapter(expenseList, expense -> {
            // Khi nhấn vào 1 item: Mở ExpenseDetailActivity và truyền ID
            Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
            intent.putExtra("EXPENSE_ID", expense.getId());
            startActivity(intent);
        });

        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        rvExpenses.setAdapter(adapter);

        // Khi nhấn nút Thêm khoản chi: Mở AddExpenseActivity
        btnAddExpense.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        double total = ExpenseManager.getInstance().getTotalAmount();
        tvTotalAmount.setText(formatter.format(total));
    }
}