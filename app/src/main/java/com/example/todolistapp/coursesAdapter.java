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

public class coursesAdapter extends RecyclerView.Adapter <coursesAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList <coursesLayoutModal> arrayList;

    public coursesAdapter(Context context, ArrayList<coursesLayoutModal> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public coursesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.courses_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull coursesAdapter.ViewHolder holder, int position) {
        coursesLayoutModal modal = arrayList.get(position);
        holder.courseName.setText(modal.getCourseName());
        holder.courseDescription.setText(modal.getCourseDescription());
        holder.cardNumbers.setText(modal.getCardNumbers());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, topicsActivity.class);
                intent.putExtra("courseID", modal.getCourseID());
                intent.putExtra("courseName", modal.getCourseName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseName;
        private final TextView courseDescription;
        private final TextView cardNumbers;
        private final CardView cardView;

        public ViewHolder(View view){
            super(view);
            courseName = view.findViewById(R.id.courseName);
            courseDescription = view.findViewById(R.id.courseDescription);
            cardNumbers = view.findViewById(R.id.numberOfCards);
            cardView = view.findViewById(R.id.coursesID);
        }
    }
}
