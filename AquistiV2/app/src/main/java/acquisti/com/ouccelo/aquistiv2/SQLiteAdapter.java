package acquisti.com.ouccelo.aquistiv2;


import java.util.ArrayList;
import java.util.List;
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;




/**
 * Created by admin on 28/10/16.
 */

public final class SQLiteAdapter extends  SQLiteOpenHelper {

    private static final String DATABASE_NAME = "articles.db";

    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PARAM = "parametres";
    public static final String PARAM_MODEENCOURS_LISTE = "L";
    public static final String PARAM_MODEENCOURS_ACHAT = "A";


    public static final String PARAM_COLUMN_VBD = "version_bd";
    public static final String PARAM_COLUMN_MODEENCOURS = "modeencours";

    public static final int PARAM_MAJ_MODEENCOURS = 1;
    public static final int PARAM_MAJ_CTRLMODE = 2;
    public static final int PARAM_MAJ_FAMENCOURS = 3;

    public static final String PARAM_COLUMN_MODECONTROLE = "modecontrole";


    public static final String PARAM_COLUMN_LISTEENCOURS = "idlisteencours";
    public static final String PARAM_COLUMN_FAMENCOURS = "idfamencours";

    public static final String TABLE_ARTICLES = "articles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LIBELLE = "libelle ";
    public static final String COLUMN_ID_FAMILLE = "FamilleId";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_PU = "pu";
    public static final String COLUMN_UNITE = "unite";
    public static final String COLUMN_QTE = "qte";



    /*

Gestion des Familles de produits	OptionGestionFamille


SaisieManuelleProduits		OptionSaisieManuelleProduits
SaisieManuelleFamilles		OptionSaisieManuelleFamilles

Gestion détaillée des produits 	OptionGestionArticleDetaillee
Gestion du Prix Unitaire Ht 	OptionGestionArticlePuht
Gestion des Quantités 		OptionGestionArticleQte
Gestion du Taux de Tva 		OptionGestionArticleTva
Gestion du Prix Ttc 		OptionGestionArticlePuttc

 */
    public static final String COLUMN_PARRECOVOVALE = "recovovale";
    public static final String COLUMN_PARGESTFAMILLE = "gestfam";
    public static final String COLUMN_PARSAISIEMANUELLE = "saisiemanuelle";
    public static final String COLUMN_PARSAISIEMANPROD = "saisiemanprod";
    public static final String COLUMN_PARSAISIEMANFAM = "saisiemanfam";
    public static final String COLUMN_PARGESTARTDETAILLEE = "gestartdet";
    public static final String COLUMN_PARGESTARTQTE = "gestartqte";
    public static final String COLUMN_PARGESTARTPU = "gestartpu";

    //public static final String COLUMN_DSLISTE = "idliste";// SI MODE LISTE INTEGER OU IDENTIFIANT LISTE SELECTIONEE OUI 1 0
    //public static final String COLUMN_DSACHATS = "estachete";// SI MODE ACHAT INTEGER OUI O 10

    public static final String TABLE_FAMILLES = "familles";

    public static final String TABLE_LISTES = "listes";
    public static final String COLUMN_LIB_LISTE = "libelle";

    /*
    public static final String TABLE_CREATE_LISTES = "create table IF NOT EXISTS "
            + TABLE_LISTES + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_LIB_LISTE + " text not null unique"
            +");";
     */


    public static final String TABLE_LSTART = "listeart";// jointure listes articles
    public static final String COLUMN_IDLST = "idlst";
    public static final String COLUMN_IDART = "idart";
    public static final String COLUMN_TYPE = "type";


    //TABLE_PARAM
    /*
    champ filtresurliste active
     */
    public static final String COLUMN_PARFILTRELISTE = "filtresurliste";


    // Database creation sql statement
    private static final String TABLE_CREATE_PARAM = "create table IF NOT EXISTS "
            + TABLE_PARAM + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + PARAM_COLUMN_VBD + " integer not null,"
            + PARAM_COLUMN_MODEENCOURS + " text not null,"
            + PARAM_COLUMN_MODECONTROLE + " LONG DEFAULT 0,"
            + PARAM_COLUMN_LISTEENCOURS + " LONG DEFAULT 0,"
            + PARAM_COLUMN_FAMENCOURS + " LONG DEFAULT 0,"
            + COLUMN_PARRECOVOVALE + " INTEGER DEFAULT 0,"
            + COLUMN_PARGESTFAMILLE + " INTEGER DEFAULT 0,"
            + COLUMN_PARSAISIEMANUELLE + " INTEGER DEFAULT 0,"
            + COLUMN_PARSAISIEMANPROD + " INTEGER DEFAULT 0,"
            + COLUMN_PARSAISIEMANFAM + " INTEGER DEFAULT 0,"
            + COLUMN_PARGESTARTDETAILLEE + " INTEGER DEFAULT 0,"
            + COLUMN_PARGESTARTQTE + " INTEGER DEFAULT 0,"
            + COLUMN_PARGESTARTPU + " INTEGER DEFAULT 0,"
            + COLUMN_PARFILTRELISTE + " INTEGER DEFAULT 0"
            +");";
    public static final String TABLE_CREATE_ARTICLE = "create table IF NOT EXISTS "
            + TABLE_ARTICLES + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_LIBELLE + " text not null unique,"
            + COLUMN_ID_FAMILLE + " integer not null,"
            + COLUMN_PU + " REAL DEFAULT 0,"
            + COLUMN_UNITE + " text default \"à l'unité\","
            + COLUMN_QTE + " REAL DEFAULT 0,"
            + COLUMN_IMG + " BLOB DEFAULT NULL"
            +");";
//    + COLUMN_DSLISTE + " INTEGER DEFAULT 0,"
//    + COLUMN_DSACHATS + " INTEGER DEFAULT 0,"

    public static final String TABLE_CREATE_FAMILLE = "create table IF NOT EXISTS "
            + TABLE_FAMILLES + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_LIBELLE + " text not null unique"
            +");";

    public static final String TABLE_CREATE_LISTES = "create table IF NOT EXISTS "
            + TABLE_LISTES + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_LIB_LISTE + " text not null unique"
            +");";


    public static final String TABLE_CREATE_LSTART = "create table IF NOT EXISTS "
            + TABLE_LSTART + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_IDLST + " INTEGER DEFAULT 0,"
            + COLUMN_IDART + " INTEGER DEFAULT 0,"
            + COLUMN_TYPE + " CHAR DEFAULT "+SQLiteAdapter.PARAM_MODEENCOURS_LISTE
            +");";

    public SQLiteAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_CREATE_PARAM);
        db.execSQL(TABLE_CREATE_ARTICLE);
        db.execSQL(TABLE_CREATE_FAMILLE);
        db.execSQL(TABLE_CREATE_LISTES);
        db.execSQL(TABLE_CREATE_LSTART);

    }

    public void dropDb(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LSTART);


    }

    public boolean initDb(SQLiteDatabase db)
    {
        db.execSQL(this.TABLE_CREATE_PARAM);
        db.execSQL(TABLE_CREATE_ARTICLE);
        db.execSQL(TABLE_CREATE_FAMILLE);
        db.execSQL(TABLE_CREATE_LISTES);
        db.execSQL(TABLE_CREATE_LSTART);
        return true;
    }

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteAdapter sqLiteHelper;

    /*private Context context;

    public SQLiteAdapter(Context c){
        context = c;
    }
    */
    public void Read() throws android.database.SQLException {
        sqLiteDatabase = sqLiteHelper.getReadableDatabase();
    }

    public void Write() throws android.database.SQLException {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
    }

    public void close(){

//        sqLiteHelper.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteAdapter.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + this.TABLE_ARTICLES);
        onCreate(db);
    }

}
