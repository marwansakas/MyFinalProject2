package com.example.hp1.myfinalproject;

/**
 * Created by Dell on 9/25/2017.
 */

public class News {

    String title;
    String first_Sentence;
    String artical;

    public News() {
    }

    public News(String title, String first_Sentence, String artical) {
        this.title = title;
        this.first_Sentence = first_Sentence;
        this.artical = artical;
    }

    public String getTitle() {
        return title;
    }

    public String getFirst_Sentence() {
        return first_Sentence;
    }

    public String getArtical() {
        return artical;
    }


}
