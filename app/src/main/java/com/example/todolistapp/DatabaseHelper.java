package com.example.todolistapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SpacedRep.db";
    private static final int DATABASE_VERSION = 1;

    // Three important methods i.e Constructor, onCreate(), and onUpgrade()
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // call when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create the course table
        db.execSQL("CREATE TABLE Courses (" +
                "courseID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "courseName VARCHAR(50) NOT NULL," +
                "courseDescription TEXT NOT NULL," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP)");

        // create the topics table
        db.execSQL("CREATE TABLE Topics (" +
                "topicID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "courseID INTEGER," +
                "topicName VARCHAR(50) NOT NULL," +
                "topicDescription TEXT NOT NULL," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "updated_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (courseID) REFERENCES Courses (courseID))");

        // create the TopicRecourses table
        db.execSQL("CREATE TABLE TopicResources (" +
                "resourceID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "topicID INTEGER," +
                "imageUrl VARCHAR(255) NOT NULL," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (topicID) REFERENCES Topics (topicID))");
    }

    // call when the database need to be updraged
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades here
        // For simplicity, we'll just drop and recreate the tables
        db.execSQL("DROP TABLE IF EXISTS TopicResources");
        db.execSQL("DROP TABLE IF EXISTS Topics");
        db.execSQL("DROP TABLE IF EXISTS Courses");
        onCreate(db);
    }
}