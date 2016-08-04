package eu.domibus.demoimageweb;

/**
 * Created by nguyhoa on 29/07/2016.
 */
public class PartyID {
    private String id;
    private String value;
    public PartyID(String id){
        this.id=id;
        this.value=value;
    }

    public PartyID(String id, String value){
        this.id=id;
        this.value=value;


    }
    public String getId(){
        return id;
    }

    public String getValue(){
        return value;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
