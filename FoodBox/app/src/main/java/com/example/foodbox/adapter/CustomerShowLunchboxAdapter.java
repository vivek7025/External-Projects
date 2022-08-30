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
import com.example.foodbox.customer.CustomerShowLunchbox;
import com.example.foodbox.customer.CustomerShowRestaurantFood;
import com.example.foodbox.model.lunchboxmodel;

import java.util.ArrayList;
import java.util.List;

public class CustomerShowLunchboxAdapter extends RecyclerView.Adapter<CustomerShowLunchboxAdapter.viewHolder> {

    private Context mContext;
    private String userName;
    private String restName;
    private String rst;
    private List<lunchboxmodel> mlunchboxList = new ArrayList<>();

    public CustomerShowLunchboxAdapter(Context mContext, List<lunchboxmodel> mlunchboxList,String restName, String userName) {
        this.mContext = mContext;
        this.mlunchboxList = mlunchboxList;
        this.userName = userName;
        this.restName = restName;
    }
    public CustomerShowLunchboxAdapter(Context mContext, List<lunchboxmodel> mlunchboxList, String userName) {
        this.mContext = mContext;
        this.mlunchboxList = mlunchboxList;
        this.userName = userName;
    }

    @NonNull
    @Override
    public CustomerShowLunchboxAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_lunchbox, parent, false);
        return new CustomerShowLunchboxAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerShowLunchboxAdapter.viewHolder holder, int position) {
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
                Intent intent = new Intent(mContext, CustomerShowLunchbox.class);
                intent.putExtra("lunchboxId", model.getId());
                intent.putExtra("lunchboxName", model.getlunchboxName());
                if(model.getrestName() == null || model.getrestName().length() == 0){
                    rst = restName;
                }else{
                    rst = model.getrestName();
                }
                intent.putExtra("restName", rst);
                intent.putExtra("userName", userName);
                intent.putExtra("lunchboxImage", model.getlunchboxImage());
                intent.putExtra("lunchboxDesc", model.getlunchboxDesc());
                intent.putExtra("item1", model.getItem1());
                intent.putExtra("item1Price", model.getItem1Price());
                intent.putExtra("item1Count", model.getItem1Count());
                intent.putExtra("item1Amount", model.getItem1Amount());
                intent.putExtra("item2", model.getItem2());
                intent.putExtra("item2Price", model.getItem2Price());
                intent.putExtra("item2Count", model.getItem2Count());
                intent.putExtra("item2Amount", model.getItem2Amount());
                intent.putExtra("item3", model.getItem3());
                intent.putExtra("item3Price", model.getItem3Price());
                intent.putExtra("item3Count", model.getItem3Count());
                intent.putExtra("item3Amount", model.getItem3Amount());
                intent.putExtra("item4", model.getItem4());
                intent.putExtra("item4Price", model.getItem4Price());
                intent.putExtra("item4Count", model.getItem4Count());
                intent.putExtra("item4Amount", model.getItem4Amount());
                intent.putExtra("item5", model.getItem5());
                intent.putExtra("item5Price", model.getItem5Price());
                intent.putExtra("item5Count", model.getItem5Count());
                intent.putExtra("item5Amount", model.getItem5Amount());
                intent.putExtra("item6", model.getItem6());
                intent.putExtra("item6Price", model.getItem6Price());
                intent.putExtra("item6Count", model.getItem6Count());
                intent.putExtra("item6Amount", model.getItem6Amount());
                intent.putExtra("item7", model.getItem7());
                intent.putExtra("item7Price", model.getItem7Price());
                intent.putExtra("item7Count", model.getItem7Count());
                intent.putExtra("item7Amount", model.getItem7Amount());
                intent.putExtra("item8", model.getItem8());
                intent.putExtra("item8Price", model.getItem8Price());
                intent.putExtra("item8Count", model.getItem8Count());
                intent.putExtra("item8Amount", model.getItem8Amount());
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
