package com.example.hp1.myfinalproject;

import java.io.Serializable;

/**
 * Created by Dell on 9/18/2017.
 */

public class InformationRegistered implements Serializable{

    private String _Id;
    private String FirstName;
    private String LastName;
    private String Email;
    private String PassWord;
    private String Takhassos;
    private String EngPoints;
    private String MathPoints;
    private String Grade;

    public InformationRegistered(){}

    public InformationRegistered(String _id, String firstName, String lastName, String email, String passWord, String takhassos, String engPoints, String mathPoints, String grade) {
        _Id = _id;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        PassWord = passWord;
        Takhassos = takhassos;
        EngPoints = engPoints;
        MathPoints = mathPoints;
        Grade = grade;
    }

    public String getTakhassos() {
        return Takhassos;
    }

    public String get_Id() {
        return _Id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassWord() {
        return PassWord;
    }

    public String getEngPoints() {
        return EngPoints;
    }

    public String getMathPoints() {
        return MathPoints;
    }

    public String getGrade() {
        return Grade;
    }
}
