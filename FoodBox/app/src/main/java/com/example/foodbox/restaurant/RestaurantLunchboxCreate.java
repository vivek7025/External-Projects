package com.example.foodbox.restaurant;

import static com.example.foodbox.restaurant.RestaurantDashboard.IMAGE_REQUEST;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodbox.R;
import com.example.foodbox.model.RestaurantModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class RestaurantLunchboxCreate extends AppCompatActivity {
    EditText et_lunchboxName;
    EditText et_lunchboxDesc;
    EditText et_item1;
    EditText et_item1Price;
    EditText et_item2;
    EditText et_item2Price;
    EditText et_item3;
    EditText et_item3Price;
    EditText et_item4;
    EditText et_item4Price;
    EditText et_item5;
    EditText et_item5Price;
    EditText et_item6;
    EditText et_item6Price;
    EditText et_item7;
    EditText et_item7Price;
    EditText et_item8;
    EditText et_item8Price;

    private String restName = "";
    private String restImage = "";

    private ProgressDialog progressDialog;

    Button btn_addFood;
    private String imageString;

    private Uri imageUri;
    private StorageTask uploadTask;

    private static final int STORAGE_PERMISSION_CODE = 100;

    ImageView iv_lunchboxImage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_lunchbox_create);

        progressDialog = new ProgressDialog(RestaurantLunchboxCreate.this);

        et_lunchboxName = findViewById(R.id.et_lunchboxName);
        et_lunchboxDesc = findViewById(R.id.et_lunchboxDesc);
        et_item1 = findViewById(R.id.et_item1); //item1
        et_item1Price = findViewById(R.id.et_item1Price);
        et_item2 = findViewById(R.id.et_item2); //item2
        et_item2Price = findViewById(R.id.et_item2Price);
        et_item3 = findViewById(R.id.et_item3); //item3
        et_item3Price = findViewById(R.id.et_item3Price);
        et_item4 = findViewById(R.id.et_item4); //item4
        et_item4Price = findViewById(R.id.et_item4Price);
        et_item5 = findViewById(R.id.et_item5); //item5
        et_item5Price = findViewById(R.id.et_item5Price);
        et_item6 = findViewById(R.id.et_item6); //item6
        et_item6Price = findViewById(R.id.et_item6Price);
        et_item7 = findViewById(R.id.et_item7); //item7
        et_item7Price = findViewById(R.id.et_item7Price);
        et_item8 = findViewById(R.id.et_item8); //item8
        et_item8Price = findViewById(R.id.et_item8Price);


        iv_lunchboxImage = findViewById(R.id.iv_lunchboxImage);
        btn_addFood = findViewById(R.id.btn_addlunchbox);

        storageReference = FirebaseStorage.getInstance().getReference().child("Uploads");

        // getting restaurant name from firebase
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(RestaurantRegister.RESTAURANT_USERS).child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RestaurantModel user = snapshot.getValue(RestaurantModel.class);
                restName = user.getUsername();
                restImage = user.getImageUrl();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        iv_lunchboxImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
            }
        });
        btn_addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lunchboxName = et_lunchboxName.getText().toString();
                String lunchboxDesc = et_lunchboxDesc.getText().toString();

                String item1 = et_item1.getText().toString(); //item1
                String item1Price = et_item1Price.getText().toString();
                String item2 = et_item2.getText().toString(); //item2
                String item2Price = et_item2Price.getText().toString();
                String item3 = et_item3.getText().toString(); //item3
                String item3Price = et_item3Price.getText().toString();
                String item4 = et_item4.getText().toString(); //item4
                String item4Price = et_item4Price.getText().toString();
                String item5 = et_item5.getText().toString(); //item5
                String item5Price = et_item5Price.getText().toString();
                String item6 = et_item6.getText().toString(); //item6
                String item6Price = et_item6Price.getText().toString();
                String item7 = et_item7.getText().toString(); //item7
                String item7Price = et_item7Price.getText().toString();
                String item8 = et_item8.getText().toString(); //item8
                String item8Price = et_item8Price.getText().toString();



                String lunchboxImage = imageString;
                if (lunchboxName.isEmpty()) {
                    et_lunchboxName.setError("Empty Name");
                } else if (lunchboxDesc.isEmpty()) {
                    et_lunchboxDesc.setError("Empty Desc");
                } else if (item1.isEmpty()) {
                    et_item1.setError("Empty Desc");
                }else if (item1Price.isEmpty()) {
                    et_item1Price.setError("Empty Desc");
                } else if (item2.isEmpty()) {
                    et_item2.setError("Empty Desc");
                }else if (item2Price.isEmpty()) {
                    et_item2Price.setError("Empty Desc");
                }  else if (item3.isEmpty()) {
                    et_item3.setError("Empty Desc");
                }else if (item3Price.isEmpty()) {
                    et_item3Price.setError("Empty Desc");
                }  else if (item4.isEmpty()) {
                    et_item4.setError("Empty Desc");
                }else if (item4Price.isEmpty()) {
                    et_item4Price.setError("Empty Desc");
                }  else if (item5.isEmpty()) {
                    et_item5.setError("Empty Desc");
                }else if (item5Price.isEmpty()) {
                    et_item5Price.setError("Empty Desc");
                }  else if (item6.isEmpty()) {
                    et_item6.setError("Empty Desc");
                }else if (item6Price.isEmpty()) {
                    et_item6Price.setError("Empty Desc");
                }  else if (item7.isEmpty()) {
                    et_item7.setError("Empty Desc");
                }else if (item7Price.isEmpty()) {
                    et_item7Price.setError("Empty Desc");
                } else {
                    progressDialog.setMessage("Adding Your Food");
                    progressDialog.setTitle("Adding...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    createFood2(lunchboxName, lunchboxDesc, item1, item1Price, item2, item2Price, item3, item3Price
                            , item4, item4Price, item5, item5Price, item6, item6Price, item7, item7Price
                            , item8, item8Price, restName, lunchboxImage);
                }
            }
        });

        //window or screen resolu
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.9), (int)(height*.9));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

    }

    private void createFood2(String lunchboxName, String lunchboxDesc, String item1, String item1Price,
                             String item2, String item2Price, String item3, String item3Price, String item4,
                             String item4Price, String item5, String item5Price, String item6, String item6Price,
                             String item7, String item7Price, String item8, String item8Price, String restName,
                             String lunchboxImage) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(RestaurantRegister.RESTAURANT_FOOD).child(restName).child("lunchbox");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lunchboxName", lunchboxName);
        hashMap.put("lunchboxDesc", lunchboxDesc);
        hashMap.put("item1", item1);
        hashMap.put("item1Price", item1Price);
        hashMap.put("item2", item2);
        hashMap.put("item2Price", item2Price);
        hashMap.put("item3", item3);
        hashMap.put("item3Price", item3Price);
        hashMap.put("item4", item4);
        hashMap.put("item4Price", item4Price);
        hashMap.put("item5", item5);
        hashMap.put("item5Price", item5Price);
        hashMap.put("item6", item6);
        hashMap.put("item6Price", item6Price);
        hashMap.put("item7", item7);
        hashMap.put("item7Price", item7Price);
        hashMap.put("item8", item8);
        hashMap.put("item8Price", item8Price);
        hashMap.put("lunchboxImage", lunchboxImage);
        hashMap.put("id", userId);
        reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(RestaurantLunchboxCreate.this, "Foood Added Successfully", Toast.LENGTH_SHORT).show();
                    imageString = "";
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("RestaurantsUserWithlb").child(userId);
                    HashMap<String, String> hashMap2 = new HashMap<>();
                    hashMap2.put("id", userId);
                    hashMap2.put("username", restName);
                    hashMap2.put("imageUrl", restImage);
                    hashMap2.put("search", restName.toLowerCase());
                    reference2.setValue(hashMap2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(RestaurantLunchboxCreate.this, RestaurantLunchboxDashboard.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(RestaurantLunchboxCreate.this, "Food Created Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = RestaurantLunchboxCreate.this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(RestaurantLunchboxCreate.this);
        pd.setMessage("Uploading...");
        pd.show();
        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        try {
                            Uri downloadingUri = task.getResult();
                            Log.d("TAG", "onComplete: uri completed");
                            String mUri = downloadingUri.toString();
                            imageString = mUri;
                            Glide.with(RestaurantLunchboxCreate.this).load(imageUri).into(iv_lunchboxImage);
                        } catch (Exception e) {
                            Log.d("TAG1", "error Message: " + e.getMessage());
                            Toast.makeText(RestaurantLunchboxCreate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();
                    } else {
                        Toast.makeText(RestaurantLunchboxCreate.this, "Failed here", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RestaurantLunchboxCreate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(RestaurantLunchboxCreate.this, "No image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(RestaurantLunchboxCreate.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }

    public void checkPermission(String permission, int requestCode) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(RestaurantLunchboxCreate.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(RestaurantLunchboxCreate.this, new String[]{permission}, requestCode);
        } else {
            openImage();
            Toast.makeText(RestaurantLunchboxCreate.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImage();
                Toast.makeText(RestaurantLunchboxCreate.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RestaurantLunchboxCreate.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}