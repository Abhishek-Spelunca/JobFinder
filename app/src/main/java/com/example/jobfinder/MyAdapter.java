package com.example.jobfinder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private List<DataClass> datalist;

    public MyAdapter(Context context, List<DataClass> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(datalist.get(position).getDataImage()).into(holder.recImage);
        holder.recTitle.setText(datalist.get(position).getDataTitle());
        holder.recCompany.setText(datalist.get(position).getDataCompany());
        holder.recType.setText(datalist.get(position).getDataType());
        holder.recDesc.setText(datalist.get(position).getDataDesc());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("Image",datalist.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Company",datalist.get(holder.getAdapterPosition()).getDataCompany());
                intent.putExtra("Title",datalist.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Type",datalist.get(holder.getAdapterPosition()).getDataType());
                intent.putExtra("Description",datalist.get(holder.getAdapterPosition()).getDataDesc());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList){
        datalist=searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTitle,recCompany,recType,recDesc;
    CardView recCard;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage=itemView.findViewById(R.id.recImage);
        recTitle=itemView.findViewById(R.id.recTitle);
        recType=itemView.findViewById(R.id.recType);
        recDesc=itemView.findViewById(R.id.recDesc);
        recCompany=itemView.findViewById(R.id.recCompany);
        recCard=itemView.findViewById(R.id.recCard);
    }
}
