package net.rayab.mahimah.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MySQLi extends SQLiteOpenHelper {

    //Version Of This Database ( Befor Change And Upgrade to New Changes, Must be Change Version ++
    public static int SQLiVersion = 1;

    //Database Name
    private static String DatabaseName = "Mahimah";

    //Tables
    private static String[] Tables = new String[]{
            "TB_Services",
            "TB_SubServices",
            "TB_SubServicesDiscount",
            "TB_OfferTime",
            "TB_OfferChoise",
            "TB_OfferPrice",
            "TB_Skill",
            "TB_SkillProducts",
    };

    //Fields
    private static String id                                    = "id";
    private static String url                                   = "url";
    private static String name                                  = "name";
    private static String hidden                                = "hidden";
    private static String adress                                = "adress";
    private static String position                              = "position";
    private static String skill_id                              = "skill_id";
    private static String type                                  = "type";
    private static String title                                 = "title";
    private static String price                                 = "price";
    private static String pricenew                              = "pricenew";
    private static String description                           = "description";
    private static String service_id                            = "service_id";
    private static String discount_amount                       = "discount_amount";
    private static String offer_price                           = "offer_price";
    private static String subservice_id_1                       = "subservice_id_1";
    private static String subservice_id_2                       = "subservice_id_2";



    //Create Tables Here
    private static String[] CreatTableQuery = new String[]{
            "CREATE TABLE " + Tables[0] +//TB_Services
                    " (" +
                    id                          + " INTEGER PRIMARY KEY, " +
                    url                         + " INTEGER, " +
                    title                       + " TEXT, " +
                    adress                      + " TEXT, " +
                    hidden                      + " INTEGER, " +
                    position                    + " INTEGER, " +
                    type                        + " INTEGER " +
                    ");",
            "CREATE TABLE " + Tables[1] +//TB_SubServices
                    " (" +
                    id                          + " INTEGER PRIMARY KEY, " +
                    service_id                  + " INTEGER, " +
                    title                       + " TEXT, " +
                    price                       + " TEXT, " +
                    pricenew                    + " TEXT, " +
                    description                 + " TEXT, " +
                    hidden                      + " INTEGER, " +
                    position                    + " INTEGER " +
                    ");",
            "CREATE TABLE " + Tables[2] +//TB_SubServicesDiscount
                    " (" +
                    id                          + " INTEGER PRIMARY KEY, " +
                    subservice_id_1             + " INTEGER, " +
                    subservice_id_2             + " INTEGER, " +
                    discount_amount             + " TEXT " +
                    ");",
            "CREATE TABLE " + Tables[3] +//TB_OfferTime
                    " (" +
                    id                          + " INTEGER PRIMARY KEY, " +
                    subservice_id_1             + " INTEGER, " +
                    subservice_id_2             + " INTEGER, " +
                    discount_amount             + " TEXT " +
                    ");",
            "CREATE TABLE " + Tables[4] +//TB_OfferChoise
                    " (" +
                    id                          + " INTEGER PRIMARY KEY, " +
                    subservice_id_1             + " INTEGER, " +
                    subservice_id_2             + " INTEGER, " +
                    discount_amount             + " TEXT " +
                    ");",
            "CREATE TABLE " + Tables[5] +//TB_OfferPrice
                    " (" +
                    id                          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    offer_price                 + " INTEGER " +
                    ");",
            "CREATE TABLE " + Tables[6] +//TB_Skill
                    " (" +
                    id                          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    name                        + " TEXT, " +
                    price                       + " INTEGER " +
                    ");",
            "CREATE TABLE " + Tables[7] +//TB_SkillProducts
                    " (" +
                    id                          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    service_id                  + " INTEGER, " +
                    skill_id                    + " INTEGER " +
                    ");"
    };

    private SQLiteDatabase Database;
    public MySQLi(Context context){
        super(context, DatabaseName, null, SQLiVersion);
    }
    public void open() throws SQLException {
        Database = this.getWritableDatabase();
    }
    public void close(){
        try {
            this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        try{
            for(String CREAT_TABLE : CreatTableQuery){
                db.execSQL(CREAT_TABLE);
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int Version){
        for(String Table : Tables){
            db.execSQL("DROP TABLE IF EXISTS " + Table);
        }
        onCreate(db);
    }
    public boolean reNewDataBase(){
        SQLiteDatabase db = this.getWritableDatabase();
        for(String Table : Tables){
            db.execSQL("DROP TABLE IF EXISTS " + Table);
        }
        onCreate(db);

        return true;
    }
    public boolean TRAUNCATE(String Table){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String Query = "TRUNCATE TBALE " + Table;
            db.execSQL(Query);
            db.close();

            return true;
        }catch (Exception ignored){
            return false;
        }
    }
    public String Execute(String Query){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(Query);
            db.close();

            return "Execute SuccessFully.";
        }catch (Exception Ex){
            return  Ex.getMessage();
        }
    }
    public <T> List<T> Select(String Query, Class mClass, Context context){
        Context mContext = context;
        SQLiteDatabase db = this.getWritableDatabase();
        List<T> mList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery(Query, null);
            cursor.moveToFirst();
            String[] ColumnNames = cursor.getColumnNames();
            int cursorCount = cursor.getCount();
            for (int i = 0; i < cursorCount; i++) {
                int CorsurColumn = 0;
                Object Obj = mClass.newInstance();
                for (String ColumnName : ColumnNames) {
                    Field cField = mClass.getDeclaredField(ColumnName);
                    cField.setAccessible(true);

                    if (cField.getType().equals(int.class)) {
                        cField.set(Obj, cursor.getInt(CorsurColumn));
                    } else if (cField.getType().equals(String.class)) {
                        cField.set(Obj, cursor.getString(CorsurColumn));
                    } else if (cField.getType().equals(Double.class)) {
                        cField.set(Obj, cursor.getDouble(CorsurColumn));
                    } else if (cField.getType().equals(Float.class)) {
                        cField.set(Obj, cursor.getFloat(CorsurColumn));
                    } else if (cField.getType().equals(Long.class)) {
                        cField.set(Obj, cursor.getLong(CorsurColumn));
                    }
                    CorsurColumn++;
                }
                Class<T> dClass = (Class<T>) Obj.getClass();
                try {
                    mList.add(dClass.cast(Obj));
                } catch (Exception Ex) {
                    String Er = Ex.getMessage();
                }
                cursor.moveToNext();
            }
        }catch (Exception Ex){
            String Er = Ex.getMessage();
        }
        return mList;
    }
    public int LastId(String Table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + Table;
        Cursor cursor = db.rawQuery(Query, null);

        if (cursor.moveToLast()) {
            //name = cursor.getString(column_index);//to get other values
            return cursor.getInt(0);//to get id, 0 is the column index
        }else{
            return 0;
        }
    }
    public int Count(String Table){
        try{
            String Query = "SELECT * FROM " + Table;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor Curs = db.rawQuery(Query, null);
            db.close();
            return Curs.getCount();
        }catch (Exception ignored){
            return  0;
        }
    }

}
