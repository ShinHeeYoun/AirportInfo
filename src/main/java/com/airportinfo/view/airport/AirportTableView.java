package com.airportinfo.view.airport;

import com.airportinfo.misc.UneditableTableModel;
import com.airportinfo.model.Airport;
import com.airportinfo.util.ThemeManager;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A view class handling airports table.
 *
 * @author JumoKookbob
 * @author lalaalal
 */
public class AirportTableView extends AirportView {
    private JPanel panel;
    private final UneditableTableModel tableModel = new UneditableTableModel(Airport.getLocalizedAttributeNames(), 0);
    private JTable table;
    private JScrollPane scrollPane;
    private final ArrayList<String> removedAttributes = new ArrayList<>();

    public AirportTableView() {
        $$$setupUI$$$();
        addLocaleChangeListener(locale -> updateTableHeader());
        addThemeChangeListener(theme -> scrollPane.getViewport().setBackground(ThemeManager.getDefaultColor("Table.background")));
    }

    public void addMouseClickAction(Consumer<MouseEvent> consumer) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                consumer.accept(e);
            }
        });
    }

    /**
     * Get selected airport from table.
     *
     * @return Selected airport
     */
    public Airport getSelectedAirport() {
        int row = table.getSelectedRow();
        int modelIndex = table.getColumn("IATA").getModelIndex();
        int column = table.convertColumnIndexToView(modelIndex);
        Optional<Airport> optional = airports.stream().filter(airport -> airport.getIATA() == table.getValueAt(row, column)).findFirst();
        return optional.orElse(null);
    }

    /**
     * Get all selected airports from table.
     *
     * @return Selected airports
     */
    public ArrayList<Airport> getSelectedAirports() {
        ArrayList<Airport> result = new ArrayList<>();
        int[] rows = table.getSelectedRows();
        int modelIndex = table.getColumn("IATA").getModelIndex();
        int column = table.convertColumnIndexToView(modelIndex);
        for (int row : rows) {
            Optional<Airport> optional = airports.stream().filter(airport -> airport.getIATA() == table.getValueAt(row, column)).findFirst();
            optional.ifPresent(result::add);
        }
        return result;
    }

    /**
     * Remove column.
     *
     * @param attributeName Header name
     */
    public void removeColumn(String attributeName) {
        TableColumn tableColumn = table.getColumn(attributeName);
        table.getColumnModel().removeColumn(tableColumn);
        removedAttributes.add(attributeName);
    }

    private void updateTableHeader() {
        JTableHeader tableHeader = table.getTableHeader();
        TableColumnModel columnModel = tableHeader.getColumnModel();
        String[] header = Arrays.stream(Airport.getLocalizedAttributeNames()).filter(s -> !removedAttributes.contains(s)).toArray(String[]::new);
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn tableColumn = columnModel.getColumn(i);
            tableColumn.setHeaderValue(header[i]);
        }
        tableHeader.repaint();
        updateView();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void updateView() {
        while (tableModel.getRowCount() > 0)
            tableModel.removeRow(0);

        for (Airport airport : airports) {
            tableModel.addRow(airport.toArray());
        }
    }

    private void createUIComponents() {
        table = new JTable(tableModel);
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
        scrollPane = new JScrollPane();
        panel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(false);
        scrollPane.setViewportView(table);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
