package com.joshd898.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;

public class TestGallery {
    private Drawing d1;
    private Drawing d2;
    private Drawing d3;
    private Gallery gallery;

    @BeforeEach
    public void runBefore() {
        d1 = new Drawing(10, 10, Color.WHITE, "Drawing 1");
        d2 = new Drawing(10, 10, Color.BLACK, "Drawing 2");
        d3 = new Drawing(10, 10, Color.RED, "Drawing 3");
        gallery = new Gallery();
    }

    @Test
    void testConstructor() {
        assertTrue(gallery.isEmpty());
        assertEquals(0, gallery.size());
    }

    @Test
    void testAddAndRemoveDrawings() {
        gallery.addDrawing(d1);
        assertEquals(1, gallery.size());
        assertFalse(gallery.isEmpty());
        assertEquals(d1, gallery.getCurrentDrawing());

        gallery.removeCurrentDrawing();
        assertTrue(gallery.isEmpty());
    }

    @Test
    void testMultipleAddAndGetCurrentDrawing() {
        gallery.addDrawing(d1);
        gallery.addDrawing(d2);
        gallery.addDrawing(d3);
        assertEquals(d1, gallery.getCurrentDrawing());

        gallery.increaseCurrentIndex();
        assertEquals(d2, gallery.getCurrentDrawing());

        gallery.increaseCurrentIndex();
        assertEquals(d3, gallery.getCurrentDrawing());

        // Should not increase beyond max
        gallery.increaseCurrentIndex();
        assertEquals(d3, gallery.getCurrentDrawing());
    }

    @Test
    void testDecreaseIndex() {
        gallery.addDrawing(d1);
        gallery.addDrawing(d2);
        gallery.addDrawing(d3);
        gallery.increaseCurrentIndex();
        gallery.increaseCurrentIndex();
        assertEquals(d3, gallery.getCurrentDrawing());

        gallery.decreaseCurrentIndex();
        assertEquals(d2, gallery.getCurrentDrawing());

        gallery.decreaseCurrentIndex();
        assertEquals(d1, gallery.getCurrentDrawing());

        // Should not decrease below 0
        gallery.decreaseCurrentIndex();
        assertEquals(d1, gallery.getCurrentDrawing());
    }

    @Test
    void testRemoveCurrentAdjustsIndex() {
        gallery.addDrawing(d1);
        gallery.addDrawing(d2);
        gallery.addDrawing(d3);

        gallery.increaseCurrentIndex(); // now at d2
        gallery.removeCurrentDrawing(); // removes d2
        assertEquals(2, gallery.size());
        assertEquals(d1, gallery.getCurrentDrawing()); // index was 1 → 0
    }
}
