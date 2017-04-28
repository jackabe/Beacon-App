package nsa.com.museum;

import org.junit.Before;
import org.junit.Test;

import nsa.com.museum.HistoryActivity.History;

import static org.junit.Assert.assertEquals;

/**
 * Created by c1571078 on 21/04/2017.
 */
public class HistoryTest {

    History beaconTest;

    @Before
    public void createNewHistoryBeacon () throws Exception {
        beaconTest = new History();
    }

    @Test
    public void getAndSetBeaconIDTest() throws Exception {
        String beaconIDTest = "123456abcdef";

        beaconTest.setBeaconId("123456abcdef");

        assertEquals(beaconIDTest, beaconTest.getBeaconId());
    }

    @Test
    public void getAndSetObjectNameTest() throws Exception {
        String objectNameTest = "BEACON TEST";

        beaconTest.setObjectName("BEACON TEST");

        assertEquals(objectNameTest, beaconTest.getObjectName());
    }

    @Test
    public void getAndSetURLTest() throws Exception {
        String urlTest = "www.beaconurltest.com";

        beaconTest.setUrl("www.beaconurltest.com");

        assertEquals(urlTest, beaconTest.getUrl());
    }
}
