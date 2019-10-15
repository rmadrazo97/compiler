import java.util.UUID;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

/**
 *
 * @author joseg
 */
public class Semantic {
    public static int scope = 0;
    
    public static void main(String[] args) {

        
        try{
            Parser parser = new Parser();
            parser.main(new String[0]);      
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error.");
        }
        
        recorrer(Parser.father);
        
    }
    
    public static void recorrer(Parse_pointer padre){       
        scopef(padre);
        
        if(padre.children.size() > 0){            
            for(int i = 0; i < padre.children.size(); i++){
                if (padre.children.get(i).value.equals("type") && padre.children.get(i+1).value.equals("id") ){
                    if (padre.value.equals("var_decl")){
                        // insert to table. 
                        insertId_var_decl(padre);

                    }else {
                        // insertar el siguiente type y el siguiente id a la tabla; 
                        insertId(padre, i);
                    }
                }
                recorrer(padre.children.get(i));
            }
        }
    }
    
    public static void insertId_var_decl(Parse_pointer padre){
        for(int i = 1; i < padre.children.size(); i++){
            if(!padre.children.get(i).value.equals(";") && !padre.children.get(i).value.equals(",") ){
                // insert to table
                String iden = UUID.randomUUID().toString();
                System.out.printf("%30s%7s%7s%15s\n", iden ,padre.children.get(0).children.get(0).value,padre.children.get(i).children.get(0).value,scope);
            }
        }
    }

    public static void insertId(Parse_pointer padre, int contador){
        String iden = UUID.randomUUID().toString();
        if(padre.value.equals("method_decl_alt")){
            System.out.printf("%30s,%7s%7s%15s\n", iden,padre.children.get(contador).children.get(0).value,padre.children.get(contador+1).children.get(0).value,scope + 1);
        }else{
            System.out.printf("%30s%7s%7s%15s\n", iden,padre.children.get(contador).children.get(0).value,padre.children.get(contador+1).children.get(0).value,scope);
        }

    }
    
    public static void scopef (Parse_pointer padre){
        if (padre.value.equals("block")){
            scope +=1;
        }
    }
    
}
