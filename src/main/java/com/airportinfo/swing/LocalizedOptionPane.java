package com.airportinfo.swing;

import com.airportinfo.util.Translator;

import javax.swing.*;
import java.awt.*;

/**
 * Support localized JOptionPane dialogs.
 *
 * @author lalaalal
 */
public class LocalizedOptionPane {
    public static void showErrorMessageDialog(Component parentComponent, String message) {
        String dialogTitle = Translator.getBundleString("error");
        String dialogMessage = Translator.getBundleString(message);
        JOptionPane.showMessageDialog(parentComponent, dialogMessage, dialogTitle, JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoMessageDialog(Component parentComponent, String message) {
        String dialogTitle = Translator.getBundleString("info");
        String dialogMessage = Translator.getBundleString(message);
        JOptionPane.showMessageDialog(parentComponent, dialogMessage, dialogTitle, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirmDialog(Component parentComponent, String titleKey, String messageKey) {
        String dialogTitle = Translator.getBundleString(titleKey);
        String dialogMessage = Translator.getBundleString(messageKey);
        int result = JOptionPane.showConfirmDialog(parentComponent, dialogMessage, dialogTitle, JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION || result == JOptionPane.OK_OPTION;
    }
}
