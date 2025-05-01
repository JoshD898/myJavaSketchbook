package com.joshd898.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Drawing extends BufferedImage {
    private String title;
    private Color backgroundColor;
    private long drawingID;

    public Drawing(int width, int height, Color color, String title) {
        super(width, height, BufferedImage.TYPE_INT_RGB);
        this.backgroundColor = color;
        clear();
        this.title = title;
    }

    public static Drawing fromBytes(String title, long drawingID, byte[] drawingBytes) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(drawingBytes));
        Drawing d = new Drawing(img.getWidth(), img.getHeight(), Color.WHITE, title);
        d.setContent(img);
        d.setDrawingID(drawingID);
        return d;
    }

    // Returns a byte array representation of the drawing content (NOT title, background or id)
    public byte[] getByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(this, "png", baos);
        return baos.toByteArray();
    }

    // Resets all pixels in the drawing to the background color
    public void clear() {
        Graphics2D g = super.createGraphics();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
        g.dispose();
    }

    // Draws a circle with given radius and color at the given point
    public void draw(int centerX, int centerY, int radius, Color color) {
        int width = getWidth();
        int height = getHeight();
        int rgb = color.getRGB();
        int radiusSquared = radius * radius;

        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int y = centerY - radius; y <= centerY + radius; y++) {
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    int dx = x - centerX;
                    int dy = y - centerY;
                    if (dx * dx + dy * dy <= radiusSquared) {
                        setRGB(x, y, rgb);
                    }
                }
            }
        }
    }

    // Returns an identical but distinct Drawing object
    public Drawing getCopy() {
        Drawing newDrawing = new Drawing(super.getWidth(), super.getHeight(), backgroundColor, title);
        newDrawing.setContent(this);
        return newDrawing;        
    }

    // Sets the content of the current drawing to that of the provided image
    public void setContent(BufferedImage img) {
        Graphics2D g = super.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDrawingID(long drawingID) {
        this.drawingID = drawingID;
    }

    public long getDrawingID() {
        return drawingID;
    }
}
