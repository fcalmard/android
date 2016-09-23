package ouccelo.com.acquisti;

import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MySQLiteHelper extends SQLiteOpenHelper {

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

    public static final String RECH_TABLE_PARAM_VBD = "select "+PARAM_COLUMN_VBD+" from "+TABLE_PARAM;


    public static final String TABLE_ARTICLES = "articles";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_LIBELLE = "libelle ";
    public static final String COLUMN_ID_FAMILLE = "FamilleId";
    public static final String COLUMN_IMG = "img";


    public static final String COLUMN_PUHT = "puht";
    public static final String COLUMN_TXTVA = "txtva";
    public static final String COLUMN_PUTTC = "puttc";
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
    public static final String COLUMN_PARGESTARTPUHT = "gestartpuht";
    public static final String COLUMN_PARGESTARTTVA = "gestarttva";
    public static final String COLUMN_PARGESTARTPUTTC = "gestartputtc";

    public static final String COLUMN_DSLISTE = "idliste";// SI MODE LISTE INTEGER OU IDENTIFIANT LISTE SELECTIONEE OUI 1 0
    public static final String COLUMN_DSACHATS = "estachete";// SI MODE ACHAT INTEGER OUI O 10

    public static final String TABLE_FAMILLES = "familles";

    public static final String TABLE_LISTES = "listes";
    public static final String COLUMN_LIB_LISTE = "libelle";

    //TABLE_PARAM
    /*
    champ filtresurliste active
     */
    public static final String COLUMN_PARFILTRELISTE = "filtresurliste";

    private static final String DATABASE_NAME = "articles.db";

	// Database creation sql statement
    private static final String TABLE_CREATE_PARAM = "create table "
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
            + COLUMN_PARGESTARTPUHT + " INTEGER DEFAULT 0,"
            + COLUMN_PARGESTARTTVA + " INTEGER DEFAULT 0,"
            + COLUMN_PARGESTARTPUTTC + " INTEGER DEFAULT 0,"
            + COLUMN_PARFILTRELISTE + " INTEGER DEFAULT 0"
            +");";
	public static final String TABLE_CREATE_ARTICLE = "create table "
			+ TABLE_ARTICLES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, "
			+ COLUMN_LIBELLE + " text not null unique,"
            + COLUMN_ID_FAMILLE + " integer not null,"
            + COLUMN_PUHT + " REAL DEFAULT 0,"
            + COLUMN_TXTVA + " REAL DEFAULT 20.00,"
            + COLUMN_PUTTC + " REAL DEFAULT 0.00,"
            + COLUMN_QTE + " REAL DEFAULT 0,"
            + COLUMN_DSLISTE + " INTEGER DEFAULT 0,"
            + COLUMN_DSACHATS + " INTEGER DEFAULT 0,"
            + COLUMN_IMG + " BLOB DEFAULT NULL"
    +");";

    public static final String TABLE_CREATE_FAMILLE = "create table "
            + TABLE_FAMILLES + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_LIBELLE + " text not null unique"
            +");";

    public static final String TABLE_CREATE_LISTES = "create table "
            + TABLE_LISTES + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_LIB_LISTE + " text not null unique"
            +");";


	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

    public boolean MiseAJourParam(Context context,Param oParam)
    {
        boolean bMaj=false;

        //Param newParam = new Param(1,4,PARAM_MODEENCOURS_LISTE,0,0,oParam.getFamilleEnCours());

        final ParamDataSource pds = new ParamDataSource(context);

        pds.open();

        pds.updateParam(oParam);

        pds.close();

        bMaj=true;

        return bMaj;
    }

    public int ControleVersionBaseDeDonnees(Context context,SQLiteDatabase db)
    {
      //Log.v("MYSQLHELPER","CTRLVERSIONBD,138");
        //Log.d("MYSQLHELPER"," RECHERCHE "+TABLE_PARAM);

        /*
        db.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_FAMILLES);
        db.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_LISTES);



        db.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_PARAM);
        db.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_FAMILLES);
        db.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_LISTES);
        try {
            db.execSQL(MySQLiteHelper.TABLE_CREATE_PARAM);
            db.execSQL(MySQLiteHelper.TABLE_CREATE_ARTICLE);
            db.execSQL(MySQLiteHelper.TABLE_CREATE_FAMILLE);
            db.execSQL(MySQLiteHelper.TABLE_CREATE_LISTES);
        }catch (SQLiteException e)
        {
            Log.d("MYSQLHELPER"," PB LORS DE LA CREATION BD "+e.getMessage());

        }finally {
            Log.d("MYSQLHELPER"," CREATION BD EFFECTUEE");

        }

        */

        int vbd=7;
        Long idparam= Long.valueOf(0);

        Param newParam = new Param();
        int derniereversionbd;

        return vbd;
    }

    public void majBaseDeDonnees(Context context,SQLiteDatabase db,int version)
    {
        //Param param = new Param();

       //Log.v("majBaseDeDonnees", "325 version "+ version);

        switch (version)
        {
            case 1://
                //TABLE_CREATE_ARTICLE
                this.MiseAJourParamVersionBase(context,db,1);
                break;
            case 2:/* ajout champ COLUMN_IMG à la TABLE_ARTICLES*/
/*
                String sql = "ALTER TABLE "+TABLE_ARTICLES+" ADD COLUMN "+COLUMN_IMG+" BLOB DEFAULT NULL;";
               //Log.v("majBaseDeDonnees", "336 SQL >"+sql);

                try {
                    db.execSQL(sql);

                }catch (SQLiteException e)
                {
                   //Log.d("ERREUR MAJVBD",e.getMessage().toString());

                }

                this.MiseAJourParamVersionBase(context,db,2);
*/
                /*
                ParamDataSource pds = new ParamDataSource(context);
                pds.open();

                String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                        MySQLiteHelper.PARAM_COLUMN_VBD,MySQLiteHelper.PARAM_COLUMN_MODEENCOURS };



                //db.query(TABLE_ARTICLES,allColumns,"",allColumns,"","","");

                Cursor cursor = db.query(MySQLiteHelper.TABLE_PARAM,
                        allColumns, null, null,
                        null, null, null);

                int nbc=cursor.getCount();

                if(nbc>0)
                {
                    cursor.moveToFirst();

                    param = pds.cursorToParam(cursor);

                    String msg="VBD "+param.getversionBd();

                    //Log.v("**********VBD=",msg);

                    param.setversionBd(version);

                    pds.updateParam(param);

                    //Log.v("RECH_TABLE_PARAM1"," LECTURE "+TABLE_PARAM+" COUNT ="+cursor.getCount());


                }else{

                }

                pds.close();

                cursor.close();
                */


                break;
            case 3:
                /*
                table ARTICLES LISTE ACHATS

                ajout COLUMN_PUHT COLUMN_TXTVA COLUMN_PUTTC COLUMN_QTE

                COLUMN_DSLISTE PREND 1 SI ARTICLE SELECTIONNE OU IDENTIFIANT DE LA LISTE

                COLUMN_DSACHATS PREND 1 SI ARTICLE SELECTIONNE

                TABLE TABLE_PARAM

                    SI PARAM_COLUMN_MODEENCOURS = EN MODE LISTE
                    CACHER  BTN PASSAGE EN MODE CONTROLE imageBtnActiverModeCtrl
                    SI PARAM_COLUMN_MODEENCOURS = EN MODE ACHAT

                        PARAM_COLUMN_MODECONTROLE

                            SI ARTICLE ACHETE
                                SI PARAM_COLUMN_MODECONTROLE= 0 SORT DE LA LISTE
                                SI PARAM_COLUMN_MODECONTROLE = 1 ON AFFICHE TOUTE LA LISTE


                 */
                String sqlart1 ="ALTER TABLE "+TABLE_ARTICLES+" ADD COLUMN "+COLUMN_PUHT+" REAL DEFAULT 0;";
                String sqlart2 ="ALTER TABLE "+TABLE_ARTICLES+" ADD COLUMN "+COLUMN_TXTVA+" REAL DEFAULT 20.00;";
                String sqlart3 ="ALTER TABLE "+TABLE_ARTICLES+" ADD COLUMN "+COLUMN_PUTTC+" REAL DEFAULT 0;";
                String sqlart4 ="ALTER TABLE "+TABLE_ARTICLES+" ADD COLUMN "+COLUMN_QTE+" REAL DEFAULT 1;";

                String sqlart5 ="ALTER TABLE "+TABLE_ARTICLES+" ADD COLUMN "+COLUMN_DSLISTE+" INTEGER DEFAULT 0;";
                String sqlart6 ="ALTER TABLE "+TABLE_ARTICLES+" ADD COLUMN "+COLUMN_DSACHATS+" INTEGER DEFAULT 0;";

                String sqlart7 = "ALTER TABLE "+TABLE_ARTICLES+" ADD COLUMN "+COLUMN_IMG+" BLOB DEFAULT NULL;";

                //String sqlparam= "ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+PARAM_COLUMN_MODECONTROLE+" INTEGER DEFAULT 0";
                //String sqlparam2 = "ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+PARAM_COLUMN_LISTEENCOURS+" STRING DEFAULT '';";

                String sqlalterart2 = "ALTER TABLE "+TABLE_ARTICLES+" DROP COLUMN "+COLUMN_PUHT+" REAL DEFAULT 0;";

                //Log.v("MYSQLHELPER maj table PARAM ",sqlparam);

                try {
                    db.execSQL(sqlart1);
                   //Log.v("MYSQLHELPER","451 SQLART1>"+sqlart1);
                }catch (SQLiteException e)
                {
                   //Log.v("MYSQLHELPER","ERROR LIGNE 454 "+e.getMessage());
                }
                try {
                    db.execSQL(sqlart2);
                   //Log.v("MYSQLHELPER","459 SQLART2>"+sqlart2);
                }catch (SQLiteException e)
                {
                   //Log.v("MYSQLHELPER","ERROR LIGNE 461 "+e.getMessage());
                }
                try {
                    db.execSQL(sqlart3);
                   //Log.v("MYSQLHELPER","463 SQLART3>"+sqlart3);
                }catch (SQLiteException e)
                {
                   //Log.v("MYSQLHELPER","ERROR LIGNE 468 "+e.getMessage());
                }
                try {
                    db.execSQL(sqlart4);
                   //Log.v("MYSQLHELPER","469 SQLART4>"+sqlart4);
                }catch (SQLiteException e)
                {
                   //Log.v("MYSQLHELPER","ERROR LIGNE 475 "+e.getMessage());
                }
                try {
                    db.execSQL(sqlart5);
                   //Log.v("MYSQLHELPER","475 SQLART5>"+sqlart5);
                }catch (SQLiteException e)
                {
                   //Log.v("MYSQLHELPER","ERROR LIGNE 482 "+e.getMessage());
                }
                try {
                    db.execSQL(sqlart6);
                   //Log.v("MYSQLHELPER","481 sqlart1"+sqlart6);
                }catch (SQLiteException e)
                {
                   //Log.v("MYSQLHELPER","ERROR LIGNE 489 "+e.getMessage());
                }
                this.MiseAJourParamVersionBase(context,db,3);

                break;
            case 4:
               //String sqlparam= "ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+PARAM_COLUMN_FAMENCOURS+" LONG DEFAULT 0;";

                //Log.d("MAJVBD 4"," TRAITEMENT MISE A JOUR VERSION BD VERSION="+sqlparam);
/*
                try {
                    //db.execSQL(sqlparam);
                    //Toast.makeText(context,sqlparam,Toast.LENGTH_LONG).show();


                    //db.execSQL(sqlalterart);
                    //db.execSQL(sqlalterparam);


                }catch (SQLiteException e)
                {
                    Log.d("FINALLY MAJVBD 4","FINALLY MISE A JOUR VERSION BD VERSION="+e.getMessage()+" >"+sqlparam+"<");

                }finally {
                    Log.d("FINALLY MAJVBD 4","FINALLY MISE A JOUR VERSION BD VERSION="+version);

                }
                this.MiseAJourParamVersionBase(context,db,4);
*/
                break;

            case 5:
                /*
                int imaj=1;
                String sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARRECOVOVALE+" REAL DEFAULT 0;";

                try {
                    db.execSQL(sqlparam1);
                   //Log.d("MAJVBD "+imaj,sqlparam1);
                    imaj++;

                    //sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTFAMILLE+" REAL DEFAULT 0;";
                    //db.execSQL(sqlparam1);
                    //imaj++;

                    //sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARSAISIEMANUELLE+" REAL DEFAULT 0;";
                    //db.execSQL(sqlparam1);
                    //imaj++;

                    //sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARSAISIEMANPROD+" REAL DEFAULT 0;";
                    //db.execSQL(sqlparam1);
                    //imaj++;

                    //sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARSAISIEMANFAM+" REAL DEFAULT 0;";
                    //db.execSQL(sqlparam1);
                    //imaj++;

                    //sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTDETAILLEE+" REAL DEFAULT 0;";
                    //db.execSQL(sqlparam1);
                    //imaj++;

                    //sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTQTE+" REAL DEFAULT 0;";
                    //db.execSQL(sqlparam1);
                    //imaj++;

                    //sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTPUHT+" REAL DEFAULT 0;";
                   // db.execSQL(sqlparam1);
                    //imaj++;

                    //sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTTVA+" REAL DEFAULT 0;";
                    //db.execSQL(sqlparam1);
                    //imaj++;

                    //sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTPUTTC+" REAL DEFAULT 0;";
                    //db.execSQL(sqlparam1);
                    //imaj++;

                }catch (SQLiteException e)
                {
                   Log.d("ERREUR MAJVBD "+imaj,e.getMessage().toString()+" "+imaj+" >"+sqlparam1+"<");

                }finally {
                   //Log.d("FINALLY MAJVBD 5","FINALLY MISE A JOUR VERSION BD VERSION="+version);



                this.MiseAJourParamVersionBase(context,db,5);

                Log.d("FINALLY MAJVBD 5","FIN TRAITEMENT MISE A JOUR VERSION BD VERSION="+version);
                 }*/
                break;
            case 6:
                //
/*
                sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARFILTRELISTE+" INTEGER DEFAULT 0;";

                try {
                    db.execSQL(sqlparam1);

                }catch (SQLiteException e)
                {
                    Log.d("ERREUR MAJVBD 6 "+e.getMessage().toString(),""+sqlparam1);

                }finally {
                    Log.d("FINALLY MAJVBD 6","FINALLY MISE A JOUR VERSION BD VERSION="+version);

                }
                Log.d("FINALLY MAJVBD 6","FIN TRAITEMENT MISE A JOUR VERSION BD VERSION="+version);

                //this.MiseAJourParamVersionBase(context,db,version+1);
                */


                break;
            case 7:


                break;
            default:
                break;
        }

        try {
            db.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_LISTES);
            db.execSQL(MySQLiteHelper.TABLE_CREATE_LISTES);

        }catch (SQLiteException e)
        {
            //Log.d("ERREUR MAJVBD 7 ",""+e.getMessage().toString()+" version="+version);

        }finally {
            //Log.d("FINALLY MAJVBD 7","FINALLY MISE A JOUR VERSION BD VERSION="+version);

        }
        //Log.d("FINALLY MAJVBD 7","FIN TRAITEMENT MISE A JOUR VERSION BD VERSION="+version);

        this.MiseAJourParamVersionBase(context,db,7);

       //Log.v("MYSQLHELPER MAJBD", "MYSQLHELPER,488");
    }

    public void MiseAJourParamVersionBase(Context context,SQLiteDatabase db,int version)
    {
        Param param = new Param();

        ParamDataSource pds = new ParamDataSource(context);
        pds.open();

       // String[] allColumns = { MySQLiteHelper.COLUMN_ID,
        //        MySQLiteHelper.PARAM_COLUMN_VBD,MySQLiteHelper.PARAM_COLUMN_MODEENCOURS };

        String[] allColumns0 = { MySQLiteHelper.COLUMN_ID,
                MySQLiteHelper.PARAM_COLUMN_VBD
                ,MySQLiteHelper.PARAM_COLUMN_MODEENCOURS
                ,MySQLiteHelper.PARAM_COLUMN_MODECONTROLE
                ,MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS
                ,MySQLiteHelper.PARAM_COLUMN_FAMENCOURS

        };

        String[] allColumns1 = { MySQLiteHelper.COLUMN_ID,
                MySQLiteHelper.PARAM_COLUMN_VBD
                ,MySQLiteHelper.PARAM_COLUMN_MODEENCOURS
                ,MySQLiteHelper.PARAM_COLUMN_MODECONTROLE
                ,MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS
                ,MySQLiteHelper.PARAM_COLUMN_FAMENCOURS
                ,MySQLiteHelper.COLUMN_PARGESTARTPUTTC

        };

        String[] allColumns = {MySQLiteHelper.COLUMN_ID,
                MySQLiteHelper.PARAM_COLUMN_VBD
                , MySQLiteHelper.PARAM_COLUMN_MODEENCOURS
                , MySQLiteHelper.PARAM_COLUMN_MODECONTROLE
                , MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS
                , MySQLiteHelper.PARAM_COLUMN_FAMENCOURS
                , MySQLiteHelper.COLUMN_PARGESTARTPUTTC
                , MySQLiteHelper.COLUMN_PARFILTRELISTE
        };

        if(version==1)
        {
            allColumns=allColumns0;
        }
        if(version>4)
        {
            allColumns=allColumns1;

            if(version==5)
            {
                allColumns=allColumns1;
            }
            if(version==6)
            {
                //                sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARFILTRELISTE+" INTEGER DEFAULT 0;";

               int z=0;
            }
            if(version>6)
            {
                allColumns=pds.allColumns;
            }
        }else
        {
            allColumns=allColumns0;

        }

        allColumns=pds.allColumns;

        Log.v("MYSQLHELPER", "MYSQLHELPER,619 version="+version);

        //db.query(TABLE_ARTICLES,allColumns,"",allColumns,"","","");

        Cursor cursor = db.query(MySQLiteHelper.TABLE_PARAM,
                allColumns, null, null,
                null, null, null);
        Log.v("MYSQLHELPER", "MYSQLHELPER,550");

        int nbc=cursor.getCount();

        if(nbc>0)
        {
            cursor.moveToFirst();
            Log.v("MYSQLHELPER", "MYSQLHELPER, 634 version="+version);

            param = pds.cursorToParam(cursor);

           // long v=cursor.getLong(1);
            //param.setversionBd((int) v);

            String msg="VBD "+param.getversionBd();

           //Log.v("MYSQLHELPER","603 "+msg);

            param.setversionBd(version);
            //Log.v("MYSQLHELPER","562");

            pds.updateParam(param);

            //Log.v("MYSQLHELPER","566");

            //Log.v("RECH_TABLE_PARAM1"," LECTURE "+TABLE_PARAM+" COUNT ="+cursor.getCount());


        }else{

        }

        pds.close();

        cursor.close();

        //Log.v("MYSQLHELPER", "MiseAJourParamVersionBase,573 VERSION="+version);

    }
	@Override
	public void onCreate(SQLiteDatabase db) {
        this.createDb(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        this.dropDb(db);
		onCreate(db);
        //Log.v("MYSQLHELPER","589 DROP TABLES");
	}

    public void createDb(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE_PARAM);
        db.execSQL(TABLE_CREATE_ARTICLE);
        db.execSQL(TABLE_CREATE_FAMILLE);
        db.execSQL(TABLE_CREATE_LISTES);
    }

    public void dropDb(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAM);
        Log.v("DROPDATABASE"," >"+TABLE_PARAM+" DELETE ");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTES);
    }
}
