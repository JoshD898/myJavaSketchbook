package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Drawing extends BufferedImage {
    private String title;
    private Color backgroundColor;

    public Drawing(int width, int height, Color color, String title) {
        super(width, height, BufferedImage.TYPE_INT_RGB);
        this.backgroundColor = color;
        clear();
        this.title = title;
    }

    /**
     * Sets all pixels in the drawing to original background color
     */
    public void clear() {
        Graphics2D g = super.createGraphics();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
        g.dispose();
    }

    /**
     * Draws a circle with given radius and color around center point (x,y)
     * 
     * @param centerX The x-coordinate of the center pixel
     * @param centerY The y-coordinate of the center pixel
     * @param radius The radius of the circle to draw
     * @param color The color of the circle to draw
     */
    public void draw(int centerX, int centerY, int radius, Color color) {
        int width = getWidth();
        int height = getHeight();
        int rgb = color.getRGB();
        int rSquared = radius * radius;

        for (int x = centerX - radius; x <= centerX + radius; x++) {
            for (int y = centerY - radius; y <= centerY + radius; y++) {
                if (x >= 0 && x < width && y >= 0 && y < height) {
                    int dx = x - centerX;
                    int dy = y - centerY;
                    if (dx * dx + dy * dy <= rSquared) {
                        setRGB(x, y, rgb);
                    }
                }
            }
        }
    }

    /**
     * Makes a copy of the current drawing
     * 
     * @return A copy of the current drawing
     */
    public Drawing copy() {
        Drawing newDrawing = new Drawing(super.getWidth(), super.getHeight(), backgroundColor, title);
        newDrawing.setContent(this);
        return newDrawing;        
    }

    /**
     * Sets the content of the drawing to that of another drawing
     * 
     * @param d The drawing to copy content from
     */
    public void setContent(Drawing d) {
        Graphics2D g = super.createGraphics();
        g.drawImage(d, 0, 0, null);
        g.dispose();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
