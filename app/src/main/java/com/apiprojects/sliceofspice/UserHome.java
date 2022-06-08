package com.apiprojects.sliceofspice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.apiprojects.sliceofspice.Adapters.CategoriesAdapter;
import com.apiprojects.sliceofspice.Adapters.UserCatAdapter;
import com.apiprojects.sliceofspice.models.CartDetails;
import com.apiprojects.sliceofspice.models.Category;
import com.apiprojects.sliceofspice.models.Favourites;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserHome extends AppCompatActivity {
    RecyclerView categoryRecyclerView;

    DatabaseReference databaseReference;
    UserCatAdapter userCatAdapter;

    ArrayList<Category> categoryList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        categoryRecyclerView=findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userCatAdapter=new UserCatAdapter(new UserCatAdapter.ClickListener() {
            @Override
            public void productsPage(int position) {
                Intent i =new Intent(UserHome.this,UserViewProducts.class);
                i.putExtra("categoryId",categoryList.get(position).getKey());

                startActivity(i);
            }

        });

        categoryRecyclerView.setAdapter(userCatAdapter);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Categories");
        fetchItems();

    }

    public void fetchItems(){
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();

        System.out.println("kruthik123" + uid);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoryList=new ArrayList<>();
                for(DataSnapshot data:snapshot.getChildren()){
                    Category category=data.getValue(Category.class);
                    category.setKey(data.getKey());
                    categoryList.add(category);
                }
                userCatAdapter.setItems(categoryList);
                userCatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.cart:
                Intent i =new Intent(UserHome.this, CartActivity.class);
                startActivity(i);
                return true;
            case R.id.profile:
                Intent intent1 =new Intent(UserHome.this, MyProfileActivity.class);
                startActivity(intent1);
                return true;
            case R.id.favourites:
                Intent intent2 =new Intent(UserHome.this, FavouritesActivity.class);
                startActivity(intent2);
                return true;
            case R.id.logout:
                Intent intent = new Intent(UserHome.this, UserLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}