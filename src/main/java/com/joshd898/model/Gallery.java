package com.joshd898.model;

import java.util.ArrayList;
import java.util.List;


// Represents a gallery containing an arbitrary number of drawings
public class Gallery {

    private List<Drawing> drawingList;

    public Gallery() {
        drawingList = new ArrayList<>();
    }

    public void addDrawing(Drawing d) {
        drawingList.add(d);
    }

    public List<Drawing> getDrawingList() {
        return drawingList;
    }

    public void removeDrawing(int index) {
        drawingList.remove(index);
    }
}
