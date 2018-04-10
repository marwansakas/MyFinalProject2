package com.example.hp1.myfinalproject.JavaClasses;

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
    private String Section;

    public InformationRegistered(){}



    public InformationRegistered(String _id, String firstName, String lastName, String email, String passWord, String takhassos, String engPoints, String mathPoints, String grade,String section) {
        _Id = _id;
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        PassWord = passWord;
        Takhassos = takhassos;
        EngPoints = engPoints;
        MathPoints = mathPoints;
        Grade = grade;
        Section = section;


    }



    @Override
    public String toString() {
        return "InformationRegistered{" +
                "_Id='" + _Id + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Email='" + Email + '\'' +
                ", PassWord='" + PassWord + '\'' +
                ", Takhassos='" + Takhassos + '\'' +
                ", EngPoints='" + EngPoints + '\'' +
                ", MathPoints='" + MathPoints + '\'' +
                ", Grade='" + Grade +" "+Section +'\''+
                '}';

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

    public String getGrade() { return Grade; }

    public String getSection() { return Section; }

    public void set_Id(String _Id) {
        this._Id = _Id;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public void setTakhassos(String takhassos) {
        Takhassos = takhassos;
    }

    public void setEngPoints(String engPoints) {
        EngPoints = engPoints;
    }

    public void setMathPoints(String mathPoints) {
        MathPoints = mathPoints;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public void setSection(String section) { Section = section; }
}
