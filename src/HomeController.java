package id.ac.ui.cs.ppl_c02.kantin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;

/**
 * Created by Evan Octavius S on 4/12/2015.
 */
public class HomeController{
    public HomeController(){

    }

    public String getKiosk(String id,ContentResolver contentResolver){
        StringBuilder kiosk = new StringBuilder();
        String[] mProjection =
                {
                        Kiosk.Kiosks.KEY_NO,
                        Kiosk.Kiosks.KEY_OWNER,
                        Kiosk.Kiosks.KEY_NAME,
                        Kiosk.Kiosks.KEY_DESC,
                        Kiosk.Kiosks.KEY_TELP
                };
        String mSelectionClause = Kiosk.Kiosks.KEY_NO + " = ?";
        String[] mSelectionArgs = {""};
        mSelectionArgs[0] = id;
        Cursor c = contentResolver.query(Kiosk.Kiosks.CONTENT_URI, mProjection, mSelectionClause, mSelectionArgs, null);
        if (c.moveToFirst()){
            for(int i = 0;i<3;i++) {
                kiosk.append(mProjection[i] + ": " + c.getString(i));
                kiosk.append(", ");
            }
        }
        Log.e("Masuk","Hasil = " + kiosk);
        return kiosk.toString();
    }
}
