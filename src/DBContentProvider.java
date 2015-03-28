package id.ac.ui.cs.ppl_c02.kantin;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Evan Octavius S on 3/26/2015.
 */
public class DBContentProvider extends ContentProvider {

    private static final String TAG = "DBContentProvider";

    private static final int DATABASE_VERSION = 1;

    public static final String AUTHORITY = "id.ac.ui.cs.ppl_c02.kantin.DBContentProvider";

    private static final String DATABASE_NAME = "database",
    TABLE_MENU = "Menu",
    TABLE_KIOSK = "Kios",
    TABLE_MKIOS = "MenuKios",
    TABLE_USER = "Pengguna",
    TABLE_USER_HISTORY = "RiwayatPengguna",
    TABLE_LIKE_HISTORY = "RiwayatLike",
    TABLE_CKIOS = "KomentarKios",
    TABLE_CMENU = "KomentarMenu",
    TABLE_ADMIN = "Admin";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_KIOSK + " (" + Kiosk.Kiosks.KEY_NO + " CHAR(2) NOT NULL PRIMARY KEY," + Kiosk.Kiosks.KEY_NAME + " VARCHAR(50) NOT NULL,"+ Kiosk.Kiosks.KEY_TELP + " VARCHAR(15)"+ Kiosk.Kiosks.KEY_DESC+" VARCHAR(100),");
            db.execSQL("CREATE TABLE " + TABLE_MENU + " (" + Menu.Menus.KEY_NAME + " VARCHAR(50) NOT NULL PRIMARY KEY ,"+Menu.Menus.KEY_CALORIE+" INT NOT NULL,"+Menu.Menus.KEY_FAT+" FLOAT,"+Menu.Menus.KEY_CARBO+" FLOAT,"+Menu.Menus.KEY_PROT+" FLOAT,"+Menu.Menus.KEY_CHOLERSTEROL+" INT NOT NULL,"+Menu.Menus.KEY_VARIETY+" VARCHAR(25));");
            db.execSQL("CREATE TABLE " + TABLE_MKIOS + " (" + MenuKios.KioskMenus.KEY_KIOSK);
            db.execSQL("CREATE TABLE ");
            db.execSQL("CREATE TABLE ");
            db.execSQL("CREATE TABLE ");
            db.execSQL("CREATE TABLE ");
            db.execSQL("CREATE TABLE ");
            db.execSQL("CREATE TABLE ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_KIOSK);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MKIOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_HISTORY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKE_HISTORY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CKIOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CMENU);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
            onCreate(db);
        }
        /*
        public Menu getMenu(String name){
            SQLiteDatabase db = getReadableDatabase();

            Cursor cursor = db.query(TABLE_MENU, new String[] {KEY_NAME,KEY_VARIETY,KEY_CHOLERSTEROL,KEY_CALORIE},KEY_NAME + "=?",new String[]{name},KEY_NAME,null,null,null);

            if (cursor != null){
                cursor.moveToFirst();
            }

            Menu menu = new Menu(cursor.getString(0),Integer.parseInt(cursor.getString(1)),Float.parseFloat(cursor.getString(2)),Float.parseFloat(cursor.getString(3)),Float.parseFloat(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),cursor.getString(6));

            return menu;
        }*/
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
