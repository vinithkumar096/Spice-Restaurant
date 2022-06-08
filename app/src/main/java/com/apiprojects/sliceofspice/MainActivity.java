package com.apiprojects.sliceofspice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apiprojects.sliceofspice.Adapters.CategoriesAdapter;
import com.apiprojects.sliceofspice.models.Category;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;

    RecyclerView categoryRecyclerView;

    DatabaseReference databaseReference;

    ArrayList<Category> categoryList=new ArrayList<>();

    CategoriesAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryRecyclerView=findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawe_layout);
        navigationView=findViewById(R.id.nv); ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.addCategory:
                        Intent intent1 =new Intent(MainActivity.this,AddCategory.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent1);

                        break;
                    case R.id.addProduct:
                        Intent p =new Intent(MainActivity.this,AddProduct.class);
                        p.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(p);

                        break;


//                    case R.id.bookings:
//                        Intent acceptance =new Intent(OrganiserHomeActivity.this,AcceptanceActivity.class);
////                        logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        startActivity(acceptance);
//                        break;
                    case R.id.logout:
                        Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;





                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

         categoriesAdapter=new CategoriesAdapter(new CategoriesAdapter.ClickListener() {
            @Override
            public void productsPage(int position) {
                Intent i =new Intent(MainActivity.this,ViewProducts.class);
                i.putExtra("categoryId",categoryList.get(position).getKey());

                startActivity(i);
            }

            @Override
            public void delete(int position) {
                databaseReference.child(categoryList.get(position).getKey()).removeValue().addOnSuccessListener(suc->{
                    Toast.makeText(MainActivity.this, "deleted category",Toast.LENGTH_LONG).show();
                    categoriesAdapter.removeItem(position);

                }).addOnFailureListener(er->{
                    Toast.makeText(MainActivity.this, er.getMessage(),Toast.LENGTH_LONG).show();




                });
                Toast.makeText(MainActivity.this, categoryList.get(position).getKey(),Toast.LENGTH_LONG).show();
            }
            @Override
            public void detailsPage(int position) {
                Intent intent =new Intent(MainActivity.this,AddCategory.class);
                intent.putExtra("category",categoryList.get(position));
                startActivity(intent);
            }
        });

         categoryRecyclerView.setAdapter(categoriesAdapter);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Categories");
         fetchItems();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void fetchItems(){
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();

        System.out.println("kruthik123" + uid);
        Query query=databaseReference.orderByChild("uuid").equalTo(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               categoryList=new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    Category category=data.getValue(Category.class);
                    category.setKey(data.getKey());
                    categoryList.add(category);
                }
                categoriesAdapter.setItems(categoryList);
                categoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}