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
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class topicsActivity extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton addTopic;
    String courseName;
    long courseID;
    RecyclerView recyclerView;
    topicsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        // find widgets by id's
        toolbar = findViewById(R.id.topicsToolbar);
        addTopic = findViewById(R.id.addTopicsFloatingButton);
        recyclerView = findViewById(R.id.topicsRecyclerview);
        courseID = getIntent().getIntExtra("courseID", 0);
        courseName = getIntent().getStringExtra("courseName");

        // call methods here
        toolBar();
        addTopicButton();
        displayContent();

    }
    public void toolBar(){
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(myFunctions.shortenText(courseName, 10));
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
                return super.onOptionsItemSelected(item);
        }
    }
    public void addTopicButton(){
        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    public void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View topicsViews = inflater.inflate(R.layout.login_dialog, null);
        EditText topicName = topicsViews.findViewById(R.id.dialogCourse);
        EditText topicDescription = topicsViews.findViewById(R.id.dialogDescription);
        topicName.setHint("Enter your topic");
        topicDescription.setHint("Description of topic");

        // build the alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Create new Topic")
                .setView(topicsViews);

        // create the alertdialg and show
        AlertDialog alertDialog = builder.show();

        // find the button inside the topicsViews created and then setOnClickListener onto it
        topicsViews.findViewById(R.id.dialogCreateBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // dismiss the alertdialog
                alertDialog.dismiss();

                // get the user input from those edittext in the topicsView inflated
                String topicName = ((EditText) topicsViews.findViewById(R.id.dialogCourse)).getText().toString();
                String topicDescription = ((EditText) topicsViews.findViewById(R.id.dialogDescription)).getText().toString();

                // create the dbManager to handle database connection and performing of the cruds
                DBManager dbManager = new DBManager(topicsActivity.this);

                // open connection to the database
                dbManager.open();

                // add the topics to the database
                long id = dbManager.addTopic(courseID, topicName, topicDescription);

                // close the connection to the database
                dbManager.close();

                if (id != -1){
                    Toast.makeText(topicsActivity.this, "Topic Created Successfully", Toast.LENGTH_SHORT).show();
                    // update the Recyclerview
                    displayContent();
                } else {
                    Toast.makeText(topicsActivity.this, "Failed to create topic: Report to the developers", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // dismiss the alertdialog when the cancel button is clicked
        topicsViews.findViewById(R.id.dialogCancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void displayContent(){
        // initialize the dbManager
        DBManager dbManager = new DBManager(this);
        dbManager.open();

        List <topicsModal> topicsList = dbManager.getAllTopics(courseID);
        ArrayList <topicLayoutModal> arrayList = new ArrayList <topicLayoutModal> ();

        for (topicsModal topic : topicsList){
            arrayList.add(new topicLayoutModal(topic.getTopicID(), topic.getTopicName(), topic.getTopicDescription(), formatDate(topic.getCreatedAt())));
        }

        adapter = new topicsAdapter(this, arrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
    public String formatDate(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateformat = simpleDateFormat.format(date);
        return dateformat;
    }
}