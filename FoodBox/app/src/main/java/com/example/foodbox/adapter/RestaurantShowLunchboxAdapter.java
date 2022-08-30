package com.example.foodbox.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodbox.R;
import com.example.foodbox.customer.CustomerShowRestaurantFood;
import com.example.foodbox.model.lunchboxmodel;
import com.example.foodbox.restaurant.RestaurantLunchoxView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantShowLunchboxAdapter extends RecyclerView.Adapter<RestaurantShowLunchboxAdapter.viewHolder> {

    private Context mContext;
    private String userName;
    private String lunchboxid;
    private List<lunchboxmodel> mlunchboxList = new ArrayList<>();

    public RestaurantShowLunchboxAdapter(Context mContext, List<lunchboxmodel> mlunchboxList,String userName) {
        this.mContext = mContext;
        this.mlunchboxList = mlunchboxList;
        this.userName = userName;
    }

    @NonNull
    @Override
    public RestaurantShowLunchboxAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_lunchbox, parent, false);
        return new RestaurantShowLunchboxAdapter.viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantShowLunchboxAdapter.viewHolder holder, int position) {
        lunchboxmodel model = mlunchboxList.get(position);
        holder.tv_lunchboxname.setText(model.getlunchboxName());
        holder.item1.setText(model.getItem1());
        holder.item2.setText(model.getItem2());
        holder.item3.setText(model.getItem3());
        holder.item4.setText(model.getItem4());
        holder.item5.setText(model.getItem5());
        holder.item6.setText(model.getItem6());
        holder.item7.setText(model.getItem7());
        holder.item8.setText(model.getItem8());


        if (model.getlunchboxImage().equals("default")) {
            holder.iv_restImage.setImageResource(R.drawable.restaurant);
        } else {
            Glide.with(mContext).load(model.getlunchboxImage()).into(holder.iv_restImage);
        }

        holder.ll_lunchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RestaurantLunchoxView.class);
                intent.putExtra("lunchboxImage", model.getlunchboxImage());
                intent.putExtra("lunchboxName", model.getlunchboxName());
                intent.putExtra("item1", model.getItem1());
                intent.putExtra("item1Price", model.getItem1Price());
                intent.putExtra("item2", model.getItem2());
                intent.putExtra("item2Price", model.getItem2Price());
                intent.putExtra("item3", model.getItem3());
                intent.putExtra("item3Price", model.getItem3Price());
                intent.putExtra("item4", model.getItem4());
                intent.putExtra("item4Price", model.getItem4Price());
                intent.putExtra("item5", model.getItem5());
                intent.putExtra("item5Price", model.getItem5Price());
                intent.putExtra("item6", model.getItem6());
                intent.putExtra("item6Price", model.getItem6Price());
                intent.putExtra("item7", model.getItem7());
                intent.putExtra("item7Price", model.getItem7Price());
                intent.putExtra("item8", model.getItem8());
                intent.putExtra("item8Price", model.getItem8Price());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlunchboxList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_restImage;
        private TextView tv_lunchboxname;
        private LinearLayout ll_lunchbox;
        private TextView item1;
        private TextView item2;
        private TextView item3;
        private TextView item4;
        private TextView item5;
        private TextView item6;
        private TextView item7;
        private TextView item8;


        public viewHolder(@NonNull View itemView) {
            super(itemView);

            iv_restImage = itemView.findViewById(R.id.iv_restImage);
            tv_lunchboxname = itemView.findViewById(R.id.tv_lunchboxname);
            ll_lunchbox = itemView.findViewById(R.id.ll_lunchbox);
            item1 = itemView.findViewById(R.id.item1);
            item2 = itemView.findViewById(R.id.item2);
            item3 = itemView.findViewById(R.id.item3);
            item4 = itemView.findViewById(R.id.item4);
            item5 = itemView.findViewById(R.id.item5);
            item6 = itemView.findViewById(R.id.item6);
            item7 = itemView.findViewById(R.id.item7);
            item8 = itemView.findViewById(R.id.item8);
        }
    }
}
