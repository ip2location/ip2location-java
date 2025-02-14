package com.ip2location;

import com.opencsv.*;
import com.opencsv.exceptions.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class parses region information CSV and returns the region code.
 * <p>
 * Copyright (c) 2002-2025 IP2Location.com
 * <p>
 *
 * @author IP2Location.com
 * @version 8.12.1
 */
public class Region {
    private final Map<String, List<Map<String, String>>> records = new HashMap<>();

    /**
     * This constructor reads the region information CSV and store the parsed info.
     *
     * @param CSVFile The full path to the region information CSV file.
     */
    public Region(String CSVFile) throws IOException, CsvValidationException {
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
            if (line.containsKey("subdivision_name")) {
                String cc = line.get("country_code");
                if (!records.containsKey(cc)) {
                    records.put(cc, new ArrayList<>());
                }
                Map<String, String> item = new HashMap<>();
                item.put(line.get("subdivision_name").toUpperCase(), line.get("code"));
                records.get(cc).add(item);
            } else {
                throw new IOException("Invalid region information CSV file.");
            }
        }
    }

    /**
     * This function gets the region code for the supplied country code and region name.
     *
     * @param countryCode ISO-3166 country code
     * @param regionName  Region name
     * @return String     region code
     */
    public String getRegionCode(final String countryCode, final String regionName) throws IOException {
        if (records.isEmpty()) {
            throw new IOException("No record available.");
        } else {
            final List<Map<String, String>> items = records.get(countryCode);
            if (items == null) return null;

            final String region = regionName.toUpperCase();
            for (Map<String, String> item : items) {
                final String regionCode = item.get(region);
                if (regionCode != null) return regionCode;
            }
        }
        return null;
    }

    /**
     * @deprecated Use {{@link #getRegionCode(String, String)} instead.
     */
    @Deprecated
    public String GetRegionCode(final String countryCode, final String regionName) throws IOException {
        return getRegionCode(countryCode, regionName);
    }
}
