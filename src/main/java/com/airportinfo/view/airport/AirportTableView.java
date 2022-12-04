package com.airportinfo.view.airport;

import com.airportinfo.misc.UneditableTableModel;
import com.airportinfo.model.Airport;
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

    public AirportTableView() {
        $$$setupUI$$$();
        addLocaleChangeListener((locale) -> updateTable());
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
        TableColumn tableColumn = table.getColumn("IATA");
        int row = table.getSelectedRow();
        int column = tableColumn.getModelIndex();
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
        TableColumn tableColumn = table.getColumn("IATA");
        int[] rows = table.getSelectedRows();
        int column = tableColumn.getModelIndex();
        for (int row : rows) {
            Optional<Airport> optional = airports.stream().filter(airport -> airport.getIATA() == table.getValueAt(row, column)).findFirst();
            optional.ifPresent(result::add);
        }
        return result;
    }

    private void updateTable() {
        JTableHeader tableHeader = table.getTableHeader();
        TableColumnModel columnModel = tableHeader.getColumnModel();
        String[] header = Airport.getLocalizedAttributeNames();
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
