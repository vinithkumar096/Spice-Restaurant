package com.apiprojects.sliceofspice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.apiprojects.sliceofspice.models.CartDetails;
import com.apiprojects.sliceofspice.models.Favourites;
import com.apiprojects.sliceofspice.models.Products;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class ViewProduct extends AppCompatActivity {
    EditText name,price,desc,et_cat,offerPrice;
    Button addToCart,favButton;
    ImageView image_upload;
    String imageUrlToUpload;
    DatabaseReference databaseReference;
    DatabaseReference favdataBaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        Products product=getIntent().getParcelableExtra("product");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        favdataBaseRef = db.getReference("Favourites");
        image_upload = findViewById(R.id.image_upload);

        name = findViewById(R.id.et_name);
        price = findViewById(R.id.et_price);
        desc = findViewById(R.id.et_desc);
        offerPrice=findViewById(R.id.offer_price);
        favButton=findViewById(R.id.btnFav);


        addToCart = findViewById(R.id.submit);


        if (product != null) {
            {

                name.setText(product.getName());
                price.setText(product.getPrice());
                desc.setText(product.getDesc());
                desc.setFocusable(false);
                desc.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                desc.setClickable(false);
                imageUrlToUpload=product.getImage();
                if( product.getPrice()!=null && product.getOffer()!=null){
                    double afterDiscount= Float.parseFloat(product.getPrice())*((100-Float.parseFloat(product.getOffer()))/100);
                    offerPrice.setText(String.format("%.2f", afterDiscount) + "$");
                    price.setPaintFlags( price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    offerPrice.setText("No Discount");
                }

                Glide.with(this).load(product.getImage()).into(image_upload);
            }
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
                CartDetails cartDetails=new CartDetails(name.getText().toString(),price.getText().toString(),desc.getText().toString(),imageUrlToUpload,uid,"false",product.getOffer(),offerPrice.getText().toString());


                databaseReference.push().setValue(cartDetails).addOnSuccessListener(suc -> {
                    Toast.makeText(ViewProduct.this, "Added To Cart", Toast.LENGTH_LONG).show();
                 finish();

                }).addOnFailureListener(fail -> {
                    Toast.makeText(ViewProduct.this, "Failed", Toast.LENGTH_LONG).show();
                });
            }
        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();
                Favourites favourites=new Favourites(name.getText().toString(),price.getText().toString(),desc.getText().toString(),imageUrlToUpload,uid);
                favdataBaseRef.push().setValue(favourites).addOnSuccessListener(suc -> {
                    Toast.makeText(ViewProduct.this, "Added To Cart", Toast.LENGTH_LONG).show();
                    favButton.setText("Added");

                }).addOnFailureListener(fail -> {
                    Toast.makeText(ViewProduct.this, "Failed", Toast.LENGTH_LONG).show();
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