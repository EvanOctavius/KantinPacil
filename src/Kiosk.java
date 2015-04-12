package id.ac.ui.cs.ppl_c02.kantin;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Evan Octavius S on 3/24/2015.
 */
public class Kiosk {

    public static int _no, _telp;
    public static String _name, _owner,_desc;

    public Kiosk(int no, String nama, int telp, String pemilik, String keterangan){
        _no = no;
        _name = nama;
        _telp = telp;
        _owner = pemilik;
        _desc = keterangan;
    }

    public static final class Kiosks implements BaseColumns {
        private Kiosks() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/Kios");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.kiosks";

        public static final int KIOSK_CODE = 0;

        public static final String KEY_NO = "no";

        public static final int KIOSK_NO_CODE = 01;

        public static final String KEY_NAME = "nama";

        public static final int KIOSK_NAME_CODE = 02;

        public static final String KEY_TELP = "telepon";

        public static final int KIOSK_TELP_CODE = 03;

        public static final String KEY_OWNER = "pemilik";

        public static final int KIOSK_OWNER_CODE = 04;

        public static final String KEY_DESC = "keterangan";

        public static final int KIOSK_DESC_CODE = 05;
    }

    public static int getNo(){
        return _no;
    }

    public static String getNama(){
        return _name;
    }

    public static String getPemilik(){
        return _owner;
    }

    public static int getTelp(){
        return _telp;
    }

    public static String getKeterangan(){
        return _desc;
    }
}