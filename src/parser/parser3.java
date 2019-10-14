/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import compiler.scanner.Scannerc;
import compiler.scanner.token;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author joseg
 */
public class parser3 {
    static int contador = 0;
   
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        ArrayList<token> token_stream = Scannerc.token_stream;
        
        parse_pointer puntero_padre = new parse_pointer(null, "production", "program");
        program(token_stream, puntero_padre);
    }
    
    public static void program(ArrayList<token> tokens, parse_pointer padre){
        //class
        if(tokens.get(contador).value.equals("class")){
            System.out.println("Parseado: class");
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador += 1;
            
            if(tokens.get(contador).value.equals("Program")){
                System.out.println("Parseado Program");
                contador += 1;
                
                if(tokens.get(contador).value.equals("{")){
                    System.out.println("Parseado: {");
                    contador += 1;
                    
                    //llamada a field decl 
                    boolean continuar = true;
                    while(continuar){
                        continuar = field_decl(tokens);
                    }
                    
                    boolean continuar2 = true;
                    while(continuar2){
                        continuar2 = method_decl(tokens);
                    }
                    
                    if(tokens.get(contador).value.equals("}")){
                        System.out.println("Parseado: }");
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

    public static boolean field_decl(ArrayList<token> tokens, parse_pointer padre){
       
        int inicio = contador;
        
        // padre viene de la llamada anerior -> padre

        if(type(tokens, padre)){
            
            // hijo_1 -> type.
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "type");
            padre.children.add(hijo_1);

            
            if(field_decl_alt(tokens)){
                
                // hijo_2 -> field_decl_alt
                parse_pointer hijo_2 = new parse_pointer(padre, "production", "field_decl_alt");
                padre.children.add(hijo_2);

                boolean continuar = true;
                while(continuar){
                    if(tokens.get(contador).value.equals(",")){
                        // hijo_3
                        parse_pointer hijo_3 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_3);

                        System.out.println("Parseado: ,");

                        contador += 1;
                       
                        //hijo_4
                        parse_pointer hijo_4 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        
                        continuar = field_decl_alt(tokens);
                        // if continuar add as children
                        if (continuar){
                            padre.children.add(hijo_4); 
                        }
                        
                    }else{
                        break;
                    }
                }
                
                if(tokens.get(contador).value.equals(";")){
                    
                    parse_pointer hijo_5 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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

    public static boolean field_decl_alt(ArrayList<token> tokens){
        int inicio = contador;
        
        if(id(tokens)){
            if(tokens.get(contador).value.equals("[")){
                System.out.println("Parseado: [");
                contador += 1;
            
                if(int_literal(tokens)){
                    if(tokens.get(contador).value.equals("]")){
                        System.out.println("Parseado: ]");
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

    public static boolean method_decl(ArrayList<token> tokens, parse_pointer padre){
        int inicio = contador; 
        
        if(type(tokens) || tokens.get(contador).value.equals("void")){

            if (tokens.get(contador).value.equals("void")){
               
                parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_1); 

            } else {
                parse_pointer hijo_1 = new parse_pointer(padre, "production", "type");
                padre.children.add(hijo_1); 


            }

            if(tokens.get(contador).value.equals("void")){
                
                parse_pointer hijo_2 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2); 

                contador += 1;
            }
            if(id(tokens)){
                if(tokens.get(contador).value.equals("(")){
                    
                    parse_pointer hijo_3 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_3); 
                    
                    System.out.println("Parseado: (");
                    contador += 1;
                    
                    if(tokens.get(contador).value.equals(")")){
                        
                        parse_pointer hijo_4 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_4);
                        
                        System.out.println("Parseado: )");
                        contador += 1;
                        
                        if(block(tokens)){
                            
                            parse_pointer hijo_5 = new parse_pointer(padre, "production", "block" );
                            padre.children.add(hijo_5);
                            
                            return true; 
                        }else{
                            contador = inicio; 
                            return false;
                        }
                    }else{
                        
                        if(method_decl_alt(tokens)){
                            
                            parse_pointer hijo_6 = new parse_pointer(padre, "production", "method_decl_alt");
                            padre.children.add(hijo_6);
                            
                            if(tokens.get(contador).value.equals(")")){
                                
                                parse_pointer hijo_7 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                padre.children.add(hijo_7);
                                
                                System.out.println("Parseado: )");
                                contador += 1;
                    
                                if(block(tokens)){

                                    parse_pointer hijo_8 = new parse_pointer(padre, "production", "block" );
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

    public static boolean method_decl_alt(ArrayList<token> tokens){
        
        if(type(tokens)){
            if(id(tokens)){
                boolean continuar = true; 
                int inicio = contador;
                while(continuar){
                    if(tokens.get(contador).value.equals(",")){
                        System.out.println("Parseado , ");
                        contador += 1;
                        
                        if(type(tokens)){
                            if(id(tokens)){
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

    public static boolean block(ArrayList<token> tokens, parse_pointer padre){
        
        if(tokens.get(contador).value.equals("{")){
            
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado: {");
            contador += 1;
            
            boolean continuar = true;
            while(continuar){
                
                parse_pointer hijo_2 = new parse_pointer(padre, "production", "var_decl");
                continuar = var_decl(tokens);

                if (continuar){
                    padre.children.add(hijo_2);
                } 
            }
            
            boolean continuar2 = true;
            while(continuar2){
                
                parse_pointer hijo_3 = new parse_pointer(padre, "production", "var_decl");

                continuar2 = statement(tokens);
                if (continuar){
                    padre.children.add(hijo_3);
                }
            }
            
            if(tokens.get(contador).value.equals("}")){
                
                parse_pointer hijo_4 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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

    public static boolean var_decl(ArrayList<token> tokens){
        
        int inicio = contador; 
        
        if(type(tokens)){
            if(id(tokens)){
                boolean continuar = true; 
                while(continuar){
                    if(tokens.get(contador).value.equals(",")){
                        System.out.println("Parseado: ,");
                        contador += 1;
                
                        continuar = id(tokens);
                    }else{
                        break;
                    }
                }
                if(tokens.get(contador).value.equals(";")){
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

    public static boolean type(ArrayList<token> tokens, parse_pointer padre){
        
        String value = tokens.get(contador).value;
        
        if(value.equals("int") || value.equals("boolean")){
            
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado: " + value);
            contador += 1;
            return true;
        }else{
            return false;
        }
    }

    public static boolean statement(ArrayList<token> tokens){
        int inicio = contador; 
        
        String value = tokens.get(contador).value;
        if(value.equals("if")){
            System.out.println("Parseado: if");
            contador += 1;
            
            if(tokens.get(contador).value.equals("(")){
                System.out.println("Parseado: (");
                contador += 1; 
                
                if(expr(tokens)){
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        contador += 1;
                        
                        if(block(tokens)){
                            if(tokens.get(contador).value.equals("else")){
                                System.out.println("Parseado: else");
                                contador += 1; 
                                
                                if(block(tokens)){
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
            contador += 1; 
            
            if(id(tokens)){
                if(tokens.get(contador).value.equals("=")){
                    System.out.println("Parseado: =");
                    contador += 1; 
                    
                    if(expr(tokens)){
                        if(tokens.get(contador).value.equals(",")){
                            System.out.println("Parseado: ,");
                            contador += 1;
                            
                            if(expr(tokens)){
                                if(block(tokens)){
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
            contador += 1;
            
            if(expr(tokens)){
                if(tokens.get(contador).value.equals(";")){
                    System.out.println("Parseado: ;");
                    contador += 1; 
                    return true;
                }else {
                    contador = inicio;
                    return false;
                }
            }else{
                if(tokens.get(contador).value.equals(";")){
                    System.out.println("Parseado: ;");
                    contador += 1; 
                    return true; 
                }else {
                    contador = inicio; 
                    return false;
                }
            }
        }else if(value.equals("break")){
            System.out.println("Parseado: break");
            contador += 1; 
            
            if(tokens.get(contador).value.equals(";")){
                System.out.println("Parseado: ;");
                contador += 1;
                return true;
            }else {
                contador = inicio;
                return false;
            }
        }else if(value.equals("continue")){
            System.out.println("Parseado: continue");
            contador += 1;
            
            if(tokens.get(contador).value.equals(";")){
                System.out.println("Parseado: ;");
                contador += 1;
                return true;
            }else {
                contador = inicio; 
                return false;
            }
        }else if(block(tokens)){
            return true;
        }else{
            int inicio2 = contador;
            if(method_call(tokens)){
                if(tokens.get(contador).value.equals(";")){
                    System.out.println("Parseado: ;");
                    contador += 1; 
                    
                    return true;
                }else {
                    contador = inicio; 
                    return false;
                }
            }else{
                contador = inicio2;
                if(location(tokens)){
                    if(assign_op(tokens)){
                        if(expr(tokens)){
                            if(tokens.get(contador).value.equals(";")){
                                System.out.println("Parseado: ;");
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

    public static boolean assign_op(ArrayList<token> tokens, parse_pointer padre){
        String value = tokens.get(contador).value;
        
        if(value.equals("=") || value.equals("+=") || value.equals("-=")){

            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado: " + value);
            contador += 1;
            return true;
        }else{
            return false;
        }
    }

    public static boolean method_call(ArrayList<token> tokens){
        int inicio = contador; 
        
        if(tokens.get(contador).value.equals("callout")){
            System.out.println("Parseado: callout");
            contador += 1;
            
            if(tokens.get(contador).value.equals("(")){
                System.out.println("Parseado: (");
                contador += 1; 
                
                if(string_literal(tokens)){
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        contador += 1; 
                        
                        return true;
                    }else{
                        if(tokens.get(contador).value.equals(",")){
                            if(callout_arg(tokens)){
                                boolean continuar = true;
                                while(continuar){
                                    if(tokens.get(contador).value.equals(",")){
                                        System.out.println("Parseado: ,");
                                        contador += 1;
                                        continuar = callout_arg(tokens);
                                    }else{
                                        break;
                                    }
                                }
                                
                                if(tokens.get(contador).value.equals(")")){
                                    System.out.println("Parseado: )");
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
            if(method_name(tokens)){
                if(tokens.get(contador).value.equals("(")){
                    System.out.println("Parseado: (");
                    contador += 1;
                
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        contador += 1;
                        return true;
                    }else{
                        if(expr(tokens)){
                            boolean continuar = true;
                            while(continuar){
                                if(tokens.get(contador).value.equals(",")){
                                    System.out.println("Parseado: ,");
                                    contador += 1;
                                    continuar = expr(tokens);
                                }else{
                                    break;
                                }
                            }
                            
                            if(tokens.get(contador).value.equals(")")){
                                System.out.println("Parseado: )");
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

    public static boolean method_name(ArrayList<token> tokens, parse_pointer padre){
        
        if (id(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "id");
            padre.children.add(hijo_1);
        }
        
    }
    
    public static boolean location(ArrayList<token> tokens){
        int inicio = contador; 
        if(id(tokens)){
            if(tokens.get(contador).value.equals("[")){
                System.out.println("Parseado [");
                contador += 1; 
                
                if(expr(tokens)){
                    if(tokens.get(contador).value.equals("]")){
                        System.out.println("Parseado ]");
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

    public static boolean expr(ArrayList<token> tokens, parse_pointer padre){
        int inicio = contador; 
        if(tokens.get(contador).value.equals("-")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            System.out.println("Parseado -");
            contador += 1; 
            
            if(expr(tokens)){
                
                parse_pointer hijo_2 = new parse_pointer(padre, "production", "expr");
                padre.children.add(hijo_2);
                
                if(expr_alt(tokens)){
                    
                    parse_pointer hijo_3 = new parse_pointer(padre, "production", "expr_alt");
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
            parse_pointer hijo_4 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_4);
            System.out.println("Parseado !");
            contador += 1;
            
            if(expr(tokens)){
                parse_pointer hijo_5 = new parse_pointer(padre, "production", "expr");
                padre.children.add(hijo_5);
                if(expr_alt(tokens)){
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
            parse_pointer hijo_6 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_6);
            
            if(expr(tokens)){
                
                parse_pointer hijo_7 = new parse_pointer(padre, "production", "expr");
                padre.children.add(hijo_7);

                if(tokens.get(contador).value.equals(")")){

                    parse_pointer hijo_8 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_8);

                    System.out.println("Parseado )");
                    contador += 1; 
                    
                    if(expr_alt(tokens)){
                        
                        parse_pointer hijo_9 = new parse_pointer(padre, "production", "expr_alt");
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
        }else if(location(tokens)){
            
            parse_pointer hijo_10 = new parse_pointer(padre, "production", "location");
            padre.children.add(hijo_10);

            if(expr_alt(tokens)){
                
                parse_pointer hijo_11 = new parse_pointer(padre, "production", "expr_alt");
                padre.children.add(hijo_11);

                return true; 
            }else{
                contador = inicio;
                return false;
            }
        }else if(method_call(tokens)){
            
            parse_pointer hijo_12 = new parse_pointer(padre, "production", "method_call");
            padre.children.add(hijo_12);
            
            if(expr_alt(tokens)){
                
                parse_pointer hijo_13 = new parse_pointer(padre, "production", "expr_alt");
                padre.children.add(hijo_13);

                return true; 
            }else{
                contador = inicio;
                return false;
            }
        }else if(literal(tokens)){

            parse_pointer hijo_14 = new parse_pointer(padre, "production", "literal");
            padre.children.add(hijo_14);

            if(expr_alt(tokens)){

                parse_pointer hijo_15 = new parse_pointer(padre, "production", "expr_alt");
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

    public static boolean expr_alt(ArrayList<token> tokens){
        int inicio = contador;
        if(bin_op(tokens)){
            if(expr(tokens)){
                if(expr_alt(tokens)){
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

    public static boolean callout_arg(ArrayList<token> tokens, parse_pointer padre){

        if(expr(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "expr");
            padre.children.add(hijo_1);
            return true;
        }else  if(string_literal(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "string_literal");
            padre.children.add(hijo_1);
            return true;
        }else{
            //System.out.println("Error");        
            return false;
        }
    }

    public static boolean bin_op(ArrayList<token> tokens, parse_pointer padre){
        if (arith_op(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "arith_op");
            padre.children.add(hijo_1);
            return true;
        } else  if (rel_op(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "rel_op");
            padre.children.add(hijo_1);
            return true;
        } else if (eq_op(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "eq_op");
            padre.children.add(hijo_1);
            return true;
        } else if (cond_op(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "cond_op");
            padre.children.add(hijo_1);
            return true;
        } else {
            //System.out.println("Error");        
            return false;
        }
    }

    public static boolean arith_op(ArrayList<token> tokens, parse_pointer padre){
        if(tokens.get(contador).value.equals("+")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: +");
            return true;
        }else if (tokens.get(contador).value.equals("-")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: -");
            return true; 
        }else if (tokens.get(contador).value.equals("*")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: *");
            return true;
        }else if (tokens.get(contador).value.equals("/")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: /");
            return true;
        }else if (tokens.get(contador).value.equals("%")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: %");
            return true;
        }else {
            //System.out.println("Error");
            return false;
        }
    }

    public static boolean rel_op(ArrayList<token> tokens, parse_pointer padre){

        if(tokens.get(contador).value.equals("<")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: <");
            return true;
        } else if (tokens.get(contador).value.equals(">")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: >");
            return true; 
        }else if (tokens.get(contador).value.equals("<=")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: <=");
            return true; 
        } else if (tokens.get(contador).value.equals(">=")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: >=");
            return true; 
        } else {
            //System.out.println("Error");
            return false;
        }      
    }

    public static boolean eq_op(ArrayList<token> tokens, parse_pointer padre){
        if(tokens.get(contador).value.equals("==")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: ==");
            return true;
        } else if (tokens.get(contador).value.equals("!=")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador +=1;
            System.out.println("Parseado: !=");
            return true;
        } else {
            //System.out.println("Error");
            return false;
        }
    }

    public static boolean cond_op(ArrayList<token> tokens, parse_pointer padre){
        if(tokens.get(contador).value.equals("&&")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            contador +=1;
            System.out.println("Parseado: &&");
            return true;
        } else if (tokens.get(contador).value.equals("||")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            contador +=1;
            System.out.println("Parseado: ||");
            return true;
        } else {
            //System.out.println("Error");
            return false;
        }
    }

    public static boolean literal(ArrayList<token> tokens, parse_pointer padre){
        if (int_literal(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "int_literal");
            padre.children.add(hijo_1);      
            return true;
        } else  if (char_literal(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "char_literal");
            padre.children.add(hijo_1); 
            return true;
        } else  if (bool_literal(tokens)){
            parse_pointer hijo_1 = new parse_pointer(padre, "production", "bool_literal");
            padre.children.add(hijo_1); 
            return true;
        } else {
            //System.out.println("Error");        
            return false;
        }
    }

    public static boolean id(ArrayList<token> tokens, parse_pointer padre){
        if (tokens.get(contador).type.equals("Identifier")){
            
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado type: <Identifier>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }
    }

    public static boolean char_literal(ArrayList<token> tokens, parse_pointer padre){
        if(tokens.get(contador).type.equals("Char_Literal")){
            
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado " + tokens.get(contador).value);
            contador += 1;
            return true;
        }else{
            return false;
        }
    }

    public static boolean int_literal(ArrayList<token> tokens, parse_pointer padre){
        if (tokens.get(contador).type.equals("DECIMAL")){
            
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            
            System.out.println("Parseado type: <int>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }
    }

    public static boolean hex_literal(ArrayList<token> tokens, parse_pointer padre){
        if (tokens.get(contador).type.equals("HEXADECIMAL")){
            
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);

            System.out.println("Parseado type: <hex>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }

}

    public static boolean string_literal(ArrayList<token> tokens, parse_pointer padre){
        if (tokens.get(contador).type.equals("String_Literal")){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            System.out.println("Parseado type: <String_Literal>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }
}

    public static boolean bool_literal(ArrayList<token> tokens, parse_pointer padre){
        if (tokens.get(contador).value.equals("true") || tokens.get(contador).value.equals("false") ){
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            System.out.println("Parseado type: <bool>");  
            contador += 1;      
            return true;
        } else {
   
            return false;
   
        }
    }
}
