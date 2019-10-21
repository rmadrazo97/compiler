import java.util.ArrayList;
import java.util.UUID;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;


/**
 *
 * @author joseg
 */
public class Semantic {
    public static int scope = 0;
    public static ArrayList<String> toWrite = new ArrayList<String>();
    
    public static void main(String[] args){

        try {
            Parser parser = new Parser();
            parser.main(new String[0]);
        } catch (FileNotFoundException ex) {
            System.err.println("Error.");
        }

        Block scope0 = new Block(Parser.father);
        recorrer(father, scope0);

    }

    public static void recorrer(Parse_pointer padre, Block padreb){       
        
        Block valor = scopef(padre, padreb);
        Block siguiente;
        if(valor == null){
            siguiente = padreb;
        }else{
            siguiente = valor;
        }
        
        if(padre.children.size() > 0){            
            for(int i = 0; i < padre.children.size(); i++){
                if (padre.children.get(i).value.equals("type") && padre.children.get(i+1).value.equals("id") ){
                    if (padre.value.equals("var_decl")){
                        //HAY QUE REVISAR UNIQUENESS 
                        insertId_var_decl(padre , siguiente);
                    }else {
                        // insertar el siguiente type y el siguiente id a la tabla; 
                        //HAY QUE REVISAR UNIQUENESS 
                        insertId(padre, i , siguiente);
                    }
                }
                if(padre.children.get(i).value.equals("location")){
                    siguiente.utilizaciones.add(padre.children.get(i).children.get(0).children.get(0).value);
                }
                recorrer(padre.children.get(i), siguiente);
            }
        }
    }
    
    public static void insertId_var_decl(Parse_pointer padre, Block bloque){
        for(int i = 1; i < padre.children.size(); i++){
            if(!padre.children.get(i).value.equals(";") && !padre.children.get(i).value.equals(",") ){
                // insert to table
                String iden = UUID.randomUUID().toString();
                ArrayList<String> insertar = new ArrayList<>();
                insertar.add(padre.children.get(0).children.get(0).value);
                insertar.add(padre.children.get(i).children.get(0).value);
                bloque.declaraciones.add(insertar);
                System.out.printf("%30s%7s%7s%7s\n", iden ,padre.children.get(0).children.get(0).value,padre.children.get(i).children.get(0).value,scope);
            }
        }
    }

    public static void insertId(Parse_pointer padre, int contador, Block bloque){
        String iden = UUID.randomUUID().toString();
        ArrayList<String> insertar = new ArrayList<>();
        insertar.add(padre.children.get(contador).children.get(0).value);
        insertar.add(padre.children.get(contador+1).children.get(0).value);
        bloque.declaraciones.add(insertar);
        if(padre.value.equals("method_decl_alt")){
            System.out.printf("%30s,%7s%7s%15s\n", iden,padre.children.get(contador).children.get(0).value,padre.children.get(contador+1).children.get(0).value,scope + 1);
        }else{
            System.out.printf("%30s%7s%7s%15s\n", iden,padre.children.get(contador).children.get(0).value,padre.children.get(contador+1).children.get(0).value,scope);
        }

    }
    
    public static Block scopef (Parse_pointer padre, Block padreb){
        if (padre.value.equals("block")){
            scope +=1;
            Block retornar = new Block(padre);
            retornar.padre = padreb;
            padreb.hijos.add(retornar);
            return retornar;
        }else{
            return null;
        }
    }
    
}
