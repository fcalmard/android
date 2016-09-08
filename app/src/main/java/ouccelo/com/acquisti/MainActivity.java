package ouccelo.com.acquisti;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ArrayAdapter<Famille> myAdapter;
        private ArrayAdapter<Article> myAdapterArt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Close(view);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        ParamDataSource pdts = new ParamDataSource(this);
        pdts.open();

        MySQLiteHelper mssql = new MySQLiteHelper(this);

        mssql.ControleVersionBaseDeDonnees(this, pdts.getDatabase());

        Param nParam = pdts.LectureParam();

        Long listeid=nParam.getListeEnCours();
        Long lidfamille=nParam.getFamilleEnCours();

        //Log.v("MAINACT CREATE","81 LISTEID="+listeid+" FAMILLEID="+lidfamille);

        pdts.close();

        AfficheFamilles();
        AfficheArticles();

       //Log.v("MAINACT CREATE","93");

        /*
            Selection Liste
         */
        Spinner spinner_liste = (Spinner) findViewById(R.id.spinner_liste);

        List exemple = new ArrayList();
        exemple.add("Choisir liste");
        exemple.add("ma liste2");
        exemple.add("ma liste3");

        		/*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
		Avec la liste des elements (exemple) */
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                exemple
        );
        /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner_liste.setAdapter(adapter);

        spinner_liste.setPrompt("Selectionnez une liste");

        spinner_liste.setSelection(listeid.intValue(),true);

        //SpinnerListeListener OnItemSelectedListener
        spinner_liste.setOnItemSelectedListener(new SpinnerListeListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {

                ParamDataSource pdts = new ParamDataSource(selectedItemView.getContext());
                pdts.open();
                //MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS

                Param param=pdts.LectureParam();

                //Log.v("MAIN ONITEMSELECTED","1056 listeid="+listeid);
                if(id!=0)
                {
                    param.setListeencours(id);

                    pdts.updateParam(param);

                    pdts.close();

                   //Log.v("MAINACT","131, LISTE SELECTIONNEE ID="+id);

                    AfficheArticles();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
            }
        });

        /*
            Selection Famille
         */
        Spinner spinner_famille = (Spinner) findViewById(R.id.spinner_famille);

        //Enfin on passe l'adapter au Spinner et c'est tout
       // spinner_famille.setAdapter(adapter);
        spinner_famille.setAdapter(myAdapter);

        spinner_famille.setPrompt("Selectionnez une Famille");
        spinner_famille.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
               //Log.v("MAINACT","127 FAMILLE SELECTIONNEE");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
            }
        });
        //spinner_famille.setOnItemClickListener(new SpinnerFamilleListener());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    private void AfficheFamilles()
        {
            FamillesDataSource datasourcefam = new FamillesDataSource(this);
            datasourcefam.open();

            List<Famille> listValues = datasourcefam.getAllFamilles(true);
            myAdapter = new ArrayAdapter<Famille>(this, R.layout.row_layout_fam,
                    R.id.listText, listValues);
            Spinner spinner_famille = (Spinner) findViewById(R.id.spinner_famille);

            //Enfin on passe l'adapter au Spinner et c'est tout
            // spinner_famille.setAdapter(adapter);
            spinner_famille.setAdapter(myAdapter);

            datasourcefam.close();
        }
    /*

     */
    private void AfficheArticles()
    {

        final Context context = this;
       //Log.v("MAINACT AfficheArticles","188");

        AppBarLayout.LayoutParams lp1 = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.MATCH_PARENT);

        AppBarLayout.LayoutParams lp2 = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);

        AppBarLayout.LayoutParams lp3 = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);

        AppBarLayout.LayoutParams lpfill = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.FILL_PARENT,
                DrawerLayout.LayoutParams.FILL_PARENT);
       //Log.v("MAINACT AfficheArticles","201");

        final ArticleDataSource datasourceart = new ArticleDataSource(this);
        datasourceart.open();
        Param nParam=new Param();

        MySQLiteHelper mysqlhlpr=new MySQLiteHelper(context);
        //Log.v("AfficheArticles","454");
        ParamDataSource dtsparam = new ParamDataSource(context);
        //Log.v("AfficheArticles","456");
        //mysqlhlpr.LectureParam(this,dtsparam.getDatabase());
        //Log.v("AfficheArticles","458");
        dtsparam.open();
        //Log.v("AfficheArticles","460");
        nParam=dtsparam.LectureParam();
        //Log.v("AfficheArticles","462");
        final String modeEnCours=nParam.getModeencours();

        final Long idfamille=nParam.getFamilleEnCours();

        Long listeid=nParam.getListeEnCours();

       //Log.v("MAIN","249 MODEENCOURS >"+modeEnCours+" FAMILLE="+idfamille+" LISTEID="+listeid);

        //Param oParam=mysqlhlpr.MiseAJourParam(context,dtsparam.getDatabase(),false,MySQLiteHelper.PARAM_MAJ_CTRLMODE,nParam);

        //bCtrlActive

        List<Article> listValuesArt = datasourceart.getAllArticles(idfamille,"",modeEnCours,nParam.getBmodectrl());

        //Log.v("AfficheArticles","494");
        myAdapterArt = new ArrayAdapter<Article>(this, R.layout.row_layout_article,
                R.id.listText, listValuesArt);
        //Log.v("AfficheArticles","497");
        final LinearLayout LinearLayoutlisteproduits = (LinearLayout) findViewById(R.id.idlisteproduits);

        LinearLayoutlisteproduits.removeAllViewsInLayout();
        //Log.v("AfficheArticles","478");
        LinearLayoutlisteproduits.setBackgroundColor(Color.LTGRAY);
        //Log.v("AfficheArticles","480");
        final LinearLayout gabaritListeDet = new LinearLayout (this);
        gabaritListeDet. setGravity(Gravity.LEFT);
        //Log.v("AfficheArticles","483");
        gabaritListeDet.setOrientation(LinearLayout.VERTICAL);
        //Log.v("AfficheArticles","485");
        gabaritListeDet.removeAllViewsInLayout();

        gabaritListeDet.setPadding(25,25,0,0);

        //String modeEnCours = MySQLiteHelper.PARAM_MODEENCOURS_LISTE;//                oParam.getModeencours();
        //Log.v("AfficheArticles","491");
        boolean bmodeliste=modeEnCours.equals(MySQLiteHelper.PARAM_MODEENCOURS_LISTE);
        //Log.v("AfficheArticles","493");
        //famille selectionnees
        int ifamsel = 0;// oParam.

        int iart=0;

        for (Article art : listValuesArt)
        {
            LinearLayout gabaritDet = new LinearLayout (this);

            gabaritDet.setVerticalScrollBarEnabled(true);
            iart++;

            final ImageButton btnach= new ImageButton(context);

            btnach.setId(iart);

            btnach.setTag(art);

            btnach.setOnClickListener(new View.OnClickListener() {

                //int idbtn=btnach.getId();

                @Override
                public void onClick(View view)
                {

                    //final Context context = view.getContext();

                    //AlertDialog.Builder adb = new AlertDialog.Builder(context);

                    //adb.setTitle(dlgslib);

                    int id=view.getId();

                    Article art = (Article) view.getTag();

                    long l=0;

                    Log.v("ONCLICK"," 321 ARTICLE "+art.toString());

                    ParamDataSource pdts = new ParamDataSource(view.getContext());
                    pdts.open();
                    //MySQLiteHelper.PARAM_COLUMN_LISTEENCOURS

                    Param param=pdts.LectureParam();

                    long lid= param.getListeEnCours();

                    pdts.close();

                    /*
                    on enleve l'article de la liste
                     */
                    if(art.getIdliste()!=0)
                    {
                        lid=0;
                        Log.v("ONCLICK"," 339 "+lid);

                    }else
                    {
                        //lid=art.getIdliste();
                        Log.v("ONCLICK"," 344 DANS LISTE "+lid);
                    }

                    majArticle(datasourceart,art,modeEnCours,lid);

                    finish();
                    startActivity(getIntent());

                }
            });

            gabaritDet.setLayoutParams(lp3);

            gabaritDet.setOrientation(LinearLayout.HORIZONTAL);

            gabaritDet. setGravity(Gravity.LEFT|Gravity.START);

            gabaritDet.setClickable(true);

            TextView texttv = new TextView(context);

            String libelle=art.getLibelle();//+" LISTE= "+art.getIdliste();

            texttv.setText(art.toString());

            //Log.v("COMPTEUR ", String.valueOf(Integer.valueOf(iart)));

            texttv.setHeight(100);

            texttv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);

            //texttv.setTextSize(500,40);
            //setFont(textView3,"CURSTOM-FONT2.ttf");
            texttv.setTextColor(Color.BLUE);

            // setFont(texttv,"Roboto-Light.ttf");
            setFont(texttv,"RobotoCondensed-Bold.ttf");
            // setFont(texttv,"Roboto-Italic.ttf");

            int w=700;
            //OrientationHelper.HORIZONTAL

            int oi = getResources().getConfiguration().orientation ;
            //Log.v("MAIN ACTIVITY","Portrait-517 OI="+oi);

            if (oi == 1)
            {
                w=400;//portrait
            }
            else
            {
                if (oi == 2)
                {
                    w=700;//paysage
                }
            }

            //libelle produit

            texttv.setWidth(w);

            gabaritDet.addView(texttv);

            /*
            tester statut dans liste ou achat en fonction du mode
             */
            if(bmodeliste)
            {
                if(art.getIdliste()!=0)
                {
                    btnach.setImageResource(R.drawable.delete2);
                }else
                {
                    btnach.setImageResource(R.drawable.ajout2);

                }
                //Log.v("MAIN ACTIVITY","MODE 586 "+MySQLiteHelper.PARAM_MODEENCOURS_LISTE);


            }else{
                if(art.getEstachete()==1)
                {
                    btnach.setImageResource(R.drawable.delete2);

                }else
                {
                    btnach.setImageResource(R.drawable.kaddy);

                }
                //Log.v("MAIN ACTIVITY","MODE 591 "+MySQLiteHelper.PARAM_MODEENCOURS_ACHAT);
            }
            gabaritDet.addView(btnach);

            // gabaritDet.setPadding(10,10,10,10);

            gabaritDet.setLayoutParams(lp2);

            gabaritDet.setMinimumWidth(500);

            gabaritDet.setMinimumHeight(100);

            gabaritListeDet.addView(gabaritDet);

        }

        //Log.v("MAIN","560");

        LinearLayoutlisteproduits.addView(gabaritListeDet);

        LinearLayoutlisteproduits.setScrollContainer(true);

        //Log.v("MAIN","566");

        //LinearLayoutlisteproduits.setLayoutParams(lp3);

        // LinearLayoutlisteproduits.setLayoutParams(lpfill);
        //wrap content height LinearLayoutlisteproduits.setLayoutParams(lp3);


        final ImageButton imagebuttonMode = (ImageButton) findViewById(R.id.imageBtnMode);

        imagebuttonMode.setOnClickListener(new View.OnClickListener() {

            //int idbtn=btnach.getId();

            @Override
            public void onClick(View view)
            {

                final Context context = view.getContext();

                ParamDataSource dtsparam = new ParamDataSource(context);

                dtsparam.open();

                MySQLiteHelper mysqlhlpr=new MySQLiteHelper(context);

                Param nParam=dtsparam.LectureParam();

                String modeEnCours=nParam.getModeencours();
               //Log.v("MAINACTIVITY","453 imagebuttonMode MODEENCOURS >"+nParam.getModeencours());

                boolean modeliste=modeEnCours.equals(MySQLiteHelper.PARAM_MODEENCOURS_LISTE);

                if(modeliste)
                {
                    nParam.setModeencours(MySQLiteHelper.PARAM_MODEENCOURS_ACHAT);
                    //Toast.makeText(MainActivity.this, " 460 PASSE EN MODE ACHAT getModeencours= "+nParam.getModeencours(), Toast.LENGTH_LONG).show();
                    //Log.v("MAINACTIVITY","652 PASSAGEENMODEACHAT"+nParam.getModeencours());

                }else
                {
                    nParam.setModeencours(MySQLiteHelper.PARAM_MODEENCOURS_LISTE);
                    //Log.v("MAINACTIVITY","657 PASSAGEENMODELISTE"+nParam.getModeencours());
                    //Toast.makeText(MainActivity.this, " 467 PASSE EN MODE LISTE getModeencours= "+nParam.getModeencours(), Toast.LENGTH_LONG).show();
                }

                boolean bmaj=mysqlhlpr.MiseAJourParam(view.getContext(),nParam);

                //mysqlhlpr.MiseAJourParam2(context,dtsparam.getDatabase(),true,MySQLiteHelper.PARAM_MAJ_MODEENCOURS,nParam);
                if(bmaj)
                {

                    //dtsparam.LectureParam();

                    //
                    //
                    //Toast.makeText(MainActivity.this, " PARAMETRES MISE A JOUR getModeencours= "+nParam.getModeencours(), Toast.LENGTH_LONG).show();

                }
//

                //Toast.makeText(MainActivity.this, " BUTTON MODE ", Toast.LENGTH_LONG).show();

                dtsparam.close();

                finish();

                startActivity(getIntent());

            }
        });

        //final ImageButton imageBtnActiverModeCtrl = (ImageButton) findViewById(R.id.imageBtnActiverModeCtrl);
        final ImageButton imageBtnActiverModeCtrl = (ImageButton) findViewById(R.id.BtnActiverModeCtrl);

        if (bmodeliste)
        {
            //Log.v("MAIN ACT","702 MODE LISTE "+MySQLiteHelper.PARAM_MODEENCOURS_LISTE);
            //btnactmodectrl.setVisibility(0);
            imagebuttonMode.setBackgroundResource(R.drawable.liste2);
        }else
        {
            //Log.v("MAIN ACT","707 MODE ACHAT "+MySQLiteHelper.PARAM_MODEENCOURS_ACHAT);
            //btnactmodectrl.setVisibility(1);
            imagebuttonMode.setBackgroundResource(R.drawable.box);
        }
        //Log.v("MAIN ACT","711");

        boolean ctrlact=false;//oParam.getBmodectrl();


        ctrlact=nParam.getBmodectrl();
        if(ctrlact)
        {
            imageBtnActiverModeCtrl.setBackgroundResource(R.drawable.checklist);

        }else
        {
            imageBtnActiverModeCtrl.setBackgroundResource(R.drawable.controleb);

        }
        //Log.v("MAIN ACT","723");
        //String modeEnCours=oParam.getModeencours();
        boolean modeachat=modeEnCours.equals(MySQLiteHelper.PARAM_MODEENCOURS_ACHAT);
        //Log.v("MAIN ACT","726");

        final ImageButton btnactmodectrl = (ImageButton) findViewById(R.id.BtnActiverModeCtrl);
        //Log.v("MAIN ACT","729");

        if(modeachat)
        {
            imageBtnActiverModeCtrl.setVisibility(View.VISIBLE);

        }else
        {
            imageBtnActiverModeCtrl.setVisibility(View.GONE);
        }
        //Log.v("MAIN ACT","739");

        dtsparam.close();

        // btnactmodectrl.setVisibility(modeachat);

        //Log.v("MAIN","704 MODEENCOURS>"+modeEnCours+"<");

        imageBtnActiverModeCtrl.setOnClickListener(new View.OnClickListener() {

            //int idbtn=btnach.getId();

            @Override
            public void onClick(View view)
            {

                final Context context = view.getContext();

                ParamDataSource dtsparam = new ParamDataSource(context);
                dtsparam.open();

                //MySQLiteHelper mysqlhlpr=new MySQLiteHelper(context);

                Param nParam= dtsparam.LectureParam();
                if(nParam.getBmodectrl())
                {
                    nParam.setBmodectrl(false);
                }else
                {
                    nParam.setBmodectrl(true);
                }
                dtsparam.updateParam(nParam);

                //Param oParam=mysqlhlpr.MiseAJourParam(context,dtsparam.getDatabase(),true,MySQLiteHelper.PARAM_MAJ_CTRLMODE,nParam);
                //boolean ctrlact=oParam.getBmodectrl();

                //view.

                String modeEnCours=nParam.getModeencours();

                Long listeid=nParam.getListeEnCours();

               //Log.d("MAIN ACTIVITY CLICK", "LECTURE MODE EN COURS 580 >" + modeEnCours + "< ");
                //Log.d("MAIN ACTIVITY CLICK", "LECTURE MODE EN COURS >" + modeEnCours + "< resultOfComparison=" + modeliste);


                //mysqlhlpr.ControleVersionBaseDeDonnees(context,dtsparam.getDatabase());

                //Log.v("MAIN","FIN TRAITEMENT VERSIONDATABASE >"+nversion+"<");


                dtsparam.close();


                finish();

                startActivity(getIntent());

            }
        });

        //Toast.makeText(MainActivity.this, " Fin Affichage Articles LISTE="+listeid+"FAMILLE="+idfamille+" MODE="+modeEnCours, Toast.LENGTH_LONG).show();


    }
    public void majArticle(ArticleDataSource datasourceart,Article art,String modeEnCours,long idliste) {
        /*
        tester mode liste mode achat
         */
//        String[]  allColumns=datasourceart.allColumns;

        boolean modeachat=modeEnCours.equals(MySQLiteHelper.PARAM_MODEENCOURS_ACHAT);

        Log.v("MAJARTICLE", " Article" + art.toString()+" MODEENCOURS >"+modeEnCours+"<");
        if(modeachat)
        {
            Log.v("MAJARTICLE","MODE ACHAT 871 Estachete >"+art.getEstachete()+"<");

            if(art.getEstachete()==1)
                art.setEstachete(0);
            else
                art.setEstachete(1);

            datasourceart.updateArticle(art);

        }else
        {
            Log.v("MAJARTICLE", String.format("MODE LISTE IDLISTE=%d", idliste));

            if(idliste>-1)
            {
                art.setIdliste(idliste);

                long lidliste=art.getIdliste();

                //Log.v("MAJARTICLE","836 ID LISTE="+lidliste);

                datasourceart.updateArticle(art);

                //ArticleDataSource dtsart = new ArticleDataSource();
                // final ArticleDataSource datasourceart = new ArticleDataSource(this);


                // String[] allColumns={MySQLiteHelper.COLUMN_LIBELLE,MySQLiteHelper.COLUMN_DSLISTE};
/*
                Cursor cursor = datasourceart.getDatabase().query(MySQLiteHelper.TABLE_ARTICLES,
                        allColumns, MySQLiteHelper.COLUMN_ID + " = " + art.getId(), null,
                        null, null, null);

                cursor.moveToFirst();//.cursorToArticle(cursor);

                //Log.v("MAJARTICLE","852, ID LISTE="+cursor.getLong(3));

                Article lectArticle = datasourceart.cursorToArticle(cursor);

                lectArticle.setIdliste(cursor.getLong(3));

                lidliste=lectArticle.getIdliste();

                cursor.close();

                Log.v("MAJARTICLE","862 ID LISTE="+lidliste);

                finish();
                startActivity(getIntent());
                */

            }
        }

    }

    /*

     */
    public void setFont(TextView textView, String fontName) {
        if(fontName != null){
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
                textView.setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        if (item != null) {
            int id = item.getItemId();

            if (id == R.id.nav_managefamilles) {
                /*
                familles
                 */
                final Intent intent = new Intent(MainActivity.this, Familles.class);
                startActivity(intent);
                AfficheFamilles();

            } else if (id == R.id.nav_managearticles) {
                /*
                articles
                 */
                final Intent intent = new Intent(MainActivity.this, Articles.class);
                startActivity(intent);
                AfficheArticles();
            } else if (id == R.id.nav_initlistes) {

                InitListe();
            } else if (id == R.id.nav_initbase) {

                InitBase();
                AfficheFamilles();
                AfficheArticles();

            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

        ParamDataSource dtsparam = new ParamDataSource(this);
        dtsparam.open();

        Param nParam = dtsparam.LectureParam();

        Long listeid=nParam.getListeEnCours();
        Long lidfamille=nParam.getFamilleEnCours();

        AfficheFamilles();


        dtsparam.close();
        AfficheArticles();

        //Log.v("MAINACT START","251 LISTEID="+listeid+" FAMILLEID="+lidfamille);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://ouccelo.com.acquisti/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://ouccelo.com.acquisti/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void InitBase()
    {
        final Context context = this;

        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        adb.setIcon(android.R.drawable.ic_dialog_alert);

        int s = R.string.msginitbd;

        adb.setTitle(s);

        adb.setPositiveButton("Ok", null);

        adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*
                Log.v("MAINACT START","870 avant startService InitBaseActivity ");

                //ArtServices arts = new ArtServices();

                //startService(new Intent(context, ArtServices.class));

                final Intent intent = new Intent(MainActivity.this, InitBaseActivity.class);
                startActivity(intent);

                Log.v("MAINACT START","874 apres startService InitBaseActivity ");
                */

                /*
                new Thread()
                {
                    @Override
                    public void run() {



                        try {


                            ArticleDataSource artdts = new ArticleDataSource(context);



                            //AfficheArticles();

                        } catch (final Exception ex) {

                            Log.i("---","Exception in thread "+ex.getMessage());

                        }


                    }

                }.start();
*/
                ArticleDataSource artdts = new ArticleDataSource(context);

                artdts.open();

                artdts.chargeFamillesArticles(context);

                artdts.close();

                AfficheArticles();

                Toast.makeText(MainActivity.this, "Initialisation terminée", Toast.LENGTH_LONG).show();

            }

        });

        adb.setNegativeButton(R.string.no, null);

        adb.setMessage(s);

        adb.show();


    }

    private void InitListe()
    {
        /*
        Renititaliser Liste et Achats
        Article.idliste=0
        Article.estachete=false
         */
        //MySQLiteHelper.COLUMN_DSLISTE
        //MySQLiteHelper.COLUMN_DSACHATS

        final Context context = this;

        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        adb.setIcon(android.R.drawable.ic_dialog_alert);

        int s = R.string.msginitliste;

        adb.setTitle(s);

        adb.setPositiveButton("Ok", null);

        adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                ArticleDataSource artdts = new ArticleDataSource(context);

                artdts.open();

                artdts.razListe();

                artdts.close();

                AfficheArticles();
            }

        });

        adb.setNegativeButton(R.string.no, null);

        adb.setMessage(s);

        adb.show();
    }
    public void Close(View View) {

        final Context context = this;

        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        adb.setIcon(android.R.drawable.ic_dialog_alert);

        int s = R.string.msgquit;

        adb.setTitle(s);

        adb.setPositiveButton("Ok", null);

        adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }

        });

        adb.setNegativeButton(R.string.no, null);

        adb.setMessage(s);

        adb.show();

    }
}

class SpinnerListeListener implements OnItemSelectedListener, OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        //Spinner spinner_liste = (Spinner) findViewById(R.id.spinner_liste);
        //Long listeid=spinner_liste.getSelectedItemId();



        Log.v("MAINONITEMCLICK","LISTE 170");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Log.v("MAINONITEMSELECTED","LISTE 177");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

class SpinnerFamilleListener implements OnItemSelectedListener, OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Log.v("MAINONITEMCLICK","FAMILLE 192");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Log.v("MAINONITEMSELECTED","FAMILLE 199");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}