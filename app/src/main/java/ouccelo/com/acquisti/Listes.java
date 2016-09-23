package ouccelo.com.acquisti;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Comparator;
import java.util.List;


public class Listes extends ListActivity implements View.OnClickListener{

        /*
    bdd LISTES
     */

    private ListesDataSource datasource;

    // affichage
    private ImageButton imagebutton1;
    private ImageButton imagebutton2;
    private ImageButton imagebutton3;
    private ImageButton imagebutton4;
    EditText editText;

    Liste listeSelected = null;

    // adapter
    private ArrayAdapter<Liste> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ListView lv1 ;
        String lv_arr[]={"Android","iPhone","BlackBerry","AndroidPeople","Symbian", "Saint-malo"};

     //   lv1=(ListView)findViewById(R.id.listView1);
        //lv1=(ListView)findViewById(R.id.idlisteproduits);
        // By using setAdpater method in listview we an add string array in list.
        //lv1.setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , lv_arr));

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onStart() {
        super.onStart();

        listeSelected=null;

        // ouverture d'une connexion avec la bdd
        datasource = new ListesDataSource(this);
        datasource.open();

        // nos boutons

        imagebutton1 = (ImageButton) findViewById(R.id.imageButton1);
        imagebutton1.setOnClickListener((View.OnClickListener) this);
        imagebutton1.setImageResource(android.R.drawable.ic_input_add);

        imagebutton2 = (ImageButton) findViewById(R.id.imagebutton2);
        imagebutton2.setOnClickListener(this);
        imagebutton2.setEnabled(false);

        imagebutton3 = (ImageButton) findViewById(R.id.imageButton3);
        imagebutton3.setOnClickListener(this);
        imagebutton3.setEnabled(false);

        imagebutton4 = (ImageButton) findViewById(R.id.imageButton4);
        imagebutton4.setOnClickListener(this);
        imagebutton4.setEnabled(true);


        // notre champ de saisie

        editText = (EditText) findViewById(R.id.editText1);

        // on récupère les listes
        List<Liste> listValues = datasource.getAllListes(false);

        // on créé un adapter
        myAdapter = new ArrayAdapter<Liste>(this, R.layout.row_layout_lst,
                R.id.listTextListes, listValues);

       // myAdapter.clear();

        setListAdapter(myAdapter);


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.imageButton1) {
            if (editText.getText().length() > 0) {
                if (listeSelected != null) {
                    // UPDATE
                    listeSelected.setLibelle(editText.getText().toString());
                    datasource.updateListe(listeSelected);
                } else {
                    // CREATE
                    myAdapter.add(datasource.createListe(editText.getText()
                            .toString()));
                    editText.setText("");
                }
                myAdapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(this, "Pas de nom!",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

        }

        if (v.getId() == R.id.imagebutton2) {
            listeSelected = null;
            editText.setText("");
            imagebutton1.setImageResource(android.R.drawable.ic_input_add);
        }

        if (v.getId() == R.id.imageButton3) {
            if (listeSelected != null) {
                editText.setText("");
                imagebutton1.setImageResource(android.R.drawable.ic_input_add);

                datasource.deleteListe(listeSelected);
                myAdapter.remove(listeSelected);
                myAdapter.notifyDataSetChanged();
                listeSelected = null;
            }

        }

        //TRI
        if (v.getId() == R.id.imageButton4) {


            Drawable drawable = imagebutton4.getDrawable();

            if (drawable.getConstantState().equals(getResources().getDrawable(android.R.drawable.arrow_up_float).getConstantState())){
                //Do your work here StringDescComparator StringAscComparator
                imagebutton4.setImageResource(android.R.drawable.arrow_down_float);


                myAdapter.sort(StringDescComparator);
            }else {
                imagebutton4.setImageResource(android.R.drawable.arrow_up_float);

                myAdapter.sort(StringAscComparator);

            }
            listeSelected = null;

            editText.setText("");

            myAdapter.notifyDataSetChanged();

            imagebutton1.setImageResource(android.R.drawable.ic_input_add);


        }


    }

    @Override
    protected void onListItemClick(ListView list, View view, int position,
                                   long id) {
        super.onListItemClick(list, view, position, id);

        listeSelected = (Liste) getListView().getItemAtPosition(position);

        editText.setText(listeSelected.getLibelle());

        imagebutton1.setImageResource(android.R.drawable.ic_menu_edit);

        imagebutton2.setEnabled(true);
        imagebutton3.setEnabled(true);


    }

    public static Comparator<Liste> StringDescComparator = new Comparator<Liste>() {

        public int compare(Liste app1, Liste app2) {

            String stringName1 = app2.getLibelle();
            String stringName2 = app1.getLibelle();

            return stringName2.compareToIgnoreCase(stringName1);
        }
    };

    public static Comparator<Liste> StringAscComparator = new Comparator<Liste>() {

        public int compare(Liste app1, Liste app2) {

            String stringName1 = app1.getLibelle();
            String stringName2 = app2.getLibelle();

            return stringName2.compareToIgnoreCase(stringName1);
        }
    };

}
