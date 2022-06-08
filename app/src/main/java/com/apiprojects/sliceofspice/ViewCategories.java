package com.apiprojects.sliceofspice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.apiprojects.sliceofspice.Adapters.CartAdapter;
import com.apiprojects.sliceofspice.Adapters.UserProductsAdapter;
import com.apiprojects.sliceofspice.models.CartDetails;
import com.apiprojects.sliceofspice.models.Products;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ViewCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);


    }
}