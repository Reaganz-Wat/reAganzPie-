package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class resourcesActivity extends AppCompatActivity {

    Toolbar toolbar;
    String topicName;
    long topicID;
    FloatingActionButton cameraButton;
    private static final int REQUEST_IMAGE_CAPTURE = 1888;
    RecyclerView recyclerView;
    resourcesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        // find view by id here
        toolbar = findViewById(R.id.resourcesToolbar);
        cameraButton = findViewById(R.id.takeResourcePhoto);
        recyclerView = findViewById(R.id.resourcesRecyclerview);
        topicName = getIntent().getStringExtra("topicName");
        topicID = getIntent().getIntExtra("topicID", 0);

        // methods here
        toolBar();
        getContent();

    }
    public void toolBar(){
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(myFunctions.shortenText(topicName, 10));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return onContextItemSelected(item);
        }
    }

    public void getContent(){

        ArrayList <resourceSampleModal> arrayList = new ArrayList();
        arrayList.add(new resourceSampleModal(R.mipmap.reagans));
        arrayList.add(new resourceSampleModal(R.mipmap.bb1));
        arrayList.add(new resourceSampleModal(R.mipmap.brain));
        arrayList.add(new resourceSampleModal(R.mipmap.logo));
        arrayList.add(new resourceSampleModal(R.mipmap.reagans));
        arrayList.add(new resourceSampleModal(R.mipmap.bb1));
        arrayList.add(new resourceSampleModal(R.mipmap.brain));
        arrayList.add(new resourceSampleModal(R.mipmap.logo));

        adapter = new resourcesAdapter(this, arrayList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}