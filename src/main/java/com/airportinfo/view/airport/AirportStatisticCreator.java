package com.airportinfo.view.airport;

import com.airportinfo.model.Airport;
import com.airportinfo.view.chart.LegendList;

/**
 * Create LegendList by airports.
 *
 * @author lalaalal
 */
public interface AirportStatisticCreator {
    LegendList createStatistic(Iterable<Airport> airports);
}
