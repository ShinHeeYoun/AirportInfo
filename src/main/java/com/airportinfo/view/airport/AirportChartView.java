package com.airportinfo.view.airport;

import com.airportinfo.view.chart.AbstractChartView;
import com.airportinfo.view.chart.LegendList;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

/**
 * Airport view showing chart.
 *
 * @author lalaalal
 */
public class AirportChartView extends AirportView {
    private JPanel panel;
    private AbstractChartView chartView;
    private AirportStatisticCreator statisticCreator;

    public AirportChartView(AbstractChartView chartView, AirportStatisticCreator statisticCreator) {
        this.statisticCreator = statisticCreator;

        setChartView(chartView);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void updateView() {
        LegendList legends = statisticCreator.createStatistic(airports);
        chartView.setLegends(legends);
    }

    /**
     * Change chart view.
     *
     * @param chartView New chart view
     */
    public void setChartView(AbstractChartView chartView) {
        this.chartView = chartView;
        panel.removeAll();
        panel.add(chartView.getPanel(), BorderLayout.CENTER);

        updateView();
    }

    /**
     * Change the way to create statistics.
     *
     * @param statisticCreator New airport statistic creator
     */
    public void setStatisticCreator(AirportStatisticCreator statisticCreator) {
        this.statisticCreator = statisticCreator;

        updateView();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(0, 0));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
