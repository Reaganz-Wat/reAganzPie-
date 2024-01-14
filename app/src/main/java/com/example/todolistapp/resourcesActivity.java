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
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        DBManager dbManager = new DBManager(this);
        dbManager.open();

        // Fetch all references for the images belonging to the particular ID
        List<topicResourcesModal> resources = dbManager.getAllTopicResources(topicID);

        dbManager.close();

        ArrayList <resourceSampleModal> arrayList = new ArrayList();

        // Iterate through the fetched resources and create ResourceSampleModal objects
        for (topicResourcesModal resource : resources) {
            arrayList.add(new resourceSampleModal(resource.getImageURL()));
        }


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
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Camera app not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Toast.makeText(this, "Image Captured", Toast.LENGTH_SHORT).show();

            if (data != null && data.getExtras() != null) {
                // The image is in the extras, save it to storage and add the reference to the database
                Bundle extras = data.getExtras();
                // Get the image bitmap
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // Save the image to storage
                String imagePath = saveImageToStorage(imageBitmap);

                // Add the reference to the database
                DBManager dbManager = new DBManager(this);
                dbManager.open();
                long resourceID = dbManager.addTopicResource(topicID, imagePath);
                dbManager.close();

                // Add the new resource to the RecyclerView
                Toast.makeText(this, "Image added successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String saveImageToStorage(Bitmap bitmap) {
        // Save the image to the app's internal storage or external storage
        // You may need to handle permissions for saving to external storage
        // Return the path or URI of the saved image

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            FileOutputStream  fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (imageFile != null) {
            return imageFile.getAbsolutePath();
        } else {
            return null;
        }
    }

}

