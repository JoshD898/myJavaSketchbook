package org.joshd898.ui.login;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

// A panel that you can add components to, all components will be centered and stacked vertically
public class VerticallyStackedPanel extends JPanel {
    private int row;
    private GridBagConstraints gbc;

    public VerticallyStackedPanel(int verticalSpacer) {
        super(new GridBagLayout());
        super.setOpaque(false);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(verticalSpacer, 0, verticalSpacer, 0);

        row = 0;
    }

    public void add(JComponent component) {
        gbc.gridy = row++;
        add(component, gbc);
    }
}


