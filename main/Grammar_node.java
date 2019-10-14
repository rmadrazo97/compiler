public class Grammar_node {
    
    boolean es_padre;
    boolean es_terminal;
    String value;
    
    public Grammar_node(boolean es_padre, boolean es_terminal, String value){
        this.es_padre = es_padre;
        this.es_terminal = es_terminal; 
        this.value = value;
    }
    
}
