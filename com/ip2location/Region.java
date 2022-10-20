package com.ip2location;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.File;
import com.opencsv.*;
import com.opencsv.exceptions.*;

/**
 * This class parses region information CSV and returns the region code.
 * <p>
 * <b>Requirements:</b> Java SDK 1.4 or later<br>
 * <p>
 * Copyright (c) 2002-2022 IP2Location.com
 * <p>
 *
 * @author IP2Location.com
 * @version 8.10.0
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
     * @param CountryCode ISO-3166 country code
     * @param RegionName  Region name
     * @return String
     */
    public String GetRegionCode(String CountryCode, String RegionName) throws IOException {
        if (records.isEmpty()) {
            throw new IOException("No record available.");
        } else {
            if (!records.containsKey(CountryCode)) {
                return null;
            }
            List<Map<String, String>> items = records.get(CountryCode);

            for (Map<String, String> item : items) {
                if (item.containsKey(RegionName.toUpperCase())) {
                    return item.get(RegionName.toUpperCase());
                }
            }
        }
        return null;
    }

}