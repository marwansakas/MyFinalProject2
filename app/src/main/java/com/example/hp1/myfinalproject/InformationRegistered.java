package com.example.hp1.myfinalproject;

import java.io.Serializable;

/**
 * Created by Dell on 9/18/2017.
 */

public class InformationRegistered implements Serializable{

    public String Id;
    public String FirstName;
    public String LastName;
    public String Email;
    public String PassWord;
    public String Takhassos;
    public int EngPoints;
    public int MathPoints;
    public int Grade;

    public InformationRegistered(){}

    public InformationRegistered(String id, String firstName, String lastName, String email, String passWord, String takhassos, int engPoints, int mathPoints, int grade) {
        Id = id;
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
