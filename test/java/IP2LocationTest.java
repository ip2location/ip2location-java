import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.*;
import java.io.*;

import com.ip2location.*;

class IP2LocationTest {
    private static IP2Location loc;
    private static String binfile = "IP2LOCATION-LITE-DB1.BIN";
    private static String binfilepath;
    private static byte[] binFileBytes;

    private static String ip = "8.8.8.8";

    @BeforeAll
    static void Setup() throws IOException {
        Path binpath = Paths.get("src", "test", "resources", binfile);
        binfilepath = binpath.toFile().getAbsolutePath();
        binFileBytes = Files.readAllBytes(binpath);
    }

    @BeforeEach
    void Init() {
        loc = new IP2Location();
    }

    @Test
    void TestOpenException() {
        assertThrows(IOException.class, () -> {
            loc.Open("dummy.bin");
        });

        assertThrows(NullPointerException.class, () -> {
            loc.Open((byte[])null);
        });
    }

    @Test
    void TestQueryCountryCode() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getCountryShort(), "US");
    }

    @Test
    void TestQueryCountryLong() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getCountryLong(), "United States of America");
    }

    @Test
    void TestQueryRegion() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getRegion(), "Not_Supported");
    }

    @Test
    void TestQueryCity() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getCity(), "Not_Supported");
    }

    @Test
    void TestQueryLatitude() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getLatitude(), 0.0);
    }

    @Test
    void TestQueryLongitude() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getLongitude(), 0.0);
    }

    @Test
    void TestQueryZipCode() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getZipCode(), "Not_Supported");
    }

    @Test
    void TestQueryTimeZone() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getTimeZone(), "Not_Supported");
    }

    @Test
    void TestQueryISP() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getISP(), "Not_Supported");
    }

    @Test
    void TestQueryDomain() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getDomain(), "Not_Supported");
    }

    @Test
    void TestQueryNetSpeed() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getNetSpeed(), "Not_Supported");
    }

    @Test
    void TestQueryIDDCode() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getIDDCode(), "Not_Supported");
    }

    @Test
    void TestQueryAreaCode() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getAreaCode(), "Not_Supported");
    }

    @Test
    void TestQueryWeatherStationCode() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getWeatherStationCode(), "Not_Supported");
    }

    @Test
    void TestQueryWeatherStationName() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getWeatherStationName(), "Not_Supported");
    }

    @Test
    void TestQueryMCC() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getMCC(), "Not_Supported");
    }

    @Test
    void TestQueryMNC() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getMNC(), "Not_Supported");
    }

    @Test
    void TestQueryMobileBrand() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getMobileBrand(), "Not_Supported");
    }

    @Test
    void TestQueryElevation() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getElevation(), 0.0);
    }

    @Test
    void TestQueryUsageType() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getUsageType(), "Not_Supported");
    }

    @Test
    void TestQueryAddressType() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getAddressType(), "Not_Supported");
    }

    @Test
    void TestQueryCategory() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getCategory(), "Not_Supported");
    }

    @Test
    void TestQueryDistrict() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getDistrict(), "Not_Supported");
    }

    @Test
    void TestQueryASN() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getASN(), "Not_Supported");
    }

    @Test
    void TestQueryAS() throws IOException {
        loc.Open(binfilepath);
        IPResult rec = loc.IPQuery(ip);
        assertEquals(rec.getAS(), "Not_Supported");
    }

    @Test
    void TestMetaData() throws IOException {
        loc.Open(binfilepath);
        MetaData metaData = loc.MetaData();
        assertEquals(14, metaData.getDBDay());
        assertEquals(3, metaData.getDBMonth());
        assertEquals(21, metaData.getDBYear());
        assertEquals(195811, metaData.getDBCount());
        assertEquals(2, metaData.getDBColumn());
        assertEquals(2094492, metaData.getFileSize());
    }

    @AfterEach
    void TearDown() {
        loc.Close();
        loc = null;
    }
}


