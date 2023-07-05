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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    ProgressBar pgbRegister;
    EditText etRegisterEmail, etRegisterPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btn_register);
        etRegisterEmail = findViewById(R.id.et_register_email);
        etRegisterPassword = findViewById(R.id.et_register_password);
        pgbRegister = findViewById(R.id.pgb_register);
        pgbRegister.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        /*btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
            }
        });*/

        btnRegister.setOnClickListener(view ->{
            registerUser();
        });
    }

    private void registerUser() {
        String email = etRegisterEmail.getText().toString();
        String password = etRegisterPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etRegisterEmail.setError("Enter email");
            etRegisterEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)) {
            etRegisterPassword.setError("Enter password");
            etRegisterPassword.requestFocus();
        }
        else {
            pgbRegister.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        pgbRegister.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                    }
                    else {
                        pgbRegister.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "Registration error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}