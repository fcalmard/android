package ouccelo.com.acquisti;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ParamDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    public String[] allColumns0 = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.PARAM_COLUMN_VBD
            ,MySQLiteHelper.PARAM_COLUMN_MODEENCOURS
            ,MySQLiteHelper.PARAM_COLUMN_MODECONTROLE
            ,MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS
            ,MySQLiteHelper.PARAM_COLUMN_FAMENCOURS


    };

    public String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.PARAM_COLUMN_VBD
            ,MySQLiteHelper.PARAM_COLUMN_MODEENCOURS
            ,MySQLiteHelper.PARAM_COLUMN_MODECONTROLE
            ,MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS
            ,MySQLiteHelper.PARAM_COLUMN_FAMENCOURS
            ,MySQLiteHelper.COLUMN_PARRECOVOVALE
            ,MySQLiteHelper.COLUMN_PARGESTFAMILLE
            ,MySQLiteHelper.COLUMN_PARSAISIEMANUELLE
            ,MySQLiteHelper.COLUMN_PARSAISIEMANPROD
            ,MySQLiteHelper.COLUMN_PARSAISIEMANFAM
            ,MySQLiteHelper.COLUMN_PARGESTARTDETAILLEE
            ,MySQLiteHelper.COLUMN_PARGESTARTQTE
            ,MySQLiteHelper.COLUMN_PARGESTARTPUHT
            ,MySQLiteHelper.COLUMN_PARGESTARTTVA
            ,MySQLiteHelper.COLUMN_PARGESTARTPUTTC

    };

    public ParamDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase()
    {
        return this.database;
    }

    public void close() {
        dbHelper.close();
        this.database=null;
    }

    public Param createParam(int versionbd,String modeencours,int modectrl) {
        Param newParam=null;
       //Log.v("PARAMDTS ","CREATEPARAM 40");
        ContentValues values = new ContentValues();

        //MySQLiteHelper.PARAM_MODEENCOURS_ACHAT
        //MySQLiteHelper.PARAM_MODEENCOURS_LISTE;

        values.put(MySQLiteHelper.PARAM_COLUMN_VBD, versionbd);

        values.put(MySQLiteHelper.PARAM_COLUMN_MODEENCOURS, modeencours);

        values.put(MySQLiteHelper.PARAM_COLUMN_MODECONTROLE, modectrl);

        values.put(MySQLiteHelper.PARAM_COLUMN_MODECONTROLE, modectrl);

        long insertId=0;
       //Log.v("PARAMDTS ","CREATEPARAM 58 VBD="+versionbd);

       /* Log.d("TEST","insertion PARAM 1 "+);
        Log.d("TEST","insertion PARAM 1 modeencours >"+modeencours+"<");
       *
       * */

        try{
            insertId = database.insert(MySQLiteHelper.TABLE_PARAM, null,values);
        }
        catch (Exception e){

           //Log.d("PARAMDTS","insertion PARAM 70 ERREUR= "+e.getMessage());

        }

       //Log.v("PARAMDTS","72, insertion PARAM 4 "+versionbd+" ID="+insertId);

        if (insertId!=0)
        {
            Cursor cursor = database.query(MySQLiteHelper.TABLE_PARAM,
                    this.allColumns0, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            if(versionbd>4)
            {
                cursor = database.query(MySQLiteHelper.TABLE_PARAM,
                        this.allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                        null, null, null);
            }

           //Log.d("*** NEWPARAM OK","PARAM COUNT ="+cursor.getCount());
            if(cursor.getCount()>0)
            {
                cursor.moveToFirst();


               //Log.v("PARAMDTS","85");
                newParam = cursorToParam(cursor);
               //Log.v("PARAMDTS","87");

            }
            cursor.close();

        }
        //Log.d("PARAMDTS","93, insertion PARAM 6 "+versionbd);

        return newParam;
    }

    public void updateParam(Param param){

        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.PARAM_COLUMN_VBD, param.getversionBd());

        values.put(MySQLiteHelper.PARAM_COLUMN_MODEENCOURS, param.getModeencours());

        values.put(MySQLiteHelper.PARAM_COLUMN_MODECONTROLE, param.getBmodectrl());

        values.put(MySQLiteHelper.PARAM_COLUMN_FAMENCOURS, param.getFamilleEnCours());

        values.put(MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS, param.getListeEnCours());

        Log.d("PARAMDTS","146, insertion PARAM param.getversionBd()= "+param.getversionBd());

        if(param.getversionBd()>4)
        {

            Log.d("PARAMDTS","151, insertion PARAM param.getversionBd()= "+param.getversionBd());

            values.put(MySQLiteHelper.COLUMN_PARRECOVOVALE, param.getBrecovocale());

            values.put(MySQLiteHelper.COLUMN_PARGESTFAMILLE, param.getBgestfamart());
            values.put(MySQLiteHelper.COLUMN_PARSAISIEMANUELLE, param.getBsaisiemanuelle());
            values.put(MySQLiteHelper.COLUMN_PARSAISIEMANPROD, param.getBsaisiemanart());
            values.put(MySQLiteHelper.COLUMN_PARSAISIEMANFAM, param.getBsaisiemanfamille());
            values.put(MySQLiteHelper.COLUMN_PARGESTARTDETAILLEE, param.getBsaisiedetailart());
            values.put(MySQLiteHelper.COLUMN_PARGESTARTQTE, param.getBsaisiedetailartqte());
            values.put(MySQLiteHelper.COLUMN_PARGESTARTPUHT, param.getBsaisiedetailartpuht());
            values.put(MySQLiteHelper.COLUMN_PARGESTARTTVA, param.getBsaisiedetailarttva());
            values.put(MySQLiteHelper.COLUMN_PARGESTARTPUTTC, param.getBsaisiedetailartputtc());
        }


        //Log.d("UPDATEPARAM"," >ID >"+param.getId()+"<<<<<<");
		//Log.d("UPDATEPARAM"," VBD >>>>>>>"+param.getversionBd()+"<<<<<<");
		//Log.d("UPDATEPARAM"," MODE >>>>>>>"+param.getModeencours()+"<<<<<<");
		//Log.d("UPDATEPARAM"," MODECTRL >>>>>>>"+param.getBmodectrl()+"<<<<<<");

        database.update(MySQLiteHelper.TABLE_PARAM, values,
                MySQLiteHelper.COLUMN_ID + " = ? ",
                new String[] { String.valueOf(param.getId()) });
    }

    public void deleteParam(Param param) {
        long id = param.getId();
        database.delete(MySQLiteHelper.TABLE_PARAM,
                MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Param> getAllParams(boolean spin) {
        List<Param> params = new ArrayList<>();

        if(spin)
        {
            Param param0 = new Param(0,0,"Saisissez un param");
            params.add(param0);
        }

        //Log.v("PARAM DATA SOURCE, LISTE PARAMETRES ","134");

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PARAM,
                this.allColumns, null, null, null, null, null);

        //Log.v("PARAM DATA SOURCE, LISTE PARAMETRES ","139");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Param param = cursorToParam(cursor);
            params.add(param);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return params;
    }

    public List<Param> getAllParams(String order) {

        List<Param> params = new ArrayList<Param>();

        //Log.v("PARAM DATA SOURCE, LISTE PARAMETRES ","158");

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PARAM,
                this.allColumns,null,null,null,null,order);
        //Log.v("PARAM DATA SOURCE, LISTE PARAMETRES ","162");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Param param = cursorToParam(cursor);
            params.add(param);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return params;

    }

    public Param cursorToParam(Cursor cursor) {
        /*

        private long id;
        private int versionbd;
        private String modeencours;
        private boolean bmodectrl;
        private Long listeencours;
        private Long familleEnCours;

        String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                MySQLiteHelper.PARAM_COLUMN_VBD
                ,MySQLiteHelper.PARAM_COLUMN_MODEENCOURS
                ,MySQLiteHelper.PARAM_COLUMN_MODECONTROLE
                ,MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS
                ,MySQLiteHelper.PARAM_COLUMN_FAMENCOURS};

            ,MySQLiteHelper.COLUMN_PARRECOVOVALE
            ,MySQLiteHelper.COLUMN_PARGESTFAMILLE
            ,MySQLiteHelper.COLUMN_PARSAISIEMANUELLE
            ,MySQLiteHelper.COLUMN_PARSAISIEMANPROD
            ,MySQLiteHelper.COLUMN_PARSAISIEMANFAM
            ,MySQLiteHelper.COLUMN_PARGESTARTDETAILLEE
            ,MySQLiteHelper.COLUMN_PARGESTARTQTE
            ,MySQLiteHelper.COLUMN_PARGESTARTPUHT
            ,MySQLiteHelper.COLUMN_PARGESTARTTVA
            ,MySQLiteHelper.COLUMN_PARGESTARTPUTTC
         */

        int icol=0;

        Param param = new Param();
        Long id= cursor.getLong(0);
        param.setId(id);

        icol++;
        int vbd=0;
        if (icol<cursor.getColumnCount())
        {
            vbd=cursor.getInt(1);

        }
        param.setversionBd(vbd);
        icol++;
        String s=MySQLiteHelper.PARAM_MODEENCOURS_LISTE;

        if (icol<cursor.getColumnCount())
        {
            s=cursor.getString(2);

        }
        param.setModeencours(s);
        icol++;

        //Log.v("PARAMDTS","cursorToParam 214 PARAM_COLUMN_MODEENCOURS "+param.getModeencours());
       // Log.v("PARAMDTS","cursorToParam 214 FAMILLE="+param.getFamilleEnCours());

        int ctrlactive=0;
        if (icol<cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(3);

        }
        icol++;

        param.setBmodectrl(ctrlactive==1);

        Long listeid= Long.valueOf(0);
        if (icol<cursor.getColumnCount())
        {
            listeid=cursor.getLong(4);

        }
        param.setListeencours(listeid);
        icol++;

        Long lf= Long.valueOf(0);
        if (icol<cursor.getColumnCount())
        {
            lf=cursor.getLong(5);

        }
        param.setFamilleEnCours(lf);
        icol++;

        Log.v("PDS, CURSORTOPARAM","247 LECTURE listeid="+listeid+" ID="+id);

        ctrlactive=0;
        if (icol<cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(6);//RECOVOCALE
        }
        icol++;
        param.setBrecovocale(ctrlactive==1);

        Log.v("PDS, CURSORTOPARAM","253");
        ctrlactive=0;
        if (icol<=cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(7);//GEST FAMILLE
        }
        icol++;
        param.setBgestfamart(ctrlactive==1);

        ctrlactive=0;
        if (icol<=cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(8);//SAISIE MANUELLE
        }
        icol++;
        param.setBsaisiemanuelle(ctrlactive==1);

        ctrlactive=0;
        if (icol<=cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(9);//SAISIE MANUELLE ARTICLE
        }
        icol++;
        param.setBsaisiemanart(ctrlactive==1);

        ctrlactive=0;
        if (icol<=cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(10);//SAISIE MANUELLE FAMILLE
        }
        icol++;
        param.setBsaisiemanfamille(ctrlactive==1);

        ctrlactive=0;
        if (icol<=cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(11);//SAISIE DETAIL ARTICLE
        }
        icol++;
        param.setBsaisiedetailart(ctrlactive==1);

        ctrlactive=0;
        if (icol<=cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(12);//SAISIE DETAIL ARTICLE QTE
        }
        icol++;
        param.setBsaisiedetailartqte(ctrlactive==1);

        ctrlactive=0;
        if (icol<=cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(13);//SAISIE DETAIL ARTICLE PU HT
        }
        icol++;
        param.setBsaisiedetailartpuht(ctrlactive==1);

        ctrlactive=0;
        if (icol<=cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(14);//SAISIE DETAIL ARTICLE TVA
        }
        icol++;
        param.setBsaisiedetailarttva(ctrlactive==1);

        ctrlactive=0;
        if (icol<=cursor.getColumnCount())
        {
            ctrlactive=cursor.getInt(15);//SAISIE DETAIL ARTICLE PU TTC
        }
        icol++;
        param.setBsaisiedetailartputtc(ctrlactive==1);

        return param;
    }

    public Param LectureParam()
    {
        Param newParam = new Param();

        String snouveaumode=MySQLiteHelper.PARAM_MODEENCOURS_LISTE;

        newParam.setModeencours(snouveaumode);
        newParam.setFamilleEnCours((long) 0);
        //Log.v("PARAM DATA SOURCE, CHANGE MODE ","189");

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PARAM,
                this.allColumns,null,null,null,null,null);
     //Log.v("PARAM DATA SOURCE","232 NBPARAM "+cursor.getCount());

        if(cursor.getCount()>0)
        {
        //Log.d("PARAMDATASOURCE","236, GETCOUNT >"+cursor.getCount()+"<");

            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {

                newParam = cursorToParam(cursor);
            }

            //Log.d("PARAMDATASOURCE","255, GETFAMILLEID >"+cursor.getLong(5)+"<");
           //Log.d("PARAMDATASOURCE","256, GETMODEENCOURS >"+cursor.getString(2)+"<");
        }


        /*
        while (!cursor.isAfterLast()) {
        //if (!cursor.isAfterLast()) {

        }
        */
        return newParam;
    }

    public String ParamChangeMode()
    {
        String snouveaumode=MySQLiteHelper.PARAM_MODEENCOURS_LISTE;
        //Log.v("PARAM DATA SOURCE, CHANGE MODE ","189");

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PARAM,
                this.allColumns,null,null,null,null,null);
        //Log.v("PARAM DATA SOURCE, CHANGE MODE ","196");

        //Log.d("PARAMCHANGEMODE"," GETCOUNT >"+cursor.getCount()+"<");

        int c=0;
        cursor.moveToFirst();
        // while (!cursor.isAfterLast()) {
        if (!cursor.isAfterLast()) {
            c++;
            Param param = cursorToParam(cursor);

            String smode=param.getModeencours();

            switch (smode) {
                case MySQLiteHelper.PARAM_MODEENCOURS_ACHAT:

                    snouveaumode = MySQLiteHelper.PARAM_MODEENCOURS_LISTE;


                    break;
                case MySQLiteHelper.PARAM_MODEENCOURS_LISTE:

                    snouveaumode = MySQLiteHelper.PARAM_MODEENCOURS_ACHAT;

                    break;
                default:
                    snouveaumode = MySQLiteHelper.PARAM_MODEENCOURS_LISTE;
                    break;

            }

            param.setModeencours(snouveaumode);

            updateParam(param);

        }
        // make sure to close the cursor
        cursor.close();

        return snouveaumode;

    }

}
