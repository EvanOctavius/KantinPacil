package id.ac.ui.cs.ppl_c02.kantin;

import android.accounts.Account;
import android.annotation.TargetApi;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Evan Octavius S on 3/31/2015.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter{

    public static final String TAG = "SyncAdapter";

    private static final String URL = "ppl-c02.cs.ui.ac.id";

    /**
     * Network connection timeout, in milliseconds.
     */
    private static final int NET_CONNECT_TIMEOUT_MILLIS = 15000;  // 15 seconds

    /**
     * Network read timeout, in milliseconds.
     */
    private static final int NET_READ_TIMEOUT_MILLIS = 10000;  // 10 seconds

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
            final URL location = new URL(URL);
            InputStream stream = null;
            JSONParser inputJson = new JSONParser();
            try {
                Log.i(TAG, "Streaming data from network: " + location);
                stream = downloadUrl(location);
                updateLocalDBData(inputJson.getJSONfromURL(URL), syncResult,"kios");
                updateLocalDBData(inputJson.getJSONfromURL(URL), syncResult,"menu");
                updateLocalDBData(inputJson.getJSONfromURL(URL), syncResult,"menukios");
                updateLocalDBData(inputJson.getJSONfromURL(URL), syncResult,"pengguna");
                updateLocalDBData(inputJson.getJSONfromURL(URL), syncResult,"riwayatpengguna");
                updateLocalDBData(inputJson.getJSONfromURL(URL), syncResult,"riwayatlike");
                updateLocalDBData(inputJson.getJSONfromURL(URL), syncResult,"komentarkiosk");
                updateLocalDBData(inputJson.getJSONfromURL(URL), syncResult,"komentarmenu");
                updateLocalDBData(inputJson.getJSONfromURL(URL), syncResult,"admin");

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
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
        DBContentProvider db = new DBContentProvider(getContext());
        if(flag == "kios"){
            ContentValues cvKios = new ContentValues();
            for(int i = 0 ; i < stream.length(); i++) {
                try {
                    cvKios.put("no", stream.getJSONObject(i).getString("no"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //if(db.getWritableDatabase() == null){

            //}
        }
    }

    /**
     * Given a string representation of a URL, sets up a connection and gets an input stream.
     */
    private InputStream downloadUrl(final URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(NET_READ_TIMEOUT_MILLIS /* milliseconds */);
        conn.setConnectTimeout(NET_CONNECT_TIMEOUT_MILLIS /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }
}
