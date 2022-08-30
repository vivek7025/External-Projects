package com.example.foodbox.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodbox.R;
import com.example.foodbox.restaurant.RestaurantLunchboxCreate;
import com.example.foodbox.restaurant.RestaurantLunchboxDashboard;
import com.example.foodbox.restaurant.RestaurantRegister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CustomerShowLunchbox extends AppCompatActivity {

    TextView et_lunchboxName,et_lunchboxDesc;
    TextView item1,item1Price,item1Count,item1Amount;
    ImageView sub1,add1;
    TextView item2,item2Price,item2Count,item2Amount;
    ImageView sub2,add2;
    TextView item3,item3Price,item3Count,item3Amount;
    ImageView sub3,add3;
    TextView item4,item4Price,item4Count,item4Amount;
    ImageView sub4,add4;
    TextView item5,item5Price,item5Count,item5Amount;
    ImageView sub5,add5;
    TextView item6,item6Price,item6Count,item6Amount;
    ImageView sub6,add6;
    TextView item7,item7Price,item7Count,item7Amount;
    ImageView sub7,add7;
    TextView item8,item8Price,item8Count,item8Amount;
    ImageView sub8,add8;
    String totamount ="";
    String pattern = "/";
    String[] itemTotAmount;
    ImageView iv_lunchboxImage;

    Button btn_addToCartLunchbox,btn_saveLunchbox;
    private ProgressDialog progressDialog;

    Intent intent;
    String lunchboxId ="";
    String lunchboxName = "";
    String restName = "";
    String userName = "";
    String lunchboxImage ="";
    String lunchboxDesc ="";
    String s_item1 = "";
    String s_item1Price = "";
    String s_item1Count = "";
    String s_item1Amount = "";
    String s_item2 = "";
    String s_item2Price = "";
    String s_item2Count = "";
    String s_item2Amount = "";
    String s_item3 = "";
    String s_item3Price = "";
    String s_item3Count = "";
    String s_item3Amount = "";
    String s_item4 = "";
    String s_item4Price = "";
    String s_item4Count = "";
    String s_item4Amount = "";
    String s_item5 = "";
    String s_item5Price = "";
    String s_item5Count = "";
    String s_item5Amount = "";
    String s_item6 = "";
    String s_item6Price = "";
    String s_item6Count = "";
    String s_item6Amount = "";
    String s_item7 = "";
    String s_item7Price = "";
    String s_item7Count = "";
    String s_item7Amount = "";
    String s_item8 = "";
    String s_item8Price = "";
    String s_item8Count = "";
    String s_item8Amount = "";
    String foodPrice ="";

    ImageView iv_cart;


    TextView lunchboxTotAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_show_lunchbox);

        et_lunchboxName = findViewById(R.id.et_lunchboxName);
        et_lunchboxDesc = findViewById(R.id.et_lunchboxDesc);
        lunchboxTotAmount = findViewById(R.id.lunchboxTotAmount);
        iv_lunchboxImage = findViewById(R.id.iv_lunchboxImage);

        iv_cart = findViewById(R.id.iv_cart);

        btn_addToCartLunchbox = findViewById(R.id.btn_addToCartLunchbox);
        btn_saveLunchbox = findViewById(R.id.btn_saveLunchbox);

        progressDialog = new ProgressDialog(CustomerShowLunchbox.this);


        item1 = findViewById(R.id.item1);
        item1Price = findViewById(R.id.item1Price);
        item1Count = findViewById(R.id.item1Count);
        item1Amount = findViewById(R.id.item1Amount);
        sub1 = findViewById(R.id.sub1);
        add1 = findViewById(R.id.add1);
        item2 = findViewById(R.id.item2);
        item2Price = findViewById(R.id.item2Price);
        item2Count = findViewById(R.id.item2Count);
        item2Amount = findViewById(R.id.item2Amount);
        sub2 = findViewById(R.id.sub2);
        add2 = findViewById(R.id.add2);
        item3 = findViewById(R.id.item3);
        item3Price = findViewById(R.id.item3Price);
        item3Count = findViewById(R.id.item3Count);
        item3Amount = findViewById(R.id.item3Amount);
        sub3 = findViewById(R.id.sub3);
        add3 = findViewById(R.id.add3);
        item4 = findViewById(R.id.item4);
        item4Price = findViewById(R.id.item4Price);
        item4Count = findViewById(R.id.item4Count);
        item4Amount = findViewById(R.id.item4Amount);
        sub4 = findViewById(R.id.sub4);
        add4 = findViewById(R.id.add4);
        item5 = findViewById(R.id.item5);
        item5Price = findViewById(R.id.item5Price);
        item5Count = findViewById(R.id.item5Count);
        item5Amount = findViewById(R.id.item5Amount);
        sub5 = findViewById(R.id.sub5);
        add5 = findViewById(R.id.add5);
        item6 = findViewById(R.id.item6);
        item6Price = findViewById(R.id.item6Price);
        item6Count = findViewById(R.id.item6Count);
        item6Amount = findViewById(R.id.item6Amount);
        sub6 = findViewById(R.id.sub6);
        add6 = findViewById(R.id.add6);
        item7 = findViewById(R.id.item7);
        item7Price = findViewById(R.id.item7Price);
        item7Count = findViewById(R.id.item7Count);
        item7Amount = findViewById(R.id.item7Amount);
        sub7 = findViewById(R.id.sub7);
        add7 = findViewById(R.id.add7);
        item8 = findViewById(R.id.item8);
        item8Price = findViewById(R.id.item8Price);
        item8Count = findViewById(R.id.item8Count);
        item8Amount = findViewById(R.id.item8Amount);
        sub8 = findViewById(R.id.sub8);
        add8 = findViewById(R.id.add8);

        intent = getIntent();
        lunchboxId = intent.getStringExtra("lunchboxId");
        lunchboxName = intent.getStringExtra("lunchboxName");
        restName = intent.getStringExtra("restName");
        userName = intent.getStringExtra("userName");
        lunchboxImage = intent.getStringExtra("lunchboxImage");
        lunchboxDesc = intent.getStringExtra("lunchboxDesc");
        s_item1 = intent.getStringExtra("item1");
        s_item1Price = intent.getStringExtra("item1Price");
        s_item1Count = intent.getStringExtra("item1Count");
        s_item1Amount = intent.getStringExtra("item1Amount");
        s_item2 = intent.getStringExtra("item2");
        s_item2Price = intent.getStringExtra("item2Price");
        s_item2Count = intent.getStringExtra("item2Count");
        s_item2Amount = intent.getStringExtra("item2Amount");
        s_item3 = intent.getStringExtra("item3");
        s_item3Price = intent.getStringExtra("item3Price");
        s_item3Count = intent.getStringExtra("item3Count");
        s_item3Amount = intent.getStringExtra("item3Amount");
        s_item4 = intent.getStringExtra("item4");
        s_item4Price = intent.getStringExtra("item4Price");
        s_item4Count = intent.getStringExtra("item4Count");
        s_item4Amount = intent.getStringExtra("item4Amount");
        s_item5 = intent.getStringExtra("item5");
        s_item5Price = intent.getStringExtra("item5Price");
        s_item5Count = intent.getStringExtra("item5Count");
        s_item5Amount = intent.getStringExtra("item5Amount");
        s_item6 = intent.getStringExtra("item6");
        s_item6Price = intent.getStringExtra("item6Price");
        s_item6Count = intent.getStringExtra("item6Count");
        s_item6Amount = intent.getStringExtra("item6Amount");
        s_item7 = intent.getStringExtra("item7");
        s_item7Price = intent.getStringExtra("item7Price");
        s_item7Count = intent.getStringExtra("item7Count");
        s_item7Amount = intent.getStringExtra("item7Amount");
        s_item8 = intent.getStringExtra("item8");
        s_item8Price = intent.getStringExtra("item8Price");
        s_item8Count = intent.getStringExtra("item8Count");
        s_item8Amount = intent.getStringExtra("item8Amount");


        btn_addToCartLunchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Adding Your Food");
                progressDialog.setTitle("Adding...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                addToCartFirebase();
            }
        });

        btn_saveLunchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Adding Your Food");
                progressDialog.setTitle("Adding...");
                progressDialog.setCanceledOnTouchOutside(false);
                createFood2();
            }
        });

        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerShowLunchbox.this, CustomerCart.class));
            }
        });



        setOnclick();

        if(s_item1Amount == null || s_item1Amount.length() == 0){
            itemTotAmount = new String[]{s_item1Price, s_item2Price, s_item3Price, s_item4Price, s_item5Price, s_item6Price
                    , s_item7Price, s_item8Price};
            setUI();
        }else{
            itemTotAmount = new String[]{s_item1Amount, s_item2Amount, s_item3Amount, s_item4Amount, s_item5Amount,
                    s_item6Amount, s_item7Amount, s_item8Amount};
            setUiWithCA();
        }
        tot_amount_calc(itemTotAmount);

    }

    private void createFood2() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("SavedLunchBox").child(userName);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("lunchboxName", lunchboxName);
        hashMap.put("lunchboxDesc", lunchboxDesc);
        hashMap.put("restName", restName);
        hashMap.put("item1", s_item1);
        hashMap.put("item1Price", s_item1Price);
        hashMap.put("item1Count", item1Count.getText().toString());
        hashMap.put("item1Amount", item_amount_extact(item1Amount.getText().toString()));
        hashMap.put("item2", s_item2);
        hashMap.put("item2Price", s_item2Price);
        hashMap.put("item2Count", item2Count.getText().toString());
        hashMap.put("item2Amount", item_amount_extact(item2Amount.getText().toString()));
        hashMap.put("item3", s_item3);
        hashMap.put("item3Price", s_item3Price);
        hashMap.put("item3Count", item3Count.getText().toString());
        hashMap.put("item3Amount", item_amount_extact(item3Amount.getText().toString()));
        hashMap.put("item4", s_item4);
        hashMap.put("item4Price", s_item4Price);
        hashMap.put("item4Count", item4Count.getText().toString());
        hashMap.put("item4Amount", item_amount_extact(item4Amount.getText().toString()));
        hashMap.put("item5", s_item5);
        hashMap.put("item5Price", s_item5Price);
        hashMap.put("item5Count", item5Count.getText().toString());
        hashMap.put("item5Amount", item_amount_extact(item5Amount.getText().toString()));
        hashMap.put("item6", s_item6);
        hashMap.put("item6Price", s_item6Price);
        hashMap.put("item6Count", item6Count.getText().toString());
        hashMap.put("item6Amount", item_amount_extact(item6Amount.getText().toString()));
        hashMap.put("item7", s_item7);
        hashMap.put("item7Price", s_item7Price);
        hashMap.put("item7Count", item7Count.getText().toString());
        hashMap.put("item7Amount", item_amount_extact(item7Amount.getText().toString()));
        hashMap.put("item8", s_item8);
        hashMap.put("item8Price", s_item8Price);
        hashMap.put("item8Count", item8Count.getText().toString());
        hashMap.put("item8Amount", item_amount_extact(item8Amount.getText().toString()));
        hashMap.put("lunchboxImage", lunchboxImage);
        hashMap.put("id", userId);
        reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(CustomerShowLunchbox.this, "Foood Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerShowLunchbox.this, "Food Created Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addToCartFirebase() {
        String total = lunchboxTotAmount.getText().toString();
        if((String.valueOf(total)).equalsIgnoreCase("0")){

        }else{
            foodPrice = String.valueOf(total);
        }
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(CustomerRegister.CART).child(userId);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("foodName", lunchboxName);
        hashMap.put("foodDesc", lunchboxDesc);
        hashMap.put("foodPrice", foodPrice);
        hashMap.put("foodImage", lunchboxImage);
        hashMap.put("restName", restName);
        hashMap.put("userName", userName);
        hashMap.put("id", userId);
        reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(CustomerShowLunchbox.this, "Foood Added To Cart Successfully " + userName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CustomerShowLunchbox.this, "Food Adding Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setOnclick() {
        sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = subQuantity(s_item1Price,(item1Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item1Count.setText(rt_value[0]);
                item1Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = addQuantity(s_item1Price,(item1Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item1Count.setText(rt_value[0]);
                item1Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });

        sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = subQuantity(s_item2Price,(item2Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item2Count.setText(rt_value[0]);
                item2Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = addQuantity(s_item2Price,(item2Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item2Count.setText(rt_value[0]);
                item2Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });

        sub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = subQuantity(s_item3Price,(item3Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item3Count.setText(rt_value[0]);
                item3Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = addQuantity(s_item3Price,(item3Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item3Count.setText(rt_value[0]);
                item3Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });

        sub4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = subQuantity(s_item4Price,(item4Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item4Count.setText(rt_value[0]);
                item4Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
        add4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = addQuantity(s_item4Price,(item4Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item4Count.setText(rt_value[0]);
                item4Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });

        sub5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = subQuantity(s_item5Price,(item5Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item5Count.setText(rt_value[0]);
                item5Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
        add5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = addQuantity(s_item5Price,(item5Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item5Count.setText(rt_value[0]);
                item5Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
        sub6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = subQuantity(s_item6Price,(item6Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item6Count.setText(rt_value[0]);
                item6Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
        add6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = addQuantity(s_item6Price,(item6Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item6Count.setText(rt_value[0]);
                item6Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });

        sub7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = subQuantity(s_item7Price,(item7Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item7Count.setText(rt_value[0]);
                item7Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
        add7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = addQuantity(s_item7Price,(item7Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item7Count.setText(rt_value[0]);
                item7Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });

        sub8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = subQuantity(s_item8Price,(item8Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item8Count.setText(rt_value[0]);
                item8Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
        add8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rt_value [] = addQuantity(s_item8Price,(item8Count.getText().toString()),(lunchboxTotAmount.getText().toString()));
                item8Count.setText(rt_value[0]);
                item8Amount.setText(rt_value[1]);
                lunchboxTotAmount.setText(rt_value[2]);
            }
        });
    }

    private String[] addQuantity(String item_price,String item_count,String tot_amount) {
        String rt_value [] = new String[3];
        int count = Integer.parseInt(item_count);
        int t_amount = Integer.parseInt(tot_amount);
        int price,tot = 0;

        count++;
        price = Integer.parseInt(item_amount_extact(item_price));
        tot = price*count;

        t_amount = t_amount + price;


        rt_value[0] = String.valueOf(count);
        rt_value[1] = String.valueOf(tot);
        rt_value[2] =String.valueOf(t_amount);
        return rt_value;

    }

    private String[] subQuantity(String item_price,String item_count,String tot_amount) {
        String rt_value [] = new String[3];
        int count = Integer.parseInt(item_count);
        int t_amount = Integer.parseInt(tot_amount);
        int price,tot = 0;

        if(item_count.equalsIgnoreCase("0")){
            Toast.makeText(this, "minim val", Toast.LENGTH_SHORT).show();
        }else{
            count--;
            price = Integer.parseInt(item_amount_extact(item_price));
            tot = price*count;

            if(tot == 0){
                t_amount = t_amount - price;
            }else{
                t_amount = t_amount - tot;
            }
        }

        rt_value[0] = String.valueOf(count);
        rt_value[1] = String.valueOf(tot);
        rt_value[2] =String.valueOf(t_amount);
        return rt_value;
    }

    private void setUI() {
        et_lunchboxName.setText(lunchboxName);
        et_lunchboxDesc.setText(lunchboxDesc);
        if (lunchboxImage.equals("default")) {
            iv_lunchboxImage.setImageResource(R.drawable.restaurant);
        } else {
            Glide.with(CustomerShowLunchbox.this).load(lunchboxImage).into(iv_lunchboxImage);
        }


        item1.setText(s_item1);
        item1Price.setText(s_item1Price);
        item1Amount.setText(item_amount_extact(s_item1Price));
        item2.setText(s_item2);
        item2Price.setText(s_item2Price);
        item2Amount.setText(item_amount_extact(s_item2Price));
        item3.setText(s_item3);
        item3Price.setText(s_item3Price);
        item3Amount.setText(item_amount_extact(s_item3Price));
        item4.setText(s_item4);
        item4Price.setText(s_item4Price);
        item4Amount.setText(item_amount_extact(s_item4Price));
        item5.setText(s_item5);
        item5Price.setText(s_item5Price);
        item5Amount.setText(item_amount_extact(s_item5Price));
        item6.setText(s_item6);
        item6Price.setText(s_item6Price);
        item6Amount.setText(item_amount_extact(s_item6Price));
        item7.setText(s_item7);
        item7Price.setText(s_item7Price);
        item7Amount.setText(item_amount_extact(s_item7Price));
        item8.setText(s_item8);
        item8Price.setText(s_item8Price);
        item8Amount.setText(item_amount_extact(s_item8Price));
    }

    private void setUiWithCA() {
        et_lunchboxName.setText(lunchboxName);
        et_lunchboxDesc.setText(lunchboxDesc);
        if (lunchboxImage.equals("default")) {
            iv_lunchboxImage.setImageResource(R.drawable.restaurant);
        } else {
            Glide.with(CustomerShowLunchbox.this).load(lunchboxImage).into(iv_lunchboxImage);
        }


        item1.setText(s_item1);
        item1Price.setText(s_item1Price);
        item1Amount.setText(s_item1Amount);
        item1Count.setText(s_item1Count);
        item2.setText(s_item2);
        item2Price.setText(s_item2Price);
        item2Amount.setText(s_item2Amount);
        item2Count.setText(s_item2Count);
        item3.setText(s_item3);
        item3Price.setText(s_item3Price);
        item3Amount.setText(s_item3Amount);
        item3Count.setText(s_item3Count);
        item4.setText(s_item4);
        item4Price.setText(s_item4Price);
        item4Amount.setText(s_item4Amount);
        item4Count.setText(s_item4Count);
        item5.setText(s_item5);
        item5Price.setText(s_item5Price);
        item5Amount.setText(s_item5Amount);
        item5Count.setText(s_item5Count);
        item6.setText(s_item6);
        item6Price.setText(s_item6Price);
        item6Amount.setText(s_item6Amount);
        item6Count.setText(s_item6Count);
        item7.setText(s_item7);
        item7Price.setText(s_item7Price);
        item7Amount.setText(s_item7Amount);
        item7Count.setText(s_item7Count);
        item8.setText(s_item8);
        item8Price.setText(s_item8Price);
        item8Amount.setText(s_item8Amount);
        item8Count.setText(s_item8Count);
    }

    private String item_amount_extact(String item_price){
        String words[] = item_price.split(pattern);
        String prs = words[0];
        prs = prs.replaceAll("[^\\d]", " ");
        prs = prs.trim();
        prs = prs.replaceAll(" +", " ");
        return prs;
    }

    private void tot_amount_calc(String[] itemTotAmount){
        int amt_calc = 0;
        for(int i = 0; i<itemTotAmount.length;i++ )
        {
            amt_calc = amt_calc + Integer.parseInt(item_amount_extact(itemTotAmount[i]));
        }
        lunchboxTotAmount.setText(String.valueOf(amt_calc));
    }


}