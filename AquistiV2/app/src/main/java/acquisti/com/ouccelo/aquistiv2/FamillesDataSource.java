package acquisti.com.ouccelo.aquistiv2;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FamillesDataSource {

	// Database fields
	private SQLiteDatabase database;
	private SQLiteAdapter dbHelper;


	private String[] allColumns = { SQLiteAdapter.COLUMN_ID,
			SQLiteAdapter.COLUMN_LIBELLE };
	private Context context;

	public FamillesDataSource(Context context) {
		dbHelper = new SQLiteAdapter(context);
		this.context=context;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		//dbHelper.close();
	}

	public Famille createFamille(String libelle) {

		//Famille newFamille=null;

		ContentValues values = new ContentValues();
        values.put(SQLiteAdapter.COLUMN_LIBELLE, libelle);
        long insertId=0;

        //Log.d("createFamille","43, insertion FAMILLE 1 "+libelle);

		try{
           // database.beginTransaction();

           // Log.d("createFamille","48, insertion FAMILLE 2 "+libelle);

			insertId = database.insert(SQLiteAdapter.TABLE_FAMILLES, null,
					values);

            //Log.d("createFamille","53 insertion FAMILLE 3 "+libelle+" insertId="+insertId);

		}
		catch (Exception e){

			Log.v("createFamille","60 insertion FAMILLE************** ERREUR= "+e.getMessage());

		}

       //Log.d("createFamille","APRES INSERTION FAMILLE 62 >"+libelle+" insertID="+insertId);
/*
        if (insertId!=0)
        {
            Cursor cursor = database.query(SQLiteAdapter.TABLE_FAMILLES,
                    allColumns, SQLiteAdapter.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
           //Log.v("createFamille","70 RECHERCHE FAMILLE insertId="+insertId+" "+libelle+" COUNT ="+cursor.getCount());
            if(cursor.getCount()>0)
            {
                cursor.moveToFirst();
               //Log.d("createFamille","72,APRES RECHERCHE FAMILLE ");

                newFamille = cursorToFamille(cursor);

               //Log.d("createFamille","76,APRES RECHERCHE FAMILLE >"+newFamille.getId()+" "+newFamille.getLibelle());

            }
            cursor.close();

        }*/
		Famille newFamille = new Famille(insertId,libelle);
        return newFamille;
	}

    public Famille createFamille(Long idFam,String libelle) {
        Famille newFamille=null;

        ContentValues values = new ContentValues();
        values.put(SQLiteAdapter.COLUMN_ID_FAMILLE, idFam);
        values.put(SQLiteAdapter.COLUMN_LIBELLE, libelle);
        long insertId=0;

        //Log.d("createFamille","43, insertion FAMILLE 1 "+libelle);

        try{
            // database.beginTransaction();

            // Log.d("createFamille","48, insertion FAMILLE 2 "+libelle);

            insertId = database.insert(SQLiteAdapter.TABLE_FAMILLES, null,
                    values);

            //Log.d("createFamille","53 insertion FAMILLE 3 "+libelle+" insertId="+insertId);

        }
        catch (Exception e){

           //Log.v("createFamille","60 insertion FAMILLE************** ERREUR= "+e.getMessage());

        }

       //Log.d("createFamille","APRES INSERTION FAMILLE 62 >"+libelle+" insertID="+insertId);

        if (insertId!=0)
        {
            Cursor cursor = database.query(SQLiteAdapter.TABLE_FAMILLES,
                    allColumns, SQLiteAdapter.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            //Log.v("createFamille","70 RECHERCHE FAMILLE insertId="+insertId+" "+libelle+" COUNT ="+cursor.getCount());
            if(cursor.getCount()>0)
            {
                cursor.moveToFirst();
                //Log.d("createFamille","72,APRES RECHERCHE FAMILLE ");

                newFamille = cursorToFamille(cursor);

                //Log.d("createFamille","76,APRES RECHERCHE FAMILLE >"+newFamille.getId()+" "+newFamille.getLibelle());

            }
            cursor.close();

        }

        return newFamille;
    }

	public void updateFamille(Famille famille){
		ContentValues values = new ContentValues();

        values.put(SQLiteAdapter.COLUMN_LIBELLE, famille.getLibelle());

		database.update(SQLiteAdapter.TABLE_FAMILLES, values,
				SQLiteAdapter.COLUMN_ID + " = ? ",
				new String[] { String.valueOf(famille.getId()) });
	}

	public void deleteFamille(Famille famille) {
		long id = famille.getId();
		database.delete(SQLiteAdapter.TABLE_FAMILLES,
				SQLiteAdapter.COLUMN_ID + " = " + id, null);
	}

	public List<Famille> getAllFamilles(boolean spin) {
		List<Famille> familles = new ArrayList<>();

		if(spin)
		{
            //getres R.string.msgsaisiefam

           //String s= R.string.msgsaisiefam.getString();
			// Resources res = new Resources();
			String s="Famille";

            s=this.context.getResources().getString(R.string.msgsaisiefam)+" ...";
			s="Famille";
           // String s=Resources.getSystem().getString(R.string.msgsaisiefam);
            Famille famille0 = new Famille(0,s);
			familles.add(famille0);
		}


		Cursor cursor = database.query(SQLiteAdapter.TABLE_FAMILLES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();

		//Log.d("FAMILLES DTS","127 NOMBRE="+cursor.getCount());

		while (!cursor.isAfterLast()) {
			Famille famille = cursorToFamille(cursor);
			familles.add(famille);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return familles;
	}

	public List<Famille> getAllFamilles(String order) {

		List<Famille> familles = new ArrayList<Famille>();
//, null, null, null, null, null);
		Cursor cursor = database.query(SQLiteAdapter.TABLE_FAMILLES,
				allColumns,null,null,null,null,order);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Famille famille = cursorToFamille(cursor);
			familles.add(famille);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return familles;

	}

	public List<String> getAllFamillesLibelle(String order) {
        List<String> list = new ArrayList<String>();

        List<Famille> listValues = this.getAllFamilles("");

        list.add("Choisir famille");
		for (Famille fam : listValues)
		{
            list.add(fam.getLibelle());

		}
		return list;
	}

	private Famille cursorToFamille(Cursor cursor) {
		Famille famille = new Famille();
		famille.setId(cursor.getLong(0));
		famille.setLibelle(cursor.getString(1));
		return famille;
	}

    public SQLiteDatabase getDatabase()
    {
        return database;
    }


}
