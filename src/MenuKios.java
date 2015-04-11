package id.ac.ui.cs.ppl_c02.kantin;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Evan Octavius S on 3/28/2015.
 */
public class MenuKios {

    public static int _kiosk, _cost, _total;

    public static String _menu;

    public MenuKios(int kios, String menu, int harga, int total){
        _kiosk = kios;
        _menu = menu;
        _cost = harga;
        _total = total;
    }

    public static final class KioskMenus implements BaseColumns {
        private KioskMenus() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/kioskmenus");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.kioskmenus";

        public static final int MKIOSK_CODE = 2;

        public static final String KEY_KIOSK = "kios";

        public static final String KEY_MENU = "menu";

        public static final String KEY_COST = "harga";

        public static final String KEY_TOTAL = "total";
    }

    public static int getKios(){
        return _kiosk;
    }

    public static int getHarga(){
        return _cost;
    }

    public static int getTotal(){
        return _total;
    }

    public static String getMenu(){
        return _menu;
    }
}