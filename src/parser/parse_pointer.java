package parser;

import java.util.ArrayList;

public class parse_pointer {
    public Grammar_node puntero;
    public ArrayList<Grammar_node> children = new ArrayList<>();
    
    public parse_pointer(Grammar_node puntero){
        this.puntero = puntero;
    }
    
}
