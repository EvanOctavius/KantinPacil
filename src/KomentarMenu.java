package id.ac.ui.cs.ppl_c02.kantin;

import android.net.Uri;
import android.provider.BaseColumns;

import java.sql.Date;

/**
 * Created by Evan Octavius S on 3/28/2015.
 */
public class KomentarMenu {

    public static String _comment, _menu, _user;

    public static int _id, _kiosk;

    public static Date _date;

    public KomentarMenu(int kios, String menu, String pengguna, int id, Date tanggal, String isi){
        _kiosk = kios;
        _menu = menu;
        _user = pengguna;
        _id = id;
        _date = tanggal;
        _comment = isi;
    }

    public static final class MenuComments implements BaseColumns {
        private MenuComments() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/menucomments");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.menucomments";

        public static final int CMENU_CODE = 7;

        public static final String KEY_KIOSK = "kios";

        public static final String KEY_MENU = "menu";

        public static final String KEY_USER = "pengguna";

        public static final String KEY_ID = "id";

        public static final String KEY_DATE = "tanggal";

        public static final String KEY_COMMENT = "isi";
    }

    public static String getPengguna(){
        return _user;
    }

    public static String getMenu(){
        return _menu;
    }

    public static int getKios(){
        return _kiosk;
    }

    public static int getID(){
        return _id;
    }

    public static Date getTanggal(){
        return _date;
    }

    public static String getKomentar(){
        return _comment;
    }
}
