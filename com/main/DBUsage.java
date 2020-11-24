package com.main;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;

public class DBUsage {
    public static void main(String[] args) {
        IP2Location loc = null;
        try {
            String ip = "8.8.8.8";
            String binfile = System.getProperty("user.dir")
                    + "/demoData/" +
                    "IP-COUNTRY-REGION-CITY-LATITUDE-LONGITUDE-SAMPLE.BIN";

            loc = new IP2Location();
            // loc.IPDatabasePath = binfile;
            loc.Open(binfile, true);

            IPResult rec = loc.IPQuery(ip);
            if ("OK".equals(rec.getStatus())) {
                System.out.println(rec);
            } else if ("EMPTY_IP_ADDRESS".equals(rec.getStatus())) {
                System.out.println("IP address cannot be blank.");
            } else if ("INVALID_IP_ADDRESS".equals(rec.getStatus())) {
                System.out.println("Invalid IP address.");
            } else if ("MISSING_FILE".equals(rec.getStatus())) {
                System.out.println("Invalid database path.");
            } else if ("IPV6_NOT_SUPPORTED".equals(rec.getStatus())) {
                System.out.println("This BIN does not contain IPv6 data.");
            } else {
                System.out.println("Unknown error." + rec.getStatus());
            }
            System.out.println("Java Component: " + rec.getVersion());
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace(System.out);
        } finally {
            loc.Close();
        }
    }
}
