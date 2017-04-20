package nsa.com.museum.BeaconActivity;

/**
 * Created by c1673107 on 27/03/2017.
 */
public class Beacons {

    String beaconId;
    String objectName;
    String url;
    byte[] image;
    String museumId;

    public Beacons() {

    }

    // get and setters for beacons.
    // non empty constructor isn't called as i made it for the ease of generating getters and setters.

    public Beacons(String beaconId, String objectName, String url, String museumId, byte[] image) {
        this.beaconId = beaconId;
        this.objectName = objectName;
        this.url = url;
        this.image = image;
        this.museumId = museumId;
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

    public String getMuseumId() {
        return museumId;
    }

    public void setMuseumId(String museumId) {
        this.museumId = museumId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
