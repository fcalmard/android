package acquisti.com.ouccelo.aquistiv2;

public class Article {

	private long id;
	private String libelle;
	private long FamilleId;
    private float pu=0;
    private float qte=1;
	private String unite="";//à la pièce, au kg,...";

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
    }
    public Article(long id, String libelle,long FamilleId,long idliste,boolean estachete,float pu,String sunite) {
        this.id = id;
        this.libelle = libelle;
        this.FamilleId = FamilleId;
        this.pu=pu;
        this.qte=1;
        this.unite=sunite;

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

    public void setPu(float pu)
    {
        this.pu=pu;
    }
    public float getPu()
    {
        return this.pu;
    }

    public void setUnite(String sunite)
    {
        this.unite=sunite;
    }
    public String getUnite()
    {
        return this.unite;
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

        return sl;
	}

}
