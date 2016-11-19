package acquisti.com.ouccelo.aquistiv2;

/**
 * Created by francois on 19/09/16.
 */
public class Liste {
    private long id;
    private String libelle;

    public Liste(){}

    public Liste(Long id,String sLibelle){

        this.id = id;
        this.libelle=sLibelle;
    }
    public Liste(String sLibelle){
        this.libelle=sLibelle;
    }

    public void setId(long id)
    {
        this.id=id;
    }
    public long getId()
    {
        return this.id;
    }

    public void setLibelle(String sLibelle)
    {
        this.libelle=sLibelle;
    }
    public String getLibelle()
    {
        return this.libelle;
    }

    @Override
    public String toString() {
        return this.libelle;
    }

}
