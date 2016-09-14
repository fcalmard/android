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

    public static final String SQL_INIT_LISTES=" "+COLUMN_DSLISTE;

    public static final String TABLE_FAMILLES = "familles";

    public static final String RECH_TABLE_PARAM_ALL = "select "+COLUMN_ID+","+PARAM_COLUMN_VBD+","+PARAM_COLUMN_MODEENCOURS+" from "+TABLE_PARAM;

    private static final String DATABASE_NAME = "articles.db";

	// Database creation sql statement
    private static final String TABLE_CREATE_PARAM = "create table "
            + TABLE_PARAM + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + PARAM_COLUMN_VBD + " integer not null,"
            + PARAM_COLUMN_MODEENCOURS + " text not null,"
            + PARAM_COLUMN_MODECONTROLE + " LONG DEFAULT 0,"
            + PARAM_COLUMN_LISTEENCOURS + " LONG DEFAULT 0,"
            + PARAM_COLUMN_FAMENCOURS + " LONG DEFAULT 0"
            +");";
	public static final String TABLE_CREATE_ARTICLE = "create table "
			+ TABLE_ARTICLES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, "
			+ COLUMN_LIBELLE + " text not null unique,"
	        + COLUMN_ID_FAMILLE + " integer not null"
			+");";

	public static final String TABLE_CREATE_FAMILLE = "create table "
			+ TABLE_FAMILLES + "(" + COLUMN_ID
			+ " integer primary key autoincrement, "
			+ COLUMN_LIBELLE + " text not null unique"
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

       /*


        *
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILLES);
        db.execSQL(MySQLiteHelper.TABLE_CREATE_PARAM);
        db.execSQL(MySQLiteHelper.TABLE_CREATE_ARTICLE);
        db.execSQL(MySQLiteHelper.TABLE_CREATE_FAMILLE);
        */
      //Log.d("MYSQLHELPER"," 145");

        int vbd=0;
        Long idparam= Long.valueOf(0);

        Param newParam = new Param();

        try {

//MySQLiteHelper.PARAM_COLUMN_MODEENCOURS,MySQLiteHelper.PARAM_COLUMN_MODECONTROLE

            String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                    MySQLiteHelper.PARAM_COLUMN_VBD };


            Cursor cursor = db.query(MySQLiteHelper.TABLE_PARAM,
                    allColumns, null, null,
                    null, null, null);


            int nbc=cursor.getCount();
          //Log.d("RECH_TABLE_PARAM1","166 LECTURE "+TABLE_PARAM+" COUNT ="+nbc);

            int derniereversionbd;

            derniereversionbd=5;

            if(nbc>0)
            {
                cursor.moveToFirst();

                vbd=cursor.getInt(1);
                //Log.v("MYSQLHELPER","MYSQLHELPER,212 VERSION BASE DE DONNÉES"+vbd);

                //for(int l=0; l<=5; l++){
                for(int version=vbd; version<=derniereversionbd; version++){
                    //Log.v("MYSQLHELPER","MYSQLHELPER,215 VERSION BASE DE DONNÉES"+version);
                    this.majBaseDeDonnees(context,db,version);

                }
            }else{

              //Log.d("RECH_TABLE_PARAM1"," 185 LECTURE NBC=0");

                vbd=1;

                // createParam
                ParamDataSource pds = new ParamDataSource(context);
              //Log.d("RECH_TABLE_PARAM1"," 191");

                pds.open();
              Log.d("RECH_TABLE_PARAM1"," 194");
                newParam=pds.createParam(vbd,MySQLiteHelper.PARAM_MODEENCOURS_LISTE,0);
              Log.d("RECH_TABLE_PARAM1"," 196");

                pds.close();

                for(int version=vbd; version<=derniereversionbd; version++){
                   //Log.v("MYSQLHELPER","MYSQLHELPER,179 VERSION BASE DE DONNÉES"+version);
                    this.majBaseDeDonnees(context,db,version);
                }


            }

            cursor.close();

            //Log.d("RECH_TABLE_PARAM1"," RECHERCHE  1 ID PARAM = "+idparam.toString());

        }catch (SQLiteException e)//Exception
        {
          //Log.v("RECH_TABLE_PARAM1","ERREUR 243 ");

            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAM);

            //db.execSQL(TABLE_CREATE_PARAM);

            //Log.v("MYSQLHELPER TABLE_CREATE_PARAM",TABLE_CREATE_PARAM);

            this.majBaseDeDonnees(context,db,1);
            this.majBaseDeDonnees(context,db,2);
            this.majBaseDeDonnees(context,db,3);
            this.majBaseDeDonnees(context,db,4);
            this.majBaseDeDonnees(context,db,5);

            //Log.d("CtrlVBD","333");


            //String smode=PARAM_MODEENCOURS_LISTE;

            //Param nparam = new Param(0,versionBd,smode);

            //Log.d("ERREURCONTROLEVERSIONBD"," ERREURSTRING  "+e.toString()+" CAUSE "+e.getCause());
            //Log.d("ERREURCONTROLEVERSIONBD"," RECHERCHE  "+e.getMessage());
/**/


           // newParam.setversionBd(1);
            vbd=1;
           // newParam.setModeencours(MySQLiteHelper.PARAM_MODEENCOURS_LISTE);

            ParamDataSource pds = new ParamDataSource(context);
            pds.open();
            //Log.v("MYSQLHELPER"," 416");
            newParam=pds.createParam(vbd,MySQLiteHelper.PARAM_MODEENCOURS_LISTE,0);
            //Log.v("MYSQLHELPER"," 418");

            /* idparam=newParam.getId(); */

            //Log.d("ERREURCONTROLEVERSIONBD",TABLE_CREATE_PARAM+" IDPARAM="+idparam);

            pds.close();

        }

       //Log.v("RECH_TABLE_PARAM2"," 316, RECHERCHE  2 VERSION BASE DE DONNEES "+vbd);

        return vbd;
    }

    public void majBaseDeDonnees(Context context,SQLiteDatabase db,int version)
    {
        //Param param = new Param();

       //Log.v("majBaseDeDonnees", "325 version "+ version);

        switch (version)
        {
            case 1://
                this.MiseAJourParamVersionBase(context,db,1);
                break;
            case 2:/* ajout champ COLUMN_IMG à la TABLE_ARTICLES*/

                String sql = "ALTER TABLE "+TABLE_ARTICLES+" ADD COLUMN "+COLUMN_IMG+" BLOB DEFAULT NULL;";
               //Log.v("majBaseDeDonnees", "336 SQL >"+sql);

                try {
                    db.execSQL(sql);
                    /*
                    openDB.execSQL("ALTER TABLE favs" + " DROP COLUMN favsCount");
                     */
                    sql = "ALTER TABLE "+TABLE_ARTICLES+" DROP COLUMN "+COLUMN_IMG+";";
                    //Log.v("majBaseDeDonnees", "344 SQL >"+sql);
                    //db.execSQL(sql);

                }catch (SQLiteException e)
                {
                   //Log.d("ERREUR MAJVBD",e.getMessage().toString());

                }

                this.MiseAJourParamVersionBase(context,db,2);

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

                //String sqlparam= "ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+PARAM_COLUMN_MODECONTROLE+" INTEGER DEFAULT 0";
                //String sqlparam2 = "ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+PARAM_COLUMN_LISTEENCOURS+" STRING DEFAULT '';";

                String sqlalterart2 = "ALTER TABLE "+TABLE_ARTICLES+" DROP COLUMN "+COLUMN_PUHT+" REAL DEFAULT 0;";

                /*sqlalterart = sqlalterart + " DROP COLUMN "+COLUMN_TXTVA;
                sqlalterart = sqlalterart + " DROP COLUMN "+COLUMN_PUTTC;
                sqlalterart = sqlalterart + " DROP COLUMN "+COLUMN_QTE;

                sqlalterart = sqlalterart + " DROP COLUMN "+COLUMN_DSLISTE;
                sqlalterart = sqlalterart + " DROP COLUMN "+COLUMN_DSACHATS;*/

                //String sqlalterparam = "ALTER TABLE "+TABLE_PARAM+" DROP COLUMN "+PARAM_COLUMN_MODECONTROLE;
                //sqlalterparam=sqlalterparam+ "ALTER TABLE "+TABLE_PARAM+" DROP COLUMN "+PARAM_COLUMN_FAMENCOURS;

               ////Log.v("MYSQLHELPER","436 sqlalterart"+sqlalterart);
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

                break;
            case 4:
               String sqlparam= "ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+PARAM_COLUMN_FAMENCOURS+" LONG DEFAULT 0;";
                try {
                    //db.execSQL(sqlparam);
                    //Toast.makeText(context,sqlparam,Toast.LENGTH_LONG).show();


                    //db.execSQL(sqlalterart);
                    //db.execSQL(sqlalterparam);

                    this.MiseAJourParamVersionBase(context,db,4);


                }catch (SQLiteException e)
                {
                    //Log.d("ERREUR MAJVBD",e.getMessage().toString());

                }
            case 5:
                int imaj=1;
                String sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARRECOVOVALE+" REAL DEFAULT 0;";

                try {
                    db.execSQL(sqlparam1);
                   //Log.d("MAJVBD "+imaj,sqlparam1);
                    imaj++;

                    sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTFAMILLE+" REAL DEFAULT 0;";
                    db.execSQL(sqlparam1);
                    imaj++;

                    sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARSAISIEMANUELLE+" REAL DEFAULT 0;";
                    db.execSQL(sqlparam1);
                    imaj++;

                    sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARSAISIEMANPROD+" REAL DEFAULT 0;";
                    db.execSQL(sqlparam1);
                    imaj++;

                    sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARSAISIEMANFAM+" REAL DEFAULT 0;";
                    db.execSQL(sqlparam1);
                    imaj++;

                    sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTDETAILLEE+" REAL DEFAULT 0;";
                    db.execSQL(sqlparam1);
                    imaj++;

                    sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTQTE+" REAL DEFAULT 0;";
                    db.execSQL(sqlparam1);
                    imaj++;

                    sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTPUHT+" REAL DEFAULT 0;";
                    db.execSQL(sqlparam1);
                    imaj++;

                    sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTTVA+" REAL DEFAULT 0;";
                    db.execSQL(sqlparam1);
                    imaj++;

                    sqlparam1 ="ALTER TABLE "+TABLE_PARAM+" ADD COLUMN "+COLUMN_PARGESTARTPUTTC+" REAL DEFAULT 0;";
                    db.execSQL(sqlparam1);
                    imaj++;

                }catch (SQLiteException e)
                {
                   Log.d("ERREUR MAJVBD "+imaj,e.getMessage().toString()+" "+imaj+" >"+sqlparam1+"<");

                }finally {
                   //Log.d("FINALLY MAJVBD 5","FINALLY MISE A JOUR VERSION BD VERSION="+version);

                }




                this.MiseAJourParamVersionBase(context,db,version+1);

                Log.d("FINALLY MAJVBD 5","FIN TRAITEMENT MISE A JOUR VERSION BD VERSION="+version);

            default:
                break;
        }
       //Log.v("MYSQLHELPER MAJBD", "MYSQLHELPER,488");
    }

    public void MiseAJourParamVersionBase(Context context,SQLiteDatabase db,int version)
    {
        Param param = new Param();

        ParamDataSource pds = new ParamDataSource(context);
        pds.open();

       // String[] allColumns = { MySQLiteHelper.COLUMN_ID,
        //        MySQLiteHelper.PARAM_COLUMN_VBD,MySQLiteHelper.PARAM_COLUMN_MODEENCOURS };

        String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                MySQLiteHelper.PARAM_COLUMN_VBD
                ,MySQLiteHelper.PARAM_COLUMN_MODEENCOURS
                ,MySQLiteHelper.PARAM_COLUMN_MODECONTROLE
                ,MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS
                ,MySQLiteHelper.PARAM_COLUMN_FAMENCOURS};
if(version>4)
{
    allColumns=pds.allColumns;

}

        Log.v("MYSQLHELPER", "MYSQLHELPER,543");

        //db.query(TABLE_ARTICLES,allColumns,"",allColumns,"","","");

        Cursor cursor = db.query(MySQLiteHelper.TABLE_PARAM,
                allColumns, null, null,
                null, null, null);
        Log.v("MYSQLHELPER", "MYSQLHELPER,550");

        int nbc=cursor.getCount();

        if(nbc>0)
        {
            cursor.moveToFirst();

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
    }

    public void dropDb(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILLES);
    }
}
