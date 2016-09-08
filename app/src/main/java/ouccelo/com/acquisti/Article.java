package ouccelo.com.acquisti;

public class Article {

	private long id;
	private String libelle;
	private long FamilleId;
	private long idliste=0;
    private boolean estachete=false;
    private float puht=0;
    private float txtva=20;//taux par défault
    private float puttc=0;
    private float qte=1;

	public Article(){}

    public Article(String libelle,long FamilleId) {
        this.libelle = libelle;
        this.FamilleId = FamilleId;
    }
    public Article(long id, String libelle,long FamilleId) {
        this.id = id;
        this.libelle = libelle;
        this.FamilleId = FamilleId;
    }
    public Article(long id, String libelle,long FamilleId,long idliste,boolean estachete) {
        this.id = id;
        this.libelle = libelle;
        this.FamilleId = FamilleId;
        this.idliste=idliste;
        this.estachete=estachete;
    }
    public Article(long id, String libelle,long FamilleId,long idliste,boolean estachete,float puht,float txtva,float puttc) {
        this.id = id;
        this.libelle = libelle;
        this.FamilleId = FamilleId;
        this.idliste=idliste;
        this.estachete=estachete;
        this.puht=puht;
        this.txtva=txtva;
        this.puttc=puttc;
        this.qte=1;

    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public void setIdliste(long id)
	{
		this.idliste=id;
	}
	public long getIdliste()
	{
		return this.idliste;
	}
    public void setEstachete(int estachete)
    {
        this.estachete=false;

        if(estachete==1)
        {
            this.estachete=true;

        }
    }
    public int getEstachete()
    {
        if(this.estachete)
            return 1;
        else
            return 0;

    }

    public void setPuht(float puht)
    {
        this.puht=puht;
    }
    public float getPuht()
    {
        return this.puht;
    }

    public void setTxtva(float txtva)
    {
        this.txtva=txtva;
    }
    public float getTxtva()
    {
        return this.txtva;
    }

    public void setPuttc(float puttc)
    {
        this.puttc=puttc;
    }
    public float getPuttc()
    {
        return this.puttc;
    }

    public void setQte(float qte)
    {
        this.qte=qte;
    }
    public float getQte()
    {
        return this.qte;
    }

    public void setFamilleId(long FamilleId) {
		this.FamilleId = FamilleId;
	}

    public long getFamilleId() {
        return this.FamilleId;
    }

	@Override
	public String toString() {
		String sl=" "+this.libelle;

        //sl=sl+" IDLISTE="+this.idliste;
        /*


        sl=sl +" "+getId();
        */

        //sl=sl+getFamilleId();

        //sl=sl+" "+getIdliste();
        return sl;
	}

}
