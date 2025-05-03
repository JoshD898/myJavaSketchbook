package org.joshd898.ui.app;

import javax.swing.JLabel;

import org.joshd898.ui.MainFrame;
import org.joshd898.ui.RoundedButton;

public class EmptyDisplayPanel extends AbstractAppLayout {
    private static final String ADD_MESSAGE = "Your gallery is empty!";
    private static final String ADD_BUTTON_LABEL = "+";
    
    public EmptyDisplayPanel() {
        super();

        JLabel addMessage = new JLabel(ADD_MESSAGE);
        addMessage.setFont(LABEL_FONT);

        middlePanel.add(addMessage);

        RoundedButton add = super.makeRoundedButton(ADD_BUTTON_LABEL);
        add.addActionListener(e -> onAdd());

        super.addButtonToBottomPanel(add);
    } 

    private void onAdd() {
        MainFrame.getInstance().updateDisplay(new EditPanel(true));
    }
}
