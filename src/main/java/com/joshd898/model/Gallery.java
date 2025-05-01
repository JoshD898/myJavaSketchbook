package com.joshd898.model;

import java.util.ArrayList;
import java.util.List;


// Represents a gallery containing an arbitrary number of drawings, with an index used to navigate drawings
public class Gallery {
    private List<Drawing> drawingList;
    private int index;

    public Gallery() {
        drawingList = new ArrayList<>();
        index = 0;
    }

    public void addDrawing(Drawing d) {
        drawingList.add(d);
    }

    public int size() {
        return drawingList.size();
    }

    public boolean isEmpty() {
        return drawingList.isEmpty();
    }

    public Drawing getCurrentDrawing() {
        return drawingList.get(index);
    }

    public void removeCurrentDrawing() {
        drawingList.remove(index);
        decreaseCurrentIndex();
    }

    public void increaseCurrentIndex() {
        if (index < drawingList.size() - 1) {
            index++;
        }
    }

    public void decreaseCurrentIndex() {
        if (index > 0) {
            index--;
        }
    }    
}
