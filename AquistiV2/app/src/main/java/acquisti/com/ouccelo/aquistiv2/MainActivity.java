package acquisti.com.ouccelo.aquistiv2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import static acquisti.com.ouccelo.aquistiv2.R.id.collapseActionView;
import static acquisti.com.ouccelo.aquistiv2.R.id.spinner_familles;
import static acquisti.com.ouccelo.aquistiv2.R.id.textView;
import static android.view.View.TEXT_ALIGNMENT_CENTER;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayAdapter<Famille> myAdapterFam;
    private ArrayAdapter<Article> myAdapterArt;
    private ArrayAdapter<Liste> myAdapterLst;
    private Spinner spinner_liste;
    private Spinner spinner_famille;
    public Long articleid;
    Long listeid;

    public final static String ARTICLE_ID =
            "com.ltm.ltmactionbar.MESSAGE";

    private static final int DIALOG_ALERT = 10;

    SQLiteAdapter database;// = new SQLiteAdapter(context);

    Param nParam ;// = dtsparam.LectureParam();

    ParamDataSource dtsparam ;//= new ParamDataSource(context);

    ArticleDataSource artdts;// = new ArticleDataSource(context);

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Context context = this;

        dtsparam = new ParamDataSource(context);

        boolean b = dtsparam.open();

        if (b) {
            //boolean bres=database.InitDb(dtsparam.getDatabase(),context);

            database = new SQLiteAdapter(context);

            database.getWritableDatabase();

            //database.dropDb(dtsparam.getDatabase());

            database.initDb(dtsparam.getDatabase());

            Spinner spinner_famille = (Spinner) findViewById(R.id.spinner_familles);

            spinner_famille.setOnItemSelectedListener(new SpinnerFamilleListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if (selectedItemView != null) {
                        dtsparam= new ParamDataSource(selectedItemView.getContext());

                        dtsparam.open();
                        //dtsparam
                        //pdts.open();
                        nParam = dtsparam.LectureParam();
                        if (id != -1) {
                            nParam.setFamilleEnCours(id);
                            dtsparam.updateParam(nParam);
                            AfficheListeArticle(context);
                        }
                    } else {
                        // ParamDataSource pdts = new ParamDataSource(selectedItemView.getContext());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });

        }


        //R.id.spinner_familles;
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onStart() {
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        final Context context = this;

        //SQLiteAdapter database = new SQLiteAdapter(this);

        //ParamDataSource pdts = new ParamDataSource(context);
        //dtsparam.open();

        //SQLiteAdapter database = new SQLiteAdapter(context);
        //database = new SQLiteAdapter(context);

        //delete
        //
        //database.dropDb(pdts.getDatabase());
        database.initDb(dtsparam.getDatabase());


        boolean b = dtsparam.open();
        if (!b) {

            AlertDialog.Builder adb = new AlertDialog.Builder(context);

            adb.setIcon(android.R.drawable.ic_dialog_alert);

            int s = R.string.msgpbopenparam;

            adb.setTitle(s);

            adb.setPositiveButton("Ok", null);


            adb.setMessage(s);

            adb.show();
        }

        artdts = new ArticleDataSource(context);

        b = artdts.open();
        if (!b) {

            AlertDialog.Builder adb = new AlertDialog.Builder(context);

            adb.setIcon(android.R.drawable.ic_dialog_alert);

            int s = R.string.msgpbopenarticle;

            adb.setTitle(s);

            adb.setPositiveButton("Ok", null);

            adb.setMessage(s);

            adb.show();

            finish();

        }


        // InitListe();

//        artdts.open();

        //  artdts.razListe();


        AfficheListes(Long.valueOf(0));

        AfficheFamilles();

        // AfficheArticles();

        AfficheListeArticle(context);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
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

            final Intent intent = new Intent(MainActivity.this, ParametresActivity.class);
            startActivity(intent);
            AfficheListeArticle(this);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        boolean bSaisieManuelle = true;
        boolean bSaisieManuelleArticles = true;
        boolean bSaisieManuelleFamilles = true;
        boolean bSaisieManuelleListes = true;


        int id = item.getItemId();

        if (id == R.id.gestart) {
            /*
            gestion des articles

             */

            if (bSaisieManuelle) {
                if (bSaisieManuelleArticles) {
                    final Intent intent = new Intent(MainActivity.this, ArticlesActivity.class);
                    startActivity(intent);
                    AfficheListeArticle(this);
                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(this);

                    adb.setIcon(android.R.drawable.ic_dialog_alert);

                    int s = R.string.lib_saismanprod_nonaccess;

                    adb.setTitle(s);

                    adb.setPositiveButton("Ok", null);

                    adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }

                    });

                    //adb.setNegativeButton(R.string.no, null);

                    adb.setMessage(s);

                    adb.show();
                }

            } else {
                AlertDialog.Builder adb = new AlertDialog.Builder(this);

                adb.setIcon(android.R.drawable.ic_dialog_alert);

                int s = R.string.lib_saismanuelle_nonaccess;

                adb.setTitle(s);

                adb.setPositiveButton("Ok", null);

                adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }

                });

                //adb.setNegativeButton(R.string.no, null);

                adb.setMessage(s);

                adb.show();
            }
        } else if (id == R.id.gestfam) {

                        /*
            gestion des articles

             */

            if (bSaisieManuelle) {
                if (bSaisieManuelleFamilles) {
                    final Intent intent = new Intent(MainActivity.this, FamillesActivity.class);
                    startActivity(intent);
                    //AfficheFamilles();
                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(this);

                    adb.setIcon(android.R.drawable.ic_dialog_alert);

                    int s = R.string.lib_saismanprod_nonaccess;

                    adb.setTitle(s);

                    adb.setPositiveButton("Ok", null);

                    adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }

                    });

                    //adb.setNegativeButton(R.string.no, null);

                    adb.setMessage(s);

                    adb.show();
                }

            } else {
                AlertDialog.Builder adb = new AlertDialog.Builder(this);

                adb.setIcon(android.R.drawable.ic_dialog_alert);

                int s = R.string.lib_saismanuelle_nonaccess;

                adb.setTitle(s);

                adb.setPositiveButton("Ok", null);

                adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }

                });

                //adb.setNegativeButton(R.string.no, null);

                adb.setMessage(s);

                adb.show();
            }
        } else if (id == R.id.initbd) {

            InitBd();
            AfficheListeArticle(this);

        } else if (id == R.id.initliste) {

            InitListe();

        } else if (id == R.id.gestlistes) {

                        /*
            gestion des articles

             */

            if (bSaisieManuelle) {
                if (bSaisieManuelleListes) {

                    final Intent intent = new Intent(MainActivity.this, ListesActivity.class);

                    startActivity(intent);

                    AfficheListeArticle(this);
                } else {
                    AlertDialog.Builder adb = new AlertDialog.Builder(this);

                    adb.setIcon(android.R.drawable.ic_dialog_alert);

                    int s = R.string.lib_saismanprod_nonaccess;

                    adb.setTitle(s);

                    adb.setPositiveButton("Ok", null);

                    adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }

                    });

                    //adb.setNegativeButton(R.string.no, null);

                    adb.setMessage(s);

                    adb.show();
                }

            } else {
                AlertDialog.Builder adb = new AlertDialog.Builder(this);

                adb.setIcon(android.R.drawable.ic_dialog_alert);

                int s = R.string.lib_saismanuelle_nonaccess;

                adb.setTitle(s);

                adb.setPositiveButton("Ok", null);

                adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }

                });

                //adb.setNegativeButton(R.string.no, null);

                adb.setMessage(s);

                adb.show();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void AfficheListes(Long listeid) {
        spinner_liste = (Spinner) findViewById(R.id.spinner_listes);

        // List exemple = new ArrayList();

        //exemple.add("Choisir liste");

        final ListesDataSource listesDataSource = new ListesDataSource(this);
        boolean b = listesDataSource.open();

        if (b) {
            //listesDataSource.getDatabase().execSQL("DELETE FROM " + SQLiteAdapter.TABLE_LISTES);

            //listesDataSource.createListe("ma liste 1");

            List<String> listValuesListes = listesDataSource.getAllListesLibelle("");
        /*List<Liste> listValuesListes = listesDataSource.getAllListes("");

        for (Liste lst : listValuesListes)
        {
            exemple.add(lst.getLibelle());

        }
        */
            //listesDataSource.close();

            ArrayAdapter adapter = new ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    listValuesListes
            );

                            /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            //Enfin on passe l'adapter au Spinner et c'est tout
            spinner_liste.setAdapter(adapter);

        }

        spinner_liste.removeAllViewsInLayout();
        spinner_liste.destroyDrawingCache();
        spinner_liste.animate();

        spinner_liste.setPrompt("Selectionnez une liste");

        spinner_liste.setSelection(listeid.intValue(), true);

        //SpinnerListeListener OnItemSelectedListener
        spinner_liste.setOnItemSelectedListener(new SpinnerListeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                ParamDataSource pdts = new ParamDataSource(selectedItemView.getContext());
                pdts.open();

                //SQLiteAdapter.PARAM_COLUMN_LISTEENCOURS

                Param param = pdts.LectureParam();

                //Log.v("MAIN ONITEMSELECTED","548 listeid="+id);

                if (id != -1) {
                    param.setListeencours(id);

                    pdts.updateParam(param);

                    //Log.v("MAINACT","556, LISTE SELECTIONNEE ID="+id);

                    AfficheListeArticle(parentView.getContext());

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });


        // adapter.notifyDataSetChanged();

        /* */


    }


    /**
     * Function to load the spinner data from SQLite database
     */
    private void AfficheFamilles() {
        // database handler
        FamillesDataSource datasourcefam = new FamillesDataSource(this);

        datasourcefam.open();

        // Spinner Drop down elements
        List<String> lables = datasourcefam.getAllFamillesLibelle("");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //R.id.spinner_familles
        Spinner spinner_famille = (Spinner) findViewById(R.id.spinner_familles);

        //LinearLayout LinearLayoutlisteproduits = (LinearLayout) findViewById(R.id.idlisteproduits);


        // attaching data adapter to spinner
        spinner_famille.setAdapter(dataAdapter);


        //datasourcefam.close();

        //Toast.makeText(this," Nb de familles affichées : "+ lables.size(),Toast.LENGTH_SHORT).show();


    }

    private void InitBd() {

        final Context context = this;

        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        adb.setIcon(android.R.drawable.ic_dialog_alert);

        int s = R.string.msginitbd;

        adb.setTitle(s);

        adb.setPositiveButton("Ok", null);

        adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                //artdts = new ArticleDataSource(context);
                //artdts.open();

                //database = new SQLiteAdapter(context);

                //database.dropDb(artdts.getDatabase());
                database.initDb(artdts.getDatabase());

                artdts.chargeFamillesArticles(context);


            }

        });

        adb.setNegativeButton(R.string.no, null);

        adb.setMessage(s);

        adb.show();

        this.AfficheListeArticle(this);

    }

    /*

 */

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void AfficheListeArticle(final Context context) {

        //Log.v("ART DATA SRC","700");
        AppBarLayout.LayoutParams lp1 = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.MATCH_PARENT);

        AppBarLayout.LayoutParams lp2 = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);

        AppBarLayout.LayoutParams lp3 = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);

        AppBarLayout.LayoutParams lpfill = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.FILL_PARENT,
                DrawerLayout.LayoutParams.FILL_PARENT);


       // boolean bFiltreliste = false;

        //ParamDataSource dtsparam = new ParamDataSource(context);
        ParamDataSource dtsparam = new ParamDataSource(context);
        dtsparam.open();


        Param nParam = dtsparam.LectureParam();
        final String modeEnCours = nParam.getModeencours();
        String s = nParam.getModeencours();
        String sma = SQLiteAdapter.PARAM_MODEENCOURS_ACHAT;

        final Integer modeachat = s.compareTo(sma);
        listeartDataSource dtslstart = new listeartDataSource(context);

        dtslstart.open();

        List<listeart> listValueslstartliste = dtslstart.getAllListeart(SQLiteAdapter.PARAM_MODEENCOURS_LISTE);

        List<listeart> listValueslstartachat = dtslstart.getAllListeart(SQLiteAdapter.PARAM_MODEENCOURS_ACHAT);

        if (modeachat == 0)
        {
           //listValueslstart = dtslstart.getAllListeart(SQLiteAdapter.PARAM_MODEENCOURS_ACHAT);

        }

        spinner_liste = (Spinner) findViewById(R.id.spinner_listes);

        Long listeid = nParam.getListeEnCours();

        spinner_liste.setSelection(listeid.intValue(), true);

        boolean bctrl = nParam.getBmodectrl();

        FloatingActionButton buttonmode = (FloatingActionButton) findViewById(R.id.buttonmode);
        FloatingActionButton buttonctrl = (FloatingActionButton) findViewById(R.id.buttonctrl);

        if (modeachat != 0) {
            buttonmode.setImageResource(R.drawable.edit);
            buttonctrl.setVisibility(View.INVISIBLE);
        } else {
            buttonmode.setImageResource(R.drawable.shop);
            buttonctrl.setVisibility(View.VISIBLE);

            if (bctrl) {
                buttonctrl.setImageResource(android.R.drawable.checkbox_on_background);
            } else {
                buttonctrl.setImageResource(android.R.drawable.ic_popup_sync);
                //Log.v("changemodectrl"," PASS EN MODE CTRL OUI"); ic_popup_sync
            }
        }


        Long idfamille = nParam.getFamilleEnCours();

        Spinner spinner_famille = (Spinner) findViewById(R.id.spinner_familles);

        // spinner_famille.setSelection(idfamille.intValue(), true);
        if (idfamille.intValue() != 0) {
            //.setSelection(idfamille.intValue(),true);

            spinner_famille.setSelection(idfamille.intValue());

        }

        final LinearLayout gabaritListeDet = new LinearLayout(this);
        gabaritListeDet.setGravity(Gravity.LEFT);
        gabaritListeDet.setOrientation(LinearLayout.VERTICAL);
        gabaritListeDet.removeAllViewsInLayout();
        gabaritListeDet.setPadding(0, 0, 0, 0);

        gabaritListeDet.setLayoutParams(lpfill);

        LinearLayout LinearLayoutlisteproduits = (LinearLayout) findViewById(R.id.idlisteproduits);
        if (LinearLayoutlisteproduits != null) {
            LinearLayoutlisteproduits.removeAllViewsInLayout();

            //ArticleDataSource datasourceart = new ArticleDataSource(context);

            //artdts.open();
            //datasourceart.open();

            //Log.v("ART DATA SRC","798");

            List<Article> listValuesArt = artdts.getAllArticles(idfamille, modeEnCours, nParam.getBmodectrl(), listeid, false);

            //Log.v("AfficheArticles","731 "+listValuesArt.toArray().length);
            myAdapterArt = new ArrayAdapter<Article>(this, R.layout.row_layout_article,
                    R.id.listTextArt, listValuesArt);                //LinearLayoutlisteproduits.addView(gabaritListeDet,0);

            int iart = 0;
            int iCptrArtAAcheter = 0;

            /**
             * liste des ARTICLES
             */
            for (Article art : listValuesArt) {

                LinearLayout gabaritDet = new LinearLayout(this);
                gabaritDet.setVerticalScrollBarEnabled(true);

                gabaritDet.setLayoutParams(lpfill);

                gabaritDet.setOrientation(LinearLayout.HORIZONTAL);

                gabaritDet.setGravity(Gravity.LEFT | Gravity.START);

                gabaritDet.setClickable(true);
                gabaritDet.setX(0);


                Button imgbuttexte = new Button(context);

                String slib = art.getLibelle();
                imgbuttexte.setText(slib);

                // Log.v("750 >",slib);

                //OrientationHelper.HORIZONTAL

                int w = 700;

                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                int wmetrics = metrics.widthPixels;

                int oi = getResources().getConfiguration().orientation;
                //Log.v("MAIN ACTIVITY","Portrait-517 OI="+oi);

                if (oi == 1) {
                    w = 350;// android 4.4

                    w = wmetrics - 220;
                    //w=600;//portrait android 5.5 lollipop

                } else {
                    if (oi == 2) {
                        w = 700;//paysage
                    }
                }

                imgbuttexte.setWidth(w);
                imgbuttexte.setX(0);

                imgbuttexte.setTag(art);

                //setFont(imgbuttexte, "RobotoCondensed-Bold.ttf");

                imgbuttexte.setTextSize(20);


                imgbuttexte.setBackgroundResource(R.color.colorJaunePale);

                long lid = nParam.getListeEnCours();

                int colorid = R.color.colorJaunePale;

                long idlstart=0;
                long idlstartach=0;

                if (lid != 0) {
                    /**
                     * Affichage article de la liste
                     */

                    //Log.v("liste art 863 "," taille tableau "+listValueslstart.toArray().length);
                    for (listeart lstart2 : listValueslstartliste) {

                        //Log.v("liste art 863 ",lstart2.toString());

                        if (lstart2.getIdart() == art.getId() && lstart2.getIdlst() == lid) {
                            colorid = R.color.colorRouge;
                            colorid = R.color.colorJaunePale;

                            if(modeachat==0)// mode achat
                            {

                                idlstart=lstart2.getId();

                            }else
                            {
                                colorid = R.color.colorVertAmande;

                            }
                            //Log.v("liste art","SELECTION POUR "+lid);
                            break;

                        }
                    }
                    /*
                    mode ACHAT afficher les articles achetés en ROUGE
                     */
                    //Log.v("liste art 919 "," MODE ACHAT ="+modeachat+" SIZEACHAT= "+listValueslstartachat.size());

                    if(modeachat==0)
                    {
                        //colorid = R.color.colorJaunePale;

                        for (listeart lstart2 : listValueslstartachat) {

                            //Log.v("liste art 926 ",lstart2.toString());

                            if (lstart2.getIdart() == art.getId() && lstart2.getIdlst() == lid) {
                                colorid = R.color.colorVertAmande;
                                idlstartach=lstart2.getId();
                                break;
                            }
                        }
                    }

                }

                imgbuttexte.setBackgroundResource(colorid);

                imgbuttexte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Article art = (Article) view.getTag();

                        //Log.v("ONCLICK"," 321 ARTICLE "+art.toString());

                        ParamDataSource pdts = new ParamDataSource(view.getContext());
                        pdts.open();

                        Param param = pdts.LectureParam();

                        long lid = param.getListeEnCours();

                        /*
                        on enleve l'article de la liste
                        if (art.getIdliste() != 0) {
                            lid = 0;
                        }
                         */

                        listeartDataSource lsttds = new listeartDataSource(context);
                        lsttds.open();


                        if (lid != 0) {

      //                      ArticleDataSource datasourceart = new ArticleDataSource(view.getContext());
  //                          datasourceart.open();
//artdts
                            if (modeachat != 0) {
                                /*
                                MODE LISTE
                                listeart nlisteart = new listeart();
                                nlisteart.setIdart(art.getId());
                                nlisteart.setIdlst(lid);*/
                                long idliste = lsttds.rechercheListeart(lid, art.getId(),SQLiteAdapter.PARAM_MODEENCOURS_LISTE);

                                if (idliste == 0) {
                                    //insertion

                                    //long idlst = lsttds.createListeart(lid, art.getId(),SQLiteAdapter.PARAM_MODEENCOURS_ACHAT.charAt(1));
                                    long idlst = lsttds.createListeart(lid, art.getId(),SQLiteAdapter.PARAM_MODEENCOURS_LISTE);

                                    //Log.v("MAINACT","939 mode LISTE idlst="+idlst+" TYPE >"+SQLiteAdapter.PARAM_MODEENCOURS_LISTE+"<");

                                } else {
                                    //suppression
                                    lsttds.deleteListeart(idliste);
                                }

                                List<listeart> listValueslstart = lsttds.getAllListeart(SQLiteAdapter.PARAM_MODEENCOURS_LISTE);

                                for (listeart lstart2 : listValueslstart) {

                                    //Log.v("liste art 959 ",lstart2.toString());
    /*

                                    if(lstart2.getIdart()==art.getId())
                                    {
                                        break;

                                    }

    */
                                    //listes.add(lstart2.getId());
                                }



                                //majArticle(datasourceart, art, modeEnCours, lid);


                            } else {

//                                majArticle(datasourceart, art, modeEnCours, lid);

                                long idliste = lsttds.rechercheListeart(lid, art.getId(),SQLiteAdapter.PARAM_MODEENCOURS_ACHAT);
                                if (idliste == 0) {
                                    long idlst = lsttds.createListeart(lid, art.getId(),SQLiteAdapter.PARAM_MODEENCOURS_ACHAT);

                                    //Log.v("MAINACT","1025 mode LISTE idlst="+idlst+" TYPE >"+SQLiteAdapter.PARAM_MODEENCOURS_ACHAT+"<");


                                }else{
                                    lsttds.deleteListeart(idliste);

                                }

                            }

                            AfficheListeArticle(context);

                        }

                    }

                });


                imgbuttexte.setLayoutParams(lpfill);

                /*
                si mode liste ou idlstart!=0 si dans liste
                 */
                if(modeachat==0)
                {

                    //boolean bctrl=nParam.getBmodectrl();

                    //Log.v("1052"," MODE ACHAT CONTROLE ? "+bctrl+" IDLSTARTACH="+idlstartach+" IDLSTART="+idlstart);

                    //if(idlstartach==0)// SI N A PAS ETE ACHETE ON AFFICHE
                    if(idlstart!=0)// SI DANS LISTE
                    {

                        if(!bctrl && idlstartach!=0)// SI PAS CTRL ET DANS ACHAT
                        {
                            // ON AFFICHE PAS
                        }else{

                            iCptrArtAAcheter++;

                            iart++;

                            gabaritDet.addView(imgbuttexte);

                            //gabaritListeDet.removeAllViewsInLayout();

                            gabaritListeDet.addView(gabaritDet);
                        }

                    }
                }
                else
                {
                    if(modeachat!=0 || idlstart!=0)
                    {

                        iCptrArtAAcheter++;

                        iart++;

                        gabaritDet.addView(imgbuttexte);

                        //gabaritListeDet.removeAllViewsInLayout();

                        gabaritListeDet.addView(gabaritDet);
                    }
                }

                //LinearLayoutlisteproduits.addView(imgbuttexte,0);
                //LinearLayoutlisteproduits.addView(gabaritListeDet,0);


            }//FIN BPUCLE ARTICLE

            LinearLayoutlisteproduits.addView(gabaritListeDet, 0);


            //Log.v("845","FIN BOUCLE ARTICLE");

            if (modeachat != 0) {
                //Toast.makeText(this," Nb d' article(s) affiché(s) : "+ listValuesArt.size(),Toast.LENGTH_SHORT).show();

            } else {
                if (iCptrArtAAcheter == 0) {

                    TextView texttv = new TextView(context);

                    texttv.setX(5);
                    texttv.setText(R.string.msg_courses_terminees);
                    texttv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
                    texttv.setHeight(100);
                    texttv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
                    texttv.setTextColor(Color.BLUE);
                    setFont(texttv, "RobotoCondensed-Bold.ttf");
                    texttv.setWidth(700);

                    gabaritListeDet.addView(texttv);


                    LinearLayout gabaritDet = new LinearLayout(this);
                    gabaritDet.setVerticalScrollBarEnabled(true);

                    gabaritDet.setLayoutParams(lpfill);

                    gabaritDet.setOrientation(LinearLayout.HORIZONTAL);

                    gabaritDet.setGravity(Gravity.LEFT | Gravity.START);

                    gabaritDet.setBackgroundColor(getResources().getColor(R.color.colorRouge));

                    gabaritDet.setClickable(true);
                    gabaritDet.setX(0);

                    Toast.makeText(this,R.string.msg_courses_terminees,Toast.LENGTH_SHORT).show();

/*

                    AlertDialog.Builder adb = new AlertDialog.Builder(context);

                    adb.setIcon(android.R.drawable.ic_dialog_alert);

                    int msg = R.string.msg_courses_terminees;

                    adb.setTitle(msg);
                    adb.setMessage(msg);

                    adb.setPositiveButton("Ok", null);

                    adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }

                    });

                    adb.show();
                    */

                } else {
                    //Toast.makeText(this," Nb d' article(s) affiché(s) : "+ listValuesArt.size(),Toast.LENGTH_SHORT).show();

                }
            }

        }


    }

    private void setFont(TextView textView, String fontName) {
        if (fontName != null) {
            try {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fontName);
                textView.setTypeface(typeface);
            } catch (Exception e) {
                Log.e("FONT", fontName + " not found", e);
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void AfficheDetArticle(View view) {

        Article art = (Article) view.getTag();

        articleid = art.getId();

        //final Intent intent = new Intent(MainActivity.this, DetailArticle.class);

        //intent.putExtra("articleid",articleid.toString());

        //startActivity(intent);


        AfficheListeArticle(this);

    }

    private void InitListe() {
        /*
        Renititaliser Liste et Achats
        Article.idliste=0
        Article.estachete=false
         */
        //SQLiteAdapter.COLUMN_DSLISTE
        //SQLiteAdapter.COLUMN_DSACHATS

        final Context context = this;

        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        adb.setIcon(android.R.drawable.ic_dialog_alert);

        int s = R.string.msginitliste;
        adb.setTitle(s);

        adb.setPositiveButton("Ok", null);

        adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ArticleDataSource artdts = new ArticleDataSource(context);

                artdts.open();

                //artdts.razListe();

                listeartDataSource lstdts = new listeartDataSource(context);

                lstdts.initTable();

                AfficheListeArticle(context);
            }

        });

        adb.setNegativeButton(R.string.no, null);

        adb.setMessage(s);

        adb.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void changemode(View view) {
        ParamDataSource pdts = new ParamDataSource(view.getContext());
        pdts.open();
        Param param = pdts.LectureParam();
        String s = param.getModeencours();
        //Log.v("change mode 948 "," MODE EN COURS "+s);
        String sma = SQLiteAdapter.PARAM_MODEENCOURS_ACHAT;
        String sml = SQLiteAdapter.PARAM_MODEENCOURS_LISTE;
        Integer modeachat = s.compareTo(sma);
        if (modeachat != 0) {
            s = SQLiteAdapter.PARAM_MODEENCOURS_ACHAT;
            //Log.v("change mode 960 "," passe en mode ACHAT "+s);
        } else {
            s = SQLiteAdapter.PARAM_MODEENCOURS_LISTE;
            //Log.v("change mode 964 "," passe en mode LISTE "+s);
        }
        param.setModeencours(s);
        pdts.updateParam(param);
        AfficheListeArticle(view.getContext());
    }

    /**
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void changemodectrl(View view) {
        ParamDataSource pdts = new ParamDataSource(view.getContext());
        pdts.open();
        Param param = pdts.LectureParam();
        boolean bctrl = param.getBmodectrl();
        FloatingActionButton buttonctrl = (FloatingActionButton) findViewById(R.id.buttonctrl);

        param.setBmodectrl(!bctrl);
        pdts.updateParam(param);
        AfficheListeArticle(view.getContext());
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}


class SpinnerListeListener implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Spinner spinner_liste = (Spinner) findViewById(R.id.spinner_liste);
        //Long listeid=spinner_liste.getSelectedItemId();
        //Log.v("MAINONITEMCLICK","LISTE 170");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        //Log.v("MAINONITEMSELECTED","LISTE 177");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

class SpinnerFamilleListener implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        //Log.v("MAINONITEMCLICK","FAMILLE 1075");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        //Log.v("MAINONITEMSELECTED","FAMILLE 199");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}