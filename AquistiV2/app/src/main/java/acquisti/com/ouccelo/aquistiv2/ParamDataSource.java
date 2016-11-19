package acquisti.com.ouccelo.aquistiv2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ParamDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteAdapter dbHelper;
    public String[] allColumns0 = { SQLiteAdapter.COLUMN_ID,
            SQLiteAdapter.PARAM_COLUMN_VBD
            ,SQLiteAdapter.PARAM_COLUMN_MODEENCOURS
            ,SQLiteAdapter.PARAM_COLUMN_MODECONTROLE
            ,SQLiteAdapter.PARAM_COLUMN_LISTEENCOURS
            ,SQLiteAdapter.PARAM_COLUMN_FAMENCOURS


    };

    public String[] allColumns = { SQLiteAdapter.COLUMN_ID,
            SQLiteAdapter.PARAM_COLUMN_VBD
            ,SQLiteAdapter.PARAM_COLUMN_MODEENCOURS
            ,SQLiteAdapter.PARAM_COLUMN_MODECONTROLE
            ,SQLiteAdapter.PARAM_COLUMN_LISTEENCOURS
            ,SQLiteAdapter.PARAM_COLUMN_FAMENCOURS
            ,SQLiteAdapter.COLUMN_PARRECOVOVALE
            ,SQLiteAdapter.COLUMN_PARGESTFAMILLE
            ,SQLiteAdapter.COLUMN_PARSAISIEMANUELLE
            ,SQLiteAdapter.COLUMN_PARSAISIEMANPROD
            ,SQLiteAdapter.COLUMN_PARSAISIEMANFAM
            ,SQLiteAdapter.COLUMN_PARGESTARTDETAILLEE
            ,SQLiteAdapter.COLUMN_PARGESTARTQTE
            ,SQLiteAdapter.COLUMN_PARGESTARTPU
            ,SQLiteAdapter.COLUMN_PARFILTRELISTE

    };

    public ParamDataSource(Context context) {

       // dbHelper = new SQLiteAdapter(context);
        dbHelper = new SQLiteAdapter(context);
    }

    public boolean open()  {
//throws SQLException



        database = dbHelper.getWritableDatabase();
        boolean b = database.isOpen();


        return b;

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

        //SQLiteAdapter.PARAM_MODEENCOURS_ACHAT
        //SQLiteAdapter.PARAM_MODEENCOURS_LISTE;

        values.put(SQLiteAdapter.PARAM_COLUMN_VBD, versionbd);

        values.put(SQLiteAdapter.PARAM_COLUMN_MODEENCOURS, modeencours);

        values.put(SQLiteAdapter.PARAM_COLUMN_MODECONTROLE, modectrl);

        values.put(SQLiteAdapter.PARAM_COLUMN_MODECONTROLE, modectrl);

        long insertId=0;
       //Log.v("PARAMDTS ","CREATEPARAM 58 VBD="+versionbd);

       /* Log.d("TEST","insertion PARAM 1 "+);
        Log.d("TEST","insertion PARAM 1 modeencours >"+modeencours+"<");
       *
       * */

        try{
            insertId = database.insert(SQLiteAdapter.TABLE_PARAM, null,values);
        }
        catch (Exception e){

           //Log.d("PARAMDTS","insertion PARAM 70 ERREUR= "+e.getMessage());

        }

       //Log.v("PARAMDTS","72, insertion PARAM 4 "+versionbd+" ID="+insertId);

        if (insertId!=0)
        {
            Cursor cursor = database.query(SQLiteAdapter.TABLE_PARAM,
                    this.allColumns0, SQLiteAdapter.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            if(versionbd>4)
            {
                cursor = database.query(SQLiteAdapter.TABLE_PARAM,
                        this.allColumns, SQLiteAdapter.COLUMN_ID + " = " + insertId, null,
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

        values.put(SQLiteAdapter.PARAM_COLUMN_VBD, param.getversionBd());

        //Log.v("updateParam 145"," MODE EN COURS >"+SQLiteAdapter.PARAM_COLUMN_MODEENCOURS+">"+param.getModeencours()+"<");

        values.put(SQLiteAdapter.PARAM_COLUMN_MODEENCOURS, param.getModeencours());

        values.put(SQLiteAdapter.PARAM_COLUMN_MODECONTROLE, param.getBmodectrl());

        values.put(SQLiteAdapter.PARAM_COLUMN_FAMENCOURS, param.getFamilleEnCours());

        values.put(SQLiteAdapter.PARAM_COLUMN_LISTEENCOURS, param.getListeEnCours());

      //Log.d("PARAMDTS","146, insertion PARAM param.getversionBd()= "+param.getversionBd());


          //Log.d("PARAMDTS","151, insertion PARAM param.getversionBd()= "+param.getversionBd());

        values.put(SQLiteAdapter.COLUMN_PARRECOVOVALE, param.getBrecovocale());

        values.put(SQLiteAdapter.COLUMN_PARGESTFAMILLE, param.getBgestfamart());
        values.put(SQLiteAdapter.COLUMN_PARSAISIEMANUELLE, param.getBsaisiemanuelle());
        values.put(SQLiteAdapter.COLUMN_PARSAISIEMANPROD, param.getBsaisiemanart());
        values.put(SQLiteAdapter.COLUMN_PARSAISIEMANFAM, param.getBsaisiemanfamille());
        values.put(SQLiteAdapter.COLUMN_PARGESTARTDETAILLEE, param.getBsaisiedetailart());
        values.put(SQLiteAdapter.COLUMN_PARGESTARTQTE, param.getBsaisiedetailartqte());
        values.put(SQLiteAdapter.COLUMN_PARGESTARTPU, param.getBsaisiedetailartputtc());
        values.put(SQLiteAdapter.COLUMN_PARFILTRELISTE, param.getBfiltreliste());


       // Log.d("UPDATEPARAM"," >ID >"+param.getId()+"<<<<<<");
       // Log.d("UPDATEPARAM"," >getBsaisiemanuelle >"+param.getBsaisiemanuelle()+"<<<<<<");

		//Log.d("UPDATEPARAM"," VBD >>>>>>>"+param.getversionBd()+"<<<<<<");
		//Log.d("UPDATEPARAM"," MODE >>>>>>>"+param.getModeencours()+"<<<<<<");
		//Log.d("UPDATEPARAM"," MODECTRL >>>>>>>"+param.getBmodectrl()+"<<<<<<");

        database.update(SQLiteAdapter.TABLE_PARAM, values,
                SQLiteAdapter.COLUMN_ID + " = ? ",
                new String[] { String.valueOf(param.getId()) });
    }

    public void deleteParam(Param param) {
        long id = param.getId();
        database.delete(SQLiteAdapter.TABLE_PARAM,
                SQLiteAdapter.COLUMN_ID + " = " + id, null);
    }

    public List<Param> getAllParams(boolean spin) {
        List<Param> params = new ArrayList<>();

        if(spin)
        {
            Param param0 = new Param(0,0,"Saisissez un param");
            params.add(param0);
        }

        //Log.v("PARAM DATA SOURCE, LISTE PARAMETRES ","134");

        Cursor cursor = database.query(SQLiteAdapter.TABLE_PARAM,
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

        Cursor cursor = database.query(SQLiteAdapter.TABLE_PARAM,
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

        Param param = new Param();
        Long id= cursor.getLong(0);
        param.setId(id);

        int vbd=0;

        try {
            vbd = cursor.getInt(1);
            param.setversionBd(vbd);
            String s = SQLiteAdapter.PARAM_MODEENCOURS_LISTE;
            s = cursor.getString(2);

           // Log.v("cursorToParam","LECTURE MODE EN COURS "+s);

            param.setModeencours(s);
            int ctrlactive = 0;
            ctrlactive = cursor.getInt(3);
            param.setBmodectrl(ctrlactive == 1);
            Long listeid = Long.valueOf(0);
            listeid = cursor.getLong(4);
            param.setListeencours(listeid);
            Long lf = Long.valueOf(0);
            lf = cursor.getLong(5);
            param.setFamilleEnCours(lf);
            ctrlactive = cursor.getInt(6);//RECOVOCALE
            param.setBrecovocale(ctrlactive == 1);
            ctrlactive = cursor.getInt(7);//GEST FAMILLE
            param.setBgestfamart(ctrlactive == 1);

            ctrlactive = cursor.getInt(8);//SAISIE MANUELLE
            param.setBsaisiemanuelle(ctrlactive == 1);

            ctrlactive = cursor.getInt(9);//SAISIE MANUELLE ARTICLE
            param.setBsaisiemanart(ctrlactive == 1);
            ctrlactive = cursor.getInt(10);//SAISIE MANUELLE FAMILLE
            param.setBsaisiemanfamille(ctrlactive == 1);
            ctrlactive = cursor.getInt(11);//SAISIE DETAIL ARTICLE
            param.setBsaisiedetailart(ctrlactive == 1);
            ctrlactive = cursor.getInt(12);//SAISIE DETAIL ARTICLE QTE
            param.setBsaisiedetailartqte(ctrlactive == 1);
            ctrlactive = cursor.getInt(13);//SAISIE DETAIL ARTICLE PU ttc
            param.setBsaisiedetailartputtc(ctrlactive == 1);

            ctrlactive = cursor.getInt(14);//COLUMN_PARFILTRELISTE
            param.setBfiltreliste(ctrlactive == 1);

        }
        catch (Exception e)
        {
            Log.v("PDS","ERROR "+e.getMessage());
        }finally {
            //Log.v("PDS","FIN ");
        }
        return param;
    }

    public Param LectureParam()
    {
        Param newParam = new Param();

        String snouveaumode=SQLiteAdapter.PARAM_MODEENCOURS_LISTE;

        newParam.setModeencours(snouveaumode);
        newParam.setFamilleEnCours((long) 0);
        //Log.v("PARAM DATA SOURCE, CHANGE MODE ","189");

        Cursor cursor = database.query(SQLiteAdapter.TABLE_PARAM,
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
        }else
        {
            createParam(7,snouveaumode,0);
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
        String snouveaumode=SQLiteAdapter.PARAM_MODEENCOURS_LISTE;
        //Log.v("PARAM DATA SOURCE, CHANGE MODE ","189");

        Cursor cursor = database.query(SQLiteAdapter.TABLE_PARAM,
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
                case SQLiteAdapter.PARAM_MODEENCOURS_ACHAT:

                    snouveaumode = SQLiteAdapter.PARAM_MODEENCOURS_LISTE;


                    break;
                case SQLiteAdapter.PARAM_MODEENCOURS_LISTE:

                    snouveaumode = SQLiteAdapter.PARAM_MODEENCOURS_ACHAT;

                    break;
                default:
                    snouveaumode = SQLiteAdapter.PARAM_MODEENCOURS_LISTE;
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
