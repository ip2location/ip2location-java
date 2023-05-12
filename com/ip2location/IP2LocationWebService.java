package com.ip2location;

import java.nio.charset.StandardCharsets;
import java.net.URL;
import java.net.URLEncoder;
import java.lang.StringBuffer; // JDK 1.4 does not support StringBuilder so can't use that
import java.util.regex.Pattern;

import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

/**
 * This class performs the lookup of IP2Location data from an IP address by querying the IP2Location Web Service.
 * <p>
 * Example usage scenarios:
 * <ul>
 *   <li>Redirect based on country</li>
 *   <li>Digital rights management</li>
 *   <li>Web log stats and analysis</li>
 *   <li>Auto-selection of country on forms</li>
 *   <li>Filter access from countries you do not do business with</li>
 *   <li>Geo-targeting for increased sales and click-through</li>
 *   <li>And much, much more!</li>
 * </ul>
 * <p>
 * Copyright (c) 2002-2023 IP2Location.com
 * <p>
 *
 * @author IP2Location.com
 * @version 8.11.1
 */
public class IP2LocationWebService {
    
    private static final Pattern API_KEY_PATTERN = Pattern.compile("^[\\dA-Z]{10,}$");
    private static final Pattern PACKAGE_PATTERN = Pattern.compile("^WS\\d+$");
    private static final String API_IP_2_LOCATION_V_2_HOST = "api.ip2location.com/v2/";
    private static final String API_IP_2_LOCATION_IO_HOST = "api.ip2location.io/";
    private static final String HTTPS = "https";
    private static final String HTTP = "http";

    private final JsonParser jsonParser;

    private String _APIKey = "";
    private String _Package = "";
    private boolean _UseSSL = true;

    public IP2LocationWebService() {
        this.jsonParser = new JsonParser();
    }

    /**
     * This function initializes the params for the web service.
     *
     * @param APIKey  IP2Location Web Service API key
     * @param Package IP2Location Web Service package (WS1 to WS25)
     * @throws IllegalArgumentException If an invalid parameter is specified
     */
    public void Open(String APIKey, String Package) throws IllegalArgumentException {
        Open(APIKey, Package, true);
    }

    /**
     * This function initializes the params for the web service.
     *
     * @param APIKey  IP2Location Web Service API key
     * @param Package IP2Location Web Service package (WS1 to WS25)
     * @param UseSSL  Set to true to call the web service using SSL
     * @throws IllegalArgumentException If an invalid parameter is specified
     */
    public void Open(String APIKey, String Package, boolean UseSSL) throws IllegalArgumentException {
        _APIKey = APIKey;
        _Package = Package;
        _UseSSL = UseSSL;

        CheckParams();
    }

    /**
     * This function validates the API key and package params.
     */
    private void CheckParams() throws IllegalArgumentException {
        if ((!API_KEY_PATTERN.matcher(_APIKey).matches()) && (!_APIKey.equals("demo"))) {
            throw new IllegalArgumentException("Invalid API key.");
        } else if (!PACKAGE_PATTERN.matcher(_Package).matches()) {
            throw new IllegalArgumentException("Invalid package name.");
        }
    }

    /**
     * This function to query IP2Location data.
     *
     * @param IPAddress IP Address you wish to query
     * @return IP2Location data
     * @throws IllegalArgumentException If an invalid parameter is specified
     * @throws RuntimeException         If an exception occurred at runtime
     */
    public JsonObject IPQuery(String IPAddress) throws IllegalArgumentException, RuntimeException {
        return IPQuery(IPAddress, null, "en");
    }

    /**
     * This function to query IP2Location data.
     *
     * @param IPAddress IP Address you wish to query
     * @param Language  The translation language
     * @return IP2Location data
     * @throws IllegalArgumentException If an invalid parameter is specified
     * @throws RuntimeException         If an exception occurred at runtime
     */
    public JsonObject IPQuery(String IPAddress, String Language) throws IllegalArgumentException, RuntimeException {
        return IPQuery(IPAddress, null, Language);
    }

    /**
     * This function to query IP2Location data.
     *
     * @param IPAddress IP Address you wish to query
     * @param AddOns    The list of AddOns results to return
     * @return IP2Location data
     * @throws IllegalArgumentException If an invalid parameter is specified
     * @throws RuntimeException         If an exception occurred at runtime
     */
    public JsonObject IPQuery(String IPAddress, String[] AddOns) throws IllegalArgumentException, RuntimeException {
        return IPQuery(IPAddress, API_IP_2_LOCATION_IO_HOST, AddOns, null);
    }

    /**
     * This function to query IP2Location data.
     *
     * @param IPAddress IP Address you wish to query
     * @param AddOns    The list of AddOns results to return
     * @param Language  The translation language
     * @return IP2Location data
     * @throws IllegalArgumentException If an invalid parameter is specified
     * @throws RuntimeException         If an exception occurred at runtime
     */
    public JsonObject IPQuery(String IPAddress, String[] AddOns, String Language) throws IllegalArgumentException, RuntimeException {
        return IPQuery(IPAddress, API_IP_2_LOCATION_V_2_HOST, AddOns, Language);
    }

    /**
     * This function to query IP2Location data.
     *
     * @param IPAddress IP Address you wish to query
     * @param AddOns    The list of AddOns results to return
     * @param Language  The translation language
     * @return IP2Location data
     * @throws IllegalArgumentException If an invalid parameter is specified
     * @throws RuntimeException         If an exception occurred at runtime
     */
    private JsonObject IPQuery(String IPAddress, String Host, String[] AddOns, String Language) throws IllegalArgumentException, RuntimeException {
        try {
            CheckParams(); // check here in case user haven't called Open yet

            String builtUrl = buildUrl(IPAddress, Host, AddOns, Language);
            String response = Http.get(new URL(builtUrl));
            
            return jsonParser.parse(response).getAsJsonObject();
        } catch (IllegalArgumentException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private String buildUrl(String IPAddress, String Host, String[] AddOns, String Language) {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer
            .append(_UseSSL ? HTTPS : HTTP)
            .append("://")
            .append(Host)
            .append("?key=")
            .append(_APIKey)
            .append("&package=")
            .append(_Package)
            .append("&ip=")
            .append(URLEncoder.encode(IPAddress, StandardCharsets.UTF_8));

        if (Language != null) {
            stringBuffer
                .append("&lang=")
                .append(URLEncoder.encode(Language, StandardCharsets.UTF_8));
        }

        if ((AddOns != null) && (AddOns.length > 0)) {
            stringBuffer
                .append("&addon=")
                .append(URLEncoder.encode(StringUtils.join(AddOns, ","), StandardCharsets.UTF_8));
        }
        return stringBuffer.toString();
    }

    /**
     * This function to check web service credit balance.
     *
     * @return Credit balance
     * @throws IllegalArgumentException If an invalid parameter is specified
     * @throws RuntimeException         If an exception occurred at runtime
     */
    public JsonObject GetCredit() throws IllegalArgumentException, RuntimeException {
        try {
            CheckParams(); // check here in case user haven't called Open yet

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer
                .append(_UseSSL ? HTTPS : HTTP)
                .append("://")
                .append(API_IP_2_LOCATION_V_2_HOST)
                .append("?key=")
                .append(_APIKey)
                .append("&check=true");

            String response = Http.get(new URL(stringBuffer.toString()));

            return jsonParser.parse(response).getAsJsonObject();
        } catch (IllegalArgumentException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}