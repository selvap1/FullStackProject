package edu.farmingdale.fullstackproject;

import javafx.beans.property.SimpleStringProperty;

public class Person {
    private static int idCounter = 0;
    private final int id;  // Make id final as it should not change
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty department;
    private final SimpleStringProperty major;
    private final SimpleStringProperty email;
    private final SimpleStringProperty imageURL;  // Keep this for URL

    public Person(String firstName, String lastName, String department, String major, String email, String imageURL) {
        this.id = ++idCounter;  // Increment the counter to get a new id
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.department = new SimpleStringProperty(department);
        this.major = new SimpleStringProperty(major);
        this.email = new SimpleStringProperty(email);
        this.imageURL = new SimpleStringProperty(imageURL);  // Correctly set the image URL
    }

    // Getters for the ID and other fields
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getDepartment() {
        return department.get();
    }

    public String getMajor() {
        return major.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getImageURL() {
        return imageURL.get();
    }
}
