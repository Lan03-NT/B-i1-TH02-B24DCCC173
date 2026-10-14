package com.example.bai1;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView tvDetailTitle, tvDetailAmount, tvDetailCategory, tvDetailDate, tvDetailNote;
    private String expenseId;
    private final DecimalFormat formatter = new DecimalFormat("#,### đ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView btnBackHeader = findViewById(R.id.btnBackHeader);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailAmount = findViewById(R.id.tvDetailAmount);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailNote = findViewById(R.id.tvDetailNote);
        AppCompatButton btnBack = findViewById(R.id.btnBack);
        AppCompatButton btnDelete = findViewById(R.id.btnDelete);

        expenseId = getIntent().getStringExtra("EXPENSE_ID");
        loadExpenseData();

        btnBackHeader.setOnClickListener(v -> finish());
        btnBack.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> showDeleteConfirmDialog());
    }

    private void loadExpenseData() {
        if (expenseId == null) return;

        Expense expense = ExpenseManager.getInstance().getExpenseById(expenseId);
        if (expense != null) {
            tvDetailTitle.setText(expense.getTitle());
            tvDetailAmount.setText(formatter.format(expense.getAmount()));
            tvDetailCategory.setText(expense.getCategory());
            tvDetailDate.setText(expense.getDate());
            tvDetailNote.setText(expense.getNote() != null && !expense.getNote().isEmpty() ? expense.getNote() : "Không có ghi chú");
        }
    }

    private void showDeleteConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_delete_title)
                .setMessage(R.string.confirm_delete_msg)
                .setPositiveButton(R.string.btn_confirm_yes, (dialog, which) -> {
                    boolean removed = ExpenseManager.getInstance().removeExpense(expenseId);
                    if (removed) {
                        Toast.makeText(this, "Đã xóa khoản chi thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Không thể xóa khoản chi này!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(R.string.btn_confirm_cancel, (dialog, which) -> dialog.dismiss())
                .show();
    }
}