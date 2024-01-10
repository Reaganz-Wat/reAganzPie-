package com.example.todolistapp;


public class topicLayoutModal {
    private int topicID;
    private String topicName;
    private String topicDescription;
    private String createdAt;

    public topicLayoutModal(int topicID, String topicName, String topicDescription, String createdAt) {
        this.topicID = topicID;
        this.topicName = topicName;
        this.topicDescription = topicDescription;
        this.createdAt = createdAt;
    }

    // getter and setter


    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
