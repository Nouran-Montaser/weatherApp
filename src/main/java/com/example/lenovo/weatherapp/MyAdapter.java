package com.example.lenovo.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lenovo.weatherapp.Pojo.Forecastday;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myHolder> {

    private Context context;
    private ArrayList<Forecastday> details;

    public MyAdapter(Context context, ArrayList<Forecastday> details) {
        this.context = context;
        this.details = details;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new myHolder(view);
    }


    @Override
    public void onBindViewHolder(myHolder holder, int position){
        holder.daytxt.setText(details.get(position).getDate());
        holder.cTxt.setText(details.get(position).getDay().getMaxtemp_c());
        Picasso.get().load("http:"+details.get(position).getDay().getCondition().getIcon()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder{

        TextView daytxt;
        ImageView imageView;
        TextView cTxt;

        public myHolder(View itemView) {
            super(itemView);
            daytxt = itemView.findViewById(R.id.item_day_txt);
            imageView = itemView.findViewById(R.id.item_img);
            cTxt = itemView.findViewById(R.id.item_cls_txt);
        }
    }
}
