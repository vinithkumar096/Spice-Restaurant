package com.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnFav,btnFav1,btnFav2,btnCart,btnCart1,btnCart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        btnFav=(Button) findViewById(R.id.btnFav);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, FavouriteActivity.class));
            }
        });
        btnFav1=(Button) findViewById(R.id.btnFav1);
        btnFav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, FavouriteActivity.class));
            }
        });
        btnFav2=(Button) findViewById(R.id.btnFav2);
        btnFav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, FavouriteActivity.class));
            }
        });

        btnCart=(Button) findViewById(R.id.btnCart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, CartActivity.class));
            }
        });
        btnCart1=(Button) findViewById(R.id.btnCart1);
        btnCart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, CartActivity.class));
            }
        });
        btnCart2=(Button) findViewById(R.id.btnCart2);
        btnCart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, CartActivity.class));
            }
        });
    }
}