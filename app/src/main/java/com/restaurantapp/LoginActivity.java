package com.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    Button btnLogin;
    TextView tv_reg_here,tv_forgetpwd,tvadmin;
    EditText et_USERNAME,et_PWD;
    Spinner spin_role;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tv_reg_here = (TextView) findViewById(R.id.tv_reg_here);
        tv_forgetpwd = (TextView) findViewById(R.id.tv_forgetpwd);

        et_USERNAME = (EditText) findViewById(R.id.et_USERNAME);
        et_PWD = (EditText) findViewById(R.id.et_PWD);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_USERNAME.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Enter Valid Username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_PWD.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            }
        });

        tv_reg_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}