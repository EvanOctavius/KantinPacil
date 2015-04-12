package id.ac.ui.cs.ppl_c02.kantin;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Evan Octavius S on 3/24/2015.
 */
public class Menu {
    public static String _name, _variety;

    public static float _carbo,_fat,_protein;

    public static int _calori,_cholersterol;

    public Menu(String nama, int kalori, float lemak, float karbohidrat, float protein, int kolesterol, String jenis){
        _name = nama;
        _variety = jenis;
        _cholersterol = kolesterol;
        _calori = kalori;
        _carbo = karbohidrat;
        _fat = lemak;
        _protein = protein;
    }

    public static final class Menus implements BaseColumns {
        private Menus() {
        }

        public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/Menu");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.menus";

        public static final int MENU_CODE = 1;

        public static final String KEY_NAME = "nama";

        public static final int MENU_NAME_CODE = 11;

        public static final String KEY_VARIETY = "jenis";

        public static final int MENU_VARIETY_CODE = 12;

        public static final String KEY_CHOLERSTEROL = "kolesterol";

        public static final int MENU_CHOLESTEROL_CODE = 13;

        public static final String KEY_CALORIE = "kalori";

        public static final int MENU_CALORIE_CODE = 14;

        public static final String KEY_CARBO = "karbohidrat";

        public static final int MENU_CARBO_CODE = 15;

        public static final String KEY_FAT = "lemak";

        public static final int MENU_FAT_CODE = 16;

        public static final String KEY_PROT = "protein";

        public static final int MENU_PROT_CODE = 17;
    }

    public static String getNama(){
        return _name;
    }

    public static String getJenis(){
        return _variety;
    }

    public static int getKolesterol(){
        return _cholersterol;
    }

    public static int getKalori(){
        return _calori;
    }

    public static float getKarbohidrat(){
        return _carbo;
    }

    public static float getLemak(){
        return _fat;
    }

    public static float getProtein(){
        return _protein;
    }
}
