package com.example.bai1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText edtTitle, edtAmount, edtNote;
    private Spinner spCategory;
    private TextView tvDate;
    private Calendar selectedCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_expense);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView btnBackHeader = findViewById(R.id.btnBackHeader);
        edtTitle = findViewById(R.id.edtTitle);
        edtAmount = findViewById(R.id.edtAmount);
        spCategory = findViewById(R.id.spCategory);
        tvDate = findViewById(R.id.tvDate);
        edtNote = findViewById(R.id.edtNote);
        AppCompatButton btnSaveExpense = findViewById(R.id.btnSaveExpense);

        // Quay lại khi bấm nút back ở header
        btnBackHeader.setOnClickListener(v -> finish());

        // Thiết lập Spinner danh mục
        String[] categories = {"Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(adapter);

        // Thiết lập ngày mặc định là ngày hiện tại
        selectedCalendar = Calendar.getInstance();
        updateDateTextView();

        // Xử lý chọn ngày qua DatePickerDialog
        tvDate.setOnClickListener(v -> showDatePicker());

        // Xử lý nút Lưu khoản chi
        btnSaveExpense.setOnClickListener(v -> saveExpense());
    }

    private void updateDateTextView() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        tvDate.setText(sdf.format(selectedCalendar.getTime()));
    }

    private void showDatePicker() {
        int year = selectedCalendar.get(Calendar.YEAR);
        int month = selectedCalendar.get(Calendar.MONTH);
        int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            selectedCalendar.set(Calendar.YEAR, year1);
            selectedCalendar.set(Calendar.MONTH, month1);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateTextView();
        }, year, month, day);

        dialog.show();
    }

    private void saveExpense() {
        String title = edtTitle.getText().toString().trim();
        String amountStr = edtAmount.getText().toString().trim();
        String category = spCategory.getSelectedItem() != null ? spCategory.getSelectedItem().toString() : "Khác";
        String date = tvDate.getText().toString().trim();
        String note = edtNote.getText().toString().trim();

        boolean hasError = false;

        if (title.isEmpty()) {
            edtTitle.setError("Tên khoản chi không được để trống!");
            hasError = true;
        }

        double amount = 0;
        if (amountStr.isEmpty()) {
            edtAmount.setError("Vui lòng nhập số tiền!");
            hasError = true;
        } else {
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    edtAmount.setError("Số tiền phải lớn hơn 0!");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                edtAmount.setError("Số tiền không hợp lệ!");
                hasError = true;
            }
        }

        if (hasError) {
            return;
        }

        // Tạo đối tượng Expense mới và lưu vào ExpenseManager
        Expense newExpense = new Expense(title, amount, category, date, note);
        ExpenseManager.getInstance().addExpense(newExpense);

        Toast.makeText(this, "Thêm khoản chi thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }
}