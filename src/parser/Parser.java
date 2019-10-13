
package parser;
import compiler.scanner.Scannerc;
import compiler.scanner.token;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Parser {
    static ArrayList<ArrayList<String>> firsts = new ArrayList<>();
    static ArrayList<ArrayList<String>> follows = new ArrayList<>();
    static ArrayList<String> diccionario = new ArrayList<>();
    static int contador = 0;
    static Stack<Boolean> stack_rec = new Stack<>();
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException { 
        
        
        //LEER EL ARCHIVO, ESTO TIENEN QUE CAMBIARLO A SU PATH
        File contenido = new File("C:\\Users\\joseg\\OneDrive\\Desktop\\arroba.txt");
        Scanner scan = new Scanner(contenido);
        
        
        ArrayList<ArrayList<Grammar_node>> productions = new ArrayList<>();
        ArrayList<Grammar_node> production = new ArrayList<>();
        int contador_items = 0;
        
        //ESTRUCTURA DE LA PARSE TABLE 
        ArrayList<String> terminales_titulos = new ArrayList<>();
        ArrayList<String> no_terminales_titulos = new ArrayList<>();
        
        
        while(scan.hasNext()){
            String valor = scan.next();
            Grammar_node temp_obj;
            
            if(valor.equals("#")){
                productions.add(production);
                contador_items = 0;
                production = new ArrayList<>();
            }else{
                if(contador_items == 0){
                    if(valor.contains("@")){
                        temp_obj = new Grammar_node(true,false, valor);
                        if(!diccionario.contains(temp_obj.value)){
                            diccionario.add(temp_obj.value);
                            ArrayList<String> temporal_agregar = new ArrayList<>();
                            ArrayList<String> temporal_agregar2 = new ArrayList<>();
                            firsts.add(temporal_agregar);
                            follows.add(temporal_agregar2);
                        }
                        //AGREGAR A LA PARSE TABLE 
                        if(!no_terminales_titulos.contains(temp_obj.value)){
                            no_terminales_titulos.add(temp_obj.value);
                        }
                    }else{
                        temp_obj = new Grammar_node(true, true, valor);
                        if(!terminales_titulos.contains(temp_obj.value)){
                            terminales_titulos.add(temp_obj.value);
                        }
                    }
                    production.add(temp_obj);
                    contador_items += 1;
                }else{
                    if(valor.contains("@")){
                        temp_obj = new Grammar_node(false,false, valor);
                        if(!no_terminales_titulos.contains(temp_obj.value)){
                            no_terminales_titulos.add(temp_obj.value);
                        }
                    }else{
                        temp_obj = new Grammar_node(false, true, valor);
                        if(!terminales_titulos.contains(temp_obj.value)){
                            terminales_titulos.add(temp_obj.value);
                        }
                    }
                    production.add(temp_obj);
                    contador_items += 1;
                }
            }  
        }
        terminales_titulos.add("$");
        //follows.get(diccionario.indexOf("@field_alt@")).add("$");
        //follows.get(diccionario.indexOf("@method_alt@")).add("$");
        //FINALIZAR ESTRUCTURA DE LA TABLA 
        Grammar_node[][] parse_table = new Grammar_node[no_terminales_titulos.size()][terminales_titulos.size()];
        
        
        for(int i = 0; i < productions.size(); i++){
            ArrayList<String> arreglo = new ArrayList<>();
            first(productions.get(i).get(0).value, productions, arreglo);
            for(int j = 0; j < arreglo.size(); j++){
                if(!firsts.get(diccionario.indexOf(productions.get(i).get(0).value)).contains(arreglo.get(j))){
                    firsts.get(diccionario.indexOf(productions.get(i).get(0).value)).add(arreglo.get(j));
                }
            }
        }
        
        //insertar el primer follow 
        follows.get(0).add("$");
        for(int i = 0; i < productions.size(); i++){
            follow(productions.get(i).get(0),productions);
        }
        
        //SUPUESTAMENTE TODOS FIRST Y FOLLOWS YA COMPUTADOS
        //primer indice indica los no terminales, segundo indice indica los terminales
        
        
        //For each terminal a in FIRST(A) o First(productions.get(i)), add A -> alpha to M[A, a] 
        for(int i = 0; i < productions.size(); i++){
            ArrayList<String> firsts_a = firsts.get(diccionario.indexOf(productions.get(i).get(0).value));
            for(int j = 0; j < firsts_a.size(); j++){
                if(parse_table[no_terminales_titulos.indexOf(productions.get(i).get(0).value)][terminales_titulos.indexOf(firsts_a.get(j))] != null){
                    //System.out.println("AMbiguedad");
                }
                parse_table[no_terminales_titulos.indexOf(productions.get(i).get(0).value)][terminales_titulos.indexOf(firsts_a.get(j))] = productions.get(i).get(0);
            }
            //Loop de arriba revisado, OK. 
            
            
            ArrayList<String> firsts_alpha_arr = new ArrayList<String>();
            //Recorrer la produccion para empezar a encontrar todos los firsts de alpha, que alpha es cada parte de la produccion
            //empieza en j = 1 porque en j = 0 esta el inicio de la produccion no se cuenta 
            for(int j = 1; j < productions.get(i).size(); j++){
                ArrayList<String> firsts_alpha = new ArrayList<String>();
                //firsts_alpha = firsts.get(diccionario.indexOf(productions.get(i).get(0).value));
                //primero veremos si es terminal o no...
                if(productions.get(i).get(j).es_terminal){
                    firsts_alpha.add(productions.get(i).get(j).value);
                }else{
                    //de lo contrario, hay que ir a buscar cada uno de los firsts...
                    firsts_alpha = firsts.get(diccionario.indexOf(productions.get(i).get(j).value));
                }
                
                //una vez se los firsts de esa parte de la produccion, lo agrego al arreglo mayor firsts_alpha_arr
                for(int k = 0; k < firsts_alpha.size(); k++){
                    firsts_alpha_arr.add(firsts_alpha.get(k));
                }
            }
            
            //a este punto ya tenemos el first de alpha guardado en firsts_alpha_arr, ahora se hacen las inserciones 
            //if e is in first de alpha 
            if(firsts_alpha_arr.contains("e")){
               ArrayList<String> follows_a = follows.get(diccionario.indexOf(productions.get(i).get(0).value));
               for(int l = 0; l < follows_a.size(); l++){
                   //for each terminal b in follow A add A -> alpha to M[A,b] 
                   if(parse_table[no_terminales_titulos.indexOf(productions.get(i).get(0).value)][terminales_titulos.indexOf(follows_a.get(l))] != null){
                       //System.out.println("Ambiguedad");
                   }
                   parse_table[no_terminales_titulos.indexOf(productions.get(i).get(0).value)][terminales_titulos.indexOf(follows_a.get(l))] = productions.get(i).get(0);
               }
               
               //if e is in follow de alpha and $ is in follow de A, add A-> alpha to M[A, $] as well 
               if(follows_a.contains("$")){
                   if(parse_table[no_terminales_titulos.indexOf(productions.get(i).get(0).value)][terminales_titulos.indexOf("$")] != null){
                       System.out.println("Ambiguedad");
                   }
                   parse_table[no_terminales_titulos.indexOf(productions.get(i).get(0).value)][terminales_titulos.indexOf("$")] = productions.get(i).get(0);
               }
            }
        }
        
        //PARSE_TABLE CONSTRUIDA
       /*FileWriter csvWriter = new FileWriter("C:\\Users\\joseg\\OneDrive\\Desktop\\tabla.csv");
        for(int i = 0; i < parse_table.length; i++){
            for(int j = 0; j < parse_table[i].length;j++){
                if(parse_table[i][j] == null){
                    csvWriter.append("NULL");
                    csvWriter.append(",");
                }else{
                    csvWriter.append(String.join(",", parse_table[i][j].value));
                    csvWriter.append(",");
                }
            }
            csvWriter.append("\n");
        }
        
        csvWriter.flush();
        csvWriter.close();*/
        //parse(Scannerc.token_stream, parse_table, productions, terminales_titulos, no_terminales_titulos);
        /*for(int i = 0; i < follows.size(); i++){
            System.out.println(follows.get(i));
        }
        System.out.println(follows.size());
        System.out.println(firsts.size());*/
        
        
        verificar(productions, productions.get(0),Scannerc.token_stream);
        
        
        
    }
    
    public static void first(String objeto, ArrayList<ArrayList<Grammar_node>> productions, ArrayList<String> arreglo){
        for(int i = 0; i < productions.size(); i++){
            if(productions.get(i).get(0).value.equals(objeto)){
                first_aux(productions.get(i), productions,arreglo);
            }
        }
    }
    
    public static void first_aux(ArrayList<Grammar_node> production, ArrayList<ArrayList<Grammar_node>> productions, ArrayList<String> arreglo){
        Grammar_node right = production.get(1);
        
        if(right.es_terminal){
            if(!firsts.get(diccionario.indexOf(production.get(0).value)).contains(right.value)){
                arreglo.add(right.value);
            }
        }else{
            first(right.value, productions, arreglo);
        }
    }
    
    public static void follow(Grammar_node objeto, ArrayList<ArrayList<Grammar_node>> productions){
        Grammar_node alpha = null;
        Grammar_node beta = null;
        for(int i = 0 ; i < productions.size(); i ++){
            for(int j = 1; j < productions.get(i).size(); j++){
                if(productions.get(i).get(j).value.equals(objeto.value)){
                    if(j > 1){
                       alpha = productions.get(i).get(j - 1);
                    }
                    if(j < (productions.get(i).size() - 1)){
                        beta = productions.get(i).get(j + 1);
                    }
                    
                    
                    //A ESTE PUNTO YA TENEMOS EL VALOR DE ALPHA Y BETA PARA APLICAR LAS REGLAS 
                    if(beta != null){
                        //REGLA 2 CUANDO BETA ES TERMINAL, NO TIENE QUE BUSCAR FIRSTS, NO DERIVA EPSILON
                        if(beta.es_terminal && !beta.value.equals("e") ){
                            if(!follows.get(diccionario.indexOf(objeto.value)).contains(beta.value)){
                                follows.get(diccionario.indexOf(objeto.value)).add(beta.value);
                            }
                            
                        }else{
                            ArrayList<String> first_b = firsts.get(diccionario.indexOf(beta.value));
                            ArrayList<String> first_b2 = firsts.get(diccionario.indexOf(beta.value));
                            
                            if(first_b.contains("e")){
                                first_b2.remove("e");
                                ArrayList<String> valor_follows = follows.get(diccionario.indexOf(productions.get(i).get(0).value));
                                if(valor_follows.size() > 0){
                                   for(int l = 0; l < valor_follows.size(); l++){
                                       if(!follows.get(diccionario.indexOf(objeto.value)).contains(valor_follows.get(l))){
                                            follows.get(diccionario.indexOf(objeto.value)).add(valor_follows.get(l));
                                        }
                                   } 
                                }else{
                                    follow(productions.get(i).get(0), productions);
                                    for(int l = 0; l < valor_follows.size(); l++){
                                       if(!follows.get(diccionario.indexOf(objeto.value)).contains(valor_follows.get(l))){
                                            follows.get(diccionario.indexOf(objeto.value)).add(valor_follows.get(l));
                                        }
                                    } 
                                }
                                
                                for(int k = 0; k < first_b2.size(); k++){
                                if(!follows.get(diccionario.indexOf(objeto.value)).contains(first_b.get(k))){
                                    follows.get(diccionario.indexOf(objeto.value)).add(first_b2.get(k));
                                }
                            }
                                first_b.remove("e");
                            }
                            for(int k = 0; k < first_b.size(); k++){
                                if(!follows.get(diccionario.indexOf(objeto.value)).contains(first_b.get(k))){
                                    follows.get(diccionario.indexOf(objeto.value)).add(first_b.get(k));
                                }
                            }
                        }
                    }else{
                        ArrayList<String> valor_follows2 = follows.get(diccionario.indexOf(productions.get(i).get(0).value));
                        for(int l = 0; l < valor_follows2.size(); l++){
                            if(!follows.get(diccionario.indexOf(objeto.value)).contains(valor_follows2.get(l))){
                                follows.get(diccionario.indexOf(objeto.value)).add(valor_follows2.get(l));
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static void follow2(Grammar_node objeto, ArrayList<ArrayList<Grammar_node>> productions){
        ArrayList<ArrayList<Grammar_node>> productionsb = new ArrayList<>();
        
        for(int i = 0; i < productions.size(); i++){
            for(int j = 1; j < productions.get(i).size();j++){
                if(productions.get(i).get(j).value.equals(objeto.value)){
                    productionsb.add(productions.get(i));
                }
            }
        }
        //ya tenemos las producciones en donde encontro b
        
        
        for(int i = 0; i < productionsb.size(); i++){
            for(int j = 1; j < productionsb.get(i).size(); j++){
                if(productionsb.get(i).get(j).value.equals(objeto.value)){
                    ArrayList<String> firsts_beta = new ArrayList<>(); 
                    if(j < productionsb.get(i).size() - 1){
                        //aplicar regla 1
                        if(productionsb.get(i).get(j + 1).es_terminal){
                           follows.get(diccionario.indexOf(objeto.value)).add(productionsb.get(i).get(j + 1).value); 
                        }else{
                            firsts_beta = firsts.get(diccionario.indexOf(productionsb.get(i).get(j + 1).value));
                            if(firsts_beta.contains("e")){
                                firsts_beta.remove("e");
                            }
                        
                            for(int k = 0; k < firsts_beta.size(); k++){
                                ArrayList<String> valor = follows.get(diccionario.indexOf(objeto.value));
                                if(!valor.contains(firsts_beta.get(k))){
                                    follows.get(diccionario.indexOf(objeto.value)).add(firsts_beta.get(k));
                                }
                            }
                        }
                    }else{
                        //aplicar regla 2
                        follow2(productionsb.get(i).get(0), productions);
                        ArrayList<String> followa = follows.get(diccionario.indexOf(productionsb.get(j).get(0).value));
                        for(int k = 0; k < followa.size(); k++){
                            if(!follows.get(diccionario.indexOf(objeto.value)).contains(followa.get(k))){
                                follows.get(diccionario.indexOf(objeto.value)).add(followa.get(k));
                            }
                        }
                        
                        if(firsts_beta.contains("e")){
                            for(int k = 0; k < firsts_beta.size(); k++){
                                if(!follows.get(diccionario.indexOf(objeto.value)).contains(firsts_beta.get(k))){
                                    follows.get(diccionario.indexOf(objeto.value)).add(firsts_beta.get(k));
                                }
                            }
                        }
                        
                        
                    }
                    
                }
            }
            
            
        }
    }
    
    
    //voy a chequear esta funcion de nuevo
    public static void parse(ArrayList<token> tokens, Grammar_node[][] parse_table, ArrayList<ArrayList<Grammar_node>> productions, ArrayList<String> terminales_titulos, ArrayList<String> no_terminales_titulos){
        Stack<Grammar_node> stack_parse = new Stack<>();
        Grammar_node end_marker = new Grammar_node(false, false, "$");
        stack_parse.push(end_marker);
        stack_parse.push(productions.get(0).get(0));
        
        //crear la raiz del arbol de parsing 
        parse_pointer head = new parse_pointer(stack_parse.peek());
        
        int contador = 0; 
        token ip = tokens.get(contador);
        Grammar_node x = stack_parse.peek();
        //AHORITA TODA LA PREPARACION INICIAL YA SE HIZO 
        
        parse_pointer nodo_actual = head;
        while(!"$".equals(x.value)){
            //System.out.println(no_terminales_titulos);
            if(x.value.equals(ip.value)){
                stack_parse.pop();
                contador += 1;
                ip = tokens.get(contador);
                System.out.println("parseado");
                System.out.println(x.value);
                nodo_actual.children.add(x);
            }else if(x.es_terminal){
                System.out.println("ERROR");
                stack_parse.pop();
                nodo_actual.children.add(x);
            }else if(parse_table[no_terminales_titulos.indexOf(x.value)][terminales_titulos.indexOf(ip.value)] == null){
                System.out.println("ERROR");
                stack_parse.pop();
                nodo_actual.children.add(x);
            }else if(parse_table[no_terminales_titulos.indexOf(x.value)][terminales_titulos.indexOf(ip.value)] != null){
                Grammar_node value = parse_table[no_terminales_titulos.indexOf(x.value)][terminales_titulos.indexOf(ip.value)];
                nodo_actual = new parse_pointer(value);
                stack_parse.pop();
                ArrayList<Grammar_node> produccion = new ArrayList<>();
                for(int i = 0; i < productions.size(); i++){
                    if(productions.get(i).get(0).equals(value)){
                        produccion = productions.get(i);
                        break;
                    }
                }
                for(int i = produccion.size() - 1; i > 0; i--){
                    stack_parse.push(produccion.get(i));
                }
            }
            x = stack_parse.peek();
        }
        
        
    }

   
    public static void verificar(ArrayList<ArrayList<Grammar_node>> productions, ArrayList<Grammar_node> production, ArrayList<token> token_stream){
 
        for(int i = 1; i < production.size(); i++){
            if(production.get(i).es_terminal){
                if(production.get(i).value.equals(token_stream.get(contador).value)){
                    //construir arbol
                    contador += 1;
                    System.out.println(production.get(i).value);
                    stack_rec.push(Boolean.TRUE);
                }else{
                    //error
                    System.out.println("Error");
                    System.out.println(production.get(i).value);
                    stack_rec.push(Boolean.FALSE);
                }
            }else{
                ArrayList<Integer> temp_array = temporal(productions, production.get(i));
                
                for(int j = 0; j < temp_array.size(); j++){
                    verificar(productions, productions.get(temp_array.get(j)), token_stream);
                    Boolean resultado = stack_rec.pop();
                    if(resultado){
                        break;
                    }
                }
            }
        }
    }

    public static ArrayList<Integer> temporal(ArrayList<ArrayList<Grammar_node>> productions, Grammar_node production){
        
        ArrayList<Integer> temp_array = new ArrayList<>();
        
        for(int a = 0; a < productions.size(); a++ ){
            if(productions.get(a).get(0).value.equals(production.value) && productions.get(a).get(0) != production){
                temp_array.add(a);
            }
        }
        return temp_array;
    }
    
 }