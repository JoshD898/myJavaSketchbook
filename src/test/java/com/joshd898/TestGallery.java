package com.joshd898;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.awt.Color;
import com.joshd898.model.Drawing;
import com.joshd898.model.Gallery;

public class TestGallery {
    Drawing d1;
    Drawing d2;
    Gallery g;

    @BeforeEach
    void runBefore() {
        d1 = new Drawing(10, 10, Color.WHITE, "Drawing 1");
        d2 = new Drawing(10, 10, Color.BLACK, "Drawing 2");

        g = new Gallery();
    }

    @Test
    void testConstructor() {
        assertTrue(g.getDrawingList().isEmpty());
    }

    @Test
    void testAddAndRemoveDrawings() {
        g.addDrawing(d1);
        assertEquals(g.getDrawingList().size(), 1);
        g.removeDrawing(0);
        assertTrue(g.getDrawingList().isEmpty());
    }
}
