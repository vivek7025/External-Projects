package com.example.foodbox.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodbox.R;
import com.example.foodbox.model.RestaurantModel;
import com.example.foodbox.model.lunchboxmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantLunchoxView extends AppCompatActivity {
    Intent intent;
    String restName = "";
    String lunchboxid = "";
    String lunchboxDesc = "";
    String lunchboxName = "";
    String userName = "";

    ImageView iv_restImage;
    TextView tv_lunchboxname;
    TextView tv_item1;
    TextView tv_item1Price;
    TextView tv_item2;
    TextView tv_item2Price;
    TextView tv_item3;
    TextView tv_item3Price;
    TextView tv_item4;
    TextView tv_item4Price;
    TextView tv_item5;
    TextView tv_item5Price;
    TextView tv_item6;
    TextView tv_item6Price;
    TextView tv_item7;
    TextView tv_item7Price;
    TextView tv_item8;
    TextView tv_item8Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_lunchox_view);


        intent = getIntent();
        String lunchboxImage = intent.getStringExtra("lunchboxImage");
        String lunchboxName = intent.getStringExtra("lunchboxName");
        String item1 = intent.getStringExtra("item1");
        String item1Price = intent.getStringExtra("item1Price");
        String item2 = intent.getStringExtra("item2");
        String item2Price = intent.getStringExtra("item2Price");
        String item3 = intent.getStringExtra("item3");
        String item3Price = intent.getStringExtra("item3Price");
        String item4 = intent.getStringExtra("item4");
        String item4Price = intent.getStringExtra("item4Price");
        String item5 = intent.getStringExtra("item5");
        String item5Price = intent.getStringExtra("item5Price");
        String item6 = intent.getStringExtra("item6");
        String item6Price = intent.getStringExtra("item6Price");
        String item7 = intent.getStringExtra("item7");
        String item7Price = intent.getStringExtra("item7Price");
        String item8 = intent.getStringExtra("item8");
        String item8Price = intent.getStringExtra("item8Price");


        iv_restImage = findViewById(R.id.iv_restImage);
        tv_lunchboxname = findViewById(R.id.tv_lunchboxname);
        tv_item1 = findViewById(R.id.item1);
        tv_item1Price = findViewById(R.id.item1Price);
        tv_item2 = findViewById(R.id.item2);
        tv_item2Price = findViewById(R.id.item2Price);
        tv_item3 = findViewById(R.id.item3);
        tv_item3Price = findViewById(R.id.item3Price);
        tv_item4 = findViewById(R.id.item4);
        tv_item4Price = findViewById(R.id.item4Price);
        tv_item5 = findViewById(R.id.item5);
        tv_item5Price = findViewById(R.id.item5Price);
        tv_item6 = findViewById(R.id.item6);
        tv_item6Price = findViewById(R.id.item6Price);
        tv_item7 = findViewById(R.id.item7);
        tv_item7Price = findViewById(R.id.item7Price);
        tv_item8 = findViewById(R.id.item8);
        tv_item8Price = findViewById(R.id.item8Price);

        // getting restaurant name from firebase
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        setUi(lunchboxImage,lunchboxName,item1,item1Price,item2,item2Price,item3,item3Price,item4,item4Price,item5,item5Price,item6,item6Price
                ,item7,item7Price,item8,item8Price);
    }

    private void setUi(String lunchboxImage,String lunchboxName, String item1, String item1Price, String item2, String item2Price,
                       String item3, String item3Price, String item4, String item4Price, String item5, String item5Price,
                       String item6, String item6Price, String item7, String item7Price, String item8, String item8Price) {
        if (lunchboxImage.equals("default")) {
            iv_restImage.setImageResource(R.drawable.restaurant);
        } else {
            Glide.with(RestaurantLunchoxView.this).load(lunchboxImage).into(iv_restImage);
        }
        tv_lunchboxname.setText(lunchboxName);
        tv_item1.setText(item1);
        tv_item1Price.setText(item1Price);
        tv_item2.setText(item2);
        tv_item2Price.setText(item2Price);
        tv_item3.setText(item3);
        tv_item3Price.setText(item3Price);
        tv_item4.setText(item4);
        tv_item4Price.setText(item4Price);
        tv_item5.setText(item5);
        tv_item5Price.setText(item5Price);
        tv_item6.setText(item6);
        tv_item6Price.setText(item6Price);
        tv_item7.setText(item7);
        tv_item7Price.setText(item7Price);
        tv_item8.setText(item8);
        tv_item8Price.setText(item8Price);

    }
}