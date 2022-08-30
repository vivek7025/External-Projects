package com.example.foodbox.restaurant;

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
import com.example.foodbox.adapter.RestaurantShowFoodAdapter;
import com.example.foodbox.adapter.RestaurantShowLunchboxAdapter;
import com.example.foodbox.model.RestaurantFood;
import com.example.foodbox.model.RestaurantModel;
import com.example.foodbox.model.lunchboxmodel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RestaurantLunchboxDashboard extends AppCompatActivity {
    private FloatingActionButton fab_createLunch;
    private String restName = "";
    private List<lunchboxmodel> mList = new ArrayList<>();
    private RestaurantShowLunchboxAdapter adapter;
    private RecyclerView rv_showAlllunchbox;

    ImageView iv_showOrders;
    ImageView iv_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_lunchbox_dashboard);

        rv_showAlllunchbox = findViewById(R.id.rv_showAlllunchbox);
        rv_showAlllunchbox.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RestaurantLunchboxDashboard.this);
        rv_showAlllunchbox.setLayoutManager(linearLayoutManager);

        iv_showOrders = findViewById(R.id.iv_showOrders);
        iv_logout = findViewById(R.id.iv_logout);

        fab_createLunch = findViewById(R.id.fab_createLunch);

        fab_createLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                createDialog2(restName);
                Intent intent = new Intent(RestaurantLunchboxDashboard.this, RestaurantLunchboxCreate.class);
                startActivity(intent);
            }

        });

        iv_showOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantLunchboxDashboard.this, RestaurantShowOrders.class);
                intent.putExtra("restName",restName);
                startActivity(intent);
            }
        });

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(RestaurantLunchboxDashboard.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                RestaurantLunchboxDashboard.this.finish();
            }
        });

        // getting restaurant name from firebase
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(RestaurantRegister.RESTAURANT_USERS).child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RestaurantModel user = snapshot.getValue(RestaurantModel.class);
                restName = user.getUsername();
                getAllFood(restName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getAllFood(String restName) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getUid() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(RestaurantRegister.RESTAURANT_FOOD).child(restName).child("lunchbox");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        lunchboxmodel lunchboxmodel = dataSnapshot.getValue(lunchboxmodel.class);
                        mList.add(lunchboxmodel);
                    }
                    adapter = new RestaurantShowLunchboxAdapter(RestaurantLunchboxDashboard.this, mList, restName);
                    rv_showAlllunchbox.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
}