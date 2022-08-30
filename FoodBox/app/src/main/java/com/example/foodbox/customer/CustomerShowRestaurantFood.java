package com.example.foodbox.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodbox.R;
import com.example.foodbox.adapter.CustomerShowFoodAdapter;
import com.example.foodbox.adapter.CustomerShowLunchboxAdapter;
import com.example.foodbox.model.RestaurantFood;
import com.example.foodbox.model.lunchboxmodel;
import com.example.foodbox.restaurant.RestaurantRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerShowRestaurantFood extends AppCompatActivity {

    private RecyclerView rv_showAllFood;
    private CustomerShowFoodAdapter adapter;
    private CustomerShowLunchboxAdapter adapter2;
    private List<RestaurantFood> mList = new ArrayList<>();
    private List<lunchboxmodel> mlunchboxList = new ArrayList<>();

    private String restName = "";
    private String userName = "";
    private String restType = "";

    ImageView iv_cart;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_show_restaurant_food);

        rv_showAllFood = findViewById(R.id.rv_showAlllunchbox);
        rv_showAllFood.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CustomerShowRestaurantFood.this);
        rv_showAllFood.setLayoutManager(linearLayoutManager);

        iv_cart = findViewById(R.id.iv_cart);

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerShowRestaurantFood.this, CustomerCart.class));
            }
        });

        intent = getIntent();
        restType = intent.getStringExtra("restType");
        restName = intent.getStringExtra("restName");
        userName = intent.getStringExtra("userName");

        // getting restaurant food from firebase
        getAllFood(restName,restType);

    }

    private void getAllFood(String restName, String restType) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getUid() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(RestaurantRegister.RESTAURANT_FOOD).child(restName).child(restType);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mList.clear();
                    mlunchboxList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if(restType.equalsIgnoreCase("normal")){
                            RestaurantFood restaurantFood = dataSnapshot.getValue(RestaurantFood.class);
                            mList.add(restaurantFood);
                        }else if(restType.equalsIgnoreCase("lunchbox")){
                            lunchboxmodel lunchboxmodel = dataSnapshot.getValue(lunchboxmodel.class);
                            mlunchboxList.add(lunchboxmodel);
                        }
                    }
                    if(restType.equalsIgnoreCase("normal")){
                        adapter = new CustomerShowFoodAdapter(CustomerShowRestaurantFood.this, mList, restName,userName);
                        rv_showAllFood.setAdapter(adapter);
                    }else if(restType.equalsIgnoreCase("lunchbox")){
                        adapter2 = new CustomerShowLunchboxAdapter(CustomerShowRestaurantFood.this, mlunchboxList,restName, userName);
                        rv_showAllFood.setAdapter(adapter2);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }

}