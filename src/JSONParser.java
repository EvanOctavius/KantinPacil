package id.ac.ui.cs.ppl_c02.kantin;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by Evan Octavius S on 4/6/2015.
 */
public class JSONParser extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] url) {
        JSONArray temp = getJSONfromURL((String) url[0]);
        return temp;
    }
    public static JSONArray getJSONfromURL(String url) {
        InputStream is = null;
        String result = "";
        JSONArray jArray = null;

        HttpResponse response;

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        // Download JSON data from URL
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if(entity!= null) {
                Log.e("1", "B");
                is = entity.getContent();
                Log.e("1", is.toString());
            }
            else{

            }

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        // Convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader( is, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try {
            jArray = new JSONArray(result);
            Log.e("JSON", jArray.getJSONObject(1).getString("no"));
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
        return jArray;
    }
}