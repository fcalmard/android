package acquisti.com.ouccelo.aquistiv2;

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
import static acquisti.com.ouccelo.aquistiv2.SQLiteAdapter.PARAM_MODEENCOURS_ACHAT;

public class ArticleDataSource {

	// Database fields
	private SQLiteDatabase database;
	//private acquisti.ouccelo.free.fr.acquisti.acquisti.SQLiteAdapter dbHelper;
    private SQLiteAdapter dbHelper;

    //,SQLiteAdapter.COLUMN_DSLISTE
    //,SQLiteAdapter.COLUMN_DSACHATS

	public String[] allColumns = {
            SQLiteAdapter.COLUMN_ID,
			SQLiteAdapter.COLUMN_LIBELLE,
            SQLiteAdapter.COLUMN_ID_FAMILLE
            ,SQLiteAdapter.COLUMN_PU
            ,SQLiteAdapter.COLUMN_UNITE
            ,SQLiteAdapter.COLUMN_QTE
    };

	public ArticleDataSource(Context context) {
		dbHelper = new SQLiteAdapter(context);
	}

	public boolean open() throws SQLException {


        database = dbHelper.getWritableDatabase();
        boolean b = database.isOpen();

        //Log.v("ART DATA SOURCE"," open 47 EST OUVERT "+b);

        return b;

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

        values.put(SQLiteAdapter.COLUMN_LIBELLE, article.getLibelle());
        //Log.d("ARTICLEDTS","createArticle-62");
		values.put(SQLiteAdapter.COLUMN_ID_FAMILLE, article.getFamilleId());


        //Log.d("ARTICLEDTS","createArticle ARTICLE "+article.getLibelle()+" FAMILLEID= "+article.getFamilleId());

        long insertId = database.insert(SQLiteAdapter.TABLE_ARTICLES, null,
                values);

        //Log.d("ARTICLEDTS","createArticle-109 "+insertId);

        return insertId;

		//return newArticle;
	}
	
	public void updateArticle(Article article){
		ContentValues values = new ContentValues();

        values.put(SQLiteAdapter.COLUMN_LIBELLE, article.getLibelle());
		values.put(SQLiteAdapter.COLUMN_ID_FAMILLE, article.getFamilleId());

       //Log.v("ARTDATASOURCE","UPDATEARTICLE,126,getPuht="+ article.getPuht());


		database.update(SQLiteAdapter.TABLE_ARTICLES, values,
				SQLiteAdapter.COLUMN_ID + " = ? ",
				new String[] { String.valueOf(article.getId()) });
	}

	public boolean chargeFamillesArticles(Context context)
	{
        boolean bres=false;

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

        //famdts.getDatabase().execSQL("DELETE FROM " + SQLiteAdapter.TABLE_FAMILLES);
        //Log.v("ARTDTS","263");
        sql="DROP TABLE IF EXISTS " + SQLiteAdapter.TABLE_FAMILLES;
        famdts.getDatabase().execSQL(sql);
        sql=SQLiteAdapter.TABLE_CREATE_FAMILLE;
        famdts.getDatabase().execSQL(sql);
        sql="DROP TABLE IF EXISTS " + SQLiteAdapter.TABLE_ARTICLES;
        famdts.getDatabase().execSQL(sql);
        sql=SQLiteAdapter.TABLE_CREATE_ARTICLE;
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
            createArticle(new Article("Asperges",famille.getId()));

        slib="Fruits";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Pommes",famille.getId()));
            createArticle(new Article("Citrons",famille.getId()));
            createArticle(new Article("Bananes",famille.getId()));
            createArticle(new Article("Raisins",famille.getId()));
            createArticle(new Article("Pastéques",famille.getId()));
            createArticle(new Article("Melons",famille.getId()));

        slib="Traiteur Charcuterie, Poisson";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Cote de boeuf",famille.getId()));
            createArticle(new Article("Cote d'agneaux",famille.getId()));
            createArticle(new Article("Poulet",famille.getId()));

        slib="Boissons";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Eau Vittel 1L",famille.getId()));
            createArticle(new Article("Eau Contrex 1Let demi",famille.getId()));
            createArticle(new Article("Vin rouge",famille.getId()));
            createArticle(new Article("Vin blanc",famille.getId()));

        slib="Boulangerie Patisserie";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Pain",famille.getId()));
            createArticle(new Article("Croissants",famille.getId()));
            createArticle(new Article("Pan Cakes",famille.getId()));

        slib="Petit déjeuner";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Céréales",famille.getId()));
            createArticle(new Article("Café",famille.getId()));
            createArticle(new Article("Thé",famille.getId()));
            createArticle(new Article("Sucre",famille.getId()));

        slib="Divers ingrédients";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Sel",famille.getId()));
            createArticle(new Article("Beurre",famille.getId()));
            createArticle(new Article("Huile olive",famille.getId()));
            createArticle(new Article("Vinaigre",famille.getId()));
            createArticle(new Article("Ail",famille.getId()));

        slib="Entretien droguerie";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Serpillieres",famille.getId()));
            createArticle(new Article("Liquide lavage sol",famille.getId()));

        slib="Bricolage";
        famille=famdts.createFamille(slib);

        slib="Desserts";
        famille=famdts.createFamille(slib);

        slib="Fromages";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Camenbert",famille.getId()));
            createArticle(new Article("Gruyere",famille.getId()));

        slib="Hygiène";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Liquide douche",famille.getId()));
            createArticle(new Article("Savon liquique",famille.getId()));

        slib="Surgelés";
        famille=famdts.createFamille(slib);
            createArticle(new Article("Glaces",famille.getId()));
            createArticle(new Article("Pizza",famille.getId()));
            createArticle(new Article("Poelée",famille.getId()));

        //famdts.close();

        ListesDataSource lstdts = new ListesDataSource(context);
        lstdts.open();
        Liste lst = lstdts.createListe("Ma premiere liste");
        //lstdts.close();

        bres=true;

        return bres;
	}

	public void deleteArticle(Article article) {
		long id = article.getId();
		database.delete(SQLiteAdapter.TABLE_ARTICLES,
				SQLiteAdapter.COLUMN_ID + " = " + id, null);
	}

	public List<Article> getAllArticles(long ifam,String sMode,boolean controle,Long iListe,boolean bFiltreliste) {
		List<Article> articles = new ArrayList<Article>();

        //Log.v("ART DATA SRC","235 getAllArticles");

		Cursor cursor;

        String sFiltre="";

		if (ifam!=0)
		{

            if(!sFiltre.equals(""))
            {
                sFiltre=sFiltre+" and ";
            }
            sFiltre=sFiltre+SQLiteAdapter.COLUMN_ID_FAMILLE+" = "+ifam;

		}

        if(sMode.equals(SQLiteAdapter.PARAM_MODEENCOURS_ACHAT))
        {
            //Log.v("ART DATA SOURCE 345"," MODEACHAT FILTRE >"+sFiltre+"<");

        }else{
           // Log.v("ART DATA SOURCE 348"," MODELISTE ILISTE >"+sFiltre+"<");

        }

        cursor=database.query(SQLiteAdapter.TABLE_ARTICLES,this.allColumns,sFiltre,null,"","",SQLiteAdapter.COLUMN_LIBELLE);

        //Log.v("ART DATA SOURCE"," 354");

        cursor.moveToFirst();

		//Log.v("ART DATA SOURCE","ARTDTS 348 FILTRE >"+sFiltre+"< MODE >"+sMode+" NOMBRE ARTICLE="+cursor.getCount());
        //Log.v("ART DATA SOURCE"," 359");

		while (!cursor.isAfterLast()) {
			Article article = cursorToArticle(cursor);
           //Log.v("ARTICLE DATASOURCE","183 ALLCOLL="+allColumns[4]);
			long ifam2=article.getFamilleId();
			articles.add(article);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
       // Log.v("ART DATA SOURCE"," 370");

        return articles;
	}

    public Article cursorToArticle(Cursor cursor) {

		Article article = new Article();
        article.setId(cursor.getLong(0));       //id
		article.setLibelle(cursor.getString(1));    //libelle
        article.setFamilleId(cursor.getLong(2));    //id famille

        article.setPu(cursor.getFloat(3));          //pu

        article.setUnite(cursor.getString(4));      //unité

        article.setQte(cursor.getFloat(5));         //qte

        return article;
	}


	public  void init()
	{
		//database.
       // SQLiteAdapter.onCreate(database); this.database
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
