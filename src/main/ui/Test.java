package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import model.RealDrawing;

public class Test {
    JFrame frame;
    RealDrawing myDrawing;
    RealDrawingPanel drawingPanel;

    public Test() {
        frame = new JFrame();
        myDrawing = new RealDrawing(600, 500, Color.GRAY, "Test");
        drawingPanel = new RealDrawingPanel(myDrawing);
    
        frame.setTitle("MySketchApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
    
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Welcome to MySketchApp!"));
        frame.add(topPanel, BorderLayout.NORTH);
    
        JPanel bottomPanel = new JPanel();
        JButton button1 = new JButton("Blue");
        JButton button2 = new JButton("Red");
        bottomPanel.add(button1);
        bottomPanel.add(button2);
        
        button1.addActionListener(e -> button1Action());
        button2.addActionListener(e -> button2Action());
        
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(drawingWrapper(), BorderLayout.CENTER);
    
        frame.setVisible(true);
    }

    private JPanel drawingWrapper() {
        JPanel wrapper = new JPanel(new GridBagLayout()); // GridBag centers by default
        wrapper.setBackground(Color.GREEN);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Center the drawingPanel
        wrapper.add(drawingPanel, gbc);

        return wrapper;
    }
    
    private void button1Action() {
        myDrawing.setBackgroundColor(Color.BLUE);
        drawingPanel.repaint();
    }

    private void button2Action() {
        myDrawing.setBackgroundColor(Color.RED);
        drawingPanel.repaint();
    }

    public static void main(String[] args) {
        new Test();
    }
}
