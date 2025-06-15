package com.s23010433.shavidu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        findViewById(R.id.tempratre_alert_button).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, TempratureActivity.class));
        });

        Button loginButton = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }
}