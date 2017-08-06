package comcesar1287.github.www.saudecard.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import comcesar1287.github.www.saudecard.controller.domain.Partner;
import comcesar1287.github.www.saudecard.controller.firebase.FirebaseHelper;

public class SaudeCardDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "bd_saude_card";
    private static final int VERSION = 3;
    private static final String TABLE_FAVORITE = "favorite";

    public SaudeCardDAO(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql_favorite = "CREATE TABLE " + TABLE_FAVORITE + "(" +
                "id_user TEXT, " +
                "url_logo TEXT, " +
                "name TEXT," +
                "address TEXT, " +
                "description TEXT, " +
                "site TEXT, " +
                "phone TEXT, " +
                "discount TEXT, " +
                "category TEXT, " +
                "subcategory TEXT, " +
                "latitude REAL, " +
                "longitude REAL, " +
                "PRIMARY KEY (id_user, url_logo)" +
                ");";
        try {
            db.execSQL(sql_favorite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql_fav = "DROP TABLE IF EXISTS " + TABLE_FAVORITE;
        db.execSQL(sql_fav);
        onCreate(db);
    }

    public void insertFavorite(String id_user, Partner partner){

        ContentValues cv = new ContentValues();
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_ID_USER, id_user);
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_URL_LOGO, partner.getUrlLogo());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_NAME, partner.getName());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_ADDRESS, partner.getAddress());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_DESCRIPTION, partner.getDescription());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_SITE, partner.getSite());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_PHONE, partner.getPhone());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_LATITUDE, partner.getLatitude());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_LONGITUDE, partner.getLongitude());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_DISCOUNT, partner.getDiscount());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_CATEGORY, partner.getCategory());
        cv.put(FirebaseHelper.FIREBASE_DATABASE_PARTNER_SUBCATEGORY, partner.getSubcategory());

        getWritableDatabase().insert(TABLE_FAVORITE, null, cv);
    }

    public List<Partner> getFavorites(String id_user) {

        String sql = "SELECT * FROM " + TABLE_FAVORITE + " where id_user = ?";
        String args[] = new String[]{id_user};
        Cursor cursor = getReadableDatabase().rawQuery(sql, args);
        List<Partner> partners= new ArrayList<>();
        Partner partner;

        while(cursor.moveToNext()){

            partner = new Partner();

            partner.setUrlLogo(cursor.getString(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_URL_LOGO)));
            partner.setName(cursor.getString(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_NAME)));
            partner.setAddress(cursor.getString(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_ADDRESS)));
            partner.setDescription(cursor.getString(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_DESCRIPTION)));
            partner.setSite(cursor.getString(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_SITE)));
            partner.setPhone(cursor.getString(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_PHONE)));
            partner.setLatitude(cursor.getDouble(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_LATITUDE)));
            partner.setLongitude(cursor.getDouble(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_LONGITUDE)));
            partner.setDiscount(cursor.getString(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_DISCOUNT)));
            partner.setCategory(cursor.getString(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_CATEGORY)));
            partner.setSubcategory(cursor.getString(cursor.getColumnIndex(FirebaseHelper.FIREBASE_DATABASE_PARTNER_SUBCATEGORY)));
            partner.setFavorite(true);
            partners.add(partner);
        }
        cursor.close();

        return partners;
    }

    public boolean isFavorite(String id_user, String url_logo) {
        String sql = "SELECT * FROM " + TABLE_FAVORITE + " WHERE url_logo = ? and id_user = ?;";
        String[] args = {url_logo, id_user};
        final Cursor cursor = getReadableDatabase().rawQuery(sql, args);

        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }else{
            cursor.close();
            return false;
        }
    }

    public long delete(String id_user, String url_logo) {

        String args[] = {id_user, url_logo};
        return getWritableDatabase().delete(TABLE_FAVORITE, "id_user=? and url_logo=?", args);
    }
}