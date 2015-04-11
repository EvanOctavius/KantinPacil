package id.ac.ui.cs.ppl_c02.kantin;

import android.net.Uri;
import android.provider.BaseColumns;

import java.sql.Date;

/**
 * Created by Evan Octavius S on 3/28/2015.
 */
public class RiwayatPengguna {

    public static String _user;

    public static int _spend, _calori, _cholesterol;

    public static Date _date;

    public RiwayatPengguna(String pengguna, Date tanggal, int kalori, int kolesterol, int pengeluaran){
        _user = pengguna;
        _date = tanggal;
        _calori = kalori;
        _cholesterol = kolesterol;
        _spend = pengeluaran;
    }

    public static final class RPs implements BaseColumns {
        private RPs() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/rps");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.rps";

        public static final int PHistory_CODE = 4;

        public static final String KEY_NAME = "pengguna";

        public static final String KEY_DATE = "tanggal";

        public static final String KEY_CALORIE = "kalori";

        public static final String KEY_CHOLESTEROL = "kolesterol";

        public static final String KEY_SPEND = "pengeluaran";
    }

    public static String getPengguna(){
        return _user;
    }

    public static Date getTanggal(){
        return _date;
    }

    public static int getKalori(){
        return _calori;
    }

    public static int getKolesterol(){
        return _cholesterol;
    }

    public static int getPengeluaran(){
        return _spend;
    }
}