package com.airportinfo.view.chart;

import com.airportinfo.view.ComponentView;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Abstract class of ChartView.
 * Provide legend interface and support color scheme, number format.
 * The method paintChart(Graphics) will draw in chartPanel.
 *
 * @author lalaalal
 */
public abstract class AbstractChartView extends ComponentView {
    public static final Color[] DEFAULT_COLOR_SCHEME = {Color.decode("#FF8787"), Color.decode("#F8C4B4"), Color.decode("#E5EBB2"), Color.decode("#BCE29E"), Color.decode("#B8E8FC"), Color.decode("#B1AFFF"), Color.decode("#C8FFD4"), Color.decode("#DFD3C3"), Color.decode("#F8EDE3"), Color.decode("#AEBDCA")};
    protected static final String DEFAULT_TITLE = "default_chart_title";
    protected final LegendList legends = new LegendList();
    private final LegendDetailGroupView legendDetailGroupView = new LegendDetailGroupView(this);
    private final HashMap<String, Integer> legendColor = new HashMap<>();
    protected NumberFormat numberFormat = NumberFormat.INT_FORMAT;
    private Color[] colorScheme = DEFAULT_COLOR_SCHEME;
    public boolean showLegendLabel = true;
    protected String title;

    /**
     * Initialize chart with title.
     *
     * @param title Key of translation or literally
     */
    public AbstractChartView(String title) {
        this.title = title;
        addLocaleChangeListener(locale -> updateTitle());
    }

    /**
     * Update
     */
    protected abstract void updateTitle();

    /**
     * @return Chart panel type with JPanel.
     */
    protected JPanel createChartPanel() {
        return new ChartPanel();
    }

    /**
     * @return Legend detail panel.
     */
    protected JPanel getLegendDetailPanel() {
        return legendDetailGroupView.getPanel();
    }

    /**
     * Change color scheme.
     *
     * @param colorScheme Color scheme to change.
     */
    public void setColorScheme(Color[] colorScheme) {
        if (colorScheme != null) {
            this.colorScheme = colorScheme;
            legendDetailGroupView.updateColor();
            getPanel().repaint();
        }
    }

    /**
     * Get nth color. Cycle if index is bigger than length of color scheme.
     *
     * @param index Index of color scheme
     * @return Nth color
     */
    public Color getColor(int index) {
        return colorScheme[index % colorScheme.length];
    }

    /**
     * Get color with legend name.
     *
     * @param legendName Legend name to search
     * @return Color allocated to legend name
     */
    public Color getColor(String legendName) {
        int colorIndex = legendColor.get(legendName);
        return getColor(colorIndex);
    }

    /**
     * Get array of legends.
     *
     * @return Array of legends.
     */
    public Legend[] getLegends() {
        return legends.toArray(new Legend[0]);
    }

    /**
     * Set legends if different with this chart.
     *
     * @param legends New legends
     */
    public void setLegends(Iterable<? extends Legend> legends) {
        clear();
        for (Legend legend : legends)
            addLegend(legend);

        getPanel().revalidate();
        getPanel().repaint();
    }

    /**
     * Change number format.
     *
     * @param numberFormat Number format to change
     */
    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    /**
     * Add new Legend with name, value. Allocate color with name.
     * Maybe need to revalidate and repaint panel.
     * DO NOT OVERRIDE, OVERRIDE addLegend(Legend)
     *
     * @param name  Legend name
     * @param value Legend value
     */
    public void addLegend(String name, Number value) {
        addLegend(new Legend(name, value));
    }

    /**
     * Add new Legend. Allocate color with name.
     * Maybe need to revalidate and repaint panel.
     *
     * @param legend Legend
     */
    public void addLegend(Legend legend) {
        legends.add(legend);

        int colorIndex = legends.size() - 1;
        legendColor.put(legend.name(), colorIndex);

        legendDetailGroupView.addLegend(legend.name());
    }

    /**
     * Number of legends.
     *
     * @return Number of legends
     */
    public int getNumLegends() {
        return legends.size();
    }

    /**
     * Remove all chart data.
     */
    public void clear() {
        legends.clear();
        legendColor.clear();
        legendDetailGroupView.clear();
    }

    /**
     * Draw chart using chartPanel's Graphics when ChartPanel.paintComponent(Graphics).
     *
     * @param graphics Graphics from chartPanel
     */
    protected abstract void paintChart(Graphics graphics);

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractChartView abstractChartView) {
            return legends.equals(abstractChartView.legends)
                    && Arrays.equals(colorScheme, abstractChartView.colorScheme);
        }
        return false;
    }

    private class ChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            paintChart(graphics);
        }
    }
}
