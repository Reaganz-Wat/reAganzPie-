package com.example.todolistapp;

import android.adservices.topics.Topic;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DBManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // CRUDS FOR COUSES
    public long addCourses(String courseName, String courseDescription){
        ContentValues values = new ContentValues();
        values.put("courseName", courseName);
        values.put("courseDescription", courseDescription);
        return database.insert("Courses", null, values);
    }

    public List<coursesModal> getAllCourses() {
        List<coursesModal> courses = new ArrayList<>();
        Cursor cursor = database.query("Courses", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                coursesModal course = new coursesModal();
                course.setCourseID(cursor.getInt(cursor.getColumnIndex("courseID")));
                course.setCourseName(cursor.getString(cursor.getColumnIndex("courseName")));
                course.setCourseDescription(cursor.getString(cursor.getColumnIndex("courseDescription")));
                courses.add(course);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return courses;
    }

    // CRUDS FOR TOPICS
    public long addTopic(long courseId, String topicName, String topicDescription) {
        ContentValues values = new ContentValues();
        values.put("courseID", courseId);
        values.put("topicName", topicName);
        values.put("topicDescription", topicDescription);
        values.put("created_at", System.currentTimeMillis());
        return database.insert("Topics", null, values);
    }

    public List<topicsModal> getAllTopics(long courseId) {
        List<topicsModal> topics = new ArrayList<>();
        String selection = "courseID=?";
        String[] selectionArgs = {String.valueOf(courseId)};
        Cursor cursor = database.query("Topics", null, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                topicsModal topic = new topicsModal();
                topic.setTopicID(cursor.getInt(cursor.getColumnIndex("topicID")));
                topic.setTopicName(cursor.getString(cursor.getColumnIndex("topicName")));
                topic.setTopicDescription(cursor.getString(cursor.getColumnIndex("topicDescription")));

                // added lines for testing
                // Add the following lines to retrieve and set the created_at date
                long createdTimestamp = cursor.getLong(cursor.getColumnIndex("created_at"));
                Date createdDate = new Date(createdTimestamp);
                topic.setCreatedAt(createdDate);

                topics.add(topic);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return topics;
    }

    // CRUDS FOR TOPIC_RESOURCES
    public long addTopicResource(long topicId, String imageUrl) {
        ContentValues values = new ContentValues();
        values.put("topicID", topicId);
        values.put("imageURL", imageUrl);
        return database.insert("TopicResources", null, values);
    }

    public List<topicResourcesModal> getAllTopicResources(long topicId) {
        List<topicResourcesModal> resources = new ArrayList<>();
        String selection = "topicID=?";
        String[] selectionArgs = {String.valueOf(topicId)};
        Cursor cursor = database.query("TopicResources", null, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                topicResourcesModal resource = new topicResourcesModal();
                resource.setTopicID(cursor.getInt(cursor.getColumnIndex("resourceID")));
                resource.setImageURL(cursor.getString(cursor.getColumnIndex("imageURL")));
                resources.add(resource);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return resources;
    }

}
