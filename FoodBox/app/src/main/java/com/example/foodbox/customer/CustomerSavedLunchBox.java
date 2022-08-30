package com.example.foodbox.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodbox.MainActivity;
import com.example.foodbox.R;
import com.example.foodbox.adapter.CustomerShowLunchboxAdapter;
import com.example.foodbox.model.lunchboxmodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerSavedLunchBox extends AppCompatActivity {
    private ImageView iv_cart;
    private ImageView iv_showOrders;
    ImageView iv_logout;
    private RecyclerView rv_showAllSavedLunchbox;
    private List<lunchboxmodel> mList = new ArrayList<>();
    private CustomerShowLunchboxAdapter mAdapter;
    String usId = "";
    private String userName = "";
    private String restName ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_saved_lunch_box);

        rv_showAllSavedLunchbox = findViewById(R.id.rv_showAllSavedLunchbox);
        rv_showAllSavedLunchbox.setLayoutManager(new LinearLayoutManager(CustomerSavedLunchBox.this));
        rv_showAllSavedLunchbox.setHasFixedSize(true);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        iv_cart = findViewById(R.id.iv_cart);
        iv_showOrders = findViewById(R.id.iv_showOrders);

        iv_logout = findViewById(R.id.iv_logout);

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CustomerSavedLunchBox.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                CustomerSavedLunchBox.this.finish();
            }
        });

        iv_showOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerSavedLunchBox.this, CustomerShowOrders.class);
                startActivity(intent);
            }
        });

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerSavedLunchBox.this, CustomerCart.class));
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
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        getSavedLunchBox();
    }

    private void getSavedLunchBox() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getUid() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("SavedLunchBox").child(userName);
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        lunchboxmodel lunchboxmodel = dataSnapshot.getValue(lunchboxmodel.class);
                        mList.add(lunchboxmodel);
                    }
                    mAdapter = new CustomerShowLunchboxAdapter(CustomerSavedLunchBox.this, mList, userName);
                    rv_showAllSavedLunchbox.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}