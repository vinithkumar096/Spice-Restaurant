package com.apiprojects.sliceofspice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apiprojects.sliceofspice.models.CartDetails;
import com.apiprojects.sliceofspice.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyProfileActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText phoneNo;
    EditText password;
    Button update;
    Button myorder;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        phoneNo = findViewById(R.id.et_phone_no);
        password = findViewById(R.id.et_password);
        update = findViewById(R.id.update);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Users");
        fetchItems();
        myorder=findViewById(R.id.myorder);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", name.getText().toString());
                hashMap.put("email", email.getText().toString());
                hashMap.put("price", phoneNo.getText().toString());
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                databaseReference.child(uid).updateChildren(hashMap).addOnSuccessListener(suc ->
                {
                    Toast.makeText(MyProfileActivity.this, "Record is updated", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MyProfileActivity.this, UserHome.class);
                    startActivity(i);

                    // finish();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(MyProfileActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });

        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyProfileActivity.this,MyOrders.class);
                startActivity(i);
            }
        });


    }

    public void fetchItems() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        System.out.println("kruthik123" + uid);
        System.out.println("kruthik123" + getIntent().getStringExtra("categoryId"));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {


                    if (data.getKey().equals(uid)) {
                        User user = data.getValue(User.class);
                        name.setText(user.getName());
                        email.setText(user.getEmail());
                        phoneNo.setText(user.getPhone());
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}