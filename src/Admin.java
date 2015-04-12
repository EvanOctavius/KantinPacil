package id.ac.ui.cs.ppl_c02.kantin;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Evan Octavius S on 3/28/2015.
 */
public class Admin {
    public Admin(){

    }

    public static final class Admins implements BaseColumns {
        private Admins() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/Admin");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.admins";

        public static final int ADMIN_CODE = 8;

        public static final String KEY_ID = "id";

        public static final String KEY_PASS = "password";
    }
}
