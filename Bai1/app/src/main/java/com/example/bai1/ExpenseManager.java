package com.example.bai1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ExpenseManager {
    private static ExpenseManager instance;
    private final ArrayList<Expense> expenseList;

    private ExpenseManager() {
        expenseList = new ArrayList<>();

        // Định dạng ngày hiện tại dd/MM/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        // Khởi tạo 3 dữ liệu mẫu theo đúng yêu cầu
        expenseList.add(new Expense("Ăn sáng", 30000, "Ăn uống", currentDate, "Ăn sáng bún bò"));
        expenseList.add(new Expense("Đổ xăng", 50000, "Đi lại", currentDate, "Đổ xăng xe máy"));
        expenseList.add(new Expense("Mua sách", 120000, "Học tập", currentDate, "Sách lập trình Android"));
    }

    public static synchronized ExpenseManager getInstance() {
        if (instance == null) {
            instance = new ExpenseManager();
        }
        return instance;
    }

    public ArrayList<Expense> getAllExpenses() {
        return expenseList;
    }

    public Expense getExpenseById(String id) {
        if (id == null) return null;
        for (Expense e : expenseList) {
            if (id.equals(e.getId())) {
                return e;
            }
        }
        return null;
    }

    public void addExpense(Expense e) {
        if (e != null) {
            expenseList.add(e);
        }
    }

    public boolean removeExpense(String id) {
        if (id == null) return false;
        for (int i = 0; i < expenseList.size(); i++) {
            if (id.equals(expenseList.get(i).getId())) {
                expenseList.remove(i);
                return true;
            }
        }
        return false;
    }

    public double getTotalAmount() {
        double total = 0;
        for (Expense e : expenseList) {
            total += e.getAmount();
        }
        return total;
    }
}