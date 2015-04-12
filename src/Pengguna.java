package id.ac.ui.cs.ppl_c02.kantin;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Evan Octavius S on 3/28/2015.
 */
public class Pengguna {

    public static String _uname,_name;

    public Pengguna(String username, String nama){
        _uname = username;
        _name = nama;
    }

    public static final class Users implements BaseColumns {
        private Users() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/Pengguna");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.users";

        public static final int USER_CODE = 3;

        public static final String KEY_UNAME = "username";

        public static final String KEY_NAME = "nama";
    }

    public static String getUserName(){
        return _uname;
    }

    public static String getNama(){
        return  _uname;
    }
}