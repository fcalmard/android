package acquisti.com.ouccelo.aquistiv2;

/**
 * Created by admin on 02/11/16.
 */

public class listeart {
    private long id;
    private long idlst;
    private long idart;
    private String type;// LISTE ACHAT


    public listeart(){}

    public listeart(Long id,Long idlst,Long idart){

        this.id = id;
        this.idart = idart;
        this.idlst = idlst;
    }


    public void setId(long id)
    {
        this.id=id;
    }
    public long getId()
    {
        return this.id;
    }

    public void setIdlst(long idlst)
    {
        this.idlst=idlst;
    }
    public long getIdlst()
    {
        return this.idlst;
    }

    public void setIdart(long idart)
    {
        this.idart=idart;
    }
    public long getIdart()
    {
        return this.idart;
    }

    public void setType(String type)
    {
        this.type=type;
    }
    public String getType()
    {
        return this.type;
    }


    @Override
    public String toString() {
        return this.id+" lst="+this.idlst+" art="+this.idart+" type >"+this.type+"<"+this.type.toString().length();
    }
}
