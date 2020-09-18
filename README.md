# IP2Location Java Component

This component allows user to query an IP address for info such as the visitor’s country, region, city, latitude, longitude, ZIP code, ISP name, domain name, time zone, connection speed, IDD code, area code, weather station code, weather station name, MCC, MNC, mobile brand name, elevation and usage type. It lookup the IP address from **IP2Location BIN Data** file. This data file can be downloaded at

* Free IP2Location BIN Data: https://lite.ip2location.com
* Commercial IP2Location BIN Data: https://www.ip2location.com/database/ip2location

## Compilation

```bash
javac com/ip2location/*.java
jar cf ip2location.jar com/ip2location/*.class
```

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
|public void Open(String DBPath, boolean UseMMF)|Initialize the component and preload the BIN file.|
|public void Open(String DBPath)|Initialize the component and preload the BIN file.|
|IPQuery(String IPAddress)|Query IP address. This method returns results in com.ip2location.IPResult object.|
|public void Close()|Destroys the mapped bytes.|

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
|getZIPCode|ZIP code or postal code.|
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
			String binfile = "/usr/data/IP-COUNTRY-REGION-CITY-LATITUDE-LONGITUDE-ZIPCODE-TIMEZONE-ISP-DOMAIN-NETSPEED-AREACODE-WEATHER-MOBILE-ELEVATION-USAGETYPE.BIN";
			
			IP2Location loc = new IP2Location();
			// loc.IPDatabasePath = binfile;
			loc.Open(binfile, true);
			
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

