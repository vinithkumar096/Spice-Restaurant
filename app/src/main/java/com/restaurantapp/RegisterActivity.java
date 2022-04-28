package com.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText et_name,et_USERNAME,et_phone,et_PWD;
    Button btnLogin;
    TextView tvsignin;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnLogin=(Button)findViewById(R.id.btnLogin);
        tvsignin=(TextView)findViewById(R.id.tvsignin);
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });

        et_name=(EditText)findViewById(R.id.et_name);
        et_USERNAME=(EditText)findViewById(R.id.et_USERNAME);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_PWD=(EditText)findViewById(R.id.et_PWD);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_name.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_USERNAME.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_phone.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please Enter Valid Phone", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(et_PWD.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }
}