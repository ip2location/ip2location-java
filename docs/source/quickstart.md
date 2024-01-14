# Quickstart

## Dependencies

This library requires IP2Location BIN database to function. You may download the BIN database at

-   IP2Location LITE BIN Data (Free): <https://lite.ip2location.com>
-   IP2Location Commercial BIN Data (Comprehensive):
    <https://www.ip2location.com>

## IPv4 BIN vs IPv6 BIN

Use the IPv4 BIN file if you just need to query IPv4 addresses.

Use the IPv6 BIN file if you need to query BOTH IPv4 and IPv6 addresses.

## Compilation

```bash
javac com/ip2location/*.java
jar cf ip2location.jar com/ip2location/*.class
```

## Sample Codes

### Query geolocation information from BIN database

You can query the geolocation information from the IP2Location BIN database as below:

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

### Processing IP address using IP Tools class

You can manupulate IP address, IP number and CIDR as below:

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

### List down country information

You can query country information for a country from IP2Location Country Information CSV file as below:

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

### List down region information

You can get the region code by country code and region name from IP2Location ISO 3166-2 Subdivision Code CSV file as below:

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