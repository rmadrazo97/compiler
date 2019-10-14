/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author joseg
 */
public class parser {
    static int contador = 0;
    static Parse_pointer father;
   
    
    public static void main(String[] args) throws FileNotFoundException, IOException {

        Scannerc lexer = new Scannerc();
        lexer.main(new String[0]);
        //Grammar_node node = new Grammar_node();
        //Parse_pointer pointer = new Parse_pointer();

        ArrayList<token> token_stream = lexer.token_stream;
        
        father = new Parse_pointer(null, "production", "program");
        program(token_stream, father);
    }

    public static void program(ArrayList<token> tokens, Parse_pointer padre){
        //class
        if(tokens.get(contador).value.equals("class")){
            System.out.println("Parseado: class");
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador += 1;
            
            if(tokens.get(contador).value.equals("Program")){
                System.out.println("Parseado Program");
                Parse_pointer hijo_2 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1;
                
                if(tokens.get(contador).value.equals("{")){
                    System.out.println("Parseado: {");
                    Parse_pointer hijo_3 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_3);
                    contador += 1;
                    
                    //llamada a field decl 
                    boolean continuar = true;
                    while(continuar){
                        Parse_pointer hijo_4 = new Parse_pointer(padre, "produccion", "field_decl");
                        continuar = field_decl(tokens, hijo_4);
                        if(continuar){
                            padre.children.add(hijo_4);
                        }
                    }
                    
                    boolean continuar2 = true;
                    while(continuar2){
                        Parse_pointer hijo_5 = new Parse_pointer(padre, "produccion", "method_decl");
                        continuar2 = method_decl(tokens, hijo_5);
                        if(continuar2){
                            padre.children.add(hijo_5);
                        }
                    }
                    
                    if(tokens.get(contador).value.equals("}")){
                        System.out.println("Parseado: }");
                        Parse_pointer hijo_6 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_6);
                    }else{
                        //System.out.println("Error");
                    }
                }else{
                    //System.out.println("Error");
                }
            }else{
                //System.out.println("Error");
            }
        }else{
            //System.out.println("Error");
        }
    }

    public static boolean field_decl(ArrayList<token> tokens, Parse_pointer padre){
       
        int inicio = contador;
        
        // padre viene de la llamada anerior -> padre
        Parse_pointer hijo_1 = new Parse_pointer(padre, "production", "type");
        if(type(tokens, hijo_1)){
            
            // hijo_1 -> type.
            padre.children.add(hijo_1);

            Parse_pointer hijo_2 = new Parse_pointer(padre, "production", "field_decl_alt");
            if(field_decl_alt(tokens, hijo_2)){
                
                // hijo_2 -> field_decl_alt
                padre.children.add(hijo_2);

                boolean continuar = true;
                while(continuar){
                    if(tokens.get(contador).value.equals(",")){
                        // hijo_3
                        Parse_pointer hijo_3 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_3);

                        System.out.println("Parseado: ,");

                        contador += 1;
                       
                        //hijo_4
                        Parse_pointer hijo_4 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        
                        continuar = field_decl_alt(tokens, hijo_4);
                        // if continuar add as children
                        if (continuar){
                            padre.children.add(hijo_4); 
                        }
                        
                    }else{
                        break;
                    }
                }
                
                if(tokens.get(contador).value.equals(";")){
                    
                    Parse_pointer hijo_5 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_5); 

                    System.out.println("Parseado: ;");
                    contador += 1;
                    return true;
                }else{
                    contador = inicio; 
                    return false;
                }
            }else{
                contador = inicio;
                return false;
            }
        }else{
            contador = inicio;
            return false;
        }
    }

    public static boolean field_decl_alt(ArrayList<token> tokens, Parse_pointer padre){
        int inicio = contador;
        
        Parse_pointer hijo_1 = new Parse_pointer(padre, "produccion", "id");
        if(id(tokens, hijo_1)){
            padre.children.add(hijo_1);
            if(tokens.get(contador).value.equals("[")){
                System.out.println("Parseado: [");
                Parse_pointer hijo_2 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1;
            
                Parse_pointer hijo_3 = new Parse_pointer(padre, "produccion", "int_literal");
                if(int_literal(tokens, hijo_3)){
                    padre.children.add(hijo_3);
                    if(tokens.get(contador).value.equals("]")){
                        System.out.println("Parseado: ]");
                        Parse_pointer hijo_4 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_4);
                        contador += 1;
                        return true;
                    }else{
                        contador = inicio; 
                        return false; 
                    }  
                }else{
                    contador = inicio; 
                    return false;
                }
            }else{
                return true;
            }
        }else{
            contador = inicio; 
            return false;
        }
    }

    public static boolean method_decl(ArrayList<token> tokens, Parse_pointer padre){
        int inicio = contador; 
        Parse_pointer hijo_0 = new Parse_pointer(padre, "production", "type");
        Parse_pointer hijo_00 = new Parse_pointer(padre, "production", "id");

        if(type(tokens, hijo_0) || tokens.get(contador).value.equals("void")){

            if (tokens.get(contador).value.equals("void")){
               
                Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_1); 

            } else {
                padre.children.add(hijo_0); 


            }

            if(tokens.get(contador).value.equals("void")){
                
                Parse_pointer hijo_2 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2); 

                contador += 1;
            }
            if(id(tokens, hijo_00)){
                padre.children.add(hijo_00);
                if(tokens.get(contador).value.equals("(")){
                    
                    Parse_pointer hijo_3 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_3); 
                    
                    System.out.println("Parseado: (");
                    contador += 1;
                    
                    if(tokens.get(contador).value.equals(")")){
                        
                        Parse_pointer hijo_4 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_4);
                        
                        System.out.println("Parseado: )");
                        contador += 1;
                        
                        Parse_pointer hijo_5 = new Parse_pointer(padre, "production", "block" );

                        if(block(tokens, hijo_5)){
                            
                            padre.children.add(hijo_5);
                            
                            return true; 
                        }else{
                            contador = inicio; 
                            return false;
                        }
                    }else{
                        Parse_pointer hijo_6 = new Parse_pointer(padre, "production", "method_decl_alt");
                        
                        if(method_decl_alt(tokens, hijo_6)){
                            
                            padre.children.add(hijo_6);
                            
                            if(tokens.get(contador).value.equals(")")){
                                
                                Parse_pointer hijo_7 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                padre.children.add(hijo_7);
                                
                                System.out.println("Parseado: )");
                                contador += 1;
                    
                                Parse_pointer hijo_8 = new Parse_pointer(padre, "production", "block" );
                                if(block(tokens, hijo_8)){

                                    padre.children.add(hijo_8);

                                    return true;
                                }else{
                                    contador = inicio; 
                                    return false; 
                                }
                            }else{
                                contador = inicio; 
                                return false; 
                            }
                        }else{
                            contador = inicio; 
                            return false; 
                        }
                    }
                }else{
                    contador = inicio; 
                    return false; 
                }
            }else{
                contador = inicio; 
                return false; 
            }
        }else{
            contador = inicio; 
            return false;
        }
    }

    public static boolean method_decl_alt(ArrayList<token> tokens, Parse_pointer padre){
        
        Parse_pointer hijo_1 = new Parse_pointer(padre, "production", "type");
        if(type(tokens, hijo_1)){
            padre.children.add(hijo_1);
            Parse_pointer hijo_2 = new Parse_pointer(padre, "production", "id");
            if(id(tokens, hijo_2)){
                padre.children.add(hijo_2);
                boolean continuar = true; 
                int inicio = contador;
                while(continuar){
                    if(tokens.get(contador).value.equals(",")){
                        System.out.println("Parseado , ");
                        Parse_pointer hijo_3 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_3);
                        contador += 1;
                        
                        Parse_pointer hijo_4 = new Parse_pointer(padre, "production", "type");
                        if(type(tokens, hijo_4)){
                            padre.children.add(hijo_4);
                            Parse_pointer hijo_5 = new Parse_pointer(padre, "production", "id");
                            if(id(tokens, hijo_5)){
                                padre.children.add(hijo_5);
                                continuar = true;
                            }else{
                                contador = inicio; 
                                return true; 
                            }
                        }else{
                            contador = inicio; 
                            return true; 
                        }
                    }else{
                        return true;
                    }
                }
                return true; 
            }else{
                return false;
            }
        }else{
            return false; 
        }
    }

    public static boolean block(ArrayList<token> tokens, Parse_pointer padre){
        
        if(tokens.get(contador).value.equals("{")){
            
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado: {");
            contador += 1;
            
            boolean continuar = true;
            while(continuar){
                
                Parse_pointer hijo_2 = new Parse_pointer(padre, "production", "var_decl");
                continuar = var_decl(tokens, hijo_2);

                if (continuar){
                    padre.children.add(hijo_2);
                } 
            }
            
            boolean continuar2 = true;
            while(continuar2){
                
                Parse_pointer hijo_3 = new Parse_pointer(padre, "production", "var_decl");

                continuar2 = statement(tokens, hijo_3);
                if (continuar){
                    padre.children.add(hijo_3);
                }
            }
            
            if(tokens.get(contador).value.equals("}")){
                
                Parse_pointer hijo_4 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_4);

                System.out.println("Parseado: }");
                contador += 1;
                
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public static boolean var_decl(ArrayList<token> tokens, Parse_pointer padre){
        
        int inicio = contador; 
        
        Parse_pointer hijo_1 = new Parse_pointer(padre, "production", "type");
        if(type(tokens, hijo_1)){
            padre.children.add(hijo_1);
            Parse_pointer hijo_2 = new Parse_pointer(padre, "production", "id");
            if(id(tokens, hijo_2)){
                padre.children.add(hijo_2);
                boolean continuar = true; 
                while(continuar){
                    if(tokens.get(contador).value.equals(",")){
                        System.out.println("Parseado: ,");
                        Parse_pointer hijo_3 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_3);
                        contador += 1;
                
                        Parse_pointer hijo_4 = new Parse_pointer(padre, "production", "id");
                        continuar = id(tokens, hijo_4);
                        if(continuar){
                            padre.children.add(hijo_4);
                        }
                    }else{
                        break;
                    }
                }
                if(tokens.get(contador).value.equals(";")){
                    System.out.println("Parseado: ;");
                    Parse_pointer hijo_5 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_5);
                    contador += 1;
            
                    return true;
                }else{
                    contador = inicio; 
                    return false; 
                }
            }else{
                contador = inicio; 
                return false; 
            }
        }else{
            contador = inicio; 
            return false;
        }
    }

    public static boolean type(ArrayList<token> tokens, Parse_pointer padre){
        
        String value = tokens.get(contador).value;
        
        if(value.equals("int") || value.equals("boolean")){
            
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado: " + value);
            contador += 1;
            return true;
        }else{
            return false;
        }
    }

    public static boolean statement(ArrayList<token> tokens, Parse_pointer padre){
        int inicio = contador; 
        
        Parse_pointer hijo_23 = new Parse_pointer(padre, "production", "block");
        String value = tokens.get(contador).value;
        if(value.equals("if")){
            System.out.println("Parseado: if");
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador += 1;
            
            if(tokens.get(contador).value.equals("(")){
                System.out.println("Parseado: (");
                Parse_pointer hijo_2 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1; 
                
                Parse_pointer hijo_3 = new Parse_pointer(padre, "production", "expr");
                if(expr(tokens, hijo_3)){
                    padre.children.add(hijo_3);
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        Parse_pointer hijo_4 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_4);
                        contador += 1;
                        
                        Parse_pointer hijo_5 = new Parse_pointer(padre, "production", "block");
                        if(block(tokens, hijo_5)){
                            padre.children.add(hijo_5);
                            if(tokens.get(contador).value.equals("else")){
                                System.out.println("Parseado: else");
                                Parse_pointer hijo_6 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                padre.children.add(hijo_6);
                                contador += 1; 
                                
                                Parse_pointer hijo_7 = new Parse_pointer(padre, "production", "block");
                                if(block(tokens, hijo_7)){
                                    padre.children.add(hijo_7);
                                    return true;
                                }else{
                                    contador = inicio; 
                                    return false;
                                }
                            }else{
                                return true;
                            }
                        }else{
                            contador = inicio; 
                            return false;
                        }
                    }else{ 
                        contador = inicio; 
                        return false; 
                    }
                }else {
                    contador = inicio; 
                    return false; 
                }
            }else {
                contador = inicio;
                return false; 
            }
        }else if(value.equals("for")){
            System.out.println("Parseado: for");
            Parse_pointer hijo_8 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_8);
            contador += 1; 
            
            Parse_pointer hijo_9 = new Parse_pointer(padre, "production", "id");
            if(id(tokens, hijo_9)){
                padre.children.add(hijo_9);
                if(tokens.get(contador).value.equals("=")){
                    System.out.println("Parseado: =");
                    Parse_pointer hijo_10 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_10);
                    contador += 1; 
                    
                    Parse_pointer hijo_11 = new Parse_pointer(padre, "production", "expr");
                    if(expr(tokens, hijo_11)){
                        padre.children.add(hijo_11);
                        if(tokens.get(contador).value.equals(",")){
                            System.out.println("Parseado: ,");
                            Parse_pointer hijo_12 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                            padre.children.add(hijo_12);
                            contador += 1;
                            
                            Parse_pointer hijo_13 = new Parse_pointer(padre, "production", "expr");
                            if(expr(tokens, hijo_13)){
                                padre.children.add(hijo_13);
                                Parse_pointer hijo_14 = new Parse_pointer(padre, "production", "block");
                                if(block(tokens, hijo_14)){
                                    padre.children.add(hijo_14);
                                    return true; 
                                }else{
                                    contador = inicio; 
                                    return false;
                                }
                            }else{
                                contador = inicio; 
                                return false; 
                            }
                        }else {
                            contador = inicio; 
                            return false;
                        }
                    }else {
                        contador = inicio;
                        return false;
                    }
                }else {
                    contador = inicio; 
                    return false;
                }
            }else {
                contador = inicio;
                return false;
            }
        }else if(value.equals("return")){
            System.out.println("Parseado: return");
            Parse_pointer hijo_15 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_15);
            contador += 1;
            
            Parse_pointer hijo_16 = new Parse_pointer(padre, "production", "expr");
            if(expr(tokens, hijo_16)){
                padre.children.add(hijo_16);
                if(tokens.get(contador).value.equals(";")){
                    System.out.println("Parseado: ;");
                    Parse_pointer hijo_17 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_17);
                    contador += 1; 
                    return true;
                }else {
                    contador = inicio;
                    return false;
                }
            }else{
                if(tokens.get(contador).value.equals(";")){
                    System.out.println("Parseado: ;");
                    Parse_pointer hijo_18 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_18);
                    contador += 1; 
                    return true; 
                }else {
                    contador = inicio; 
                    return false;
                }
            }
        }else if(value.equals("break")){
            System.out.println("Parseado: break");
            Parse_pointer hijo_19 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_19);
            contador += 1; 
            
            if(tokens.get(contador).value.equals(";")){
                System.out.println("Parseado: ;");
                Parse_pointer hijo_20 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_20);
                contador += 1;
                return true;
            }else {
                contador = inicio;
                return false;
            }
        }else if(value.equals("continue")){
            System.out.println("Parseado: continue");
            Parse_pointer hijo_21 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_21);
            contador += 1;
            
            if(tokens.get(contador).value.equals(";")){
                System.out.println("Parseado: ;");
                Parse_pointer hijo_22 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_22);
                contador += 1;
                return true;
            }else {
                contador = inicio; 
                return false;
            }
        }else if(block(tokens, hijo_23)){
            padre.children.add(hijo_23);
            return true;
        }else{
            int inicio2 = contador;
            Parse_pointer hijo_24 = new Parse_pointer(padre, "production", "method_call");
            if(method_call(tokens, hijo_24)){
                padre.children.add(hijo_24);
                if(tokens.get(contador).value.equals(";")){
                    System.out.println("Parseado: ;");
                    Parse_pointer hijo_25 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_25);
                    contador += 1; 
                    
                    return true;
                }else {
                    contador = inicio; 
                    return false;
                }
            }else{
                contador = inicio2;
                Parse_pointer hijo_26 = new Parse_pointer(padre, "production", "location");
                if(location(tokens, hijo_26)){
                    padre.children.add(hijo_26);
                    Parse_pointer hijo_27 = new Parse_pointer(padre, "production", "assign_op");
                    if(assign_op(tokens, hijo_27)){
                        padre.children.add(hijo_27);
                        Parse_pointer hijo_28 = new Parse_pointer(padre, "production", "expr");
                        if(expr(tokens, hijo_28)){
                            padre.children.add(hijo_28);
                            if(tokens.get(contador).value.equals(";")){
                                System.out.println("Parseado: ;");
                                Parse_pointer hijo_29 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                padre.children.add(hijo_29);
                                contador += 1;
                                return true;
                            }else {
                                contador = inicio2; 
                                return false;
                            }
                        }else {
                            contador = inicio2;
                            return false;
                        }
                    }else {
                        contador = inicio2; 
                        return false;
                    }
                }else {
                    contador = inicio2;
                    return false;
                }
            }
        }
        
    }

    public static boolean assign_op(ArrayList<token> tokens, Parse_pointer padre){
        String value = tokens.get(contador).value;
        
        if(value.equals("=") || value.equals("+=") || value.equals("-=")){

            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado: " + value);
            contador += 1;
            return true;
        }else{
            return false;
        }
    }

    public static boolean method_call(ArrayList<token> tokens, Parse_pointer padre){
        int inicio = contador; 
        
        if(tokens.get(contador).value.equals("callout")){
            System.out.println("Parseado: callout");
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador += 1;
            
            if(tokens.get(contador).value.equals("(")){
                System.out.println("Parseado: (");
                Parse_pointer hijo_2 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1; 
                
                Parse_pointer hijo_3 = new Parse_pointer(padre, "production", "string_literal");
                if(string_literal(tokens, hijo_3)){
                    padre.children.add(hijo_3);
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        Parse_pointer hijo_4 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_4);
                        contador += 1; 
                        
                        return true;
                    }else{
                        if(tokens.get(contador).value.equals(",")){
                            Parse_pointer hijo_5 = new Parse_pointer(padre, "production", "callout_arg");
                            if(callout_arg(tokens, hijo_5)){
                                padre.children.add(hijo_5);
                                boolean continuar = true;
                                while(continuar){
                                    if(tokens.get(contador).value.equals(",")){
                                        System.out.println("Parseado: ,");
                                        Parse_pointer hijo_6 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                        padre.children.add(hijo_6);
                                        contador += 1;
                                        
                                        Parse_pointer hijo_7 = new Parse_pointer(padre, "production", "callout_arg");
                                        continuar = callout_arg(tokens, hijo_7);
                                        if(continuar){
                                            padre.children.add(hijo_7);
                                        }
                                    }else{
                                        break;
                                    }
                                }
                                
                                if(tokens.get(contador).value.equals(")")){
                                    System.out.println("Parseado: )");
                                    Parse_pointer hijo_8 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                    padre.children.add(hijo_8);
                                    contador += 1; 
                                    return true;
                                }else {
                                    contador = inicio; 
                                    return false;
                                }
                            }else{
                                contador = inicio; 
                                return false; 
                            }
                        }else{
                            contador = inicio; 
                            return false; 
                        }
                    }
                }else {
                    contador = inicio; 
                    return false;
                }
            }else {
                contador = inicio; 
                return false;
            }
        }else{
            Parse_pointer hijo_9 = new Parse_pointer(padre, "production", "method_name");
            if(method_name(tokens, hijo_9)){
                padre.children.add(hijo_9);
                if(tokens.get(contador).value.equals("(")){
                    System.out.println("Parseado: (");
                    Parse_pointer hijo_10 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_10);
                    contador += 1;
                
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        Parse_pointer hijo_11 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_11);
                        contador += 1;
                        return true;
                    }else{
                        Parse_pointer hijo_12 = new Parse_pointer(padre, "production", "expr");
                        if(expr(tokens, hijo_12)){
                            padre.children.add(hijo_12);
                            boolean continuar = true;
                            while(continuar){
                                if(tokens.get(contador).value.equals(",")){
                                    System.out.println("Parseado: ,");
                                    Parse_pointer hijo_13 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                    padre.children.add(hijo_13);
                                    contador += 1;
                                    Parse_pointer hijo_14 = new Parse_pointer(padre, "production", "expr");
                                    continuar = expr(tokens, hijo_14);
                                    if(continuar){
                                        padre.children.add(hijo_14);
                                    }
                                }else{
                                    break;
                                }
                            }
                            
                            if(tokens.get(contador).value.equals(")")){
                                System.out.println("Parseado: )");
                                Parse_pointer hijo_15 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                padre.children.add(hijo_15);
                                contador += 1; 
                                return true;
                            }else {
                                contador = inicio; 
                                return false;
                            }
                        }else{
                            contador = inicio; 
                            return false;
                        }
                    }
                }else {
                    contador = inicio; 
                    return false;
                }
            }else {
                contador = inicio; 
                return false;
            }
        }
    }

    public static boolean method_name(ArrayList<token> tokens, Parse_pointer padre){
        
        Parse_pointer hijo_1 = new Parse_pointer(padre, "production", "id");
        if (id(tokens, hijo_1)){
            padre.children.add(hijo_1);
            return true;
        }else{
            return false;
        }
        
    }
    
    public static boolean location(ArrayList<token> tokens, Parse_pointer padre){
        int inicio = contador;
        Parse_pointer hijo_1 = new Parse_pointer(padre, "production", "id");
        if(id(tokens, hijo_1)){
            padre.children.add(hijo_1);
            if(tokens.get(contador).value.equals("[")){
                System.out.println("Parseado [");
                Parse_pointer hijo_2 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1; 
                
                Parse_pointer hijo_3 = new Parse_pointer(padre, "production", "expr");
                if(expr(tokens, hijo_3)){
                    padre.children.add(hijo_3);
                    if(tokens.get(contador).value.equals("]")){
                        System.out.println("Parseado ]");
                        Parse_pointer hijo_4 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_4);
                        contador += 1;
                        
                        return true;
                    }else{
                        contador = inicio; 
                        return false; 
                    }
                }else{
                    contador = inicio; 
                    return false;
                }
            }else{
                return true; 
            }
        }else{
            contador = inicio; 
            return false;
        }
    }

    public static boolean expr(ArrayList<token> tokens, Parse_pointer padre){
        int inicio = contador; 
        
        Parse_pointer hijo_10 = new Parse_pointer(padre, "production", "location");
        Parse_pointer hijo_12 = new Parse_pointer(padre, "production", "method_call");
        Parse_pointer hijo_14 = new Parse_pointer(padre, "production", "literal");

        if(tokens.get(contador).value.equals("-")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            System.out.println("Parseado -");
            contador += 1; 
            
            Parse_pointer hijo_2 = new Parse_pointer(padre, "production", "expr");
            if(expr(tokens, hijo_2)){
                
                padre.children.add(hijo_2);
                Parse_pointer hijo_3 = new Parse_pointer(padre, "production", "expr_alt");
                if(expr_alt(tokens, hijo_3)){
                    
                    padre.children.add(hijo_3);
                 
                    return true;
                }else{
                    contador = inicio; 
                    return false;
                }
            }else{
                contador = inicio; 
                return false;
            }
        }else if(tokens.get(contador).value.equals("!")){
            Parse_pointer hijo_4 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_4);
            System.out.println("Parseado !");
            contador += 1;
            
            Parse_pointer hijo_5 = new Parse_pointer(padre, "production", "expr");
            if(expr(tokens, hijo_5)){
                padre.children.add(hijo_5);
                Parse_pointer hijo_200 = new Parse_pointer(padre, "production", "expr_alt");
                if(expr_alt(tokens, hijo_200)){
                    padre.children.add(hijo_200);
                    return true;
                }else{
                    contador = inicio; 
                    return false;
                }
            }else{
                contador = inicio; 
                return false; 
            }
        }else if(tokens.get(contador).value.equals("(")){
            Parse_pointer hijo_6 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_6);
            
            Parse_pointer hijo_7 = new Parse_pointer(padre, "production", "expr");
            if(expr(tokens, hijo_7)){
                
                padre.children.add(hijo_7);

                if(tokens.get(contador).value.equals(")")){

                    Parse_pointer hijo_8 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_8);

                    System.out.println("Parseado )");
                    contador += 1; 
                    
                    Parse_pointer hijo_9 = new Parse_pointer(padre, "production", "expr_alt");
                    if(expr_alt(tokens, hijo_9)){
                        
                        padre.children.add(hijo_9);

                        return true; 
                    }else{
                        contador = inicio; 
                        return false; 
                    }
                }else{
                    contador = inicio; 
                    return false; 
                }
            }else{
                contador = inicio; 
                return false; 
            }
        }else if(location(tokens, hijo_10)){
            
            padre.children.add(hijo_10);
            
            Parse_pointer hijo_11 = new Parse_pointer(padre, "production", "expr_alt");
            if(expr_alt(tokens, hijo_11)){
                
                padre.children.add(hijo_11);

                return true; 
            }else{
                contador = inicio;
                return false;
            }
        }else if(method_call(tokens, hijo_12)){
            
            padre.children.add(hijo_12);
            
            Parse_pointer hijo_13 = new Parse_pointer(padre, "production", "expr_alt");
            
            if(expr_alt(tokens, hijo_13)){
                
                padre.children.add(hijo_13);

                return true; 
            }else{
                contador = inicio;
                return false;
            }
        }else if(literal(tokens, hijo_14)){

            padre.children.add(hijo_14);

            Parse_pointer hijo_15 = new Parse_pointer(padre, "production", "expr_alt");
            if(expr_alt(tokens, hijo_15)){

                padre.children.add(hijo_15);
                
                return true; 
            }else{
                contador = inicio;
                return false;
            }
        }else{
            contador = inicio; 
            return false;
        }
    }  

    public static boolean expr_alt(ArrayList<token> tokens, Parse_pointer padre){
        int inicio = contador;
        
        Parse_pointer hijo_1 = new Parse_pointer(padre, "production", "bin_op");
        if(bin_op(tokens, hijo_1)){
            padre.children.add(hijo_1);
            Parse_pointer hijo_2 = new Parse_pointer(padre, "production", "expr");
             if(expr(tokens, hijo_2)){
                 padre.children.add(hijo_2);
                 Parse_pointer hijo_3 = new Parse_pointer(padre, "production", "expr_alt");
                if(expr_alt(tokens, hijo_3)){
                    padre.children.add(hijo_3);
                    return true; 
                }else{
                    contador = inicio; 
                    return false;
                }
            }else{
                contador = inicio; 
                return false;
            }
        }else{
            contador = inicio; 
            return true;
        }
    }

    public static boolean callout_arg(ArrayList<token> tokens, Parse_pointer padre){
        Parse_pointer hijo_1 = new Parse_pointer(padre, "production", "expr");
        Parse_pointer hijo_2 = new Parse_pointer(padre, "production", "string_literal");

        if(expr(tokens, hijo_1)){
            padre.children.add(hijo_1);
            return true;
        }else  if(string_literal(tokens, hijo_2)){
            padre.children.add(hijo_2);
            return true;
        }else{
            //System.out.println("Error");        
            return false;
        }
    }

    public static boolean bin_op(ArrayList<token> tokens, Parse_pointer padre){
        Parse_pointer hijo_1 = new Parse_pointer(padre, "production", "arith_op");
        Parse_pointer hijo_2 = new Parse_pointer(padre, "production", "rel_op");
        Parse_pointer hijo_3 = new Parse_pointer(padre, "production", "eq_op");
        Parse_pointer hijo_4 = new Parse_pointer(padre, "production", "cond_op");


        if (arith_op(tokens, hijo_1)){
            padre.children.add(hijo_1);
            return true;
        } else  if (rel_op(tokens, hijo_2)){
            padre.children.add(hijo_2);
            return true;
        } else if (eq_op(tokens,hijo_3)){
            padre.children.add(hijo_3);
            return true;
        } else if (cond_op(tokens, hijo_4)){
            padre.children.add(hijo_4);
            return true;
        } else {
            //System.out.println("Error");        
            return false;
        }
    }

    public static boolean arith_op(ArrayList<token> tokens, Parse_pointer padre){
        if(tokens.get(contador).value.equals("+")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: +");
            return true;
        }else if (tokens.get(contador).value.equals("-")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: -");
            return true; 
        }else if (tokens.get(contador).value.equals("*")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: *");
            return true;
        }else if (tokens.get(contador).value.equals("/")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: /");
            return true;
        }else if (tokens.get(contador).value.equals("%")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: %");
            return true;
        }else {
            //System.out.println("Error");
            return false;
        }
    }

    public static boolean rel_op(ArrayList<token> tokens, Parse_pointer padre){

        if(tokens.get(contador).value.equals("<")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: <");
            return true;
        } else if (tokens.get(contador).value.equals(">")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: >");
            return true; 
        }else if (tokens.get(contador).value.equals("<=")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: <=");
            return true; 
        } else if (tokens.get(contador).value.equals(">=")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: >=");
            return true; 
        } else {
            //System.out.println("Error");
            return false;
        }      
    }

    public static boolean eq_op(ArrayList<token> tokens, Parse_pointer padre){
        if(tokens.get(contador).value.equals("==")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: ==");
            return true;
        } else if (tokens.get(contador).value.equals("!=")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: !=");
            return true;
        } else {
            //System.out.println("Error");
            return false;
        }
    }

    public static boolean cond_op(ArrayList<token> tokens, Parse_pointer padre){
        if(tokens.get(contador).value.equals("&&")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            contador +=1;
            System.out.println("Parseado: &&");
            return true;
        } else if (tokens.get(contador).value.equals("||")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            contador +=1;
            System.out.println("Parseado: ||");
            return true;
        } else {
            //System.out.println("Error");
            return false;
        }
    }

    public static boolean literal(ArrayList<token> tokens, Parse_pointer padre){
        
        Parse_pointer hijo_1 = new Parse_pointer(padre, "production", "int_literal");
        Parse_pointer hijo_2 = new Parse_pointer(padre, "production", "char_literal");
        Parse_pointer hijo_3 = new Parse_pointer(padre, "production", "bool_literal");

        if (int_literal(tokens, hijo_1)){
            padre.children.add(hijo_1);      
            return true;
        } else  if (char_literal(tokens, hijo_2)){
            padre.children.add(hijo_2); 
            return true;
        } else  if (bool_literal(tokens, hijo_3)){
            padre.children.add(hijo_3); 
            return true;
        } else {
            //System.out.println("Error");        
            return false;
        }
    }

    public static boolean id(ArrayList<token> tokens, Parse_pointer padre){
        if (tokens.get(contador).type.equals("Identifier")){
            
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado type: <Identifier>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }
    }

    public static boolean char_literal(ArrayList<token> tokens, Parse_pointer padre){
        if(tokens.get(contador).type.equals("Char_Literal")){
            
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado " + tokens.get(contador).value);
            contador += 1;
            return true;
        }else{
            return false;
        }
    }

    public static boolean int_literal(ArrayList<token> tokens, Parse_pointer padre){
        if (tokens.get(contador).type.equals("DECIMAL")){
            
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            
            System.out.println("Parseado type: <int>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }
    }

    public static boolean hex_literal(ArrayList<token> tokens, Parse_pointer padre){
        if (tokens.get(contador).type.equals("HEXADECIMAL")){
            
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado type: <hex>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }

}

    public static boolean string_literal(ArrayList<token> tokens, Parse_pointer padre){
        if (tokens.get(contador).type.equals("String_Literal")){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            System.out.println("Parseado type: <String_Literal>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }
}

    public static boolean bool_literal(ArrayList<token> tokens, Parse_pointer padre){
        if (tokens.get(contador).value.equals("true") || tokens.get(contador).value.equals("false") ){
            Parse_pointer hijo_1 = new Parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            System.out.println("Parseado type: <bool>");  
            contador += 1;      
            return true;
        } else {
   
            return false;
   
        }
    }
}
