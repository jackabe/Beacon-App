package nsa.com.museum;

/**
 * Created by c1673107 on 17/03/2017.
 */
public class MuseumDetails {

    private int _id;
    private String _museumName;

    public MuseumDetails() {

    }

    public MuseumDetails(int _id, String _museumName) {
        this._id = _id;
        this._museumName = _museumName;
    }


    public void setMuseumName(String _museumName) {
        this._museumName = _museumName;
    }

    public String getMuseumName() {
        return _museumName;
    }

    public int getMuseumId() {
        return _id;
    }

    public void setMuseumId(int museumId) {
        this._id = museumId;
    }

}
