package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RealDrawing extends BufferedImage {
    private String title;

    public RealDrawing(int width, int height, Color color, String title) {
        super(width, height, BufferedImage.TYPE_INT_RGB);
        setBackgroundColor(color);
        this.title = title;
    }

    /**
     * Sets all pixels in the drawing to the specified background color.
     * 
     * @param color The color to set the background to
     */
    public void setBackgroundColor(Color color) {
        Graphics2D graphic = super.createGraphics();
        graphic.setColor(color);
        graphic.fillRect(0, 0, super.getWidth(), super.getHeight());
        graphic.dispose();
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
