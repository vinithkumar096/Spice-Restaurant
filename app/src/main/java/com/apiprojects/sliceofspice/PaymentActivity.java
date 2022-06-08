package com.apiprojects.sliceofspice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apiprojects.sliceofspice.models.CartDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {

    Button save;
    DatabaseReference databaseReference;

    ArrayList<String> cartDetailsArrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        save=findViewById(R.id.save);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Cart");
        fetchItems();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<cartDetailsArrayList.size();i++){
                    HashMap<String, Object> hashMap = new HashMap<>();

                    hashMap.put("status", "true");

                    databaseReference.child(cartDetailsArrayList.get(i)).updateChildren(hashMap).addOnSuccessListener(suc ->
                    {
                        Toast.makeText(PaymentActivity.this, "Record is updated", Toast.LENGTH_SHORT).show();



                        // finish();
                    }).addOnFailureListener(er ->
                    {
                        Toast.makeText(PaymentActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
                Intent i = new Intent(PaymentActivity.this,PaymentAddActivity.class);
                startActivity(i);
            }
        });


    }
    public void fetchItems(){
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();

        System.out.println("kruthik123" + uid);
        Query query=databaseReference.orderByChild("category").equalTo(getIntent().getStringExtra("categoryId"));
        System.out.println("kruthik123" + getIntent().getStringExtra("categoryId"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartDetailsArrayList=new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    CartDetails cartDetails=data.getValue(CartDetails.class);
                    cartDetailsArrayList.add(data.getKey());


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