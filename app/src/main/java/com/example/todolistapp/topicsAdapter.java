package com.example.todolistapp;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class topicsAdapter extends RecyclerView.Adapter <topicsAdapter.ViewHolder> {
    public final Context context;
    public final ArrayList <topicLayoutModal> arrayList;

    public topicsAdapter(Context context, ArrayList<topicLayoutModal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public topicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topics_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull topicsAdapter.ViewHolder holder, int position) {
        topicLayoutModal modal = arrayList.get(position);
        holder.topicsName.setText(modal.getTopicName());
        holder.topicsDescription.setText(modal.getTopicDescription());
        holder.dateCreated.setText(modal.getCreatedAt());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, resourcesActivity.class);
                intent.putExtra("topicID", modal.getTopicID());
                intent.putExtra("topicName", modal.getTopicName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView topicsName, topicsDescription, dateCreated;
        private final CardView cardView;
        public ViewHolder(View view){
            super(view);
            topicsName = view.findViewById(R.id.topicName);
            topicsDescription = view.findViewById(R.id.topicDescription);
            dateCreated = view.findViewById(R.id.dateCreated);
            cardView = view.findViewById(R.id.topicsCardView);
        }
    }
}
