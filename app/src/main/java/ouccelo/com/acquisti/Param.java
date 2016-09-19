package ouccelo.com.acquisti;

import android.util.Log;

public class Param {

	private long id;
	private int versionbd;
	private String modeencours;
	private boolean bmodectrl;
    private Long listeencours;
    private Long familleEnCours;

    private boolean brecovocale;
    private boolean bgestfamart;

    private boolean bsaisiemanuelle;
    private boolean bsaisiemanart;
    private boolean bsaisiemanfamille;
    private boolean bsaisiedetailart;
    private boolean bsaisiedetailartqte;
    private boolean bsaisiedetailartpuht;
    private boolean bsaisiedetailarttva;
    private boolean bsaisiedetailartputtc;
//COLUMN_PARFILTRELISTE
    private boolean bfiltreliste;


	public Param(){
        long l=0;
        this.listeencours=l;
        this.familleEnCours=l;
        this.bfiltreliste=false;

    }

    public Param(long id, int versionBd,String modeencours) {
        this.id = id;
        this.versionbd = versionBd;
        this.modeencours=modeencours;
        this.bfiltreliste=false;
    }
    /*
	// Database creation sql statement
    private static final String TABLE_CREATE_PARAM = "create table "
            + TABLE_PARAM + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + PARAM_COLUMN_VBD + " integer not null,"
            + PARAM_COLUMN_MODEENCOURS + " text not null,"
            + PARAM_COLUMN_MODECONTROLE + " LONG DEFAULT 0,"
            + PARAM_COLUMN_LISTEENCOURS + " LONG DEFAULT 0,"
            + PARAM_COLUMN_FAMENCOURS + " LONG DEFAULT 0"
            +");";
     */
    public Param(long id, int versionBd,String modeencours,int imodectrl,long llisteid,long lfamid) {
        this.id = id;
        this.versionbd = versionBd;
        this.modeencours=modeencours;
        this.bmodectrl=(imodectrl==1);
        this.listeencours=llisteid;
        this.familleEnCours=lfamid;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getversionBd() {
		return versionbd;
	}

	public void setversionBd(int versionBd) {
		this.versionbd = versionBd;
	}

	public void setModeencours(String mc)
	{
        //Log.v("CLASS PARAM","61 setModeencours="+mc);

        this.modeencours=mc.toUpperCase().trim();
	}

	public String getModeencours()
	{
        //Log.v("CLASS PARAM","68 getModeencours="+this.modeencours);

        return this.modeencours;
	}

    public void setBmodectrl(boolean bctrl)
    {
        this.bmodectrl=bctrl;
    }
    public boolean getBmodectrl()
    {
        return this.bmodectrl;
    }

    public void setBrecovocale(boolean bctrl)
    {
        this.brecovocale=bctrl;
    }
    public boolean getBrecovocale()
    {
        return this.brecovocale;
    }



    public void setBsaisiemanuelle(boolean bctrl)
    {
        if(bctrl)
        {
            //Log.v("PARAMETRE 114"," SAISIE MANUELLE OUI");

        }else
        {
            //Log.v("PARAMETRE 118"," SAISIE MANUELLE NON");

        }

        this.bsaisiemanuelle=bctrl;
    }
    public boolean getBsaisiemanuelle()
    {
        //Log.v("PARAMETRE 126"," LECTURE SAISIE MANUELLE "+this.bsaisiemanuelle);
        return this.bsaisiemanuelle;
    }

    public void setBgestfamart(boolean bctrl)
    {
        this.bgestfamart=bctrl;
    }
    public boolean getBgestfamart()
    {
        return this.bgestfamart;
    }

    public void setBsaisiemanart(boolean bctrl)
    {
        if(bctrl)
        {
            //Log.v("PARAMETRE 143"," SAISIE MANUELLE ARTICLE OUI");

        }else
        {
            //Log.v("PARAMETRE 147"," SAISIE MANUELLE ARTICLE NON");

        }
        this.bsaisiemanart=bctrl;
    }
    public boolean getBsaisiemanart()
    {

        //Log.v("PARAMETRE 155"," LECTURE SAISIE MANUELLE ARTICLE "+this.bsaisiemanart);
        return this.bsaisiemanart;
    }

    public void setBsaisiemanfamille(boolean bctrl)
    {
        this.bsaisiemanfamille=bctrl;
    }
    public boolean getBsaisiemanfamille()
    {
        return this.bsaisiemanfamille;
    }

    public void setBsaisiedetailart(boolean bctrl)
    {
        if(bctrl)
        {
            //Log.v("PARAMETRE 161"," SAISIE DETAILLEE OUI");

        }else
        {
            //Log.v("PARAMETRE 165"," SAISIE DETAILLEE NON");

        }
        this.bsaisiedetailart=bctrl;
    }
    public boolean getBsaisiedetailart()
    {
        //Log.v("PARAMETRE 172"," LECTURE SAISIE DETAILLEE "+this.bsaisiedetailart);

        return this.bsaisiedetailart;
    }
    public void setBsaisiedetailartqte(boolean bctrl)
    {
        this.bsaisiedetailartqte=bctrl;
    }
    public boolean getBsaisiedetailartqte()
    {
        return this.bsaisiedetailartqte;
    }

    public void setBsaisiedetailartpuht(boolean bctrl)
    {
        this.bsaisiedetailartpuht=bctrl;
    }
    public boolean getBsaisiedetailartpuht()
    {
        return this.bsaisiedetailartpuht;
    }
    public void setBsaisiedetailarttva(boolean bctrl)
    {
        this.bsaisiedetailarttva=bctrl;
    }
    public boolean getBsaisiedetailarttva()
    {
        return this.bsaisiedetailarttva;
    }
    public void setBsaisiedetailartputtc(boolean bctrl)
    {
        this.bsaisiedetailartputtc=bctrl;
    }
    public boolean getBsaisiedetailartputtc()
    {
        return this.bsaisiedetailartputtc;
    }


    public void setBfiltreliste(boolean bctrl)
    {
        this.bfiltreliste=bctrl;
    }
    public boolean getBfiltreliste()
    {
        return this.bfiltreliste;
    }

	public Long getListeEnCours()
	{
        return this.listeencours;
	}
	public void setListeencours(Long liste)
	{
		this.listeencours=liste;
	}
/*
Mémorise famille selectinnonée

 */
	public Long getFamilleEnCours()
	{
		//Log.v("CLASS PARAM","62 GET IDFAMILLE="+this.familleEnCours.toString());
		return this.familleEnCours;

	}
	public void setFamilleEnCours(Long familleID)
	{
        this.familleEnCours=familleID;
        //Log.v("CLASS PARAM","74 PUT IDFAMILLE="+this.getFamilleEnCours().toString());

	}

	@Override
	public String toString() {
		return "version bd:"+this.versionbd+" mode en cours :"+this.modeencours+" familleEnCours="+this.familleEnCours;
	}

}
