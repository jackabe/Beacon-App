package nsa.com.museum;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;
import android.widget.EditText;

import nsa.com.museum.MainActivity.MainActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected  void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testEditText() {
        EditText text = (EditText)getActivity().findViewById(R.id.addCity);
        assertNotNull(text);
    }

    @SmallTest
    public void testButton() {
        Button button = (Button)getActivity().findViewById(R.id.searchBtn);
        assertNotNull(button);
    }

}


