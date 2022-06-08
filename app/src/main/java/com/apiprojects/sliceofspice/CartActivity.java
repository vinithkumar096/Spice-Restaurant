package com.apiprojects.sliceofspice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apiprojects.sliceofspice.Adapters.CartAdapter;
import com.apiprojects.sliceofspice.Adapters.UserProductsAdapter;
import com.apiprojects.sliceofspice.models.CartDetails;
import com.apiprojects.sliceofspice.models.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    RecyclerView cartRecyclerView;
    CartAdapter cartAdapter;
    Button btnPayment;
    DatabaseReference databaseReference;

    ArrayList<CartDetails> cartDetailsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnPayment=findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(CartActivity.this,PaymentActivity.class);
                startActivity(i);
            }
        });
        cartAdapter = new CartAdapter(true,true,new CartAdapter.ClickListener() {
            @Override
            public void delete(int position) {
                databaseReference.child(cartDetailsArrayList.get(position).getKey()).removeValue().addOnSuccessListener(suc -> {
                    Toast.makeText(CartActivity.this, "deleted category", Toast.LENGTH_LONG).show();
                    cartAdapter.removeItem(position);

                }).addOnFailureListener(er -> {
                    Toast.makeText(CartActivity.this, er.getMessage(), Toast.LENGTH_LONG).show();


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
                    if(cartDetails.getStatus().equals("false")){
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