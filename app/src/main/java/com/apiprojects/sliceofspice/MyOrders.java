package com.apiprojects.sliceofspice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apiprojects.sliceofspice.Adapters.CartAdapter;
import com.apiprojects.sliceofspice.models.CartDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrders extends AppCompatActivity {
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    Button btnPayment;
    DatabaseReference databaseReference;

    ArrayList<CartDetails> cartDetailsArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cartAdapter = new CartAdapter(false,true,new CartAdapter.ClickListener() {
            @Override
            public void delete(int position) {
                databaseReference.child(cartDetailsArrayList.get(position).getKey()).removeValue().addOnSuccessListener(suc -> {
                    Toast.makeText(MyOrders.this, "deleted category", Toast.LENGTH_LONG).show();
                    cartAdapter.removeItem(position);

                }).addOnFailureListener(er -> {
                    Toast.makeText(MyOrders.this, er.getMessage(), Toast.LENGTH_LONG).show();


                });
            }

        });
        cartRecyclerView.setAdapter(cartAdapter);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Cart");
        fetchItems();

    }


    public void fetchItems() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        System.out.println("kruthik123" + uid);
        Query query = databaseReference.orderByChild("category").equalTo(getIntent().getStringExtra("categoryId"));
        System.out.println("kruthik123" + getIntent().getStringExtra("categoryId"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartDetailsArrayList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {

                    CartDetails cartDetails = data.getValue(CartDetails.class);

                    cartDetails.setKey(data.getKey());
                    if(cartDetails.getStatus().equals("true")){
                        cartDetailsArrayList.add(cartDetails);

                    }


                }
                cartAdapter.setItems(cartDetailsArrayList);
                cartAdapter.notifyDataSetChanged();
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