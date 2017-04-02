package nsa.com.museum;

import java.io.IOException;

/**
 * Created by Jack on 02/04/2017.
 */

public class InternetConnection {

    public boolean isConnected() {
        Runtime connection = Runtime.getRuntime();
        try {
            java.lang.Process ping = connection.exec("/system/bin/ping -c 1 8.8.8.8");
            int exit = ping.waitFor();
            return  (exit == 0);
        }
        catch (IOException error) {
            error.printStackTrace();
        }
        catch (InterruptedException error) {
            error.printStackTrace();
        }
        return false;
    }
}

