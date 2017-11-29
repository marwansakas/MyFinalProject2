package com.example.hp1.myfinalproject.JavaClasses;

import android.graphics.Path;


public class FingerPath {

    public int color;
    public int strokewidth;
    public Path path;

    public FingerPath(int color, int strokewidth, Path path) {
        this.color = color;
        this.strokewidth = strokewidth;
        this.path = path;
    }

    public int getColor() {
        return color;
    }

    public int getStrokewidth() {
        return strokewidth;
    }

    public Path getPath() {
        return path;
    }
}
