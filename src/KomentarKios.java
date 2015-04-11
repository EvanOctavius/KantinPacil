package id.ac.ui.cs.ppl_c02.kantin;

import android.net.Uri;
import android.provider.BaseColumns;

import java.sql.Date;

/**
 * Created by Evan Octavius S on 3/28/2015.
 */
public class KomentarKios {

    public static int _kiosk, _id;

    public static String _user, _comment;

    public static Date _date;

    public KomentarKios(int kios, String pengguna, int id, Date tanggal, String isi){
        _kiosk = kios;
        _user = pengguna;
        _id = id;
        _date = tanggal;
        _comment = isi;
    }

    public static final class KioskComments implements BaseColumns {
        private KioskComments() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/kioskcomments");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.kioskcomments";

        public static final int CKIOSK_CODE = 6;

        public static final String KEY_KIOSK = "kios";

        public static final String KEY_USER = "pengguna";

        public static final String KEY_ID = "id";

        public static final String KEY_DATE = "tanggal";

        public static final String KEY_COMMENT = "isi";
    }

    public static int getKios(){
        return _kiosk;
    }

    public static int getID(){
        return _id;
    }

    public static String getPengguna(){
        return _user;
    }

    public static String getIsi(){
        return _comment;
    }

    public static Date getTanggal(){
        return _date;
    }
}