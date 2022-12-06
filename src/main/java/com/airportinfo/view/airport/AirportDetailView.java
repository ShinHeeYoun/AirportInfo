package com.airportinfo.view.airport;

import com.airportinfo.misc.UneditableTableModel;
import com.airportinfo.model.Airport;
import com.airportinfo.util.ThemeManager;
import com.airportinfo.view.ComponentView;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

/**
 * Showing detail of airport.
 *
 * @author lalaalal
 */
public class AirportDetailView extends ComponentView {
    private JPanel panel;
    private JTable airportDetailTable;
    private final UneditableTableModel tableModel = new UneditableTableModel(0, 2);
    private Airport airport;

    public AirportDetailView() {
        $$$setupUI$$$();
        addThemeChangeListener((theme) -> airportDetailTable.setBackground(ThemeManager.getDefaultColor("Panel.background")));
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
        updateView();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    public void updateView() {
        while (tableModel.getRowCount() > 0)
            tableModel.removeRow(0);

        String[] keys = Airport.getLocalizedAttributeNames();
        String[] values = airport.toArray();

        for (int i = 0; i < keys.length; i++) {
            String[] row = {keys[i], values[i]};
            tableModel.addRow(row);
        }
    }

    private void createUIComponents() {
        airportDetailTable = new JTable(tableModel);
        airportDetailTable.getTableHeader().setVisible(false);
        airportDetailTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        panel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(airportDetailTable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
