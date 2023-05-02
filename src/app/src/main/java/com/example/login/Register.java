package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.User;
import com.example.login.databinding.ActivityLoginBinding;
import com.example.login.databinding.ActivityRegisterBinding;
import com.example.login.ui.login.LoginActivity;
import com.example.login.ui.login.LoginViewModel;
import com.example.login.ui.login.LoginViewModelFactory;

import java.io.Console;

public class Register extends AppCompatActivity {

    private ActivityRegisterBinding binding;
//    binding = ActivityLoginBinding.inflate(getLayoutInflater());
//    setContentView(binding.getRoot());
//    final Button registerButton2 = binding.btnRegister;



//    final Button registerButton = findViewById(R.id.btn_register);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        final Button registerButton2 = binding.btnRegister;
        setContentView(R.layout.activity_register);

        final Button registerButton2 = (Button) findViewById(R.id.btn_register);

        registerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.et_name)).getText().toString();
                String username = ((EditText) findViewById(R.id.et_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
                finish();
                Intent intent2 = new Intent(Register.this, MainActivity.class);
                startActivity(intent2);
                new User(1,name,null,null,true,username,password);
            }
        });
    }
}