package com.airportinfo.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * BarChartView shows chart with bar (histogram).
 *
 * @author lalaalal
 */
public class BarChartView extends AbstractChartView {
    private final ArrayList<ChartBar> chartBars = new ArrayList<>();
    private JPanel panel;
    private JLabel titleLabel;
    private JPanel chartPanel;
    private JPanel entryPanel;
    private JPanel axisPanel;
    private double max = 0.0;
    private int numEntry = 0;

    public BarChartView() {
        $$$setupUI$$$();
    }

    public BarChartView(String title) {
        $$$setupUI$$$();
        titleLabel.setText(title);
    }

    /**
     * Change number format and update chartBars labels.
     *
     * @param numberFormat Number format to change
     */
    @Override
    public void setNumberFormat(NumberFormat numberFormat) {
        super.setNumberFormat(numberFormat);
        for (ChartBar chartBar : chartBars)
            chartBar.updateLabel(numberFormat);
    }

    @Override
    public void addEntry(String name, Number value) {
        updateAxis(value);

        ChartBar bar = new ChartBar(value, max, numberFormat, getColor(numEntry));
        chartPanel.add(bar);
        chartBars.add(bar);

        JLabel label = new JLabel(name);
        label.setHorizontalAlignment(JLabel.CENTER);
        entryPanel.add(label);

        numEntry += 1;
    }

    @Override
    public void clear() {
        chartPanel.removeAll();
        entryPanel.removeAll();
        axisPanel.removeAll();
        chartBars.clear();
    }

    private double calcMax(Number value) {
        int digit = (int) Math.log10(value.doubleValue());
        int firstDigit = (int) (value.doubleValue() / Math.pow(10, digit));
        return (firstDigit + 1) * Math.pow(10, digit);
    }

    private void updateAxis(Number value) {
        double prevMax = max;
        max = Math.max(max, calcMax(value));

        if (prevMax == max)
            return;

        for (ChartBar chartBar : chartBars)
            chartBar.updateMax(max);

        updateAxisPanel(value);
    }

    private void updateAxisPanel(Number value) {
        axisPanel.removeAll();
        int digit = (int) Math.log10(value.doubleValue());
        int firstDigit = (int) (value.doubleValue() / Math.pow(10, digit));

        for (int i = firstDigit; i >= 0; i--) {
            Number unit = i * Math.pow(10, digit);
            JLabel unitLabel = createLabel(unit);
            unitLabel.setVerticalAlignment(JLabel.BOTTOM);
            axisPanel.add(unitLabel);
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    private void createUIComponents() {
        GridLayout gridLayout = new GridLayout(1, 0);
        GridLayout axisGridLayout = new GridLayout(0, 1);
        chartPanel = new JPanel();
        chartPanel.setLayout(gridLayout);
        Color borderColor = UIManager.getDefaults().getColor("Label.foreground");
        chartPanel.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        entryPanel = new JPanel();
        entryPanel.setLayout(gridLayout);
        axisPanel = new JPanel();
        axisPanel.setLayout(axisGridLayout);
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
        panel.setLayout(new BorderLayout(0, 0));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(10, 0, 10, 0), -1, -1));
        panel.add(panel1, BorderLayout.NORTH);
        titleLabel = new JLabel();
        this.$$$loadLabelText$$$(titleLabel, this.$$$getMessageFromBundle$$$("string", "default_chart_title"));
        panel1.add(titleLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel.add(panel2, BorderLayout.CENTER);
        panel2.add(chartPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.add(entryPanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel2.add(axisPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    private static Method $$$cachedGetBundleMethod$$$ = null;

    private String $$$getMessageFromBundle$$$(String path, String key) {
        ResourceBundle bundle;
        try {
            Class<?> thisClass = this.getClass();
            if ($$$cachedGetBundleMethod$$$ == null) {
                Class<?> dynamicBundleClass = thisClass.getClassLoader().loadClass("com.intellij.DynamicBundle");
                $$$cachedGetBundleMethod$$$ = dynamicBundleClass.getMethod("getBundle", String.class, Class.class);
            }
            bundle = (ResourceBundle) $$$cachedGetBundleMethod$$$.invoke(null, path, thisClass);
        } catch (Exception e) {
            bundle = ResourceBundle.getBundle(path);
        }
        return bundle.getString(key);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
