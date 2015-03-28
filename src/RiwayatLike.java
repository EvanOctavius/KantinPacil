package id.ac.ui.cs.ppl_c02.kantin;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Evan Octavius S on 3/28/2015.
 */
public class RiwayatLike {

    public static String _user, _menu;

    public static int _kiosk;

    public RiwayatLike(String pengguna, int kios, String menu){
        _user = pengguna;
        _kiosk = kios;
        _menu = menu;
    }

    public static final class KioskComments implements BaseColumns {
        private KioskComments() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/kioskcomments");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.kioskcomments";

        public static final String KEY_KIOSK = "kios";

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
}
