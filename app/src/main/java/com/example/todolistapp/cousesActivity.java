package com.example.todolistapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class cousesActivity extends AppCompatActivity {
    FloatingActionButton button;
    coursesAdapter adapter;
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        // get views by ID here
        button = findViewById(R.id.floatingButton);
        recyclerView = findViewById(R.id.coursesRecyclerView);
        toolbar = findViewById(R.id.coursesToolBar);

        // call methods here
        toolBar();
        clickFloatingButton();
        displayContents();
    }

    public void toolBar(){
        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Courses");
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

    public void displayContents(){

        // Initialize DBManager
        DBManager dbManager = new DBManager(this);
        dbManager.open();

        // Fetch courses from the database
        List <coursesModal> coursesList = dbManager.getAllCourses();
        dbManager.close();

        ArrayList <coursesLayoutModal> arrayList = new ArrayList<coursesLayoutModal>();

        for (coursesModal course : coursesList) {
            // You may adjust the logic based on your actual data structure
            arrayList.add(new coursesLayoutModal(course.getCourseID(), course.getCourseName(), course.getCourseDescription(), "0 Card")); // Update the card value accordingly
        }

        // add information to the recyclerview from here
//        arrayList.add(new coursesLayoutModal("Introduction to Computer Security", "This deals with security of computer, and the policies, and how to safeguard computers on the network, and lastly Ethical hacking foundamentals", "43 Cards"));
//        arrayList.add(new coursesLayoutModal("Network Computing", "Deals with how computer interact, how packets flow in the network and securing a network from intruders and maintaining good bandwidth", "2 Cards"));
//        arrayList.add(new coursesLayoutModal("Modelling and Simulations", "Simulating engineering principles before actually building them into the physical world", "4 Cards"));
//        arrayList.add(new coursesLayoutModal("Mobile App Development in Java", "This is for creation of android apps using java programming language", "1 Card"));

        adapter = new coursesAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    public void clickFloatingButton(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getStarted.this, "Clicked the Floating button", Toast.LENGTH_SHORT).show();
                openDialog();
            }
        });
    }

    public void openDialog(){
        // Inflate the dialog with custom view
        LayoutInflater inflater = LayoutInflater.from(this);
        final android.view.View mDialogView = inflater.inflate(R.layout.login_dialog, null);

        // AlertDialogBuilder
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Create Course");

        // show dialog
        final AlertDialog mAlertDialog = mBuilder.show();

        // login button click of custom layout
        mDialogView.findViewById(R.id.dialogCreateBtn).setOnClickListener(v1 -> {
            // dismiss dialog
            mAlertDialog.dismiss();

            // get text from EditTexts of custom layout
            String name = ((TextView) mDialogView.findViewById(R.id.dialogCourse)).getText().toString();
            String description = ((TextView) mDialogView.findViewById(R.id.dialogDescription)).getText().toString();

            // Add the new course to the database
            DBManager dbManager = new DBManager(cousesActivity.this);
            dbManager.open();
            long courseId = dbManager.addCourses(name, description);
            dbManager.close();

            // Update the RecyclerView with the new data
            displayContents();

            Toast.makeText(this, "Data inserted Successfully", Toast.LENGTH_SHORT).show();

        });

        // cancel button click of custom layout
        mDialogView.findViewById(R.id.dialogCancelBtn).setOnClickListener(v12 -> {
            // dismiss dialog
            mAlertDialog.dismiss();
        });
    }
}