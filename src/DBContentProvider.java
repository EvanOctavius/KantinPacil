package id.ac.ui.cs.ppl_c02.kantin;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Created by Evan Octavius S on 3/26/2015.
 */
public class DBContentProvider extends ContentProvider {

    private static final String TAG = "DBContentProvider";

    private static final int DATABASE_VERSION = 1;

    public static final String AUTHORITY = "id.ac.ui.cs.ppl_c02.kantin.DBContentProvider";

    private Context mContext;

    private static HashMap<String, String> values;

    private DatabaseHelper myDB;

    private SQLiteDatabase db;

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

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_KIOSK, Kiosk.Kiosks.KIOSK_CODE);
        uriMatcher.addURI(AUTHORITY, TABLE_MENU, Menu.Menus.MENU_CODE);
        uriMatcher.addURI(AUTHORITY, TABLE_MKIOS, MenuKios.KioskMenus.MKIOSK_CODE);
        uriMatcher.addURI(AUTHORITY, TABLE_USER, Pengguna.Users.USER_CODE);
        uriMatcher.addURI(AUTHORITY, TABLE_USER_HISTORY, RiwayatPengguna.RPs.PHistory_CODE);
        uriMatcher.addURI(AUTHORITY, TABLE_LIKE_HISTORY, RiwayatLike.RLs.LHISTORY_CODE);
        uriMatcher.addURI(AUTHORITY, TABLE_CKIOS, KomentarKios.KioskComments.CKIOSK_CODE);
        uriMatcher.addURI(AUTHORITY, TABLE_CKIOS, KomentarKios.KioskComments.CKIOSK_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_CMENU, KomentarMenu.MenuComments.CMENU_CODE);
        uriMatcher.addURI(AUTHORITY, TABLE_CMENU, KomentarMenu.MenuComments.CMENU_ID);
        uriMatcher.addURI(AUTHORITY, TABLE_ADMIN, Admin.Admins.ADMIN_CODE);
    }

    public DBContentProvider(){

    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.e("DBH","Masuk");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_KIOSK + " (" + Kiosk.Kiosks.KEY_NO + " CHAR(2) NOT NULL PRIMARY KEY," + Kiosk.Kiosks.KEY_NAME + " VARCHAR(50) NOT NULL," + Kiosk.Kiosks.KEY_TELP + " VARCHAR(15)," + Kiosk.Kiosks.KEY_OWNER + " VARCHAR(50)," + Kiosk.Kiosks.KEY_DESC + " VARCHAR(100));");
            db.execSQL("CREATE TABLE " + TABLE_MENU + " (" + Menu.Menus.KEY_NAME + " VARCHAR(50) NOT NULL PRIMARY KEY," + Menu.Menus.KEY_CALORIE + " INT NOT NULL," + Menu.Menus.KEY_FAT + " FLOAT," + Menu.Menus.KEY_CARBO + " FLOAT," + Menu.Menus.KEY_PROT + " FLOAT," + Menu.Menus.KEY_CHOLERSTEROL + " INT NOT NULL," + Menu.Menus.KEY_VARIETY + " VARCHAR(25));");
            db.execSQL("CREATE TABLE " + TABLE_MKIOS + " (" + MenuKios.KioskMenus.KEY_KIOSK + " CHAR(2) NOT NULL," + MenuKios.KioskMenus.KEY_MENU + " VARCHAR(50) NOT NULL," + MenuKios.KioskMenus.KEY_COST + " INT NOT NULL," + MenuKios.KioskMenus.KEY_TOTAL + " INT NOT NULL, PRIMARY KEY(" + MenuKios.KioskMenus.KEY_KIOSK + "," + MenuKios.KioskMenus.KEY_MENU + ") FOREIGN KEY(" + MenuKios.KioskMenus.KEY_KIOSK + ") REFERENCES " + TABLE_KIOSK + "(" + Kiosk.Kiosks.KEY_NO + ") ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY(" + MenuKios.KioskMenus.KEY_MENU + ") REFERENCES " + TABLE_MENU + "(" + Menu.Menus.KEY_NAME + ") ON DELETE CASCADE ON UPDATE CASCADE);");
            db.execSQL("CREATE TABLE " + TABLE_USER + " (" + Pengguna.Users.KEY_UNAME + " VARCHAR(25) NOT NULL PRIMARY KEY," + Pengguna.Users.KEY_NAME + " VARCHAR(50) NOT NULL);");
            db.execSQL("CREATE TABLE " + TABLE_USER_HISTORY + " (" + RiwayatPengguna.RPs.KEY_NAME + " VARCHAR(25) NOT NULL," + RiwayatPengguna.RPs.KEY_DATE + " DATE NOT NULL," + RiwayatPengguna.RPs.KEY_CALORIE + " INT NOT NULL," + RiwayatPengguna.RPs.KEY_CHOLESTEROL + " INT NOT NULL," + RiwayatPengguna.RPs.KEY_SPEND + " INT NOT NULL, PRIMARY KEY(" + RiwayatPengguna.RPs.KEY_NAME + "," + RiwayatPengguna.RPs.KEY_DATE + "), FOREIGN KEY(" + RiwayatPengguna.RPs.KEY_NAME + ") REFERENCES " + TABLE_USER + "(" + Pengguna.Users.KEY_UNAME + ") ON UPDATE CASCADE ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE " + TABLE_LIKE_HISTORY + " (" + RiwayatLike.RLs.KEY_USER + " VARCHAR(25) NOT NULL," + RiwayatLike.RLs.KEY_KIOSK + " CHAR(2) NOT NULL," + RiwayatLike.RLs.KEY_MENU + " VARCHAR(50) NOT NULL, PRIMARY KEY(" + RiwayatLike.RLs.KEY_USER + "," + RiwayatLike.RLs.KEY_KIOSK + "," + RiwayatLike.RLs.KEY_MENU + "), FOREIGN KEY(" + RiwayatLike.RLs.KEY_USER + ") REFERENCES " + TABLE_USER + "(" + Pengguna.Users.KEY_UNAME + ") ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(" + RiwayatLike.RLs.KEY_KIOSK + "," + RiwayatLike.RLs.KEY_MENU + ") REFERENCES " + TABLE_MKIOS + "(" + MenuKios.KioskMenus.KEY_KIOSK + "," + MenuKios.KioskMenus.KEY_MENU + ") ON UPDATE CASCADE ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE " + TABLE_CKIOS + " (" + KomentarKios.KioskComments.KEY_KIOSK + " CHAR(2) NOT NULL," + KomentarKios.KioskComments.KEY_USER + " VARCHAR(25) NOT NULL," + KomentarKios.KioskComments.KEY_ID + " SERIAL NOT NULL," + KomentarKios.KioskComments.KEY_DATE + " DATE NOT NULL," + KomentarKios.KioskComments.KEY_COMMENT + " VARCHAR(250) NOT NULL, PRIMARY KEY(" + KomentarKios.KioskComments.KEY_KIOSK + "," + KomentarKios.KioskComments.KEY_USER + "," + KomentarKios.KioskComments.KEY_ID + "), FOREIGN KEY(" + KomentarKios.KioskComments.KEY_KIOSK + ") REFERENCES " + TABLE_KIOSK + "(" + Kiosk.Kiosks.KEY_NO + ") ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(" + KomentarKios.KioskComments.KEY_USER + ") REFERENCES " + TABLE_USER + "(" + Pengguna.Users.KEY_UNAME + ") ON UPDATE CASCADE ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE " + TABLE_CMENU + " (" + KomentarMenu.MenuComments.KEY_KIOSK + " CHAR(2) NOT NULL," + KomentarMenu.MenuComments.KEY_MENU + " VARCHAR(50) NOT NULL," + KomentarMenu.MenuComments.KEY_USER + " VARCHAR(25) NOT NULL," + KomentarMenu.MenuComments.KEY_ID + " SERIAL NOT NULL," + KomentarMenu.MenuComments.KEY_DATE + " DATE NOT NULL," + KomentarMenu.MenuComments.KEY_COMMENT + " VARCHAR(250) NOT NULL, PRIMARY KEY(" + KomentarMenu.MenuComments.KEY_KIOSK + "," + KomentarMenu.MenuComments.KEY_MENU + "," + KomentarMenu.MenuComments.KEY_USER + "," + KomentarMenu.MenuComments.KEY_ID + "), FOREIGN KEY(" + KomentarMenu.MenuComments.KEY_KIOSK + "," + KomentarMenu.MenuComments.KEY_MENU + ") REFERENCES " + TABLE_MKIOS + "(" + MenuKios.KioskMenus.KEY_KIOSK + "," + MenuKios.KioskMenus.KEY_MENU + ") ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY(" + KomentarMenu.MenuComments.KEY_USER + ") REFERENCES " + TABLE_USER + "(" + Pengguna.Users.KEY_UNAME + ") ON UPDATE CASCADE ON DELETE CASCADE);");
            db.execSQL("CREATE TABLE " + TABLE_ADMIN + " (" + Admin.Admins.KEY_ID + " CHAR(2) NOT NULL PRIMARY KEY," + Admin.Admins.KEY_PASS + " VARCHAR(50) NOT NULL);");
            Log.e("Start", "Database Created.!!");
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
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        myDB = new DatabaseHelper(mContext);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        openDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case Kiosk.Kiosks.KIOSK_CODE:
                qb.setTables(TABLE_KIOSK);
                qb.setProjectionMap(values);
                break;
            case Menu.Menus.MENU_CODE:
                qb.setTables(TABLE_MENU);
                qb.setProjectionMap(values);
                break;
            case MenuKios.KioskMenus.MKIOSK_CODE:
                qb.setTables(TABLE_MKIOS);
                qb.setProjectionMap(values);
                break;
            case Pengguna.Users.USER_CODE:
                qb.setTables(TABLE_USER);
                qb.setProjectionMap(values);
                break;
            case RiwayatPengguna.RPs.PHistory_CODE:
                qb.setTables(TABLE_USER_HISTORY);
                qb.setProjectionMap(values);
                break;
            case RiwayatLike.RLs.LHISTORY_CODE:
                qb.setTables(TABLE_LIKE_HISTORY);
                qb.setProjectionMap(values);
                break;
            case KomentarKios.KioskComments.CKIOSK_CODE:
                qb.setTables(TABLE_CKIOS);
                qb.setProjectionMap(values);
                break;
            case KomentarMenu.MenuComments.CMENU_CODE:
                qb.setTables(TABLE_CMENU);
                qb.setProjectionMap(values);
                break;
            case Admin.Admins.ADMIN_CODE:
                qb.setTables(TABLE_ADMIN);
                qb.setProjectionMap(values);
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        Cursor c = qb.query(db, projection, selection, selectionArgs, null,null, sortOrder);
        c.setNotificationUri(mContext.getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        openDatabase();
        String tableName = "";
        switch (uriMatcher.match(uri)) {
            case Kiosk.Kiosks.KIOSK_CODE:
                tableName = TABLE_KIOSK;
                break;
            case Menu.Menus.MENU_CODE:
                tableName = TABLE_MENU;
                break;
            case MenuKios.KioskMenus.MKIOSK_CODE:
                tableName = TABLE_MKIOS;
                break;
            case Pengguna.Users.USER_CODE:
                tableName = TABLE_USER;
                break;
            case RiwayatPengguna.RPs.PHistory_CODE:
                tableName = TABLE_USER_HISTORY;
                break;
            case RiwayatLike.RLs.LHISTORY_CODE:
                tableName = TABLE_LIKE_HISTORY;
                break;
            case KomentarKios.KioskComments.CKIOSK_CODE:
                tableName = TABLE_CKIOS;
                break;
            case KomentarMenu.MenuComments.CMENU_CODE:
                tableName = TABLE_CMENU;
                break;
            case Admin.Admins.ADMIN_CODE:
                tableName = TABLE_ADMIN;
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long i = db.insert(tableName,null,values);
        return uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int status = 0;
        String tableName = "";
        switch (uriMatcher.match(uri)) {
            case KomentarKios.KioskComments.CKIOSK_CODE:
                tableName = TABLE_CKIOS;
                break;
            case KomentarKios.KioskComments.CKIOSK_ID:
                break;
            case KomentarMenu.MenuComments.CMENU_CODE:
                tableName = TABLE_CMENU;
                break;
            case KomentarMenu.MenuComments.CMENU_ID:
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        status = db.delete(tableName,selection,selectionArgs);
        mContext.getContentResolver().notifyChange(uri,null);
        return status;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int status = 0;
        String tableName = "";
        switch (uriMatcher.match(uri)) {
            case Kiosk.Kiosks.KIOSK_CODE:
                tableName = TABLE_KIOSK;
                break;
            case Menu.Menus.MENU_CODE:
                tableName = TABLE_MENU;
                break;
            case MenuKios.KioskMenus.MKIOSK_CODE:
                tableName = TABLE_MKIOS;
                break;
            case Pengguna.Users.USER_CODE:
                tableName = TABLE_USER;
                break;
            case RiwayatPengguna.RPs.PHistory_CODE:
                tableName = TABLE_USER_HISTORY;
                break;
            case RiwayatLike.RLs.LHISTORY_CODE:
                tableName = TABLE_LIKE_HISTORY;
                break;
            case KomentarKios.KioskComments.CKIOSK_CODE:
                tableName = TABLE_CKIOS;
                break;
            case KomentarMenu.MenuComments.CMENU_CODE:
                tableName = TABLE_CMENU;
                break;
            case Admin.Admins.ADMIN_CODE:
                tableName = TABLE_ADMIN;
                break;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        status = db.update(tableName,values,selection,selectionArgs);
        mContext.getContentResolver().notifyChange(uri,null);
        return status;
    }

    public void openDatabase() throws SQLiteException{
        try {
            db = myDB.getWritableDatabase();
        } catch (SQLiteException ex){
            db = myDB.getReadableDatabase();
        }

    }
}
