package com.apiprojects.sliceofspice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.apiprojects.sliceofspice.Adapters.ProductsAdapter;
import com.apiprojects.sliceofspice.models.Category;
import com.apiprojects.sliceofspice.models.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewProducts extends AppCompatActivity {
    RecyclerView prodRecylerView;
    ProductsAdapter productsAdapter;

    DatabaseReference databaseReference;

    ArrayList<Products> productsList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);
        prodRecylerView = findViewById(R.id.productsRecyclerView);
        prodRecylerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productsAdapter = new ProductsAdapter(new ProductsAdapter.ClickListener() {
            @Override
            public void productsPage(int position) {

            }

            @Override
            public void delete(int position) {

                databaseReference.child(productsList.get(position).getKey()).removeValue().addOnSuccessListener(suc->{
                    Toast.makeText(ViewProducts.this, "deleted category",Toast.LENGTH_LONG).show();
                    productsAdapter.removeItem(position);

                }).addOnFailureListener(er->{
                    Toast.makeText(ViewProducts.this, er.getMessage(),Toast.LENGTH_LONG).show();




                });
                Toast.makeText(ViewProducts.this, productsList.get(position).getKey(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void detailsPage(int position) {
                Intent intent =new Intent(ViewProducts.this,AddProduct.class);
                intent.putExtra("product",productsList.get(position));
                startActivity(intent);
            }
        });

        prodRecylerView.setAdapter(productsAdapter);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Products");
        fetchItems();
    }

    public void fetchItems(){
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();

        System.out.println("kruthik123" + uid);
        Query query=databaseReference.orderByChild("uuid").equalTo(uid);
        System.out.println("kruthik123" + getIntent().getStringExtra("categoryId"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsList=new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    Products products=data.getValue(Products.class);
                    if(products.getCategory().equals(getIntent().getStringExtra("categoryId"))){
                        products.setKey(data.getKey());
                        productsList.add(products);
                    }


                }
                productsAdapter.setItems(productsList);
                productsAdapter.notifyDataSetChanged();
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