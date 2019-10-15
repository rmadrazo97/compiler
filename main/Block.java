import java.util.ArrayList;

public class Block {
    Parse_pointer bloque; 
    Block padre;
    ArrayList<Block> hijos;
    ArrayList<ArrayList<String>> declaraciones; 
    ArrayList<String> utilizaciones;
    public Block(Parse_pointer bloque){
        this.bloque = bloque;
        this.padre = this;
        this.hijos = new ArrayList<>();
        this.declaraciones = new ArrayList<>();
        this.utilizaciones = new ArrayList<>();
        
        
    }
}
