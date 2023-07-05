package com.example.librarymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvRegister;
    ProgressBar pgbLogin;
    EditText etLoginEmail, etLoginPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        etLoginEmail = findViewById(R.id.et_login_email);
        etLoginPassword = findViewById(R.id.et_login_password);
        pgbLogin = findViewById(R.id.pgb_login);
        pgbLogin.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        /*btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                Toast.makeText(LogInActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
            }
        });*/

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });

        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
        });



        /*tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));
            }
        });*/
    }

    private void loginUser() {
        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etLoginEmail.setError("Enter email");
            etLoginEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)) {
            etLoginPassword.setError("Enter password");
            etLoginPassword.requestFocus();
        }
        else {
            pgbLogin.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pgbLogin.setVisibility(View.GONE);
                        Toast.makeText(LogInActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    }
                    else {
                        pgbLogin.setVisibility(View.GONE);
                        Toast.makeText(LogInActivity.this, "Log in error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}