package com.restaurantapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apiprojects.sliceofspice.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegisterActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText phoneNo;
    EditText password;
    Button createAccount;
    TextView tvsignin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        name=findViewById(R.id.et_name);
        email=findViewById(R.id.et_email);
        phoneNo=findViewById(R.id.et_phone_no);
        password=findViewById(R.id.et_password);
        createAccount=findViewById(R.id.bt_reg);


        tvsignin=findViewById(R.id.tvsignin);
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(UserRegisterActivity.this,UserLoginActivity.class);
                startActivity(i);
            }
        });
        mAuth = FirebaseAuth.getInstance();


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User customerEnitity=new User(name.getText().toString(),email.getText().toString(),password.getText().toString(),phoneNo.getText().toString());

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(customerEnitity).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(UserRegisterActivity.this,"User Registered Successfull",Toast.LENGTH_LONG).show();
                                        finish();
                                    }else{
                                        Toast.makeText(UserRegisterActivity.this,"Failed to register",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        }else{
                            Toast.makeText(UserRegisterActivity.this,"Failed to register",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }
}