package id.ac.ui.cs.ppl_c02.kantin;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.HashMap;

/**
 * Created by Evan Octavius S on 4/12/2015.
 */
public class HomeController{
    public HomeController(){

    }

    public HashMap<String,String> getKiosk(String id,ContentResolver contentResolver){
        HashMap<String,String> kiosk = new HashMap<>();
        String[] mProjection =
                {
                        Kiosk.Kiosks.KEY_NO,
                        Kiosk.Kiosks.KEY_OWNER,
                        Kiosk.Kiosks.KEY_NAME,
                        Kiosk.Kiosks.KEY_DESC,
                        Kiosk.Kiosks.KEY_TELP
                };

        String mSelectionClause = Kiosk.Kiosks.KEY_NO + " = ?";
        String[] mSelectionArgs = {id};

        Cursor c = contentResolver.query(Kiosk.Kiosks.CONTENT_URI, mProjection, mSelectionClause, mSelectionArgs, null);
        if (c.moveToFirst()){
            kiosk.put(Kiosk.Kiosks.KEY_NO,c.getString(0));
            kiosk.put(Kiosk.Kiosks.KEY_OWNER,c.getString(1));
            kiosk.put(Kiosk.Kiosks.KEY_NAME,c.getString(2));
            kiosk.put(Kiosk.Kiosks.KEY_DESC,c.getString(3));
            kiosk.put(Kiosk.Kiosks.KEY_TELP,c.getString(4));
        }
        Log.e("Masuk","Hasil = " + kiosk);
        return kiosk;
    }

    public HashMap<String,String> getMenu(String id, ContentResolver contentResolver){
        HashMap<String,String> menu = new HashMap<>();
        String[] mProjection =
                {
                        MenuKios.KioskMenus.KEY_KIOSK,
                        MenuKios.KioskMenus.KEY_MENU,
                        MenuKios.KioskMenus.KEY_COST,
                        MenuKios.KioskMenus.KEY_TOTAL
                };
        String mSelectionClause = Kiosk.Kiosks.KEY_NO + " = ?";
        String[] mSelectionArgs = {id};

        Cursor c = contentResolver.query(MenuKios.KioskMenus.CONTENT_URI, mProjection,mSelectionClause,mSelectionArgs,null);

        if (c.moveToFirst()){
            do{
                menu.put(MenuKios.KioskMenus.KEY_KIOSK,c.getString(0));
                menu.put(MenuKios.KioskMenus.KEY_MENU,c.getString(1));
                menu.put(MenuKios.KioskMenus.KEY_COST,c.getString(2));
                menu.put(MenuKios.KioskMenus.KEY_TOTAL,c.getString(3));
            } while(c.moveToNext());
        }
        return menu;
    }
}
