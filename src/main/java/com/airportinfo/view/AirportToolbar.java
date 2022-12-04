package com.airportinfo.view;

import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Airport toolbar.
 *
 * @author lalaalal
 */
public class AirportToolbar extends ComponentView {
    private JPanel panel;
    private JPanel toolbarPanel;
    private final ArrayList<JLabel> labels = new ArrayList<>();
    private final HashMap<String, JLabel> translationLabels = new HashMap<>();
    private final ThemeManager themeManager = ThemeManager.getInstance();

    public AirportToolbar() {
        $$$setupUI$$$();
        addThemeChangeListener(theme -> {
            panel.setBackground(themeManager.getColor("Toolbar.background"));
            toolbarPanel.setBackground(themeManager.getColor("Toolbar.background"));
            for (JLabel label : labels)
                label.setForeground(themeManager.getColor("Toolbar.foreground"));
        });
        addLocaleChangeListener(locale -> {
            for (String key : translationLabels.keySet()) {
                JLabel label = translationLabels.get(key);
                label.setText(Translator.getBundleString(key));
            }
        });
        onThemeChange(AppTheme.Lite);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Add new label.
     *
     * @param key Label text or translation key
     */
    public void addLabel(String key) {
        JLabel label = new JLabel(key);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 30));
        label.setForeground(themeManager.getColor("Toolbar.foreground"));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toolbarPanel.add(label);
        labels.add(label);
    }

    /**
     * Add label with mouse action.
     *
     * @param key    Label text or translation key
     * @param action Mouse action for label
     */
    public void addLabel(String key, Consumer<MouseEvent> action) {
        JLabel label = new JLabel(Translator.getBundleString(key));
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 30));
        label.setForeground(themeManager.getColor("Toolbar.foreground"));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (action != null && label.contains(e.getPoint()))
                    action.accept(e);
            }
        });
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toolbarPanel.add(label);
        labels.add(label);
        translationLabels.put(key, label);
    }

    private void createUIComponents() {
        toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new FlowLayout());
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel.setBackground(new Color(-4605511));
        panel.setForeground(new Color(-4605511));
        toolbarPanel.setBackground(new Color(-4605511));
        panel.add(toolbarPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        toolbarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer1 = new Spacer();
        panel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
