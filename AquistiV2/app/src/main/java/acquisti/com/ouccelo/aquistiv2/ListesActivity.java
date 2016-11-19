package acquisti.com.ouccelo.aquistiv2;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
//AppCompatActivity
public class ListesActivity extends  ListActivity implements View.OnClickListener{

    Liste listeSelected = null;

    // adapter
    private ArrayAdapter<Liste> myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listes_activity);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Log.v("LISTES","38");

    }

    @Override
    public void onStart() {

        listeSelected=null;

        super.onStart();
        AfficheListes();

        //Log.v("LISTES","53");

        FloatingActionButton actbutreset= (FloatingActionButton) findViewById(R.id.floatingActionButtonReinit);
        FloatingActionButton actbutdelete= (FloatingActionButton) findViewById(R.id.floatingActionButtonDelete);

        //actbutadd.setEnabled(false);
        actbutreset.setEnabled(false);
        actbutdelete.setEnabled(false);

    }

    @Override
    protected void onListItemClick(ListView list, View view, int position,
                                   long id) {
        super.onListItemClick(list, view, position, id);

        listeSelected = (Liste) getListView().getItemAtPosition(position);

        EditText edittext=(EditText)findViewById(R.id.editText1);

        edittext.setText(listeSelected.getLibelle());

        ImageButton imgbutajout= (ImageButton) findViewById(R.id.floatingActionButtonEdit);
        ImageButton imgbutreinit= (ImageButton) findViewById(R.id.floatingActionButtonReinit);
        ImageButton imgbutdelete= (ImageButton) findViewById(R.id.floatingActionButtonDelete);

        imgbutajout.setImageResource(android.R.drawable.ic_menu_edit);

        imgbutreinit.setEnabled(true);

        imgbutdelete.setEnabled(true);


    }


    private void AfficheListes()
    {

        List<Liste> Listes = new ArrayList<Liste>();

        ListesDataSource lstdts = new ListesDataSource(this);

        lstdts.open();

        Listes=lstdts.getAllListes("");



        // notre champ de saisie

        //editText = (EditText) findViewById(R.id.editText1);

        // on récupère les listes
        List<Liste> listValues = lstdts.getAllListes(false);

        // on créé un adapter
        myAdapter = new ArrayAdapter<Liste>(this, R.layout.row_layout_lst,
                R.id.list, listValues);

        setListAdapter(myAdapter);


        Toast.makeText(this," nb de listes affichées"+Listes.size(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {

        FloatingActionButton actbutadd= (FloatingActionButton) findViewById(R.id.floatingActionButtonEdit);

        ListesDataSource lstdts = new ListesDataSource(this);

        lstdts.open();

        EditText edittext = (EditText) findViewById(R.id.editText1);

        if (v.getId() == R.id.floatingActionButtonEdit) {
            if (edittext.getText().length() > 0) {
                if (listeSelected != null) {
                    // UPDATE
                    listeSelected.setLibelle(edittext.getText().toString());
                    lstdts.updateListe(listeSelected);
                } else {
                    // CREATE
                    myAdapter.add(lstdts.createListe(edittext.getText()
                            .toString()));
                    edittext.setText("");
                }
                myAdapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(this, "Pas de nom!",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

        }

        if (v.getId() == R.id.floatingActionButtonReinit) {
            listeSelected = null;
            edittext.setText("");
            actbutadd.setImageResource(android.R.drawable.ic_input_add);
        }

        if (v.getId() == R.id.floatingActionButtonDelete) {
            if (listeSelected != null) {
                edittext.setText("");
                actbutadd.setImageResource(android.R.drawable.ic_input_add);

                lstdts.deleteListe(listeSelected);
                myAdapter.remove(listeSelected);
                myAdapter.notifyDataSetChanged();
                listeSelected = null;
            }

        }


    }
}
