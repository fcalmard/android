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

public class FamillesActivity extends ListActivity implements View.OnClickListener {

    Famille familleselected =null;

    // adapter
    private ArrayAdapter<Famille> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familles);

    }

    @Override
    public void onStart() {

        familleselected=null;

        super.onStart();
        AfficheFamilles();

        //Log.v("LISTES","53");

        FloatingActionButton actbutreset= (FloatingActionButton) findViewById(R.id.floatingActionButtonReinit);
        FloatingActionButton actbutdelete= (FloatingActionButton) findViewById(R.id.floatingActionButtonDelete);

        //actbutadd.setEnabled(false);
        actbutreset.setEnabled(false);
        actbutdelete.setEnabled(false);

    }


    private void AfficheFamilles() {

        List<Famille> Listes = new ArrayList<Famille>();

        FamillesDataSource lstdts = new FamillesDataSource(this);

        lstdts.open();


        // notre champ de saisie

        //editText = (EditText) findViewById(R.id.editText1);

        // on récupère les listes
        List<Famille> listValues = lstdts.getAllFamilles("");
        Listes = lstdts.getAllFamilles("");

        // on créé un adapter
        myAdapter = new ArrayAdapter<Famille>(this, R.layout.row_layout_fam,
                R.id.list, listValues);

        // myAdapter.clear();

        setListAdapter(myAdapter);


        Toast.makeText(this, " nb de Familles affichées" + Listes.size(), Toast.LENGTH_SHORT).show();
    }
    public void close(View view)
    {
        finish();
    }


    @Override
    protected void onListItemClick(ListView list, View view, int position,
                                   long id) {
        super.onListItemClick(list, view, position, id);

        familleselected = (Famille) getListView().getItemAtPosition(position);

        EditText edittext=(EditText)findViewById(R.id.editText1);

        edittext.setText(familleselected.getLibelle());

        ImageButton imgbutajout= (ImageButton) findViewById(R.id.floatingActionButtonEdit);
        ImageButton imgbutreinit= (ImageButton) findViewById(R.id.floatingActionButtonReinit);
        ImageButton imgbutdelete= (ImageButton) findViewById(R.id.floatingActionButtonDelete);

        imgbutajout.setImageResource(android.R.drawable.ic_menu_edit);

        imgbutreinit.setEnabled(true);

        imgbutdelete.setEnabled(true);


    }
    @Override
    public void onClick(View v) {

        FloatingActionButton actbutadd= (FloatingActionButton) findViewById(R.id.floatingActionButtonEdit);

        FamillesDataSource lstdts = new FamillesDataSource(this);

        lstdts.open();

        EditText edittext = (EditText) findViewById(R.id.editText1);

        if (v.getId() == R.id.floatingActionButtonEdit) {
            if (edittext.getText().length() > 0) {
                if (familleselected != null) {
                    // UPDATE
                    familleselected.setLibelle(edittext.getText().toString());
                    lstdts.updateFamille(familleselected);
                    AfficheFamilles();
                } else {
                    // CREATE
                    myAdapter.add(lstdts.createFamille(edittext.getText()
                            .toString()));
                    edittext.setText("");
                    AfficheFamilles();

                }
                myAdapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(this, "Pas de nom!",
                        Toast.LENGTH_SHORT);
                toast.show();
            }

        }

        if (v.getId() == R.id.floatingActionButtonReinit) {
            familleselected = null;
            edittext.setText("");
            actbutadd.setImageResource(android.R.drawable.ic_input_add);
        }

        if (v.getId() == R.id.floatingActionButtonDelete) {
            if (familleselected != null) {
                edittext.setText("");
                actbutadd.setImageResource(android.R.drawable.ic_input_add);

                lstdts.deleteFamille(familleselected);
                myAdapter.remove(familleselected);
                myAdapter.notifyDataSetChanged();
                familleselected = null;

                AfficheFamilles();

            }

        }

    }
}
