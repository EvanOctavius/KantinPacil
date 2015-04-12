package id.ac.ui.cs.ppl_c02.kantin;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * Created by Evan Octavius S on 3/31/2015.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter{

    public static final String TAG = "SyncAdapter";

    private static final String URL[] = {"http://ppl-c02.cs.ui.ac.id/index.php/json/json_kios",
                                        "http://ppl-c02.cs.ui.ac.id/index.php/json/json_menu",
                                        "http://ppl-c02.cs.ui.ac.id/index.php/json/json_menukios",
                                        "http://ppl-c02.cs.ui.ac.id/index.php/json/json_pengguna",
                                        "http://ppl-c02.cs.ui.ac.id/index.php/json/json_riwayatpengguna",
                                        "http://ppl-c02.cs.ui.ac.id/index.php/json/json_riwayatlike",
                                        "http://ppl-c02.cs.ui.ac.id/index.php/json/json_komentarkios",
                                        "http://ppl-c02.cs.ui.ac.id/index.php/json/json_komentarmenu",
                                        "http://ppl-c02.cs.ui.ac.id/index.php/json/json_admin"};


    /**
     * Content resolver, for performing database operations.
     */
    private final ContentResolver mContentResolver;

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    /**
     * Constructor. Obtains handle to content resolver for later use.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i(TAG, "Beginning network synchronization");
        try {
            JSONParser inputJson = new JSONParser();
            Log.i(TAG, "Streaming data from network: " + URL.toString());
            updateLocalDBData(inputJson.getJSONfromURL(URL[0]), syncResult, "kios");
            updateLocalDBData(inputJson.getJSONfromURL(URL[1]), syncResult,"menu");
            updateLocalDBData(inputJson.getJSONfromURL(URL[2]), syncResult,"menukios");
            updateLocalDBData(inputJson.getJSONfromURL(URL[3]), syncResult,"pengguna");
            updateLocalDBData(inputJson.getJSONfromURL(URL[4]), syncResult, "riwayatpengguna");
            updateLocalDBData(inputJson.getJSONfromURL(URL[5]), syncResult,"riwayatlike");
            updateLocalDBData(inputJson.getJSONfromURL(URL[6]), syncResult,"komentarkiosk");
            updateLocalDBData(inputJson.getJSONfromURL(URL[7]), syncResult,"komentarmenu");
            updateLocalDBData(inputJson.getJSONfromURL(URL[8]), syncResult,"admin");
        } catch (MalformedURLException e) {
            Log.wtf(TAG, "Feed URL is malformed", e);
            syncResult.stats.numParseExceptions++;
            return;
        } catch (IOException e) {
            Log.e(TAG, "Error reading from network: " + e.toString());
            syncResult.stats.numIoExceptions++;
            return;
        } catch (XmlPullParserException e) {
            Log.e(TAG, "Error parsing feed: " + e.toString());
            syncResult.stats.numParseExceptions++;
            return;
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing feed: " + e.toString());
            syncResult.stats.numParseExceptions++;
            return;
        } catch (RemoteException e) {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
            return;
        } catch (OperationApplicationException e) {
            Log.e(TAG, "Error updating database: " + e.toString());
            syncResult.databaseError = true;
            return;
        }
        Log.i(TAG, "Network synchronization complete");
    }

    private void updateLocalDBData(final JSONArray stream, final SyncResult syncResult, String flag) throws IOException, XmlPullParserException, RemoteException, OperationApplicationException, ParseException{
        Log.e("Cek", stream.length()+"");
        if(stream.length() != 0) {
            if (flag == "kios") {
                ContentValues cvKios = new ContentValues();
                Log.e("kios", "ini insert Kios");
                for (int i = 0; i < stream.length(); i++) {
                    try {
                        cvKios.put("no", stream.getJSONObject(i).getString("no"));
                        cvKios.put("nama", stream.getJSONObject(i).getString("nama"));
                        cvKios.put("telepon", stream.getJSONObject(i).getString("telepon"));
                        cvKios.put("pemilik", stream.getJSONObject(i).getString("pemilik"));
                        cvKios.put("keterangan", stream.getJSONObject(i).getString("keterangan"));
                        mContentResolver.insert(Kiosk.Kiosks.CONTENT_URI, cvKios);
                        Log.e("cvKios", i + "");
                    } catch (JSONException e) {
                        Log.e("kios", e.toString());
                    }
                }
            } else if (flag == "menu") {
                ContentValues cvMenu = new ContentValues();
                Log.e("menu", "ini insert Menu");
                for (int i = 0; i < stream.length(); i++) {
                    try {
                        cvMenu.put("nama", stream.getJSONObject(i).getString("nama"));
                        cvMenu.put("kalori", stream.getJSONObject(i).getString("kalori"));
                        cvMenu.put("lemak", stream.getJSONObject(i).getString("lemak"));
                        cvMenu.put("karbohidrat", stream.getJSONObject(i).getString("karbohidrat"));
                        cvMenu.put("protein", stream.getJSONObject(i).getString("protein"));
                        cvMenu.put("kolesterol", stream.getJSONObject(i).getString("kolesterol"));
                        cvMenu.put("jenis", stream.getJSONObject(i).getString("jenis"));
                        mContentResolver.insert(Menu.Menus.CONTENT_URI, cvMenu);
                        Log.e("cvMenu", i + " = " + cvMenu.toString());
                    } catch (JSONException e) {
                        Log.e("menu", e.toString());
                    }
                }
            } else if (flag == "menukios") {
                Log.e("menukios", "ini insert Menu Kios");
                ContentValues cvMenuKios = new ContentValues();
                for (int i = 0; i < stream.length(); i++) {
                    try {
                        cvMenuKios.put("kios", stream.getJSONObject(i).getString("kios"));
                        cvMenuKios.put("menu", stream.getJSONObject(i).getString("menu"));
                        cvMenuKios.put("harga", stream.getJSONObject(i).getString("harga"));
                        cvMenuKios.put("total", stream.getJSONObject(i).getString("total"));
                        mContentResolver.insert(MenuKios.KioskMenus.CONTENT_URI, cvMenuKios);
                        Log.e("cvMenuKios", i + " = " + cvMenuKios.toString());
                    } catch (JSONException e) {
                        Log.e("menukios", e.toString());
                    }
                }
            } else if (flag == "pengguna") {
                Log.e("pengguna", "ini insert pengguna");
                ContentValues cvPengguna = new ContentValues();
                for (int i = 0; i < stream.length(); i++) {
                    try {
                        cvPengguna.put("username", stream.getJSONObject(i).getString("username"));
                        cvPengguna.put("nama", stream.getJSONObject(i).getString("nama"));
                        mContentResolver.insert(Pengguna.Users.CONTENT_URI, cvPengguna);
                        Log.e("cvPengguna", i + " = " + cvPengguna.toString());
                    } catch (JSONException e) {
                        Log.e("pengguna", e.toString());
                    }
                }
            } else if (flag == "riawayatpengguna") {
                Log.e("rp", "ini insert riwayat pengguna");
                ContentValues cvRP = new ContentValues();
                for (int i = 0; i < stream.length(); i++) {
                    try {
                        cvRP.put("pengguna", stream.getJSONObject(i).getString("pengguna"));
                        cvRP.put("tanggal", stream.getJSONObject(i).getString("tanggal"));
                        cvRP.put("kalori", stream.getJSONObject(i).getString("kalori"));
                        cvRP.put("kolesterol", stream.getJSONObject(i).getString("kolesterol"));
                        cvRP.put("pengeluaran", stream.getJSONObject(i).getString("pengeluaran"));
                        mContentResolver.insert(RiwayatPengguna.RPs.CONTENT_URI, cvRP);
                        Log.e("cvRP", i + " = " + cvRP.toString());
                    } catch (JSONException e) {
                        Log.e("rp", e.toString());
                    }
                }
            } else if (flag == "riawayatlike") {
                Log.e("rl", "ini insert riwayat like");
                ContentValues cvRL = new ContentValues();
                for (int i = 0; i < stream.length(); i++) {
                    try {
                        cvRL.put("pengguna", stream.getJSONObject(i).getString("pengguna"));
                        cvRL.put("kios", stream.getJSONObject(i).getString("kios"));
                        cvRL.put("menu", stream.getJSONObject(i).getString("menu"));
                        mContentResolver.insert(RiwayatLike.RLs.CONTENT_URI, cvRL);
                        Log.e("cvRL", i + " = " + cvRL.toString());
                    } catch (JSONException e) {
                        Log.e("rl", e.toString());
                    }
                }
            } else if (flag == "komentarkiosk") {
                Log.e("komentar kios", "ini insert komentar kios");
                ContentValues cvKK = new ContentValues();
                for (int i = 0; i < stream.length(); i++) {
                    try {
                        cvKK.put("kios", stream.getJSONObject(i).getString("kios"));
                        cvKK.put("pengguna", stream.getJSONObject(i).getString("pengguna"));
                        cvKK.put("id", stream.getJSONObject(i).getString("id"));
                        cvKK.put("tanggal", stream.getJSONObject(i).getString("tanggal"));
                        cvKK.put("isi", stream.getJSONObject(i).getString("isi"));
                        mContentResolver.insert(KomentarKios.KioskComments.CONTENT_URI, cvKK);
                        Log.e("cvKK", i + " = " + cvKK.toString());
                    } catch (JSONException e) {
                        Log.e("KK", e.toString());
                    }
                }
            } else if (flag == "komentarmenu") {
                Log.e("komentar menu", "ini insert komentar menu");
                ContentValues cvKM = new ContentValues();
                for (int i = 0; i < stream.length(); i++) {
                    try {
                        cvKM.put("kios", stream.getJSONObject(i).getString("kios"));
                        cvKM.put("menu", stream.getJSONObject(i).getString("menu"));
                        cvKM.put("pengguna", stream.getJSONObject(i).getString("pengguna"));
                        cvKM.put("id", stream.getJSONObject(i).getString("id"));
                        cvKM.put("tanggal", stream.getJSONObject(i).getString("tanggal"));
                        cvKM.put("isi", stream.getJSONObject(i).getString("isi"));
                        mContentResolver.insert(KomentarMenu.MenuComments.CONTENT_URI, cvKM);
                        Log.e("cvKM", i + " = " + cvKM.toString());
                    } catch (JSONException e) {
                        Log.e("KM", e.toString());
                    }
                }
            } else if (flag == "admin") {
                Log.e("admin", "ini insert Admin");
                ContentValues cvAdmin = new ContentValues();
                for (int i = 0; i < stream.length(); i++) {
                    try {
                        cvAdmin.put("id", stream.getJSONObject(i).getString("id"));
                        cvAdmin.put("password", stream.getJSONObject(i).getString("password"));
                        mContentResolver.insert(Admin.Admins.CONTENT_URI,cvAdmin);
                        Log.e("cvAdmin", i + " = " + cvAdmin.toString());
                    } catch (JSONException e) {
                        Log.e("admin", e.toString());
                    }
                }
            }
        }
    }
}
