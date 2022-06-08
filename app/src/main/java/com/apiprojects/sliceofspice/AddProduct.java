package com.apiprojects.sliceofspice;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.apiprojects.sliceofspice.models.Category;
import com.apiprojects.sliceofspice.models.Products;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddProduct extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference categoryDatabaseReference;
    EditText name,price,desc,offer;
    Button submit;
    Spinner et_cat;
    ImageView image_upload;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private Uri imageUrl;
    String imageUrlToUpload;
    ProgressDialog pd;
    ArrayList<String> key=new ArrayList<>();
    ArrayList<String> value=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Products product=getIntent().getParcelableExtra("product");

        offer=findViewById(R.id.et_offer);

        et_cat=findViewById(R.id.et_cat);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        categoryDatabaseReference = db.getReference("Categories");
        fetchItems();

        image_upload = findViewById(R.id.image_upload);
        pd= new ProgressDialog(AddProduct.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.setCancelable(false);

        name = findViewById(R.id.et_name);
        price = findViewById(R.id.et_price);
        desc = findViewById(R.id.et_desc);
        

        submit = findViewById(R.id.submit);
        if (product != null) {
            {

                name.setText(product.getName());
                price.setText(product.getPrice());
                desc.setText(product.getDesc());
                offer.setText(product.getOffer());
                imageUrlToUpload=product.getImage();

                Glide.with(this).load(product.getImage()).into(image_upload);
            }
        }

        databaseReference = db.getReference("Products");

        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleyIntent = new Intent();
                galleyIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleyIntent.setType("image/*");
                startActivityForResult(galleyIntent, 2);
            }
        });

        submit.setOnClickListener(v -> {
            pd.show();
            if (product != null) {


                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", name.getText().toString());
                hashMap.put("image", imageUrlToUpload);
                hashMap.put("price", price.getText().toString());
                hashMap.put("desc",desc.getText().toString() );
                hashMap.put("offer",offer.getText().toString() );
                hashMap.put("category",value.get( et_cat.getSelectedItemPosition()) );

                databaseReference.child(product.getKey().toString()).updateChildren(hashMap).addOnSuccessListener(suc ->
                {
                    Toast.makeText(AddProduct.this, "Record is updated", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(AddProduct.this, MainActivity.class);
                    startActivity(i);

                    // finish();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(AddProduct.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }else {


                if (imageUrl != null) {
                    uploadToFireBase(imageUrl);
                } else {
                    Toast.makeText(AddProduct.this, "" + "please upload image", Toast.LENGTH_SHORT).show();
                    return;

                }
            }


        });
    }

    public void fetchItems(){
        String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Query query=databaseReference.orderByChild("orgId").equalTo(uid);

        categoryDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data:snapshot.getChildren()){
                    Category event=data.getValue(Category.class);
                    key.add(event.getName());
                    value.add(data.getKey());
                }


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddProduct.this, android.R.layout.simple_spinner_item, key);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                et_cat.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void uploadToFireBase(Uri uri) {
        StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(AddProduct.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();

                        Log.d("testkru",uri.toString());
                        imageUrlToUpload=uri.toString();

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();



                        Products products=new Products(name.getText().toString(), value.get( et_cat.getSelectedItemPosition()),imageUrlToUpload,price.getText().toString(),desc.getText().toString(),uid,offer.getText().toString());
                        databaseReference.push().setValue(products).addOnSuccessListener(suc -> {
                            Toast.makeText(AddProduct.this, "created", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(AddProduct.this, MainActivity.class);
                            startActivity(i);
                            pd.dismiss();
                        }).addOnFailureListener(fail -> {
                            Toast.makeText(AddProduct.this, "Failed", Toast.LENGTH_LONG).show();
                        });
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProduct.this, "Failed to Upload", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            imageUrl = data.getData();
            image_upload.setImageURI(imageUrl);
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
