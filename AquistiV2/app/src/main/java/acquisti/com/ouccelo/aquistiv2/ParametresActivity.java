package acquisti.com.ouccelo.aquistiv2;


import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class ParametresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametres);

        ParamDataSource pdts = new ParamDataSource(this);
        pdts.open();

        //MySQLiteHelper mssql = new MySQLiteHelper(this);

        Param param = pdts.LectureParam();

        CheckBox checkBox = (CheckBox) findViewById(R.id.optrecovocale);
        checkBox.setChecked(param.getBrecovocale());

        checkBox = (CheckBox) findViewById(R.id.optfamprod);
        checkBox.setChecked(param.getBgestfamart());

        checkBox = (CheckBox) findViewById(R.id.optsaisiemanuelle);
        checkBox.setChecked(param.getBsaisiemanuelle());

        checkBox = (CheckBox) findViewById(R.id.optsaismanart);
        checkBox.setChecked(param.getBsaisiemanart());

        checkBox = (CheckBox) findViewById(R.id.optsaismanfam);
        checkBox.setChecked(param.getBsaisiemanfamille());

        checkBox = (CheckBox) findViewById(R.id.optgestdetprod);
        checkBox.setChecked(param.getBsaisiedetailart());

        checkBox = (CheckBox) findViewById(R.id.optsaisqte);
        checkBox.setChecked(param.getBsaisiedetailartqte());

        //checkBox = (CheckBox) findViewById(R.id.optsaispuht);
        //checkBox.setChecked(param.getBsaisiedetailartpuht());

        //checkBox = (CheckBox) findViewById(R.id.optsaistva);
        //checkBox.setChecked(param.getBsaisiedetailarttva());

        checkBox = (CheckBox) findViewById(R.id.optsaisputtc);
        checkBox.setChecked(param.getBsaisiedetailartputtc());

    }

    public void close(View view)
    {
        finish();
    }

    public void enreg(View view)
    {
        // checkBoxRecoVocal= (CheckBox)R.id.optrecovocale;

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ParamDataSource pdts = new ParamDataSource(this);
        pdts.open();

        //MySQLiteHelper mssql = new MySQLiteHelper(this);

        Param param = pdts.LectureParam();

        CheckBox checkBox = (CheckBox) findViewById(R.id.optrecovocale);
        boolean optreco=checkBox.isChecked();
        param.setBrecovocale(optreco);

        checkBox = (CheckBox) findViewById(R.id.optfamprod);
        param.setBgestfamart(checkBox.isChecked());

        checkBox = (CheckBox) findViewById(R.id.optsaisiemanuelle);
        Log.v("PARAM ACTIVITY"," isChecked="+checkBox.isChecked());
        param.setBsaisiemanuelle(checkBox.isChecked());

        checkBox = (CheckBox) findViewById(R.id.optsaismanart);
        param.setBsaisiemanart(checkBox.isChecked());

        checkBox = (CheckBox) findViewById(R.id.optsaismanfam);
        param.setBsaisiemanfamille(checkBox.isChecked());

        checkBox = (CheckBox) findViewById(R.id.optgestdetprod);
        param.setBsaisiedetailart(checkBox.isChecked());

        checkBox = (CheckBox) findViewById(R.id.optsaisqte);
        param.setBsaisiedetailartqte(checkBox.isChecked());

        checkBox = (CheckBox) findViewById(R.id.optsaisputtc);
        param.setBsaisiedetailartputtc(checkBox.isChecked());

        //checkBox = (CheckBox) findViewById(R.id.optsaistva);
        //param.setBsaisiedetailarttva(checkBox.isChecked());

        //checkBox = (CheckBox) findViewById(R.id.optsaisputtc);
        //param.setBsaisiedetailartputtc(checkBox.isChecked());

        pdts.updateParam(param);

    }

    public void activeroptions(View view)
    {
        boolean activation;
        activation=false;
        if(view.getId()==R.id.optactivertoutes)
        {
            CheckBox checkBox = (CheckBox) findViewById(R.id.optdesactivertoutes);
            checkBox.setChecked(false);
            activation=true;


        }
        if(view.getId()==R.id.optdesactivertoutes)
        {
            CheckBox checkBox = (CheckBox) findViewById(R.id.optactivertoutes);
            checkBox.setChecked(false);
            activation=false;

        }
        CheckBox checkBox = (CheckBox) findViewById(R.id.optrecovocale);
        checkBox.setChecked(activation);

        checkBox = (CheckBox) findViewById(R.id.optfamprod);
        checkBox.setChecked(activation);
        checkBox = (CheckBox) findViewById(R.id.optsaisiemanuelle);
        checkBox.setChecked(activation);
        checkBox = (CheckBox) findViewById(R.id.optsaismanart);
        checkBox.setChecked(activation);
        checkBox = (CheckBox) findViewById(R.id.optsaismanfam);
        checkBox.setChecked(activation);
        checkBox = (CheckBox) findViewById(R.id.optgestdetprod);
        checkBox.setChecked(activation);


        checkBox = (CheckBox) findViewById(R.id.optgestdetprod);
        checkBox.setChecked(activation);
        checkBox = (CheckBox) findViewById(R.id.optsaisqte);
        checkBox.setChecked(activation);
        checkBox = (CheckBox) findViewById(R.id.optsaisputtc);
        checkBox.setChecked(activation);
        //checkBox = (CheckBox) findViewById(R.id.optsaistva);
        //checkBox.setChecked(activation);
        //checkBox = (CheckBox) findViewById(R.id.optsaisputtc);
        //checkBox.setChecked(activation);

    }

    public void activeroptionsaisiedetaillee(View view)
    {
        boolean activation;
        switch (view.getId())
        {
            case R.id.optsaisiemanuelle:
                CheckBox check = (CheckBox) findViewById(R.id.optsaisiemanuelle);
                activation=check.isChecked();
                check = (CheckBox) findViewById(R.id.optsaismanart);
                check.setChecked(activation);
                check = (CheckBox) findViewById(R.id.optsaismanfam);
                check.setChecked(activation);
                break;
            case R.id.optsaismanart:
                check = (CheckBox) findViewById(R.id.optsaismanart);
                activation=check.isChecked();
                if(activation)
                {
                    check = (CheckBox) findViewById(R.id.optsaisiemanuelle);
                    check.setChecked(activation);
                }
                break;
            case R.id.optsaismanfam:
                check = (CheckBox) findViewById(R.id.optsaismanfam);
                activation=check.isChecked();
                if(activation)
                {
                    check = (CheckBox) findViewById(R.id.optsaisiemanuelle);
                    check.setChecked(activation);
                }
                break;
            case R.id.optdesactivertoutes:
                activation=false;

                check = (CheckBox) findViewById(R.id.optsaisiemanuelle);
                check.setChecked(activation);
                check = (CheckBox) findViewById(R.id.optsaismanfam);
                check.setChecked(activation);
                check = (CheckBox) findViewById(R.id.optsaisiemanuelle);
                check.setChecked(activation);

                check = (CheckBox) findViewById(R.id.optgestdetprod);
                check.setChecked(activation);

                break;
            case R.id.optgestdetprod:
                check = (CheckBox) findViewById(R.id.optgestdetprod);
                activation=check.isChecked();

                CheckBox checkBox = (CheckBox) findViewById(R.id.optsaisqte);
                checkBox.setChecked(activation);
                checkBox = (CheckBox) findViewById(R.id.optsaisputtc);
                checkBox.setChecked(activation);
                //checkBox = (CheckBox) findViewById(R.id.optsaisputtc);
                //checkBox.setChecked(activation);
                break;
            case R.id.optsaisqte:
                check = (CheckBox) findViewById(R.id.optsaisqte);
                activation=check.isChecked();
                if(activation)
                {
                    check = (CheckBox) findViewById(R.id.optgestdetprod);
                    check.setChecked(activation);
                }
                break;

            case R.id.optsaisputtc:
                check = (CheckBox) findViewById(R.id.optsaisputtc);
                activation=check.isChecked();
                if(activation)
                {
                    check = (CheckBox) findViewById(R.id.optgestdetprod);
                    check.setChecked(activation);
                }
                break;

            case R.id.optactivertoutes:
                activation=true;
                break;
        }


    }
}
