package com.apiprojects.sliceofspice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apiprojects.sliceofspice.models.AdminUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AdminRegistrationActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText phoneNo;
    EditText password;
    Button createAccount;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);
        name=findViewById(R.id.et_name);
        email=findViewById(R.id.et_email);
        phoneNo=findViewById(R.id.et_phone_no);
        password=findViewById(R.id.et_password);
        createAccount=findViewById(R.id.bt_reg);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            AdminUser customerEnitity=new AdminUser(name.getText().toString(),email.getText().toString(),password.getText().toString(),phoneNo.getText().toString());

                            FirebaseDatabase.getInstance().getReference("Admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(customerEnitity).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AdminRegistrationActivity.this,"User Registered Successfull",Toast.LENGTH_LONG).show();
                                        finish();
                                    }else{
                                        Toast.makeText(AdminRegistrationActivity.this,"Failed to register",Toast.LENGTH_LONG).show();

                                    }
                                }
                            });

                        }else{
                            Toast.makeText(AdminRegistrationActivity.this,"Failed to register",Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}