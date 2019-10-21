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
    public static ArrayList<ArrayList<String>> toWrite = new ArrayList<>();
    
    public static void main(String[] args){

        try {
            Parser parser = new Parser();
            parser.main(new String[0]);
        } catch (FileNotFoundException ex) {
            System.err.println("Error.");
        }

        System.out.println("\n\n--------------SYMBOL TABLE--------------");
        System.out.println("ID                                    Type Value      Scope");
        Block scope0 = new Block(Parser.father);
            
        // create html file for visual tree.
        File treeHtml = new File ("tree.html");
        if (treeHtml.exists()){
            treeHtml.delete();
        } 
        try {treeHtml.createNewFile();}
        catch (IOException e) {
            e.printStackTrace();
        }
        
        //prepare file
        try {prepareHtml();}
        catch (IOException e) {
            e.printStackTrace();
          }

        recorrer(Parser.father, scope0);
        System.out.println("\n\n");
        recorrer_scope(scope0);



        
        


    }

    public static void prepareHtml() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("tree.html", true));
        writer.write("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>");
        writer.newLine();   //Add new line
        writer.close();
    }

    public static void makeTree(Parse_pointer nodo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("tree.html", true));
        //writer.write(""); //write line
        //writer.newLine();   //Add new line

        if(nodo.value.equals("program")){
            writer.write("<h1>"+nodo.value+"</h1>"); //write line
            writer.newLine();   //Add new line
        }

        writer.close();
    }



    public static void recorrer(Parse_pointer padre, Block padreb){       
        
        // write: <div class='row'>
        try {
            makeTree(padre); //for each child write "<div class='col'>"+nodo.value+"</div>"
        }
        catch (IOException e) {
            e.printStackTrace();
            }
        // write: </div>
        

        Block valor = scopef(padre, padreb);
        Block siguiente;
        if(valor == null){
            siguiente = padreb;
        }else{
            siguiente = valor;
        }

        /*
        if(padre.children.size() == 0){
                // function that fills html file (creates the tree)
            try {createTree(padre);}
            catch (IOException e) {
                e.printStackTrace();
                }
            // -end
        }*/

  
        
        
        if(padre.children.size() > 0){            
            for(int i = 0; i < padre.children.size(); i++){
                if (padre.children.get(i).value.equals("type") && padre.children.get(i+1).value.equals("id") ){
                    if (padre.value.equals("var_decl")){
                        // insert to table. 
                        
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
    
    public static void recorrer_scope(Block inicio){
        
        //REVISAR UNIQUENESS 
        for(int j = 0; j < inicio.declaraciones.size(); j++){
            for(int k = 0; k < inicio.declaraciones.size(); k ++){
                if(inicio.declaraciones.get(k).get(1).equals(inicio.declaraciones.get(j).get(1))){
                    if(j != k){
                        String valor = inicio.declaraciones.get(k).get(1);
                        System.out.println("---------------------------------------------------------");
                        System.out.println("Error: Declaración de variables repetidas. Valor: " + valor);
                        System.out.println("---------------------------------------------------------");
                        
                    }
                }
            }
        }
        //REVISAR QUE SE DECLARARON
        
        int contador_uso = 0;
        for(int j = 0; j < inicio.utilizaciones.size(); j++){
            contador_uso = 0;
            for(int k = 0; k < inicio.declaraciones.size(); k ++){
                if(inicio.utilizaciones.get(j).equals(inicio.declaraciones.get(k).get(1))){
                    contador_uso += 1;
                }
            }
            if(contador_uso <= 0){
                String valor = inicio.utilizaciones.get(j);
                System.out.println("---------------------------------------------------------");
                System.out.println("Error: Variable no se ha declarado : " + valor);
                System.out.println("---------------------------------------------------------");
            }
        }
        
        
        if(!inicio.padre.equals(inicio)){
            backward_scope(inicio.padre, inicio.declaraciones, inicio.utilizaciones);
        }
        
        if(inicio.hijos.size() > 0){
            for(int i = 0 ; i < inicio.hijos.size(); i++){
                recorrer_scope(inicio.hijos.get(i));
            }
        }
    }

    public static void backward_scope(Block bloque, ArrayList<ArrayList<String>> declaraciones, ArrayList<String> usos){
        
        for(int i = 0; i < declaraciones.size(); i++){
            for(int j = 0; j < bloque.declaraciones.size(); j++){
                if(bloque.declaraciones.get(j).get(1).equals(declaraciones.get(i).get(1))){
                    String valor = bloque.declaraciones.get(j).get(1);
                    System.out.println("---------------------------------------------------------");
                    System.out.println("Error: Declaración de variables repetidas. Valor: " + valor);
                    System.out.println("---------------------------------------------------------");
                }
            }
        }
                
        if (!bloque.equals(bloque.padre)){
            backward_scope(bloque.padre, declaraciones, usos);
        }
    }
}
