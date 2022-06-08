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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.apiprojects.sliceofspice.models.Category;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddCategory extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText name;
    Button submit;
    ImageView image_upload;
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private Uri imageUrl;
    String imageUrlToUpload;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Category category=getIntent().getParcelableExtra("category");

        image_upload = findViewById(R.id.image_upload);
        pd= new ProgressDialog(AddCategory.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.setCancelable(false);

        name = findViewById(R.id.et_name);

        submit = findViewById(R.id.submit);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Categories");


        if (category != null) {
            {

                name.setText(category.getName());
                imageUrlToUpload=category.getImageurl();
                Glide.with(this).load(category.getImageurl()).into(image_upload);
            }
        }
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
            if (category != null) {


                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", name.getText().toString());
                hashMap.put("imageurl", imageUrlToUpload);

                databaseReference.child(category.getKey().toString()).updateChildren(hashMap).addOnSuccessListener(suc ->
                {
                    Toast.makeText(AddCategory.this, "Record is updated", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(AddCategory.this, MainActivity.class);
                    startActivity(i);

                    // finish();
                }).addOnFailureListener(er ->
                {
                    Toast.makeText(AddCategory.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }else {


                if (imageUrl != null) {
                    uploadToFireBase(imageUrl);
                } else {
                    Toast.makeText(AddCategory.this, "" + "please upload image", Toast.LENGTH_SHORT).show();
                    return;

                }

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
                        Toast.makeText(AddCategory.this, "Uploaded Successfully", Toast.LENGTH_LONG).show();

                        Log.d("testkru",uri.toString());
                        imageUrlToUpload=uri.toString();

                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        Category category=new Category(name.getText().toString(),imageUrlToUpload,uid);
                        databaseReference.push().setValue(category).addOnSuccessListener(suc -> {
                            Toast.makeText(AddCategory.this, "created", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(AddCategory.this, MainActivity.class);
                            startActivity(i);
                            pd.dismiss();
                        }).addOnFailureListener(fail -> {
                            Toast.makeText(AddCategory.this, "Failed", Toast.LENGTH_LONG).show();
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
                Toast.makeText(AddCategory.this, "Failed to Upload", Toast.LENGTH_LONG).show();
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