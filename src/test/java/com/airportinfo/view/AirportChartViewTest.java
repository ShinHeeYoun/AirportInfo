package com.airportinfo.view;

import com.airportinfo.controller.AirportController;
import com.airportinfo.model.Airport;
import com.airportinfo.view.airport.AirportChartView;
import com.airportinfo.view.airport.AttributeStatisticCreator;
import com.airportinfo.view.chart.HistogramView;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class AirportChartViewTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, NoSuchMethodException {
        Locale.setDefault(Locale.ENGLISH);
        AirportController airportController = new AirportController();
        airportController.loadFromDB();

        MainFrame mainFrame = new AirportFrame();
        TestContentView contentView = new TestContentView(mainFrame);
        HistogramView histogramView = new HistogramView();
        AirportChartView airportChartView = new AirportChartView(histogramView, new AttributeStatisticCreator(Airport::getRegion));
        contentView.setComponent(airportChartView);
        airportChartView.setAirports(List.of(airportController.getAirports()));

        mainFrame.addContentView("AirportChartView", contentView);
        mainFrame.setContentView("AirportChartView");

        mainFrame.showFrame();
        mainFrame.load();
    }
}
