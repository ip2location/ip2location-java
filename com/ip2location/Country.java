package com.ip2location;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import com.opencsv.*;
import com.opencsv.exceptions.*;

/**
 * This class parses country information CSV and returns the country information.
 * <p>
 * Copyright (c) 2002-2025 IP2Location.com
 * <p>
 *
 * @author IP2Location.com
 * @version 8.12.1
 */
public class Country {
    private final Map<String, Map<String, String>> records = new HashMap<>();

    /**
     * This constructor reads the country information CSV and store the parsed info.
     *
     * @param CSVFile The full path to the country information CSV file.
     */
    public Country(String CSVFile) throws IOException, CsvValidationException {
        Map<String, String> line;
        File file = new File(CSVFile);
        if (!file.exists()) {
            throw new IOException("The CSV file '" + CSVFile + "' is not found.");
        }
        FileReader fr = new FileReader(file);
        if (fr.read() == -1) {
            throw new IOException("Unable to read '" + CSVFile + "'.");
        }
        CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new FileReader(file));
        while ((line = reader.readMap()) != null) {
            if (line.containsKey("country_code")) {
                records.put(line.get("country_code"), line);
            } else {
                throw new IOException("Invalid country information CSV file.");
            }
        }
    }

    /**
     * This function gets the country information for the supplied country code.
     *
     * @param CountryCode ISO-3166 country code
     * @return Map
     */
    public Map<String, String> GetCountryInfo(String CountryCode) throws IOException {
        if (records.isEmpty()) {
            throw new IOException("No record available.");
        } else {
            return records.getOrDefault(CountryCode, null);
        }
    }

    /**
     * This function gets the country information for all countries.
     *
     * @return List
     */
    public List<Map<String, String>> GetCountryInfo() throws IOException {
        List<Map<String, String>> results = new ArrayList<>();
        if (records.isEmpty()) {
            throw new IOException("No record available.");
        } else {
            for (Map.Entry<String, Map<String, String>> entry : records.entrySet()) {
                results.add(entry.getValue());
            }
        }
        return results;
    }

}