package nsa.com.museum;

/**
 * Created by c1673107 on 27/03/2017.
 */
public class Beacons {

    String beaconId;
    String objectName;
    String url;

    public Beacons() {

    }


    public Beacons(String beaconId, String objectName, String url) {
        this.beaconId = beaconId;
        this.objectName = objectName;
        this.url = url;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
