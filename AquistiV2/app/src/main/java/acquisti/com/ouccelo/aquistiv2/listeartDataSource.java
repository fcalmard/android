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

/**
 * Created by admin on 02/11/16.
 */

public class listeartDataSource {



    // Database fields
    private SQLiteDatabase database;
    private SQLiteAdapter dbHelper;
    private String[] allColumns = { SQLiteAdapter.COLUMN_ID,
            SQLiteAdapter.COLUMN_IDART,
            SQLiteAdapter.COLUMN_IDLST,
            SQLiteAdapter.COLUMN_TYPE
    };
    private Context context;

    public listeartDataSource(Context context) {
        dbHelper = new SQLiteAdapter(context);
        this.context=context;
    }

    public boolean open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return database.isOpen();
    }

    public void close() {
        dbHelper.close();
    }

    public long createListeart(Long idlst,Long idart,String type) {
        listeart newListe=null;

        ContentValues values = new ContentValues();

        values.put(SQLiteAdapter.COLUMN_IDLST,idlst);
        values.put(SQLiteAdapter.COLUMN_IDART,idart);

        values.put(SQLiteAdapter.COLUMN_TYPE,type);

        long insertId=0;

        //Log.d("createListe","43, insertion LISTE 1 "+libelle);

        try{
            // database.beginTransaction();

            // Log.d("createListe","48, insertion LISTE 2 "+libelle);

            insertId = database.insert(SQLiteAdapter.TABLE_LSTART, null,
                    values);

            //Log.d("createListe","67 insertion LISTE 3 >"+type+"< insertId="+insertId);

        }
        catch (Exception e){

            //Log.v("createListe","60 insertion LISTE************** ERREUR= "+e.getMessage());

        }

        //Log.d("createListe","APRES INSERTION LISTE 62 >"+libelle+" insertID="+insertId);

        if (insertId!=0)
        {
            Cursor cursor = database.query(SQLiteAdapter.TABLE_LSTART,
                    allColumns, SQLiteAdapter.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            //Log.v("createListe","70 RECHERCHE LISTE insertId="+insertId+" "+libelle+" COUNT ="+cursor.getCount());
            if(cursor.getCount()>0)
            {
                cursor.moveToFirst();
                //Log.d("createListe","72,APRES RECHERCHE LISTE ");

                newListe = cursorToListeart(cursor);

                //Log.d("createListe","76,APRES RECHERCHE LISTE >"+newListe.getId()+" "+newListe.getLibelle());

            }
            cursor.close();

        }

        return insertId;
    }

    public long rechercheListeart(long idlst,long idart,String stype)
    {
        long id=0;

       // Log.v("rechercheListeart"," 106 >"+SQLiteAdapter.COLUMN_TYPE+"<");
       // Log.v("rechercheListeart"," 106 >"+stype+"<");

        Cursor cursor = database.query(SQLiteAdapter.TABLE_LSTART,
                allColumns, SQLiteAdapter.COLUMN_IDLST + " = " + idlst+" and "+SQLiteAdapter.COLUMN_IDART+" = "+idart+" and "+SQLiteAdapter.COLUMN_TYPE+" = '"+stype+"'", null,
                null, null, null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            id=cursor.getLong(0);

        }
        cursor.close();

        Log.v("rechercheListeart","recherche ID="+id);

        return id;
    }

    public void updateListeart(listeart listeart){
        ContentValues values = new ContentValues();

        values.put(SQLiteAdapter.COLUMN_IDART, listeart.getIdart());
        values.put(SQLiteAdapter.COLUMN_IDLST, listeart.getIdlst());

        database.update(SQLiteAdapter.TABLE_LSTART, values,
                SQLiteAdapter.COLUMN_ID + " = ? ",
                new String[] { String.valueOf(listeart.getId()) });
    }

    public void deleteListeart(listeart listeart) {
        long id = listeart.getId();
        database.delete(SQLiteAdapter.TABLE_LSTART,
                SQLiteAdapter.COLUMN_ID + " = " + id, null);
    }
    public void deleteListeart(long idisteart) {
        database.delete(SQLiteAdapter.TABLE_LSTART,
                SQLiteAdapter.COLUMN_ID + " = " + idisteart, null);

        Log.v("deleteListeart","deleteListeart deleteListeart="+idisteart);
    }

    public List<listeart> getAllListeart(String stype) {
        List<listeart> listes = new ArrayList<listeart>();

        Cursor cursor = database.query(SQLiteAdapter.TABLE_LSTART,
                allColumns, null, null, null, null, null);

        String sel=SQLiteAdapter.COLUMN_TYPE+ "= '"+stype+"'";

        cursor=database.query(SQLiteAdapter.TABLE_LSTART,allColumns,sel,null,null,null,null);

        //Log.v("getAllListeart"," selection >"+sel+"<");

        cursor.moveToFirst();

        //Log.d("LISTES DTS","127 NOMBRE="+cursor.getCount());

        while (!cursor.isAfterLast()) {
            listeart liste = cursorToListeart(cursor);
            listes.add(liste);
            try {
                cursor.moveToNext();

            }catch (SQLiteException e)
            {
                Log.v("getAllListeart ",""+e.getMessage());
            }
        }
        // make sure to close the cursor
        cursor.close();
        return listes;
    }

    private listeart cursorToListeart(Cursor cursor) {
        listeart listeart = new listeart();
        listeart.setId(cursor.getLong(0));
        listeart.setIdart(cursor.getLong(1));
        listeart.setIdlst(cursor.getLong(2));
        listeart.setType(cursor.getString(3));
        return listeart;
    }

    public SQLiteDatabase getDatabase()
    {

        return database;
    }

public void initTable()
{
    this.open();
    getDatabase().execSQL("DELETE FROM " + SQLiteAdapter.TABLE_LSTART);

}


}
