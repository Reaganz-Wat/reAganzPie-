package com.example.todolistapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class resourcesAdapter extends RecyclerView.Adapter <resourcesAdapter.ResourceViewHolder> {

    public final Context context;
    public final ArrayList <resourceSampleModal> arrayList;

    public resourcesAdapter(Context context, ArrayList <resourceSampleModal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ResourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_layout, parent, false);
        return new ResourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResourceViewHolder holder, int position) {
        resourceSampleModal modal = arrayList.get(position);
        holder.imageView.setImageResource(modal.getURL());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detailedImageActivity.class);
                intent.putExtra("URL", modal.getURL());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ResourceViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ResourceViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.detailed_imageView);
        }
    }

}
