package com.main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ip2location.IP2LocationWebService;

public class WebUsage {
    public static void main(String[] args)
    {
        try
        {
            IP2LocationWebService ws = new IP2LocationWebService();

            String strIPAddress = "8.8.8.8";
            String strAPIKey = "YOUR_API_KEY_HERE";
            String strPackage = "WS24";
            String[] addOn = {"continent", "country", "region", "city", "geotargeting", "country_groupings", "time_zone_info"};
            String strLang = "es";
            boolean boolSSL = true;

            ws.Open(strAPIKey, strPackage, boolSSL);

            JsonObject myresult = ws.IPQuery(strIPAddress, addOn, strLang);

            if (myresult.get("response") == null)
            {
                // standard results
                System.out.println("country_code: " + ((myresult.get("country_code") != null) ? myresult.get("country_code").getAsString() : ""));
                System.out.println("country_name: " + ((myresult.get("country_name") != null) ? myresult.get("country_name").getAsString() : ""));
                System.out.println("region_name: " + ((myresult.get("region_name") != null) ? myresult.get("region_name").getAsString() : ""));
                System.out.println("city_name: " + ((myresult.get("city_name") != null) ? myresult.get("city_name").getAsString() : ""));
                System.out.println("latitude: " + ((myresult.get("latitude") != null) ? myresult.get("latitude").getAsString() : ""));
                System.out.println("longitude: " + ((myresult.get("longitude") != null) ? myresult.get("longitude").getAsString() : ""));
                System.out.println("zip_code: " + ((myresult.get("zip_code") != null) ? myresult.get("zip_code").getAsString() : ""));
                System.out.println("time_zone: " + ((myresult.get("time_zone") != null) ? myresult.get("time_zone").getAsString() : ""));
                System.out.println("isp: " + ((myresult.get("isp") != null) ? myresult.get("isp").getAsString() : ""));
                System.out.println("domain: " + ((myresult.get("domain") != null) ? myresult.get("domain").getAsString() : ""));
                System.out.println("net_speed: " + ((myresult.get("net_speed") != null) ? myresult.get("net_speed").getAsString() : ""));
                System.out.println("idd_code: " + ((myresult.get("idd_code") != null) ? myresult.get("idd_code").getAsString() : ""));
                System.out.println("area_code: " + ((myresult.get("area_code") != null) ? myresult.get("area_code").getAsString() : ""));
                System.out.println("weather_station_code: " + ((myresult.get("weather_station_code") != null) ? myresult.get("weather_station_code").getAsString() : ""));
                System.out.println("weather_station_name: " + ((myresult.get("weather_station_name") != null) ? myresult.get("weather_station_name").getAsString() : ""));
                System.out.println("mcc: " + ((myresult.get("mcc") != null) ? myresult.get("mcc").getAsString() : ""));
                System.out.println("mnc: " + ((myresult.get("mnc") != null) ? myresult.get("mnc").getAsString() : ""));
                System.out.println("mobile_brand: " + ((myresult.get("mobile_brand") != null) ? myresult.get("mobile_brand").getAsString() : ""));
                System.out.println("elevation: " + ((myresult.get("elevation") != null) ? myresult.get("elevation").getAsString() : ""));
                System.out.println("usage_type: " + ((myresult.get("usage_type") != null) ? myresult.get("usage_type").getAsString() : ""));
                System.out.println("credits_consumed: " + ((myresult.get("credits_consumed") != null) ? myresult.get("credits_consumed").getAsString() : ""));

                // continent addon
                if (myresult.get("continent") != null)
                {
                    JsonObject continentObj = myresult.getAsJsonObject("continent");
                    System.out.println("continent => name: " + continentObj.get("name").getAsString());
                    System.out.println("continent => code: " + continentObj.get("code").getAsString());
                    JsonArray myarr = continentObj.getAsJsonArray("hemisphere");
                    System.out.println("continent => hemisphere: " + myarr.toString());
                    System.out.println("continent => translations: " + continentObj.getAsJsonObject("translations").get(strLang).getAsString());
                }

                // country addon
                if (myresult.get("country") != null)
                {
                    JsonObject countryObj = myresult.getAsJsonObject("country");
                    System.out.println("country => name: " + countryObj.get("name").getAsString());
                    System.out.println("country => alpha3_code: " + countryObj.get("alpha3_code").getAsString());
                    System.out.println("country => numeric_code: " + countryObj.get("numeric_code").getAsString());
                    System.out.println("country => demonym: " + countryObj.get("demonym").getAsString());
                    System.out.println("country => flag: " + countryObj.get("flag").getAsString());
                    System.out.println("country => capital: " + countryObj.get("capital").getAsString());
                    System.out.println("country => total_area: " + countryObj.get("total_area").getAsString());
                    System.out.println("country => population: " + countryObj.get("population").getAsString());
                    System.out.println("country => idd_code: " + countryObj.get("idd_code").getAsString());
                    System.out.println("country => tld: " + countryObj.get("tld").getAsString());
                    System.out.println("country => translations: " + countryObj.getAsJsonObject("translations").get(strLang).getAsString());

                    JsonObject currencyObj = countryObj.getAsJsonObject("currency");
                    System.out.println("country => currency => code: " + currencyObj.get("code").getAsString());
                    System.out.println("country => currency => name: " + currencyObj.get("name").getAsString());
                    System.out.println("country => currency => symbol: " + currencyObj.get("symbol").getAsString());

                    JsonObject languageObj = countryObj.getAsJsonObject("language");
                    System.out.println("country => language => code: " + languageObj.get("code").getAsString());
                    System.out.println("country => language => name: " + languageObj.get("name").getAsString());
                }

                // region addon
                if (myresult.get("region") != null)
                {
                    JsonObject regionObj = myresult.getAsJsonObject("region");
                    System.out.println("region => name: " + regionObj.get("name").getAsString());
                    System.out.println("region => code: " + regionObj.get("code").getAsString());
                    System.out.println("region => translations: " + regionObj.getAsJsonObject("translations").get(strLang).getAsString());
                }

                // city addon
                if (myresult.get("city") != null)
                {
                    JsonObject cityObj = myresult.getAsJsonObject("city");
                    System.out.println("city => name: " + cityObj.get("name").getAsString());
                    System.out.println("city => translations: " + cityObj.getAsJsonArray("translations").toString());
                }

                // geotargeting addon
                if (myresult.get("geotargeting") != null)
                {
                    JsonObject geoObj = myresult.getAsJsonObject("geotargeting");
                    System.out.println("geotargeting => metro: " + geoObj.get("metro").getAsString());
                }

                // country_groupings addon
                if (myresult.get("country_groupings") != null)
                {
                    JsonArray myarr = myresult.getAsJsonArray("country_groupings");
                    if (myarr.size() > 0)
                    {
                        for (int x = 0; x < myarr.size(); x++)
                        {
                            System.out.println("country_groupings => #" + x + " => acronym: " + myarr.get(x).getAsJsonObject().get("acronym").getAsString());
                            System.out.println("country_groupings => #" + x + " => name: " + myarr.get(x).getAsJsonObject().get("name").getAsString());
                        }
                    }
                }

                // time_zone_info addon
                if (myresult.get("time_zone_info") != null)
                {
                    JsonObject tzObj = myresult.getAsJsonObject("time_zone_info");
                    System.out.println("time_zone_info => olson: " + tzObj.get("olson").getAsString());
                    System.out.println("time_zone_info => current_time: " + tzObj.get("current_time").getAsString());
                    System.out.println("time_zone_info => gmt_offset: " + tzObj.get("gmt_offset").getAsString());
                    System.out.println("time_zone_info => is_dst: " + tzObj.get("is_dst").getAsString());
                    System.out.println("time_zone_info => sunrise: " + tzObj.get("sunrise").getAsString());
                    System.out.println("time_zone_info => sunset: " + tzObj.get("sunset").getAsString());
                }
            }
            else
            {
                System.out.println("Error: " + myresult.get("response").getAsString());
            }

            myresult = ws.GetCredit();

            if (myresult.get("response") != null)
            {
                System.out.println("Credit balance: " + myresult.get("response").getAsString());
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace(System.out);
        }
    }
}
