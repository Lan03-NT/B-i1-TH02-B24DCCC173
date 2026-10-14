package com.example.bai1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private LinearLayout layoutError;
    private TextView tvErrorMessage;

    // Tài khoản cố định
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        AppCompatButton btnLogin = findViewById(R.id.btnLogin);
        layoutError = findViewById(R.id.layoutError);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);

        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        boolean hasError = false;

        if (username.isEmpty()) {
            edtUsername.setError("Vui lòng nhập tên đăng nhập!");
            hasError = true;
        }

        if (password.isEmpty()) {
            edtPassword.setError("Vui lòng nhập mật khẩu!");
            hasError = true;
        }

        if (hasError) {
            layoutError.setVisibility(View.GONE);
            return;
        }

        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            layoutError.setVisibility(View.GONE);
            // Đúng thông tin -> Chuyển sang MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Không thể quay lại bằng nút Back
        } else {
            // Sai thông tin -> Hiển thị thông báo lỗi
            tvErrorMessage.setText(R.string.error_login_failed);
            layoutError.setVisibility(View.VISIBLE);
        }
    }
}