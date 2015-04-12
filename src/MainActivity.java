package id.ac.ui.cs.ppl_c02.kantin;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button viewK = (Button)findViewById(R.id.viewKiosk1);

        final HomeController homeController = new HomeController();

        viewK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                homeController.getKiosk("02",getContentResolver());
            }
        });

        Button butt = (Button)findViewById(R.id.butt1);

        butt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new JSONParser().execute("http://ppl-c02.cs.ui.ac.id/index.php/json/json_kios");
            }
        });

        Button butt2 = (Button)findViewById(R.id.butt2);

        butt2.setOnClickListener(new View.OnClickListener(){
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            public void onClick(View v){
                Bundle bundle = new Bundle();
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED,true);
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_FORCE,true);
                bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL,true);
                ContentResolver.requestSync(null,DBContentProvider.AUTHORITY,bundle);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String executeCmd(String cmd, boolean sudo){
        try {

            Process p;
            if(!sudo)
                p= Runtime.getRuntime().exec(cmd);
            else{
                p= Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
            }
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String s;
            String res = "";
            while ((s = stdInput.readLine()) != null) {
                res += s + "\n";
            }
            p.destroy();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
}
