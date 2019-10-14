package parser;

import sun.text.normalizer.SymbolTable;

/**
 *
 * @author joseg
 */
public class semantic {
    
    public static void main(String[] args){
        recorrer(parser3.father);
    }
    
    public static void recorrer(parse_pointer padre){       
        if(padre.children.size() > 0){
            declaraciones(padre);
            for(int i = 0; i < padre.children.size(); i++){
                if(padre.children.get(i).children.size() > 0){
                    recorrer(padre.children.get(i));
                }
            }
        }
    }
    
    public static void declaraciones(parse_pointer padre){
        if (padre.puntero.value.equals("type")){
            for(int i = 0; i < padre.children.size(); i++){
                if(padre.children.get(i).puntero.value.equals("id")){

                    // push to table.]
                    int newRow = symbolTbl.length;
                    symbolTbl[][].add() 

                } else if (padre.children.get(i).puntero.value.equals(",")){

                    // ignore

                } else if (padre.children.get(i).puntero.value.equals(";")){
                    // break

                }
            } 
        }
  
    }
    
}
