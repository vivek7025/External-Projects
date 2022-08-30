package com.example.foodbox.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.foodbox.MainActivity;
import com.example.foodbox.R;
import com.example.foodbox.adapter.CustomerShowRestaurantAdapter;
import com.example.foodbox.model.CustomerLocationModel;
import com.example.foodbox.model.RestaurantModel;
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

public class CustomerLunchboxDashboard extends AppCompatActivity {

    private RecyclerView rv_showAllSavedLunchbox;
    private List<RestaurantModel> mList = new ArrayList<>();
    private CustomerShowRestaurantAdapter mAdapter;
    private String userName = "";
    String usId = "";
    private String longitude = "";
    private String latitude = "";

    private ImageView iv_cart;
    private ImageView iv_showOrders;
    ImageView iv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_lunchbox_dashboard);

        iv_cart = findViewById(R.id.iv_cart);
        iv_showOrders = findViewById(R.id.iv_showOrders);

        iv_logout = findViewById(R.id.iv_logout);

        rv_showAllSavedLunchbox = findViewById(R.id.rv_showAllSavedLunchbox);
        rv_showAllSavedLunchbox.setLayoutManager(new LinearLayoutManager(CustomerLunchboxDashboard.this));
        rv_showAllSavedLunchbox.setHasFixedSize(true);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        usId = firebaseUser.getUid();
        if (firebaseUser.getUid() != null) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("CustomersUser").child(usId).child("username");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.getValue(String.class);
                    userName = name;
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(CustomerRegister.LOCATION_CUSTOMERS).child(userName);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            CustomerLocationModel model = snapshot.getValue(CustomerLocationModel.class);
                            longitude = model.getLongitude();
                            latitude = model.getLatitude();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CustomerLunchboxDashboard.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                CustomerLunchboxDashboard.this.finish();
            }
        });

        iv_showOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerLunchboxDashboard.this, CustomerShowOrders.class);
                startActivity(intent);
            }
        });

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLunchboxDashboard.this, CustomerCart.class));
            }
        });

        getAllRestaurant();
    }

    private void getAllRestaurant() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getUid() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("RestaurantsUserWithlb");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        RestaurantModel restaurantModel = dataSnapshot.getValue(RestaurantModel.class);
                        mList.add(restaurantModel);
                    }
                    mAdapter = new CustomerShowRestaurantAdapter(CustomerLunchboxDashboard.this, mList, userName,"lunchbox");
                    rv_showAllSavedLunchbox.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}