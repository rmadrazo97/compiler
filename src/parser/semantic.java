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
        
        scope(padre);
        
        if(padre.children.size() > 0){            
            for(int i = 0; i < padre.children.size(); i++){
                if(padre.children.get(i).children.size() > 0){
                    
                    recorrer(padre.children.get(i));
                    
                    if (padre.children.get(i).value.equals("type") && padre.children.get(i+1).value.equals("id") ){
                        
                        if (padre.value.equals("var_decl")){
                            // insert to table. 
                            // insertar todos los id's separados por comas hasta ;
                            insertId_var_decl(padre, scope);

                        } else {
                            // insertar el siguiente type y el siguiente id a la tabla; 
                            insertId(padre, scope);
                        }
                    }
                }

            }
        }
    }
    
    public static void declaraciones(parse_pointer padre){
        
        for(int i = 0; i < padre.children.size(); i++){
            if(padre.children.get(i).puntero.value.equals("id")){

                // push to table.
               

            } else if (padre.children.get(i).puntero.value.equals(",")){

                // ignore
                continue;

            } else if (padre.children.get(i).puntero.value.equals(";")){
                
                // break
                break;
            }
        } 

    }

    public static void insertId_var_decl(parse_pointer padre, scope){
        for(int i = 1; i < padre.children.size(); i++){
            
            if(!padre.children.get(i).value.equals(";") && !padre.children.get(i).value.equals(",") ){
                
                // insert to table
                // type => padre.children.get(0);
                // value => padre.children.get(i);
                // table -> array_list
                

            } else {
                continue;
            }
        }
    }

    public static void InsertId (parse_pointer padre, scope){

    }

    public static void scope (parse_pointer padre){
        if (padre.value.equals("block")){
            scope +=1;
        }
    }
    
}
