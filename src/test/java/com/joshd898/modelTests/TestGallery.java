package com.joshd898.modelTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import com.joshd898.model.Drawing;
import com.joshd898.model.Gallery;

public class TestGallery {
    private Drawing d1;
    private Gallery gallery;

    @Before
    public void runBefore() {
        d1 = new Drawing(10, 10, Color.WHITE, "Drawing 1");

        gallery = new Gallery();
    }

    @Test
    public void testConstructor() {
        assertTrue(gallery.getDrawingList().isEmpty());
    }

    @Test
    public void testAddAndRemoveDrawings() {
        gallery.addDrawing(d1);
        assertEquals(1, gallery.getDrawingList().size());
        gallery.removeDrawing(0);
        assertTrue(gallery.getDrawingList().isEmpty());
    }
}
