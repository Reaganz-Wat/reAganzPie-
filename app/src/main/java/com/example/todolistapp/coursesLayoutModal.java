package com.example.todolistapp;

public class coursesLayoutModal {
    int courseID;
    String courseName, courseDescription, cardNumbers;

    public coursesLayoutModal(int courseID, String courseName, String courseDescription, String cardNumbers) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.cardNumbers = cardNumbers;
    }

    // getter

    public int getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public String getCardNumbers() {
        return cardNumbers;
    }

    // setter

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public void setCardNumbers(String cardNumbers) {
        this.cardNumbers = cardNumbers;
    }
}
