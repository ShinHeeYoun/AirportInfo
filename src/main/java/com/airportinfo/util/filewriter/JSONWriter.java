package com.airportinfo.util.filewriter;

import com.airportinfo.model.Airport;
import com.airportinfo.model.RawAirport;

import java.io.IOException;

/**
 * A writer class to write json files.
 *
 * @author JumoKookbob
 * @author lalaalal
 */
class JSONWriter extends AirportWriter {
    private boolean isFirstAirport = true;

    /**
     * constructor method
     *
     * @param fPath file path
     * @throws IOException If an I/O error occurs
     */
    public JSONWriter(String fPath) throws IOException {
        super(fPath);
        writer.write("[");
    }

    private void write(String[] keys, String[] values) throws IOException {
        if (!isFirstAirport)
            writer.write(",");
        else
            isFirstAirport = false;

        writer.write("{");
        for (int i = 0; i < keys.length; i++) {
            String airportString = String.format("\"%s\":\"%s\"", keys[i], values[i]);
            if (i != keys.length - 1)
                airportString += ",";
            writer.write(airportString);
        }
        writer.write("}");
    }

    @Override
    public void write(Airport airport) throws IOException {
        String[] keys = Airport.ATTRIBUTE_NAMES;
        String[] values = airport.toArray();

        write(keys, values);
    }

    @Override
    public void writeRawAirport(Airport airport) throws IOException {
        String[] keys = RawAirport.ATTRIBUTE_NAMES;
        String[] values = airport.getRawData().toArray();

        write(keys, values);
    }

    @Override
    public void write(Airport[] airports) throws IOException {
        for (Airport airport : airports)
            write(airport);
    }

    @Override
    public void writeRawAirports(Airport[] airports) throws IOException {
        String[] keys = RawAirport.ATTRIBUTE_NAMES;

        for (Airport airport : airports)
            write(keys, airport.getRawData().toArray());
    }

    @Override
    public void close() throws IOException {
        writer.write("]");
        super.close();
    }
}
