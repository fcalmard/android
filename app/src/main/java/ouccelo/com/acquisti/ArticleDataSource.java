package ouccelo.com.acquisti;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static ouccelo.com.acquisti.MySQLiteHelper.PARAM_MODEENCOURS_ACHAT;

public class ArticleDataSource {

	// Database fields
	private SQLiteDatabase database;
	//private acquisti.ouccelo.free.fr.acquisti.acquisti.MySQLiteHelper dbHelper;

	private ouccelo.com.acquisti.MySQLiteHelper dbHelper;

	public String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_LIBELLE,MySQLiteHelper.COLUMN_ID_FAMILLE
            ,MySQLiteHelper.COLUMN_DSLISTE
            ,MySQLiteHelper.COLUMN_DSACHATS
            ,MySQLiteHelper.COLUMN_PUHT
            ,MySQLiteHelper.COLUMN_TXTVA
            ,MySQLiteHelper.COLUMN_PUTTC
            ,MySQLiteHelper.COLUMN_QTE
    };

	public ArticleDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();

	}

	public void close() {
		dbHelper.close();
	}

	/*

	 */
	public long createArticle(Article article) {
		ContentValues values = new ContentValues();
        //
        //Log.d("ARTICLEDTS","createArticle-59");

        values.put(MySQLiteHelper.COLUMN_LIBELLE, article.getLibelle());
        //Log.d("ARTICLEDTS","createArticle-62");
		values.put(MySQLiteHelper.COLUMN_ID_FAMILLE, article.getFamilleId());


        //Log.d("ARTICLEDTS","createArticle ARTICLE "+article.getLibelle()+" FAMILLEID= "+article.getFamilleId());
/*
        try{

            //database.beginTransaction();



            //Log.d("ARTICLEDTS","createArticle ARTICLE APRES INSERTION INSERTID="+insertId);

            if(insertId!=0)
            {
                Cursor cursor = database.query(MySQLiteHelper.TABLE_ARTICLES,
                        this.allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                        null, null, null);

                cursor.moveToFirst();
                newArticle = cursorToArticle(cursor);
                cursor.close();
            }else
            {
                throw new Exception(" PROBLEME NOUVEL ARTICLE");
            }


            //database.endTransaction();
        }

        catch(Exception e)
        {
            String message = e.getMessage();
            //Log.i("ARTDTS", message);

      }finally {
            //Log.i("ARTDTS", "101 FINALLY");

        }
    */
        long insertId = database.insert(MySQLiteHelper.TABLE_ARTICLES, null,
                values);

        //Log.d("ARTICLEDTS","createArticle-109 "+insertId);

        return insertId;

		//return newArticle;
	}
	
	public void updateArticle(Article article){
		ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_LIBELLE, article.getLibelle());
		values.put(MySQLiteHelper.COLUMN_ID_FAMILLE, article.getFamilleId());
		long lidliste=article.getIdliste();
		values.put(MySQLiteHelper.COLUMN_DSLISTE, lidliste);
        if(article.getEstachete()==1)
        {
            values.put(MySQLiteHelper.COLUMN_DSACHATS,article.getEstachete());
           //Log.v("ARTDATASOURCE","UPDATEARTICLE,113,lidliste="+lidliste+" "+MySQLiteHelper.COLUMN_DSACHATS+"="+article.getEstachete()+" GETID="+article.getId());
        }else
        {
            values.put(MySQLiteHelper.COLUMN_DSACHATS,article.getEstachete());
           //Log.v("ARTDATASOURCE","UPDATEARTICLE,117,lidliste="+lidliste+" estachete="+article.getEstachete());
        }


		database.update(MySQLiteHelper.TABLE_ARTICLES, values,
				MySQLiteHelper.COLUMN_ID + " = ? ",
				new String[] { String.valueOf(article.getId()) });
	}

	public void chargeFamillesArticles(Context context)
	{
        this.open();
        /*
        Voulez vous quitter ré-Initialiser les données
         */
        Famille famille = new Famille();

       // Famille[] tblFamille = new Famille[50];
       // Article[] tblArticle = new Article[50];

        FamillesDataSource famdts = new FamillesDataSource(context);
        famdts.open();

        String sql="";

        //famdts.getDatabase().execSQL("DELETE FROM " + MySQLiteHelper.TABLE_FAMILLES);
        //Log.v("ARTDTS","263");
        sql="DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_FAMILLES;
        famdts.getDatabase().execSQL(sql);
        sql=MySQLiteHelper.TABLE_CREATE_FAMILLE;
        famdts.getDatabase().execSQL(sql);
        sql="DROP TABLE IF EXISTS " + MySQLiteHelper.TABLE_ARTICLES;
        famdts.getDatabase().execSQL(sql);
        sql=MySQLiteHelper.TABLE_CREATE_ARTICLE;
        famdts.getDatabase().execSQL(sql);
        sql ="ALTER TABLE "+MySQLiteHelper.TABLE_ARTICLES+" ADD COLUMN "+MySQLiteHelper.COLUMN_PUHT+" REAL DEFAULT 0;";
        famdts.getDatabase().execSQL(sql);
        sql ="ALTER TABLE "+MySQLiteHelper.TABLE_ARTICLES+" ADD COLUMN "+MySQLiteHelper.COLUMN_TXTVA+" REAL DEFAULT 20.00;";
        famdts.getDatabase().execSQL(sql);
        sql ="ALTER TABLE "+MySQLiteHelper.TABLE_ARTICLES+" ADD COLUMN "+MySQLiteHelper.COLUMN_PUTTC+" REAL DEFAULT 0;";
        famdts.getDatabase().execSQL(sql);
        sql ="ALTER TABLE "+MySQLiteHelper.TABLE_ARTICLES+" ADD COLUMN "+MySQLiteHelper.COLUMN_QTE+" REAL DEFAULT 1;";
        famdts.getDatabase().execSQL(sql);
        sql ="ALTER TABLE "+MySQLiteHelper.TABLE_ARTICLES+" ADD COLUMN "+MySQLiteHelper.COLUMN_DSLISTE+" INTEGER DEFAULT 0;";
        famdts.getDatabase().execSQL(sql);
        sql ="ALTER TABLE "+MySQLiteHelper.TABLE_ARTICLES+" ADD COLUMN "+MySQLiteHelper.COLUMN_DSACHATS+" INTEGER DEFAULT 0;";
        famdts.getDatabase().execSQL(sql);

        String slib="Légumes";
        famille=famdts.createFamille(slib);

            createArticle(new Article("Courgettes",famille.getId()));
            createArticle(new Article("Tomates",famille.getId()));
            createArticle(new Article("Carottes",famille.getId()));
            createArticle(new Article("Avocats",famille.getId()));
            createArticle(new Article("Persil",famille.getId()));
            createArticle(new Article("Oignons",famille.getId()));
            createArticle(new Article("Choux",famille.getId()));
            createArticle(new Article("Poireaux",famille.getId()));
            createArticle(new Article("Poivrons",famille.getId()));
            createArticle(new Article("Salade verte",famille.getId()));
            createArticle(new Article("Pommes de terre",famille.getId()));
            createArticle(new Article("asperges",famille.getId()));
        //Log.v("ARTDTS, 184",slib);
        slib="Fruits";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Pommes",famille.getId()));
            createArticle(new Article("Citrons",famille.getId()));
            createArticle(new Article("Bananes",famille.getId()));
            createArticle(new Article("Raisins",famille.getId()));
            createArticle(new Article("Pastéques",famille.getId()));
            createArticle(new Article("Melons",famille.getId()));
        //Log.v("ARTDTS, 193",slib);

        slib="Traiteur Charcuterie, Poisson";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Cote de boeuf",famille.getId()));
            createArticle(new Article("Cote d'agneaux",famille.getId()));
            createArticle(new Article("Poulet",famille.getId()));
        //Log.v("ARTDTS, 200",slib);

        slib="Boissons";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Eau Vittel 1L",famille.getId()));
            createArticle(new Article("Eau Contrex 1Let demi",famille.getId()));
            createArticle(new Article("Vin rouge",famille.getId()));
            createArticle(new Article("Vin blanc",famille.getId()));
        //Log.v("ARTDTS, 208",slib);

        slib="Boulangerie Patisserie";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Pain",famille.getId()));
            createArticle(new Article("Croissants",famille.getId()));
        //Log.v("ARTDTS, 214",slib);

        slib="Petit déjeuner";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Céréales",famille.getId()));
            createArticle(new Article("Café",famille.getId()));
            createArticle(new Article("Thé",famille.getId()));
        createArticle(new Article("Sucre",famille.getId()));
        //Log.v("ARTDTS, 222",slib);

        slib="Divers ingrédients";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Sel",famille.getId()));
            createArticle(new Article("Beurre",famille.getId()));
            createArticle(new Article("Huile olive",famille.getId()));
            createArticle(new Article("Vinaigre",famille.getId()));
            createArticle(new Article("Ail",famille.getId()));
        //Log.v("ARTDTS, 231",slib);

        slib="Entretien droguerie";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Serpillieres",famille.getId()));
            createArticle(new Article("Liquide lavage sol",famille.getId()));
        //Log.v("ARTDTS, 237",slib);

        slib="Bricolage";
        famille=famdts.createFamille(slib);
        //Log.v("ARTDTS, 241",slib+" "+famille.getId());

        slib="Desserts";
        famille=famdts.createFamille(slib);
        //Log.v("ARTDTS, 245",slib+" "+famille.getId());

        slib="Fromages";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Camenbert",famille.getId()));
            createArticle(new Article("Gruyere",famille.getId()));
        //Log.v("ARTDTS, 251",slib+" "+famille.getId());

        slib="Hygiène";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Liquide douche",famille.getId()));
            createArticle(new Article("Savon liquique",famille.getId()));
        //Log.v("ARTDTS, 257",slib+" "+famille.getId());

        slib="Surgelés";
        famille=famdts.createFamille(slib);

            createArticle(new Article("Glaces",famille.getId()));
            createArticle(new Article("Pizza",famille.getId()));
            createArticle(new Article("Poelée",famille.getId()));
        //Log.v("ARTDTS, 265",slib+" "+famille.getId());

        famdts.close();


	}
	public void razListe()
	{
		ContentValues values = new ContentValues();

		values.put(MySQLiteHelper.COLUMN_DSLISTE, 0);
		values.put(MySQLiteHelper.COLUMN_DSACHATS, 0);

		database.update(MySQLiteHelper.TABLE_ARTICLES,values,null,new String[]{});
	}

	public void deleteArticle(Article article) {
		long id = article.getId();
		database.delete(MySQLiteHelper.TABLE_ARTICLES,
				MySQLiteHelper.COLUMN_ID + " = " + id, null);
	}

	public List<Article> getAllArticles(long ifam,String order,String sMode,boolean controle) {
		List<Article> articles = new ArrayList<Article>();

		Cursor cursor;

        String sFiltre="";

        if(sMode.equals(MySQLiteHelper.PARAM_MODEENCOURS_ACHAT))
        {
            if(!sFiltre.equals(""))
            {
                sFiltre=sFiltre+" and ";
            }else
            {
                sFiltre="";
            }
            sFiltre=sFiltre+MySQLiteHelper.COLUMN_DSLISTE+" !=0";

            if(!controle)
            {
            /*
            si controle alors on affiche tout
            si non on n'affiche plus que ce que l'on a acheté
            au fur et a mesure les articles acheté disparaise de la liste

             *
             */

                if(!sFiltre.equals(""))
                {
                    sFiltre=sFiltre+" and ";
                }else
                {
                    sFiltre="";
                }
                sFiltre=sFiltre+MySQLiteHelper.COLUMN_DSACHATS+" = 0";

            }

        }

		if (ifam!=0)
		{

            if(!sFiltre.equals(""))
            {
                sFiltre=sFiltre+" and ";
            }
            sFiltre=sFiltre+MySQLiteHelper.COLUMN_ID_FAMILLE+" = "+ifam;

			//TABLE_ARTICLES_FAMILLE

		}else
		{

		}

        cursor = database.query(MySQLiteHelper.TABLE_ARTICLES,this.allColumns,sFiltre,
                null,null,null,MySQLiteHelper.COLUMN_LIBELLE,null);

		cursor.moveToFirst();

		//Log.v("ART DATA SOURCE","ARTDTS 204 FILTRE >"+sFiltre+"< MODE >"+sMode+" NOMBRE ARTICLE="+cursor.getCount());

		while (!cursor.isAfterLast()) {
			Article article = cursorToArticle(cursor);
           //Log.v("ARTICLE DATASOURCE","183 ALLCOLL="+allColumns[4]);
			long ifam2=article.getFamilleId();
			articles.add(article);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return articles;
	}

	public List<Article> getAllArticles2(String order,String sMode,boolean controle) {

		/*
		si modeachat et si controle alors si COLUMN_DSLISTE!=0
		 */

        //MySQLiteHelper.COLUMN_DSLISTE
        //MySQLiteHelper.PARAM_MODEENCOURS_ACHAT
        //MySQLiteHelper.PARAM_MODEENCOURS_LISTE
        boolean modeachat=sMode.equals(MySQLiteHelper.PARAM_MODEENCOURS_ACHAT);
        if(modeachat && controle)
        {

        }

		List<Article> articles = new ArrayList<Article>();
//, null, null, null, null, null);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ARTICLES,
				this.allColumns,null,null,null,null,order);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
           //Log.v("ARTICLE DATASOURCE","215");
			Article article = cursorToArticle(cursor);
			articles.add(article);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return articles;

	}

	public Article cursorToArticle(Cursor cursor) {
		Article article = new Article();
        article.setId(cursor.getLong(0));
		article.setLibelle(cursor.getString(1));
        article.setFamilleId(cursor.getLong(2));
        article.setIdliste(cursor.getLong(3));

        article.setEstachete(cursor.getInt(4));
		return article;
	}


	public  void init()
	{
		//database.
       // MySQLiteHelper.onCreate(database); this.database
        this.open();
        this.dbHelper.onUpgrade(database,1,2);
        this.close();

       // database.execSQL("DROP TABLE IF EXISTS " + database.TABLE_ARTICLES);
       // database.execSQL("DROP TABLE IF EXISTS " + database.TABLE_FAMILLES);

	}

    public  SQLiteDatabase getDatabase()
    {
        return database;
    }
}
