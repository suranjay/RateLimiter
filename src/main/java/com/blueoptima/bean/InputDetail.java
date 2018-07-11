package com.blueoptima.bean;

/**
 * Created by Suranjay on 10/07/18.
 */
public class InputDetail {

    private String firstName;
    private String lastName;
    private String location;

    public InputDetail(String firstName, String lastName, String location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "InputDetail{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
