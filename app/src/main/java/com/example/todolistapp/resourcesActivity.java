package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.Manifest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class resourcesActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;

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
        clickFLoatingButton();

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

    public void clickFLoatingButton(){
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(resourcesActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(resourcesActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                } else {
                    // Permission already granted, open the camera
                    openCamera();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            Toast.makeText(this, "Camera app not found", Toast.LENGTH_SHORT).show();
        }
    }

}