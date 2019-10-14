package parser;

/**
 *
 * @author joseg
 */
public class Semantic {
    
    public static void main(String[] args){
        recorrer(parser3.father);
    }
    
    public static void recorrer(parse_pointer padre){       
        if(padre.children.size() > 0){
            for(int i = 0; i < padre.children.size(); i++){
                if(padre.children.get(i).children.size() > 0){
                    recorrer(padre.children.get(i));
                }
            }
        }
    }
    
    public static void declaraciones(parse_pointer padre){
        
    }
    
}
