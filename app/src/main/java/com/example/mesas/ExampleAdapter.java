package com.example.mesas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter <ExampleAdapter.ExampleHolder>{
    private ArrayList<Item> exampleList;

    public static class ExampleHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public ExampleHolder(@NonNull View itemView) {
            super(itemView);
            mImageView= itemView.findViewById(R.id.imageView);
            mTextView1= itemView.findViewById(R.id.TextView1);
            mTextView2= itemView.findViewById(R.id.TextView2);
        }
    }

    public ExampleAdapter(ArrayList<Item> exampleList){
        this.exampleList=exampleList;

    }

    @NonNull
    @Override
    public ExampleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mesa_layout,parent,false);
        ExampleHolder evh= new ExampleHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleHolder holder, int position) {
        Item currentItem= exampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResoruce());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextView2.setText(currentItem.getmText2());


    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }
}
