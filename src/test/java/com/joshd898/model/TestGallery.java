package com.joshd898.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

public class TestGallery {
    private Drawing d1;
    private Gallery gallery;

    @BeforeEach
    public void runBefore() {
        d1 = new Drawing(10, 10, Color.WHITE, "Drawing 1");

        gallery = new Gallery();
    }

    @Test
    void testConstructor() {
        assertTrue(gallery.getDrawingList().isEmpty());
    }

    @Test
    void testAddAndRemoveDrawings() {
        gallery.addDrawing(d1);
        assertEquals(1, gallery.getDrawingList().size());
        gallery.removeDrawing(0);
        assertTrue(gallery.getDrawingList().isEmpty());
    }
}
