package com.studybuddy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.studybuddy.MainActivity;
import com.studybuddy.R;
import com.studybuddy.databinding.ActivityRegisterBinding;

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
        final ImageButton arrow = (ImageButton) findViewById(R.id.arrow);
        //final Button backImage = (ImageButton) findViewById(R.id.btn_register);

        registerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.et_name)).getText().toString();
                String username = ((EditText) findViewById(R.id.et_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
                finish();
                Intent intent2 = new Intent(Register.this, MainActivity.class);
                startActivity(intent2);
                //new User(1,name,null,null,true,username,password);
            }
        });

        registerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.et_name)).getText().toString();
                String username = ((EditText) findViewById(R.id.et_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
                finish();
                Intent intent2 = new Intent(Register.this, MainActivity.class);
                startActivity(intent2);
                //new User(1,name,null,null,true,username,password);
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent2 = new Intent(Register.this, LoginActivity.class);
                startActivity(intent2);
                //new User(1,name,null,null,true,username,password);
            }
        });
    }
}