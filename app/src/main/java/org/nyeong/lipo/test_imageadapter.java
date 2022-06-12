package org.nyeong.lipo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Model;

import java.util.ArrayList;
import java.util.Objects;

public class test_imageadapter extends RecyclerView.Adapter<test_imageadapter.MyViewHolder> {
    Context context;

    ArrayList<Model> list = new ArrayList<>();

    public test_imageadapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.test_imageview, parent, false);
        return  new MyViewHolder(view);
//        Glide.with(context).load(list.get(position).getImageUri()).into(holder.mImageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getClass()).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.m_image);
        }
    }
}
