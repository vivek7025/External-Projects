package com.example.foodbox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodbox.customer.CustomerDashboard;
import com.example.foodbox.restaurant.RestaurantDashboard;
import com.example.foodbox.rider.RiderDashboard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class splashscreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

//        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                checkUser();
//                finish();
            }
        },SPLASH_TIME_OUT);
    }
    private void checkUser() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String id = String.valueOf(currentUser.getUid());
            db.collection("user")
                    .document(id)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                String user_type = documentSnapshot.getString("user_type");
                                if (user_type.equalsIgnoreCase("customer")) {
                                    Intent intent = new Intent(getApplicationContext(), CustomerDashboard.class);
                                    startActivity(intent);
                                    finish();
                                } else if (user_type.equalsIgnoreCase("restaurant")) {
                                    Intent intent = new Intent(getApplicationContext(), RestaurantDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else if (user_type.equalsIgnoreCase("rider")) {
                                    Intent intent = new Intent(getApplicationContext(), RiderDashboard.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    });
        }
        else{
            Intent homeIntent = new Intent(splashscreen.this,MainActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }
}