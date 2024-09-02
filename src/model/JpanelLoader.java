package model;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class JpanelLoader {

    public void jPanelLoader(JScrollPane panelLoader, JPanel setPanel) {
        panelLoader.setViewportView(setPanel);
        panelLoader.revalidate();
        panelLoader.repaint();
        System.gc();
    }

}
