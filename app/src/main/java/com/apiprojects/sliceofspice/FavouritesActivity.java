package com.apiprojects.sliceofspice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.apiprojects.sliceofspice.Adapters.CartAdapter;
import com.apiprojects.sliceofspice.Adapters.FavouritesAdapter;
import com.apiprojects.sliceofspice.Adapters.ProductsAdapter;
import com.apiprojects.sliceofspice.models.CartDetails;
import com.apiprojects.sliceofspice.models.Favourites;
import com.apiprojects.sliceofspice.models.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    RecyclerView favRV;
    FavouritesAdapter favouritesAdapter;

    DatabaseReference databaseReference;

    ArrayList<Favourites> favourites=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        favRV = findViewById(R.id.favRecyclerView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        favRV.setLayoutManager(new LinearLayoutManager(this));

       favouritesAdapter=new FavouritesAdapter(true, new FavouritesAdapter.ClickListener() {
           @Override
           public void delete(int position) {
               databaseReference.child(favourites.get(position).getKey()).removeValue().addOnSuccessListener(suc -> {

                   favouritesAdapter.removeItem(position);
           });
           }});



        favRV.setAdapter(favouritesAdapter);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Favourites");
        fetchItems();

    }


    public void fetchItems() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        System.out.println("kruthik123" + uid);
        Query query = databaseReference.orderByChild("uuid").equalTo(uid);
        System.out.println("kruthik123" + getIntent().getStringExtra("categoryId"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favourites = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {

                    Favourites cartDetails = data.getValue(Favourites.class);

                    cartDetails.setKey(data.getKey());
                    favourites.add(cartDetails);


                }
                favouritesAdapter.setItems(favourites);
                favouritesAdapter.notifyDataSetChanged();
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