package com.example.todolistapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.jsibbold.zoomage.ZoomageView;

public class detailedImageActivity extends AppCompatActivity {

    public String ID;
    //ImageView imageView;
    ZoomageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_image);

        ID = getIntent().getStringExtra("URL");
        imageView = findViewById(R.id.bigImage);

        Glide.with(this)
                .load(ID)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(imageView);
    }
}