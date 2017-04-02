package nsa.com.museum;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class SetImage {

    // Code here referenced from http://stackoverflow.com/questions/20700181/convert-imageview-in-bytes-android.

    public SetImage() {

    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, output);
        return output.toByteArray();
    }

}