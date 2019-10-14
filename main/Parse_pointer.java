import java.util.ArrayList;

public class Parse_pointer {

    public Parse_pointer padre;
    public String type;
    public String value;
    public ArrayList<Parse_pointer> children = new ArrayList<>();
    
    public Parse_pointer(Parse_pointer padre, String type, String value){
        this.padre = padre;
        this.type = type;
        this.value = value;
    }
    
}
