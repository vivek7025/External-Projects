package com.example.foodbox.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CustomerDashboard extends AppCompatActivity {

    private long pressedTime;

    private RecyclerView rv_showAllRestaurant;
    private CustomerShowRestaurantAdapter mAdapter;
    private List<RestaurantModel> mList = new ArrayList<>();

    private ImageView iv_cart;
    private ImageView iv_showOrders;

    private String userName = "";
    private String longitude = "";
    private String latitude = "";
    RelativeLayout rl_savedLunchbox;
    RelativeLayout rl_lunchboxRest;

    ImageView iv_logout;
    String usId = "";

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        rl_savedLunchbox = findViewById(R.id.rl_savedLunchbox);
        rl_lunchboxRest = findViewById(R.id.rl_lunchboxRest);

        rv_showAllRestaurant = findViewById(R.id.rv_showAllRestaurant);
        rv_showAllRestaurant.setLayoutManager(new LinearLayoutManager(CustomerDashboard.this));
        rv_showAllRestaurant.setHasFixedSize(true);

        iv_cart = findViewById(R.id.iv_cart);
        iv_showOrders = findViewById(R.id.iv_showOrders);

        iv_logout = findViewById(R.id.iv_logout);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        rl_lunchboxRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboard.this, CustomerLunchboxDashboard.class);
                startActivity(intent);
            }
        });

        rl_savedLunchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboard.this, CustomerSavedLunchBox.class);
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CustomerDashboard.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                CustomerDashboard.this.finish();
            }
        });

        iv_showOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDashboard.this, CustomerShowOrders.class);
                startActivity(intent);
            }
        });

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerDashboard.this, CustomerCart.class));
            }
        });


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
        getAllRestaurant();
    }

    private void getAllRestaurant() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getUid() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(RestaurantRegister.RESTAURANT_USERS);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        RestaurantModel restaurantModel = dataSnapshot.getValue(RestaurantModel.class);
                        mList.add(restaurantModel);
                    }
                    mAdapter = new CustomerShowRestaurantAdapter(CustomerDashboard.this, mList, userName,"normal");
                    rv_showAllRestaurant.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}