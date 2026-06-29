package com.airportinfo.swing;

import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.model.Airport;
import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;
import com.airportinfo.view.AirportFrame;
import com.airportinfo.view.MainFrame;

import javax.swing.*;

public class AirportPopupMenu extends JPopupMenu {
    public AirportPopupMenu(MainFrame airportFrame, AirportController airportController, UserController userController, Airport selectedAirport) {
        setBorder(BorderFactory.createLineBorder(ThemeManager.getDefaultColor("Label.foreground")));

        JMenuItem showAirport = new JMenuItem(Translator.getBundleString("show_detail"));
        showAirport.addMouseListener(new MouseReleaseListener(mouseEvent -> {
            airportController.selectAirport(selectedAirport);
            userController.addRecent(selectedAirport);
            airportFrame.setContentView(AirportFrame.AIRPORT_DETAIL_VIEW);
        }));
        JMenuItem removeAirport = new JMenuItem(Translator.getBundleString("remove"));
        removeAirport.addMouseListener(new MouseReleaseListener(mouseEvent -> {
            boolean result = LocalizedOptionPane.showConfirmDialog(this, "alert", "are_you_sure");
            if (result)
                airportController.removeAirport(selectedAirport);
        }));

        add(showAirport);
        add(removeAirport);
    }
}
