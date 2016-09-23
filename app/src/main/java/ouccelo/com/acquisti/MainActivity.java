package ouccelo.com.acquisti;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageView;
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
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ArrayAdapter<Famille> myAdapterFam;
    private ArrayAdapter<Article> myAdapterArt;
    private ArrayAdapter<Liste> myAdapterLst;
    private Spinner spinner_liste;

    private static final int DIALOG_ALERT = 10;

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

        //mssql.ControleVersionBaseDeDonnees(this, pdts.getDatabase());

        Param nParam = pdts.LectureParam();

        //nParam.setListeencours(Long.valueOf(0));

       // pdts.updateParam(nParam);

        Long listeid=nParam.getListeEnCours();
        Long lidfamille=nParam.getFamilleEnCours();

        Log.v("MAINACT CREATE","81 LISTEID="+listeid+" FAMILLEID="+lidfamille);

        pdts.close();

        AfficheFamilles();
        AfficheArticles();

       //Log.v("MAINACT CREATE","93");

        /*
            Selection Liste
         */
        AfficheListes(listeid);
        //AfficheListe2();
       //Log.v("Main Acti","117");

/*
        Spinner spinner_liste = (Spinner) findViewById(R.id.spinner_liste);

        List exemple = new ArrayList();

        exemple.add("Choisir liste");

        final ListesDataSource listesDataSource = new ListesDataSource(this);
        listesDataSource.open();

        //listesDataSource.getDatabase().execSQL("DELETE FROM " + MySQLiteHelper.TABLE_LISTES);

        //listesDataSource.createListe("ma liste 1");

        List<Liste> listValuesListes = listesDataSource.getAllListes("");

        for (Liste lst : listValuesListes)
        {
            exemple.add(lst.getLibelle());

        }
        listesDataSource.close();
        */

        Log.v("Main Acti","143");

        /*
            Selection Famille
         */
        Spinner spinner_famille = (Spinner) findViewById(R.id.spinner_famille);

        spinner_famille.setAdapter(myAdapterFam);

       //Log.v("Main Acti","152");


        //Enfin on passe l'adapter au Spinner et c'est tout
       // spinner_famille.setAdapter(adapter);

        spinner_famille.setPrompt("Selectionnez une Famille");
        spinner_famille.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                if(selectedItemView !=null)
                {
                    ParamDataSource pdts = new ParamDataSource(selectedItemView.getContext());
                    pdts.open();
                    Param param=pdts.LectureParam();
                    if(id!=-1)
                    {
                        param.setFamilleEnCours(id);
                        pdts.updateParam(param);
                        pdts.close();
                        AfficheArticles();
                    }
                }else
                {
                    ParamDataSource pdts = new ParamDataSource(selectedItemView.getContext());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
            }
        });
       //Log.v("Main Acti","187");
        spinner_famille.setSelection(lidfamille.intValue(),true);
       //Log.v("Main Acti","189");

       /* spinner_liste = (Spinner) findViewById(R.id.spinner_liste);

        spinner_liste.setAdapter(myAdapterLst);
        */

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
       //Log.v("Main Acti","197");
        navigationView.setNavigationItemSelectedListener(this);
        Log.v("Main Acti","199");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Log.v("Main Acti","203");
    }
    private void AfficheFamilles()
    {
        FamillesDataSource datasourcefam = new FamillesDataSource(this);
        datasourcefam.open();

        List<Famille> listValues = datasourcefam.getAllFamilles(true);
        myAdapterFam = new ArrayAdapter<Famille>(this, R.layout.row_layout_fam,
                R.id.listText, listValues);
        Spinner spinner_famille = (Spinner) findViewById(R.id.spinner_famille);

        //Enfin on passe l'adapter au Spinner et c'est tout
        // spinner_famille.setAdapter(adapter);
        spinner_famille.setAdapter(myAdapterFam);

        datasourcefam.close();
    }
    private void AfficheListe2()
    {

        spinner_liste = (Spinner) findViewById(R.id.spinner_liste);

        //Log.v("AfficheListe2","215");
        ListesDataSource datasourcelst = new ListesDataSource(this);
       //Log.v("AfficheListe2","217");
        datasourcelst.open();
       //Log.v("AfficheListe2","219");
       //Log.v("AfficheListe2","221");
        List<Liste> listValues = datasourcelst.getAllListes(true);
       //Log.v("AfficheListe2","223");
        myAdapterLst = new ArrayAdapter<Liste>(this, R.layout.row_layout_lst,
                R.id.listText, listValues);
       //Log.v("AfficheListe2","226");
        //Spinner spinner_liste = (Spinner) findViewById(R.id.spinner_liste);

        //Enfin on passe l'adapter au Spinner et c'est tout
        // spinner_famille.setAdapter(adapter);
        //myAdapterLst.notifyDataSetChanged();
       //Log.v("AfficheListe2","232");

        spinner_liste.setAdapter(myAdapterLst);
       //Log.v("AfficheListe2","235");

        datasourcelst.close();
       //Log.v("AfficheListe2","238");
    }
    public void AjoutArticles(View view)
    {
        final Context context = this;

        AlertDialog.Builder adb = new AlertDialog.Builder(context);

        adb.setIcon(android.R.drawable.ic_dialog_alert);

        int s = R.string.msgajoutart;

        adb.setTitle(s);

        adb.setPositiveButton("Ok", null);

        adb.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                ParamDataSource dtsparam = new ParamDataSource(context);
                dtsparam.open();
                Param nParam=new Param();
                nParam=dtsparam.LectureParam();
                dtsparam.close();

                long idfamille=0;
                long lid= nParam.getListeEnCours();
                if(lid!=0)
                {
                    final ArticleDataSource datasourceart = new ArticleDataSource(context);
                    datasourceart.open();
                    Long idliste=nParam.getListeEnCours();

                    idfamille=nParam.getFamilleEnCours();

                    boolean bFiltreliste=nParam.getBfiltreliste();

                    final String modeEnCours=nParam.getModeencours();

                    List<Article> listValuesArt = datasourceart.getAllArticles(idfamille,"",modeEnCours,nParam.getBmodectrl(),idliste,bFiltreliste);

                    for (Article art : listValuesArt)
                    {
                        majArticle(datasourceart,art,modeEnCours,lid);

                    }
                    datasourceart.close();

                    AfficheArticles();

                  //  finish();

                    //startActivity(getIntent());

                }

            }

        });

        adb.setNegativeButton(R.string.no, null);

        adb.setMessage(s);

        adb.show();


    }
    /*

     */
    private void AfficheArticles()
    {

        final Context context = this;
        //Log.v("MAINACT AfficheArticles","322");

        AppBarLayout.LayoutParams lp1 = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.MATCH_PARENT);

        AppBarLayout.LayoutParams lp2 = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);

        AppBarLayout.LayoutParams lp3 = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.WRAP_CONTENT);

        AppBarLayout.LayoutParams lpfill = new AppBarLayout.LayoutParams(DrawerLayout.LayoutParams.FILL_PARENT,
                DrawerLayout.LayoutParams.FILL_PARENT);

        Spinner spinner_famille = (Spinner) findViewById(R.id.spinner_famille);


        final ArticleDataSource datasourceart = new ArticleDataSource(this);
        datasourceart.open();
        Param nParam=new Param();

        MySQLiteHelper mysqlhlpr=new MySQLiteHelper(context);
        ParamDataSource dtsparam = new ParamDataSource(context);
        dtsparam.open();
        nParam=dtsparam.LectureParam();
        final String modeEnCours=nParam.getModeencours();

        Long idfamille=nParam.getFamilleEnCours();
        spinner_famille.setSelection(idfamille.intValue(),true);

        //idfamille= 0L;

        Long listeid=nParam.getListeEnCours();

        //Log.v("MAIN","344 MODEENCOURS >"+modeEnCours+" FAMILLE="+idfamille+" LISTEID="+listeid);

        //Param oParam=mysqlhlpr.MiseAJourParam(context,dtsparam.getDatabase(),false,MySQLiteHelper.PARAM_MAJ_CTRLMODE,nParam);

        //bCtrlActive
        boolean bFiltreliste=nParam.getBfiltreliste();

        List<Article> listValuesArt = datasourceart.getAllArticles(idfamille,"",modeEnCours,nParam.getBmodectrl(),listeid,bFiltreliste);

        //Log.v("AfficheArticles","282 "+listValuesArt.toArray().length);
        myAdapterArt = new ArrayAdapter<Article>(this, R.layout.row_layout_article,
                R.id.listText, listValuesArt);
        //Log.v("AfficheArticles","497");
        final LinearLayout LinearLayoutlisteproduits = (LinearLayout) findViewById(R.id.idlisteproduits);

        LinearLayoutlisteproduits.removeAllViewsInLayout();
        LinearLayoutlisteproduits.setBackgroundColor(Color.LTGRAY);
        final LinearLayout gabaritListeDet = new LinearLayout (this);
        gabaritListeDet. setGravity(Gravity.LEFT);
        gabaritListeDet.setOrientation(LinearLayout.VERTICAL);
        gabaritListeDet.removeAllViewsInLayout();
        gabaritListeDet.setPadding(10,10,0,0);

        //String modeEnCours = MySQLiteHelper.PARAM_MODEENCOURS_LISTE;//                oParam.getModeencours();
        //Log.v("AfficheArticles","491");
        final boolean bmodeliste=modeEnCours.equals(MySQLiteHelper.PARAM_MODEENCOURS_LISTE);
        //Log.v("AfficheArticles","493");
        //famille selectionnees
        int ifamsel = 0;// oParam.

        ImageButton imgButtonAjoutMultiple = (ImageButton) findViewById(R.id.imgButtonAjoutMultiple);

        int visible=0;
        if(bmodeliste)
        {
            visible=View.VISIBLE;
        }else
        {
            visible=View.INVISIBLE;
        }
        final ImageButton ImageBtnFiltreListe = (ImageButton) findViewById(R.id.imageBtnFiltreLst);
        if(bFiltreliste)
        {
            ImageBtnFiltreListe.setImageResource(R.drawable.filtrelst);

        }else
        {
            ImageBtnFiltreListe.setImageResource(R.drawable.sansfiltre);

        }

        ImageBtnFiltreListe.setVisibility(visible);
        imgButtonAjoutMultiple.setVisibility(visible);
        ImageBtnFiltreListe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            ParamDataSource dtsparam = new ParamDataSource(context);
            dtsparam.open();
            Param nParam=dtsparam.LectureParam();
            boolean bFiltreliste=nParam.getBfiltreliste();
            nParam.setBfiltreliste(!bFiltreliste);
            dtsparam.updateParam(nParam);
            dtsparam.close();
            AfficheArticles();
            }
        });


        int iart=0;
        int iCptrArtAAcheter=0;

        for (Article art : listValuesArt)
        {
            LinearLayout gabaritDet = new LinearLayout (this);

            gabaritDet.setVerticalScrollBarEnabled(true);
            iart++;

            final ImageButton btnach= new ImageButton(context);

            btnach.setId(iart);
            btnach.setBackground(null);

            btnach.setTag(art);
            btnach.setX(5);

            btnach.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view)
                {

                    //final Context context = view.getContext();

                    //AlertDialog.Builder adb = new AlertDialog.Builder(context);

                    //adb.setTitle(dlgslib);

                    int id=view.getId();

                    Article art = (Article) view.getTag();

                    long l=0;

                   //Log.v("ONCLICK"," 321 ARTICLE "+art.toString());

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
                    }
                    datasourceart.open();
                    majArticle(datasourceart,art,modeEnCours,lid);
                    datasourceart.close();

                    AfficheArticles();

                    //Toast.makeText(MainActivity.this, " MAJART= "+art.getLibelle()+" "+lid, Toast.LENGTH_LONG).show();


                }
            });

            gabaritDet.setLayoutParams(lp3);

            gabaritDet.setOrientation(LinearLayout.HORIZONTAL);

            gabaritDet. setGravity(Gravity.LEFT|Gravity.START);

            gabaritDet.setClickable(true);
            gabaritDet.setX(0);


            /*
            bouton image detail ligne de course
             */
            if(nParam.getBsaisiedetailart())
            {
                ImageButton imgbutdetail = new ImageButton(context);
                imgbutdetail.setBackgroundResource(R.drawable.edit);
                imgbutdetail.setMaxWidth(5);
                imgbutdetail.setMaxHeight(5);

                imgbutdetail.setX(0);

                gabaritDet.addView(imgbutdetail);
            }


            TextView texttv = new TextView(context);

            texttv.setX(5);

            texttv.setText(art.toString());

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

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int wmetrics=metrics.widthPixels;

            int oi = getResources().getConfiguration().orientation ;
            //Log.v("MAIN ACTIVITY","Portrait-517 OI="+oi);

            if (oi == 1)
            {
                w=350;// android 4.4

                w=wmetrics-220;
                w=300;//portrait android 5.5 lollipop

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

            //ImageButton imgbuttexte = new ImageButton(context);
            Button imgbuttexte = new Button(context);
            //imgbuttexte.setBackgroundResource(R.drawable.edit);
            //imgbuttexte.setMaxWidth(5);
            //imgbuttexte.setMaxHeight(5);

            //imgbuttexte.setText(art.getLibelle()+" "+art.getIdliste()+" "+art.getEstachete());
            imgbuttexte.setText(art.getLibelle());

            imgbuttexte.setWidth(w);
            imgbuttexte.setX(10);
            imgbuttexte.setTag(art);
            setFont(imgbuttexte,"RobotoCondensed-Bold.ttf");
            imgbuttexte.setTextSize(20);
            if(art.getIdliste()!=0)
            {
                imgbuttexte.setBackgroundResource(R.color.colorVert);

            }else
            {
                imgbuttexte.setBackgroundResource(R.color.colorOrange);

            }
            imgbuttexte.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View view)
               {

                   int id=view.getId();

                   Article art = (Article) view.getTag();

                   long l=0;

                   //Log.v("ONCLICK"," 321 ARTICLE "+art.toString());

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
                   }
                   datasourceart.open();
                   majArticle(datasourceart,art,modeEnCours,lid);
                   datasourceart.close();

                   AfficheArticles();



               }

            });

            gabaritDet.addView(imgbuttexte);

            //gabaritDet.addView(texttv);

            /*
            tester statut dans liste ou achat en fonction du mode
             */
            if(bmodeliste)
            {
                if(art.getIdliste()!=0)
                {
                    btnach.setImageResource(R.drawable.delete9);
                }else
                {
                    btnach.setImageResource(R.drawable.ajout5);

                }
                //Log.v("MAIN ACTIVITY","MODE 586 "+MySQLiteHelper.PARAM_MODEENCOURS_LISTE);


            }else{
                if(art.getEstachete()==1)
                {
                    btnach.setImageResource(R.drawable.delete9);

                }else
                {
                    btnach.setImageResource(R.drawable.basket);
                    iCptrArtAAcheter++;

                }
                //Log.v("MAIN ACTIVITY","MODE 642 "+MySQLiteHelper.PARAM_MODEENCOURS_ACHAT+" CPTR="+iCptrArtAAcheter);
            }
            gabaritDet.addView(btnach);

            // gabaritDet.setPadding(10,10,10,10);

            gabaritDet.setLayoutParams(lp2);

            gabaritDet.setMinimumWidth(500);

            gabaritDet.setMinimumHeight(100);

            gabaritListeDet.addView(gabaritDet);

        }

        datasourceart.close();

        if(iCptrArtAAcheter==0 & !bmodeliste)
        {
           //Toast.makeText(MainActivity.this, " NBARTICLES= "+iCptrArtAAcheter, Toast.LENGTH_LONG).show();

            //msg_courses_terminees
            //imgbuttexte
            int w=700;
            //OrientationHelper.HORIZONTAL

            int oi = getResources().getConfiguration().orientation ;
            //Log.v("MAIN ACTIVITY","Portrait-517 OI="+oi);

            if (oi == 1)
            {
                w=330;//portrait
            }
            else
            {
                if (oi == 2)
                {
                    w=700;//paysage
                }
            }
            //

            /*final Drawable drawable = getDrawable(R.drawable.warning);
            //final Drawable drawable = R.drawable.ajout;

            ImageView image = new ImageView(context);

            image.setImageDrawable(drawable);

            gabaritListeDet.addView(image);*/
           // image.setImageDrawable(R.drawable.warning);

            TextView texttv = new TextView(context);

            texttv.setX(5);

            texttv.setText(R.string.msg_courses_terminees);
            texttv.setTextAlignment(TEXT_ALIGNMENT_CENTER);


            texttv.setHeight(100);

            texttv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);

            //texttv.setTextSize(500,40);
            //setFont(textView3,"CURSTOM-FONT2.ttf");
            texttv.setTextColor(Color.BLUE);

            // setFont(texttv,"Roboto-Light.ttf");
            setFont(texttv,"RobotoCondensed-Bold.ttf");
            texttv.setWidth(w);
            gabaritListeDet.addView(texttv);
        }

        LinearLayoutlisteproduits.addView(gabaritListeDet);

        LinearLayoutlisteproduits.setScrollContainer(true);

        //Log.v("MAIN","566");

        //LinearLayoutlisteproduits.setLayoutParams(lp3);

        // LinearLayoutlisteproduits.setLayoutParams(lpfill);
        //wrap content height LinearLayoutlisteproduits.setLayoutParams(lp3);


        final ImageButton imagebuttonMode = (ImageButton) findViewById(R.id.imageBtnMode);

        imagebuttonMode.setOnClickListener(new OnClickListener() {

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

                mysqlhlpr.MiseAJourParam(view.getContext(),nParam);

                //Toast.makeText(MainActivity.this, " BUTTON MODE >"+modeEnCours, Toast.LENGTH_LONG).show();

                dtsparam.close();

                AfficheArticles();

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

        boolean ctrlact=false;;
        ctrlact=nParam.getBmodectrl();
        if(ctrlact)
        {
            imageBtnActiverModeCtrl.setBackgroundResource(R.drawable.checklist);

        }else
        {
            imageBtnActiverModeCtrl.setBackgroundResource(R.drawable.accept3);

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

        imageBtnActiverModeCtrl.setOnClickListener(new OnClickListener() {

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

                AfficheArticles();

            }
        });

        //Toast.makeText(MainActivity.this, " Fin Affichage Articles LISTE="+listeid+"FAMILLE="+idfamille+" MODE="+modeEnCours, Toast.LENGTH_LONG).show();

    }
    public void majArticle(ArticleDataSource datasourceart,Article art,String modeEnCours,long idliste) {
        /*
        tester mode liste mode achat
         */

        boolean modeachat=modeEnCours.equals(MySQLiteHelper.PARAM_MODEENCOURS_ACHAT);

       //Log.v("MAJARTICLE", " Article" + art.toString()+" MODEENCOURS >"+modeEnCours+"<");
        if(modeachat)
        {
           //Log.v("MAJARTICLE","MODE ACHAT 871 Estachete >"+art.getEstachete()+"<");

            if(art.getEstachete()==1)
                art.setEstachete(0);
            else
                art.setEstachete(1);

            datasourceart.updateArticle(art);

        }else
        {
           //Log.v("MAJARTICLE", String.format("MODE LISTE IDLISTE=%d", idliste));

            if(idliste>-1)
            {
                art.setIdliste(idliste);

                long lidliste=art.getIdliste();

                //Log.v("MAJARTICLE","836 ID LISTE="+lidliste);

                datasourceart.updateArticle(art);

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

            final Intent intent = new Intent(MainActivity.this, ParametresActivity.class);
            startActivity(intent);
            AfficheFamilles();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        //nParam

        if (item != null) {

            ParamDataSource dtsparam = new ParamDataSource(this);

            dtsparam.open();

            //MySQLiteHelper mysqlhlpr=new MySQLiteHelper(context);

            Param nParam= dtsparam.LectureParam();

            boolean bSaisieManuelle=false;
            boolean bSaisieManuelleArticles=false;
            boolean bSaisieManuelleFamilles=false;
            bSaisieManuelle=nParam.getBsaisiemanuelle();
            bSaisieManuelleArticles=nParam.getBsaisiemanart();
            bSaisieManuelleFamilles=nParam.getBsaisiemanfamille();

            dtsparam.close();

            int id = item.getItemId();

            if (id == R.id.nav_managefamilles) {
                /*
                familles
                 */
                if(!bSaisieManuelle )
                {
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
                }else
                {
                    if(bSaisieManuelleFamilles)
                    {
                        final Intent intent = new Intent(MainActivity.this, Familles.class);
                        startActivity(intent);
                        AfficheFamilles();
                    }else
                    {
                        AlertDialog.Builder adb = new AlertDialog.Builder(this);

                        adb.setIcon(android.R.drawable.ic_dialog_alert);

                        int s = R.string.lib_saismanfam_nonaccess;

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

            } else if (id == R.id.nav_managelistes) {

                final Intent intent = new Intent(MainActivity.this, Listes.class);
                startActivity(intent);
                //AfficheListe2();
                AfficheListes(Long.valueOf(0));
                //AfficheArticles();
                //finish();
                //startActivity(getIntent());
                return true;

            } else if (id == R.id.nav_managearticles) {
                /*
                articles
                 */
                if(bSaisieManuelle )
                {
                    if(bSaisieManuelleArticles)
                    {
                        final Intent intent = new Intent(MainActivity.this, Articles.class);
                        startActivity(intent);
                        AfficheArticles();
                    }else
                    {
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

                }else
                {
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

        AfficheListes(Long.valueOf(0));


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
    private void AfficheListes(Long listeid)
    {
        spinner_liste = (Spinner) findViewById(R.id.spinner_liste);

        List exemple = new ArrayList();

        exemple.add("Choisir liste");

        final ListesDataSource listesDataSource = new ListesDataSource(this);
        listesDataSource.open();

        //listesDataSource.getDatabase().execSQL("DELETE FROM " + MySQLiteHelper.TABLE_LISTES);

        //listesDataSource.createListe("ma liste 1");

        List<Liste> listValuesListes = listesDataSource.getAllListes("");

        for (Liste lst : listValuesListes)
        {
            exemple.add(lst.getLibelle());

        }
        listesDataSource.close();

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


        spinner_liste.removeAllViewsInLayout();
        spinner_liste.destroyDrawingCache();
        spinner_liste.animate();

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

                //Log.v("MAIN ONITEMSELECTED","141 listeid="+id);
                if(id!=-1)
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

        adapter.notifyDataSetChanged();


    }
}

class SpinnerListeListener implements OnItemSelectedListener, OnItemClickListener {

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

class SpinnerFamilleListener implements OnItemSelectedListener, OnItemClickListener {

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