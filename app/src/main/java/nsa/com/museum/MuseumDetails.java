package nsa.com.museum;

/**
 * Created by c1673107 on 17/03/2017.
 */
public class MuseumDetails {

    private int _id;
    private int _artifactId;
    private int _beaconId;

    public MuseumDetails() {

    }

    public MuseumDetails(int _id, int _artifactId, int _beaconId) {
        this._id = _id;
        this._artifactId = _artifactId;
        this._beaconId = _beaconId;
    }

    public void set_artifactId(int _artifactId) {
        this._artifactId = _artifactId;
    }

    public int getMuseumName() {
        return _artifactId;
    }

    public int get_beaconId() {
        return _beaconId;
    }

    public void set_beaconId(int _beaconId) {
        this._id = _beaconId;
    }

}
