# IP2Location IP Geolocation Java Component

This IP Geolocation Java component allows user to query an IP address for info such as the visitor’s country, region, city, ISP or company name. In addition, users can also determine extra useful geolocation information such as latitude, longitude, ZIP code, domain name, time zone, connection speed, IDD code, area code, weather station code, weather station name, MCC, MNC, mobile brand name, elevation, usage type, address type, IAB category, district, autonomous system number (ASN) and autonomous system (AS). It lookup the IP address from **IP2Location BIN Data** file. This data file can be downloaded at

* Free IP2Location IP Geolocation BIN Data: https://lite.ip2location.com
* Commercial IP2Location IP Geolocation BIN Data: https://www.ip2location.com/database/ip2location

As an alternative, this IP Geolocation Java component can also call the IP2Location Web Service. This requires an API key. If you don't have an existing API key, you can subscribe for one at the below:

https://www.ip2location.com/web-service/ip2location

## Compilation

```bash
javac com/ip2location/*.java
jar cf ip2location.jar com/ip2location/*.class
```

## QUERY USING THE BIN FILE

## Parameters
Below are the parameters to set before using this class.

|Parameter Name|Description|
|---|---|
|IPDatabasePath|Sets the IP2Location database path.|
|UseMemoryMappedFile|Set to true to enable memory mapped file feature. This will increase query speed but require more memory. It is set to false by default.|

## Methods
Below are the methods supported in this class.

|Method Name|Description|
|---|---|
|Open(String DBPath, boolean UseMMF)|Initialize the component and preload the BIN file.|
|Open(String DBPath)|Initialize the component and preload the BIN file.|
|Open(byte[] db)|Initialize the component with the byte array containing the BIN file data.|
|IPQuery(String IPAddress)|Query IP address. This method returns results in com.ip2location.IPResult object.|
|Close()|Destroys the mapped bytes.|

## Result methods
Below are the result methods.

|Method Name|Description|
|---|---|
|getCountryShort|Two-character country code based on ISO 3166.|
|getCountryLong|Country name based on ISO 3166.|
|getRegion|Region or state name.|
|getCity|City name.|
|getLatitude|City level latitude.|
|getLongitude|City level longitude.|
|getZipCode|ZIP code or postal code.|
|getTimeZone|Time zone in UTC (Coordinated Universal Time).|
|getISP|Internet Service Provider (ISP) name.|
|getDomain|Domain name associated to IP address range.|
|getNetSpeed|Internet connection speed <ul><li>(DIAL) Dial-up</li><li>(DSL) DSL/Cable</li><li>(COMP) Company/T1</li></ul>|
|getIDDCode|The IDD prefix to call the city from another country.|
|getAreaCode|A varying length number assigned to geographic areas for call between cities.|
|getWeatherStationCode|Special code to identify the nearest weather observation station.|
|getWeatherStationName|Name of the nearest weather observation station.|
|getMCC|Mobile country code.|
|getMNC|Mobile network code.|
|getMobileBrand|Mobile carrier brand.|
|getElevation|Average height of city above sea level in meters (m).|
|getUsageType|Usage type classification of ISP or company:<ul><li>(COM) Commercial</li><li>(ORG) Organization</li><li>(GOV) Government</li><li>(MIL) Military</li><li>(EDU) University/College/School</li><li>(LIB) Library</li><li>(CDN) Content Delivery Network</li><li>(ISP) Fixed Line ISP</li><li>(MOB) Mobile ISP</li><li>(DCH) Data Center/Web Hosting/Transit</li><li>(SES) Search Engine Spider</li><li>(RSV) Reserved</li></ul>|
|getAddressType|IP address types as defined in Internet Protocol version 4 (IPv4) and Internet Protocol version 6 (IPv6).<ul><li>(A) Anycast - One to the closest</li><li>(U) Unicast - One to one</li><li>(M) Multicast - One to multiple</li><li>(B) Broadcast - One to all</li></ul>|
|getCategory|The domain category is based on [IAB Tech Lab Content Taxonomy](https://www.ip2location.com/free/iab-categories). These categories are comprised of Tier-1 and Tier-2 (if available) level categories widely used in services like advertising, Internet security and filtering appliances.|
|getDistrict|District.|
|getASN|Autonomous System Number.|
|getAS|Autonomous System.|
|getStatus|Status code of query.|

## Status codes
Below are the status codes.
|Code|Description|
|---|---|
|OK|The query has been successfully performed.|
|EMPTY_IP_ADDRESS|The IP address is empty.|
|INVALID_IP_ADDRESS|The format of the IP address is wrong.|
|MISSING_FILE|The BIN file path is wrong or the BIN file is unreadable.|
|IP_ADDRESS_NOT_FOUND|The IP address does not exists in the BIN file.|
|IPV6_NOT_SUPPORTED|The BIN file does not contain IPv6 data.|

## Usage

```java
import com.ip2location.*;
// import java.nio.file.*;

public class Main 
{
	public Main() 
	{
	}
	public static void main(String[] args) 
	{
		try
		{
			String ip = "8.8.8.8";
			String binfile = "/usr/data/IP-COUNTRY-REGION-CITY-LATITUDE-LONGITUDE-ZIPCODE-TIMEZONE-ISP-DOMAIN-NETSPEED-AREACODE-WEATHER-MOBILE-ELEVATION-USAGETYPE-ADDRESSTYPE-CATEGORY-DISTRICT-ASN.BIN";
			
			IP2Location loc = new IP2Location();
			
			// this is to initialize with a BIN file
			loc.Open(binfile, true);
			
			// this is to initialize with a byte array
			// Path binpath = Paths.get(binfile);
			// byte[] binFileBytes = Files.readAllBytes(binpath);
			// loc.Open(binFileBytes);
			
			IPResult rec = loc.IPQuery(ip);
			if ("OK".equals(rec.getStatus()))
			{
				System.out.println(rec);
			}
			else if ("EMPTY_IP_ADDRESS".equals(rec.getStatus()))
			{
				System.out.println("IP address cannot be blank.");
			}
			else if ("INVALID_IP_ADDRESS".equals(rec.getStatus()))
			{
				System.out.println("Invalid IP address.");
			}
			else if ("MISSING_FILE".equals(rec.getStatus()))
			{
				System.out.println("Invalid database path.");
			}
			else if ("IPV6_NOT_SUPPORTED".equals(rec.getStatus()))
			{
				System.out.println("This BIN does not contain IPv6 data.");
			}
			else
			{
				System.out.println("Unknown error." + rec.getStatus());
			}
			System.out.println("Java Component: " + rec.getVersion());
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace(System.out);
		}
		finally
		{
			loc.Close();
		}
	}
}
```

## QUERY USING THE IP2LOCATION IP GEOLOCATION WEB SERVICE

## Methods
Below are the methods supported in this class.

| Method Name                                                              | Description                                                                             |
|--------------------------------------------------------------------------|-----------------------------------------------------------------------------------------|
| Open(String APIKey, String Package, boolean UseSSL)                      | Initialize component.                                                                   |
| IPQuery(String IPAddress)                                                | Query IP address. This method returns a JsonObject.                                     |
| IPQuery(String IPAddress, String Language)                               | Query IP address and translation language. This method returns a JsonObject.            |
| IPQuery(String IPAddress, String[] AddOns)                               | Query IP address and Addons. This method returns a JsonObject.                          |
| IPQuery(String IPAddress, String[] AddOns, String Language)              | Query IP address and Addons and translation language. This method returns a JsonObject. |
| IPQuery(String IPAddress, String Host, String[] AddOns, String Language) | Query IP address and Addons and translation language. This method returns a JsonObject. |
| GetCredit()                                                              | This method returns the web service credit balance in a JsonObject.                     |

Below are the Addons supported in this class.

|Addon Name|Description|
|---|---|
|continent|Returns continent code, name, hemispheres and translations.|
|country|Returns country codes, country name, flag, capital, total area, population, currency info, language info, IDD, TLD and translations.|
|region|Returns region code, name and translations.|
|city|Returns city name and translations.|
|geotargeting|Returns metro code based on the ZIP/postal code.|
|country_groupings|Returns group acronyms and names.|
|time_zone_info|Returns time zones, DST, GMT offset, sunrise and sunset.|

## Result fields
Below are the result fields.

|Name|
|---|
|<ul><li>country_code</li><li>country_name</li><li>region_name</li><li>city_name</li><li>latitude</li><li>longitude</li><li>zip_code</li><li>time_zone</li><li>isp</li><li>domain</li><li>net_speed</li><li>idd_code</li><li>area_code</li><li>weather_station_code</li><li>weather_station_name</li><li>mcc</li><li>mnc</li><li>mobile_brand</li><li>elevation</li><li>usage_type</li><li>address_type</li><li>category</li><li>category_name</li><li>continent<ul><li>name</li><li>code</li><li>hemisphere</li><li>translations</li></ul></li><li>country<ul><li>name</li><li>alpha3_code</li><li>numeric_code</li><li>demonym</li><li>flag</li><li>capital</li><li>total_area</li><li>population</li><li>currency<ul><li>code</li><li>name</li><li>symbol</li></ul></li><li>language<ul><li>code</li><li>name</li></ul></li><li>idd_code</li><li>tld</li><li>is_eu</li><li>translations</li></ul></li><li>region<ul><li>name</li><li>code</li><li>translations</li></ul></li><li>city<ul><li>name</li><li>translations</li></ul></li><li>geotargeting<ul><li>metro</li></ul></li><li>country_groupings</li><li>time_zone_info<ul><li>olson</li><li>current_time</li><li>gmt_offset</li><li>is_dst</li><li>sunrise</li><li>sunset</li></ul></li><ul>|

## Usage

```java
import com.ip2location.*;
import com.google.gson.*;

public class Main 
{
	public Main() 
	{
	}
	public static void main(String[] args) 
	{
		try
		{
			IP2LocationWebService ws = new IP2LocationWebService();
			
			String strIPAddress = "8.8.8.8";
			String strAPIKey = "YOUR_API_KEY_HERE";
			String strPackage = "WS25";
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
				System.out.println("address_type: " + ((myresult.get("address_type") != null) ? myresult.get("address_type").getAsString() : ""));
				System.out.println("category: " + ((myresult.get("category") != null) ? myresult.get("category").getAsString() : ""));
				System.out.println("category_name: " + ((myresult.get("category_name") != null) ? myresult.get("category_name").getAsString() : ""));
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
					System.out.println("country => is_eu: " + countryObj.get("is_eu").getAsString());
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

```

## IPTOOLS CLASS

## Methods
Below are the methods supported in this class.

|Method Name|Description|
|---|---|
|public boolean IsIPv4(String IPAddress)|Returns true if string contains an IPv4 address. Otherwise false.|
|public boolean IsIPv6(String IPAddress)|Returns true if string contains an IPv6 address. Otherwise false.|
|public BigInteger IPv4ToDecimal(String IPAddress)|Returns the IP number for an IPv4 address.|
|public BigInteger IPv6ToDecimal(String IPAddress)|Returns the IP number for an IPv6 address.|
|public String DecimalToIPv4(BigInteger IPNum)|Returns the IPv4 address for the supplied IP number.|
|public String DecimalToIPv6(BigInteger IPNum)|Returns the IPv6 address for the supplied IP number.|
|public String CompressIPv6(String IPAddress)|Returns the IPv6 address in compressed form.|
|public String ExpandIPv6(String IPAddress)|Returns the IPv6 address in expanded form.|
|public List<String> IPv4ToCIDR(String IPFrom, String IPTo)|Returns a list of CIDR from the supplied IPv4 range.|
|public List<String> IPv6ToCIDR(String IPFrom, String IPTo)|Returns a list of CIDR from the supplied IPv6 range.|
|public String[] CIDRToIPv4(String CIDR)|Returns the IPv4 range from the supplied CIDR.|
|public String[] CIDRToIPv6(String CIDR)|Returns the IPv6 range from the supplied CIDR.|

## Usage

```java
import com.ip2location.*;
import java.math.BigInteger;
import java.util.*;

public class Main 
{
	public Main() 
	{
	}
	public static void main(String[] args) 
	{
		try
		{
			IPTools tools = new IPTools();
			
			System.out.println(tools.IsIPv4("60.54.166.38"));
			System.out.println(tools.IsIPv6("2600:1f18:45b0:5b00:f5d8:4183:7710:ceec"));
			System.out.println(tools.IPv4ToDecimal("60.54.166.38"));
			System.out.println(tools.IPv6ToDecimal("2600:118:450:5b00:f5d8:4183:7710:ceec"));
			System.out.println(tools.DecimalToIPv4(new BigInteger("1010214438")));
			System.out.println(tools.DecimalToIPv6(new BigInteger("50510686025047391022278667396705210092")));
			System.out.println(tools.CompressIPv6("0000:0000:0000:0035:0000:FFFF:0000:0000"));
			System.out.println(tools.ExpandIPv6("500:6001:FE:35:0:FFFF::"));
			List<String> stuff = tools.IPv4ToCIDR("10.0.0.0", "10.10.2.255");
			stuff.forEach(System.out::println);
			List<String> stuff2 = tools.IPv6ToCIDR("2001:4860:4860:0000:0000:0000:0000:8888", "2001:4860:4860:0000:eeee:ffff:ffff:ffff");
			stuff2.forEach(System.out::println);
			String[] stuff3 = tools.CIDRToIPv4("10.123.80.0/12");
			System.out.println(stuff3[0]);
			System.out.println(stuff3[1]);
			String[] stuff4 = tools.CIDRToIPv6("2002:1234::abcd:ffff:c0a8:101/62");
			System.out.println(stuff4[0]);
			System.out.println(stuff4[1]);
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace(System.out);
		}
	}
}
```

## COUNTRY CLASS

## Methods
Below are the methods supported in this class.

|Method Name|Description|
|---|---|
|Constructor(CSVFile)|Expect a IP2Location Country Information CSV file. This database is free for download at https://www.ip2location.com/free/country-information|
|GetCountryInfo(CountryCode)|Returns the country information.|

## Usage

```java
import com.ip2location.*;
import java.util.*;

public class Main 
{
	public Main() 
	{
	}
	public static void main(String[] args) 
	{
		try
		{
			Country cc = new Country("/usr/data/IP2LOCATION-COUNTRY-INFORMATION.CSV");
			
			Map<String, String> ccresult = cc.GetCountryInfo("US");
			
			System.out.println(ccresult.toString());
			
			List <Map<String, String>> ccresults = cc.GetCountryInfo();
			
			System.out.println(ccresults.toString());
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace(System.out);
		}
	}
}
```

## REGION CLASS

## Methods
Below are the methods supported in this class.

|Method Name|Description|
|---|---|
|Constructor(CSVFile)|Expect a IP2Location ISO 3166-2 Subdivision Code CSV file. This database is free for download at https://www.ip2location.com/free/iso3166-2|
|GetRegionCode(CountryCode, RegionName)|Returns the region code for the supplied country code and region name.|

## Usage

```java
import com.ip2location.*;

public class Main 
{
	public Main() 
	{
	}
	public static void main(String[] args) 
	{
		try
		{
			Region reg = new Region("/usr/data/IP2LOCATION-ISO3166-2.CSV");
			
			String regionCode = reg.GetRegionCode("US", "Nevada");
			
			System.out.println(regionCode);
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace(System.out);
		}
	}
}
```
