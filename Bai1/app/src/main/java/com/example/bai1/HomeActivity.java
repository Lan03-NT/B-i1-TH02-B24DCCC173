package com.example.bai1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tvWelcomeUser = findViewById(R.id.tvWelcomeUser);
        Button btnDetails = findViewById(R.id.btnDetails);

        String username = getIntent().getStringExtra("USERNAME");
        if (username == null || username.trim().isEmpty()) {
            username = "Khách";
        }

        tvWelcomeUser.setText(getString(R.string.welcome_user, username));

        String finalUsername = username;
        btnDetails.setOnClickListener(v -> {
            new AlertDialog.Builder(HomeActivity.this)
                    .setTitle(R.string.login_details_title)
                    .setMessage(getString(R.string.login_details_message, finalUsername))
                    .setPositiveButton(R.string.close, (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }
}