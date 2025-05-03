package org.joshd898.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.io.IOException;

public class TestDrawing {
    Drawing d1;
    Drawing d2;

    @BeforeEach
    public void runBefore() {
        d1 = new Drawing(10, 10, Color.WHITE, "Drawing 1");
        d2 = new Drawing(10, 10, Color.BLACK, "Drawing 2");
    }

    @Test
    void testTitleMethods() {
        assertEquals(d1.getTitle(), "Drawing 1");
        d1.setTitle("Test");
        assertEquals(d1.getTitle(), "Test");
    }

    @Test
    void testDrawingIDMethods() {
        d1.setDrawingID(999);
        assertEquals(999, d1.getDrawingID());
    }

    @Test
    void testToAndFromBytes() {
        try {
            d1.draw(0, 0, 1, Color.RED);
            byte[] d1Bytes = d1.getByteArray();
            Drawing drawingFromBytes = Drawing.fromBytes(d1.getTitle(), d1.getDrawingID(), d1Bytes);

            assertEquals(d1.getTitle(), drawingFromBytes.getTitle());
            assertEquals(d1.getDrawingID(), drawingFromBytes.getDrawingID());
            assertEquals(d1.getWidth(), drawingFromBytes.getWidth());
            assertEquals(d1.getHeight(), drawingFromBytes.getHeight());

            for (int x = 0; x < d1.getWidth(); x++) {
                for (int y = 0; y < d1.getHeight(); y++) {
                    assertEquals(d1.getRGB(x, y), drawingFromBytes.getRGB(x, y));
                }
            }
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testGetCopy() {
        Drawing copy = d1.getCopy();

        assertNotSame(d1, copy);
        assertEquals(d1.getTitle(), copy.getTitle());
        assertEquals(d1.getWidth(), copy.getWidth());
        assertEquals(d1.getHeight(), copy.getHeight());

        for (int x = 0; x < d1.getWidth(); x++) {
            for (int y = 0; y < d1.getHeight(); y++) {
                assertEquals(d1.getRGB(x, y), copy.getRGB(x, y));
            }
        }
    }

    @Test
    void testDraw() {
        assertEquals(d1.getRGB(0,0), Color.WHITE.getRGB());
        d1.draw(0, 0, 2, Color.RED);
        assertEquals(d1.getRGB(0,0), Color.RED.getRGB());          
        
        assertEquals(d1.getRGB(d1.getHeight() - 1, d1.getWidth() - 1),  Color.WHITE.getRGB());
        d1.draw(d1.getHeight() - 1, d1.getWidth() - 1, 2, Color.RED);
        assertEquals(d1.getRGB(d1.getHeight() - 1, d1.getWidth() - 1),  Color.RED.getRGB());
    }

    @Test
    void testSetContentAndClear() {
        for (int x = 0; x < d1.getWidth(); x++) {
            for (int y = 0; y < d1.getHeight(); y++) {
                assertEquals(d1.getRGB(x, y), Color.WHITE.getRGB());
            }
        }

        d1.setContent(d2);

        for (int x = 0; x < d1.getWidth(); x++) {
            for (int y = 0; y < d1.getHeight(); y++) {
                assertEquals(d1.getRGB(x, y), Color.BLACK.getRGB());
            }
        }

        d1.clear();

        for (int x = 0; x < d1.getWidth(); x++) {
            for (int y = 0; y < d1.getHeight(); y++) {
                assertEquals(d1.getRGB(x, y), Color.WHITE.getRGB());
            }
        }
    }
}
