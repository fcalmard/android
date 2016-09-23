package ouccelo.com.acquisti;

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
 * Created by francois on 19/09/16.
 */
public class ListesDataSource {


        // Database fields
        private SQLiteDatabase database;
        private MySQLiteHelper dbHelper;
        private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                MySQLiteHelper.COLUMN_LIB_LISTE };
        private Context context;

        public ListesDataSource(Context context) {
            dbHelper = new MySQLiteHelper(context);
            this.context=context;
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }

        public Liste createListe(String libelle) {
            Liste newListe=null;

            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_LIB_LISTE, libelle);
            long insertId=0;

            //Log.d("createListe","43, insertion LISTE 1 "+libelle);

            try{
                // database.beginTransaction();

                // Log.d("createListe","48, insertion LISTE 2 "+libelle);

                insertId = database.insert(MySQLiteHelper.TABLE_LISTES, null,
                        values);

                //Log.d("createListe","53 insertion LISTE 3 "+libelle+" insertId="+insertId);

            }
            catch (Exception e){

                //Log.v("createListe","60 insertion LISTE************** ERREUR= "+e.getMessage());

            }

            //Log.d("createListe","APRES INSERTION LISTE 62 >"+libelle+" insertID="+insertId);

            if (insertId!=0)
            {
                Cursor cursor = database.query(MySQLiteHelper.TABLE_LISTES,
                        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                        null, null, null);
                //Log.v("createListe","70 RECHERCHE LISTE insertId="+insertId+" "+libelle+" COUNT ="+cursor.getCount());
                if(cursor.getCount()>0)
                {
                    cursor.moveToFirst();
                    //Log.d("createListe","72,APRES RECHERCHE LISTE ");

                    newListe = cursorToListe(cursor);

                    //Log.d("createListe","76,APRES RECHERCHE LISTE >"+newListe.getId()+" "+newListe.getLibelle());

                }
                cursor.close();

            }

            return newListe;
        }

        public void updateListe(Liste liste){
            ContentValues values = new ContentValues();

            values.put(MySQLiteHelper.COLUMN_LIB_LISTE, liste.getLibelle());

            database.update(MySQLiteHelper.TABLE_LISTES, values,
                    MySQLiteHelper.COLUMN_ID + " = ? ",
                    new String[] { String.valueOf(liste.getId()) });
        }

        public void deleteListe(Liste liste) {
            long id = liste.getId();
            database.delete(MySQLiteHelper.TABLE_LISTES,
                    MySQLiteHelper.COLUMN_ID + " = " + id, null);
        }

        public List<Liste> getAllListes(boolean spin) {
            List<Liste> listes = new ArrayList<>();

            if(spin)
            {
                String s="Liste";
                s=this.context.getResources().getString(R.string.msgsaisieliste)+" ...";
                Liste liste0 = new Liste(Long.valueOf(0),s);
                listes.add(liste0);
            }

            Cursor cursor = database.query(MySQLiteHelper.TABLE_LISTES,
                    allColumns, null, null, null, null, null);

            cursor.moveToFirst();

            //Log.d("LISTES DTS","127 NOMBRE="+cursor.getCount());

            while (!cursor.isAfterLast()) {
                Liste liste = cursorToListe(cursor);
                listes.add(liste);
                try {
                    cursor.moveToNext();

                }catch (SQLiteException e)
                {
                    Log.v("LISTDTS",""+e.getMessage());
                }
            }
            // make sure to close the cursor
            cursor.close();
            return listes;
        }

        public List<Liste> getAllListes(String order) {

            List<Liste> Listes = new ArrayList<Liste>();
            Cursor cursor = database.query(MySQLiteHelper.TABLE_LISTES,
                    allColumns,null,null,null,null,order);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Liste liste = cursorToListe(cursor);
                Listes.add(liste);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
            return Listes;

        }

        private Liste cursorToListe(Cursor cursor) {
            Liste liste = new Liste();
            liste.setId(cursor.getLong(0));
            liste.setLibelle(cursor.getString(1));
            return liste;
        }

        public SQLiteDatabase getDatabase()
        {
            return database;
        }

}
