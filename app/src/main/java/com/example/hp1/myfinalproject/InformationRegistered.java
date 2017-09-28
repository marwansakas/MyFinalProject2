package com.example.hp1.myfinalproject;

import java.io.Serializable;

/**
 * Created by Dell on 9/18/2017.
 */

public class InformationRegistered implements Serializable{

    public String _Id;
    public String FirstName;
    public String LastName;
    public String Email;
    public String PassWord;
    public String Takhassos;
    public String EngPoints;
    public String MathPoints;
    public String Grade;

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

}
