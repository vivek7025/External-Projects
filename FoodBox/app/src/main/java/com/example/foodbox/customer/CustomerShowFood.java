package com.example.foodbox.customer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.foodbox.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CustomerShowFood extends AppCompatActivity {

    ImageView iv_foodImage;
    TextView tv_foodName;
    TextView tv_foodPrice;
    TextView tv_foodDesc;
    TextView count;
    int countInt = 1,tot =0;
    String pattern = "\bRs";

    private ProgressDialog progressDialog;

    Button btn_addToCart;
    ImageView iv_cart,ivAdd,ivSub;
    Intent intent;
    String restName = "";
    String foodImage = "";
    String foodPrice = "";
    String foodDesc = "";
    String foodName = "";
    String userName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_show_food);

        iv_foodImage = findViewById(R.id.iv_lunchboxImage);
        tv_foodName = findViewById(R.id.tv_foodName);
        tv_foodPrice = findViewById(R.id.tv_foodPrice);
        tv_foodDesc = findViewById(R.id.tv_foodDesc);
        btn_addToCart = findViewById(R.id.btn_addToCart);
        iv_cart = findViewById(R.id.iv_cart);
        ivAdd = findViewById(R.id.ivAdd);
        ivSub = findViewById(R.id.ivSub);
        count = findViewById(R.id.count);

        intent = getIntent();
        restName = intent.getStringExtra("restName");
        foodImage = intent.getStringExtra("foodImage");
        foodPrice = intent.getStringExtra("foodPrice");
        foodDesc = intent.getStringExtra("foodDesc");
        foodName = intent.getStringExtra("foodName");
        userName = intent.getStringExtra("userName");

        progressDialog = new ProgressDialog(CustomerShowFood.this);

        setUi(foodImage, foodPrice, foodDesc, foodName);

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuantity();
            }
        });

        ivSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subQuantity();
            }
        });

        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Adding Your Food");
                progressDialog.setTitle("Adding...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                addToCartFirebase();
            }
        });

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerShowFood.this, CustomerCart.class));
            }
        });

    }

    private void subQuantity() {
        int price;
        countInt--;
        String countStr = String.valueOf(countInt);
        count.setText(countStr);
        String words[] = foodPrice.split(pattern);
        String prs = words[0];
        price = Integer.parseInt(prs);
        tot = price*countInt;
        String newPrice = "Price : Rs " +String.valueOf(tot);
        tv_foodPrice.setText(newPrice);
    }

    private void addQuantity() {
        int price;
        countInt++;
        String countStr = String.valueOf(countInt);
        count.setText(countStr);
        String words[] = foodPrice.split(pattern);
        String prs = words[0];
        price = Integer.parseInt(prs);
        tot = price*countInt;
        String newPrice = "Price : Rs " +String.valueOf(tot);
        tv_foodPrice.setText(newPrice);
    }

    private void setUi(String foodImage, String foodPrice, String foodDesc, String foodName) {
        tv_foodName.setText(foodName);
        tv_foodPrice.setText("Price : Rs " +foodPrice);
        tv_foodDesc.setText(foodDesc);
        Glide.with(CustomerShowFood.this).load(foodImage).into(iv_foodImage);
    }

    private void addToCartFirebase() {
        if((String.valueOf(tot)).equalsIgnoreCase("0")){

        }else{
            foodPrice = String.valueOf(tot);
        }
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(CustomerRegister.CART).child(userId);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("foodName", foodName);
        hashMap.put("foodDesc", foodDesc);
        hashMap.put("foodPrice", foodPrice);
        hashMap.put("foodImage", foodImage);
        hashMap.put("restName", restName);
        hashMap.put("userName", userName);
        hashMap.put("id", userId);
        reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(CustomerShowFood.this, "Foood Added To Cart Successfully " + userName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerShowFood.this, "Food Adding Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}