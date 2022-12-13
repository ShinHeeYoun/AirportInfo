package com.airportinfo.view.dialog;

import com.airportinfo.util.FontManager;
import com.airportinfo.util.Translator;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author lalaalal
 */
public class AboutDialogView extends DialogView {
    private JPanel panel;
    private JLabel headLabel;
    private JLabel nameLabel;

    public AboutDialogView() {
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setTitle(Translator.getBundleString("about"));
        headLabel.setFont(FontManager.getFont(FontManager.HEADER_FONT_SIZE));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void load() {

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
        panel.setLayout(new GridLayoutManager(2, 1, new Insets(15, 15, 15, 15), -1, 10));
        headLabel = new JLabel();
        headLabel.setText("고급객체지향프로그래밍 7조");
        panel.add(headLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameLabel = new JLabel();
        nameLabel.setText("손석현 신희윤 이창협 인홍렬 임군희");
        panel.add(nameLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
