package com.airportinfo.swing;

import com.airportinfo.util.Translator;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * File chooser confirm overwrite. Use showFileChooser method.
 *
 * @author lalaalal
 */
public class CautiousFileChooser extends JFileChooser {
    public CautiousFileChooser() {
        setPreferredSize(new Dimension(700, 500));
    }

    /**
     * Show file chooser.
     *
     * @param parentComponent Parent component
     * @return The status user chosen
     * <ul>
     *     <li>JFileChooser.APPROVE_OPTION</li>
     *     <li>JFileChooser.CANCEL_OPTION</li>
     * </ul>
     */
    public int showSaveDialog(Component parentComponent) {
        setDialogTitle(Translator.getBundleString("save"));
        if (super.showSaveDialog(parentComponent) == JFileChooser.APPROVE_OPTION
                && canWrite(parentComponent, getSelectedFile())) {
            return JFileChooser.APPROVE_OPTION;
        }
        return JFileChooser.CANCEL_OPTION;
    }

    private boolean canWrite(Component parentComponent, File file) {
        if (getSelectedFile().exists()) {
            return LocalizedOptionPane.showConfirmDialog(parentComponent, "alert", "confirm_overwrite");
        }
        return true;
    }
}
