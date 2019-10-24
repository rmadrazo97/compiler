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

        Block scope0 = new Block(Parser.father); //Guarda el scope inicial (class)
        recorrer(father, scope0);               //father(primer nodo del srbol de parseo)
    }

    public static void recorrer(Parse_pointer padre, Block padreb){       
        
        Block valor = scopef(padre, padreb); // verifica si el padre que entro es un bloque
        Block siguiente;                        //almacena el siguiente, en caso el padre es = null
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

    public static void recorrer(parse_pointer nodo, Block siguiente_scope){
        //Revisar Scope 
        Block resultado_revision = check_scope(nodo, siguiente_scope);
        if(resultado_revision != null){
            siguiente_scope = resultado_revision;
        }
        
        if(nodo.children.size() > 0){            
            for(int i = 0; i < nodo.children.size(); i++){
                //revisar declaracion de variables 
                
                recorrer(nodo.children.get(i), siguiente_scope);
            }
        }
    }
    
    public static Block check_scope(parse_pointer nodo, Block scope_actual){
        if(nodo.value.equals("block")){
            Block nuevo_scope = new Block(nodo);
            nuevo_scope.padre = scope_actual;
            scope_actual.hijos.add(nuevo_scope);
            return nuevo_scope;
        }else{
            return null;
        }
    }

    public static void recorrer_scopes(Block scope_actual){
        //check var declarations from var_decl definition 
        check_var_decl(scope_actual);
        // check for field declarations 
        check_field_decl(scope_actual);
        // check for method declarations 
        check_method_decl(scope_actual);
        
        System.out.println(scope_actual.declaraciones);
        
        if(scope_actual.hijos.size() > 0){
            for(int i = 0; i < scope_actual.hijos.size(); i++){
                recorrer_scopes(scope_actual.hijos.get(i));
            }
        }
    }
    
    public static void check_var_decl(Block scope_actual){
        parse_pointer valor = scope_actual.scope_original;
        if(valor.value.equals("block")){
            for(int i = 0; i < valor.children.size(); i++){
                if(valor.children.get(i).value.equals("var_decl")){
                    parse_pointer type = valor.children.get(i).children.get(0);
                    ArrayList<parse_pointer> posibles = valor.children.get(i).children;
                    for(int j = 1; j < posibles.size(); j++){
                        if(!(posibles.get(j).value.equals(",") || posibles.get(j).value.equals(";"))){
                            ArrayList<String> temporal = new ArrayList<>();
                            temporal.add(type.children.get(0).value);
                            temporal.add(posibles.get(j).children.get(0).value);
                            scope_actual.declaraciones.add(temporal);
                        }
                    }
                }
            }
        }
    }

    public static void check_field_decl(Block scope_actual){
        if(scope_actual.scope_original.value.equals("program")){
            for(int i = 0; i < scope_actual.scope_original.children.size(); i++){
                //encontrar los field declarations
                parse_pointer valor = scope_actual.scope_original.children.get(i);
                if(valor.value.equals("field_decl")){
                    parse_pointer tipo = valor.children.get(0).children.get(0);
                    for(int j = 1; j < valor.children.size(); j++){
                        ArrayList<String> temporal = new ArrayList<>();
                        temporal.add(tipo.value);
                        if(valor.children.get(j).value.equals("field_decl_alt")){
                            for(int k = 0; k < valor.children.get(j).children.size(); k++){
                                parse_pointer valor_hijo = valor.children.get(j).children.get(k);
                                if(valor_hijo.value.equals("id")){
                                    temporal.add(valor_hijo.children.get(0).value);
                                }else{
                                    temporal.add(valor_hijo.value);
                                }
                            }
                            scope_actual.declaraciones.add(temporal);
                            temporal = new ArrayList<>();
                            temporal.add(tipo.value);
                        }else if(!(valor.children.get(j).value.equals(",") || valor.children.get(j).value.equals(";"))){
                            temporal.add(valor.children.get(j).value);
                            scope_actual.declaraciones.add(temporal);
                        }
                    }
                }
            }
        }
    }
    
    public static void check_method_decl(Block scope_actual){
        if(scope_actual.padre.scope_original.value.equals("program") && (!scope_actual.padre.equals(scope_actual))){
            parse_pointer valor = scope_actual.padre.scope_original;
            for(int i = 0; i < valor.children.size(); i++){
                if(valor.children.get(i).value.equals("method_decl")){
                    parse_pointer siguiente = valor.children.get(i);
                    for(int j = 0; j < siguiente.children.size(); j++){
                        if(siguiente.children.get(j).value.equals("method_decl_alt")){
                            parse_pointer siguiente_valor = siguiente.children.get(j);
                            ArrayList<String> temporal = new ArrayList<>();
                            for(int k = 0; k < siguiente_valor.children.size(); k++){
                                if(siguiente_valor.children.get(k).value.equals(",") || k == siguiente_valor.children.size() - 1){
                                    if(k == siguiente_valor.children.size() - 1){
                                        temporal.add(siguiente_valor.children.get(k).children.get(0).value);
                                    }
                                    scope_actual.declaraciones.add(temporal);
                                    temporal = new ArrayList<>();
                                }else{
                                    if(siguiente_valor.children.get(k).value.equals("id")){
                                        temporal.add(siguiente_valor.children.get(k).children.get(0).value);
                                    }else{
                                        temporal.add(siguiente_valor.children.get(k).value);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}
