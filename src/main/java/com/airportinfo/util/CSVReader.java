package com.airportinfo.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Read csv file. Need to close after use.
 *
 * @author lalaalal
 */
public class CSVReader implements AutoCloseable {
    private final BufferedReader reader;

    /**
     * Initialize reader with file path.
     *
     * @param path File path
     * @throws IOException If file not found
     */
    public CSVReader(String path) throws IOException {
        reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8));
    }

    /**
     * Initialize reader with input stream.
     *
     * @param inputStream Input stream
     */
    public CSVReader(InputStream inputStream) {
        reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    /**
     * Read a line split with ','.
     *
     * @return Array of String split by ','
     * @throws IOException If an I/O error occurs
     */
    public String[] read() throws IOException {
        String line = reader.readLine();
        if (line == null)
            return null;

        String[] tuple = line.split("\"");
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < tuple.length; i++) {
            if (tuple[i].length() == 0)
                continue;
            if (i % 2 == 0) {
                if (tuple[i].charAt(0) == ',')
                    tuple[i] = tuple[i].substring(1);
                String[] parts = tuple[i].split(",");
                if (parts.length > 0 && parts[0].length() == 0)
                    parts = Arrays.copyOfRange(parts, 1, parts.length);
                result.addAll(Arrays.stream(parts).toList());
            } else {
                result.add(tuple[i]);
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
