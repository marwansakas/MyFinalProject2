package com.example.hp1.myfinalproject.JavaClasses;

/**
 * Created by Dell on 9/25/2017.
 */

public class News {

    String title;
    String artical;
    String image;
    String preview;



    public News() {
    }

    public News(String title, String artical, String image, String preview) {
        this.title = title;
        this.artical = artical;
        this.image = image;
        this.preview = preview;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getTitle() {
        return title;
    }

    public String getArtical() {
        return artical;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtical(String artical) {
        this.artical = artical;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

