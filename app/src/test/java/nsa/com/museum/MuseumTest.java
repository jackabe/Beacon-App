package nsa.com.museum;

import org.junit.Test;

import static org.junit.Assert.*;
import nsa.com.museum.MainActivity.Museums;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class MuseumTest {

    private Museums museum;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void checkMuseumSetAndGetCity() throws Exception {
        museum = new Museums();
        String city = "London";
        museum.setMuseumCity(city);
        assertEquals(museum.getMuseumCity(), city);
    }

    @Test
    public void checkMuseumSetAndGetOpen() throws Exception {
        museum = new Museums();
        int open = 9;
        museum.setMuseumOpen(open);
        assertEquals(museum.getMuseumOpen(), open);
    }

    @Test
    public void checkMuseumSetAndGetClose() throws Exception {
        museum = new Museums();
        int close = 1600;
        museum.setMuseumClose(close);
        assertEquals(museum.getMuseumClose(), close);
    }
}