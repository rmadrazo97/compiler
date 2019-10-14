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
                parse_pointer hijo_2 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1;
                
                if(tokens.get(contador).value.equals("{")){
                    System.out.println("Parseado: {");
                    parse_pointer hijo_3 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_3);
                    contador += 1;
                    
                    //llamada a field decl 
                    boolean continuar = true;
                    while(continuar){
                        parse_pointer hijo_4 = new parse_pointer(padre, "produccion", "field_decl");
                        continuar = field_decl(tokens, hijo_4);
                        if(continuar){
                            padre.children.add(hijo_4);
                        }
                    }
                    
                    boolean continuar2 = true;
                    while(continuar2){
                        parse_pointer hijo_5 = new parse_pointer(padre, "produccion", "method_decl");
                        continuar2 = method_decl(tokens, hijo_5);
                        if(continuar2){
                            padre.children.add(hijo_5);
                        }
                    }
                    
                    if(tokens.get(contador).value.equals("}")){
                        System.out.println("Parseado: }");
                        parse_pointer hijo_6 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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

    public static boolean field_decl(ArrayList<token> tokens){
        int inicio = contador;
        
        if(type(tokens)){
            if(field_decl_alt(tokens)){
                boolean continuar = true;
                while(continuar){
                    if(tokens.get(contador).value.equals(",")){
                        System.out.println("Parseado: ,");
                        contador += 1;
                        continuar = field_decl_alt(tokens);
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

    public static boolean field_decl_alt(ArrayList<token> tokens, parse_pointer padre){
        int inicio = contador;
        
        parse_pointer hijo_1 = new parse_pointer(padre, "produccion", "id");
        if(id(tokens, hijo_1)){
            padre.children.add(hijo_1);
            if(tokens.get(contador).value.equals("[")){
                System.out.println("Parseado: [");
                parse_pointer hijo_2 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1;
            
                parse_pointer hijo_3 = new parse_pointer(padre, "produccion", "int_literal");
                if(int_literal(tokens, hijo_3)){
                    padre.children.add(hijo_3);
                    if(tokens.get(contador).value.equals("]")){
                        System.out.println("Parseado: ]");
                        parse_pointer hijo_4 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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

    public static boolean method_decl(ArrayList<token> tokens){
        int inicio = contador; 
        
        if(type(tokens) || tokens.get(contador).value.equals("void")){
            if(tokens.get(contador).value.equals("void")){
                contador += 1;
            }
            if(id(tokens)){
                if(tokens.get(contador).value.equals("(")){
                    System.out.println("Parseado: (");
                    contador += 1;
                    
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        contador += 1;
                        
                        if(block(tokens)){
                            return true; 
                        }else{
                            contador = inicio; 
                            return false;
                        }
                    }else{
                        
                        if(method_decl_alt(tokens)){
                            if(tokens.get(contador).value.equals(")")){
                                System.out.println("Parseado: )");
                                contador += 1;
                    
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

    public static boolean method_decl_alt(ArrayList<token> tokens, parse_pointer padre){
        
        parse_pointer hijo_1 = new parse_pointer(padre, "production", "type");
        if(type(tokens, hijo_1)){
            padre.children.add(hijo_1);
            parse_pointer hijo_2 = new parse_pointer(padre, "production", "id");
            if(id(tokens, hijo_2)){
                padre.children.add(hijo_2);
                boolean continuar = true; 
                int inicio = contador;
                while(continuar){
                    if(tokens.get(contador).value.equals(",")){
                        System.out.println("Parseado , ");
                        parse_pointer hijo_3 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_3);
                        contador += 1;
                        
                        parse_pointer hijo_4 = new parse_pointer(padre, "production", "type");
                        if(type(tokens, hijo_4)){
                            padre.children.add(hijo_4);
                            parse_pointer hijo_5 = new parse_pointer(padre, "production", "id");
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

    public static boolean block(ArrayList<token> tokens){
        
        if(tokens.get(contador).value.equals("{")){
            System.out.println("Parseado: {");
            contador += 1;
            
            boolean continuar = true;
            while(continuar){
                continuar = var_decl(tokens);
            }
            
            boolean continuar2 = true;
            while(continuar2){
                continuar2 = statement(tokens);
            }
            
            if(tokens.get(contador).value.equals("}")){
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

    public static boolean var_decl(ArrayList<token> tokens, parse_pointer padre){
        
        int inicio = contador; 
        
        parse_pointer hijo_1 = new parse_pointer(padre, "production", "type");
        if(type(tokens, hijo_1)){
            padre.children.add(hijo_1);
            parse_pointer hijo_2 = new parse_pointer(padre, "production", "id");
            if(id(tokens, hijo_2)){
                padre.children.add(hijo_2);
                boolean continuar = true; 
                while(continuar){
                    if(tokens.get(contador).value.equals(",")){
                        System.out.println("Parseado: ,");
                        parse_pointer hijo_3 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_3);
                        contador += 1;
                
                        parse_pointer hijo_4 = new parse_pointer(padre, "production", "id");
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
                    parse_pointer hijo_5 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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

    public static boolean type(ArrayList<token> tokens){
        
        String value = tokens.get(contador).value;
        
        if(value.equals("int") || value.equals("boolean")){
            System.out.println("Parseado: " + value);
            contador += 1;
            return true;
        }else{
            return false;
        }
    }

    public static boolean statement(ArrayList<token> tokens, parse_pointer padre){
        int inicio = contador; 
        
        parse_pointer hijo_23 = new parse_pointer(padre, "production", "block");
        String value = tokens.get(contador).value;
        if(value.equals("if")){
            System.out.println("Parseado: if");
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador += 1;
            
            if(tokens.get(contador).value.equals("(")){
                System.out.println("Parseado: (");
                parse_pointer hijo_2 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1; 
                
                parse_pointer hijo_3 = new parse_pointer(padre, "production", "expr");
                if(expr(tokens, hijo_3)){
                    padre.children.add(hijo_3);
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        parse_pointer hijo_4 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_4);
                        contador += 1;
                        
                        parse_pointer hijo_5 = new parse_pointer(padre, "production", "block");
                        if(block(tokens, hijo_5)){
                            padre.children.add(hijo_5);
                            if(tokens.get(contador).value.equals("else")){
                                System.out.println("Parseado: else");
                                parse_pointer hijo_6 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                padre.children.add(hijo_6);
                                contador += 1; 
                                
                                parse_pointer hijo_7 = new parse_pointer(padre, "production", "block");
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
            parse_pointer hijo_8 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_8);
            contador += 1; 
            
            parse_pointer hijo_9 = new parse_pointer(padre, "production", "id");
            if(id(tokens, hijo_9)){
                padre.children.add(hijo_9);
                if(tokens.get(contador).value.equals("=")){
                    System.out.println("Parseado: =");
                    parse_pointer hijo_10 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_10);
                    contador += 1; 
                    
                    parse_pointer hijo_11 = new parse_pointer(padre, "production", "expr");
                    if(expr(tokens, hijo_11)){
                        padre.children.add(hijo_11);
                        if(tokens.get(contador).value.equals(",")){
                            System.out.println("Parseado: ,");
                            parse_pointer hijo_12 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                            padre.children.add(hijo_12);
                            contador += 1;
                            
                            parse_pointer hijo_13 = new parse_pointer(padre, "production", "expr");
                            if(expr(tokens, hijo_13)){
                                padre.children.add(hijo_13);
                                parse_pointer hijo_14 = new parse_pointer(padre, "production", "block");
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
            parse_pointer hijo_15 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_15);
            contador += 1;
            
            parse_pointer hijo_16 = new parse_pointer(padre, "production", "expr");
            if(expr(tokens, hijo_16)){
                padre.children.add(hijo_16);
                if(tokens.get(contador).value.equals(";")){
                    System.out.println("Parseado: ;");
                    parse_pointer hijo_17 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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
                    parse_pointer hijo_18 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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
            parse_pointer hijo_19 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_19);
            contador += 1; 
            
            if(tokens.get(contador).value.equals(";")){
                System.out.println("Parseado: ;");
                parse_pointer hijo_20 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_20);
                contador += 1;
                return true;
            }else {
                contador = inicio;
                return false;
            }
        }else if(value.equals("continue")){
            System.out.println("Parseado: continue");
            parse_pointer hijo_21 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_21);
            contador += 1;
            
            if(tokens.get(contador).value.equals(";")){
                System.out.println("Parseado: ;");
                parse_pointer hijo_22 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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
            parse_pointer hijo_24 = new parse_pointer(padre, "production", "method_call");
            if(method_call(tokens, hijo_24)){
                padre.children.add(hijo_24);
                if(tokens.get(contador).value.equals(";")){
                    System.out.println("Parseado: ;");
                    parse_pointer hijo_25 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_25);
                    contador += 1; 
                    
                    return true;
                }else {
                    contador = inicio; 
                    return false;
                }
            }else{
                contador = inicio2;
                parse_pointer hijo_26 = new parse_pointer(padre, "production", "location");
                if(location(tokens, hijo_26)){
                    padre.children.add(hijo_26);
                    parse_pointer hijo_27 = new parse_pointer(padre, "production", "assign_op");
                    if(assign_op(tokens, hijo_27)){
                        padre.children.add(hijo_27);
                        parse_pointer hijo_28 = new parse_pointer(padre, "production", "expr");
                        if(expr(tokens, hijo_28)){
                            padre.children.add(hijo_28);
                            if(tokens.get(contador).value.equals(";")){
                                System.out.println("Parseado: ;");
                                parse_pointer hijo_29 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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

    public static boolean assign_op(ArrayList<token> tokens){
        String value = tokens.get(contador).value;
        
        if(value.equals("=") || value.equals("+=") || value.equals("-=")){
            System.out.println("Parseado: " + value);
            contador += 1;
            return true;
        }else{
            return false;
        }
    }

    public static boolean method_call(ArrayList<token> tokens, parse_pointer padre){
        int inicio = contador; 
        
        if(tokens.get(contador).value.equals("callout")){
            System.out.println("Parseado: callout");
            parse_pointer hijo_1 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
            padre.children.add(hijo_1);
            contador += 1;
            
            if(tokens.get(contador).value.equals("(")){
                System.out.println("Parseado: (");
                parse_pointer hijo_2 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1; 
                
                parse_pointer hijo_3 = new parse_pointer(padre, "production", "string_literal");
                if(string_literal(tokens, hijo_3)){
                    padre.children.add(hijo_3);
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        parse_pointer hijo_4 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_4);
                        contador += 1; 
                        
                        return true;
                    }else{
                        if(tokens.get(contador).value.equals(",")){
                            parse_pointer hijo_5 = new parse_pointer(padre, "production", "callout_arg");
                            if(callout_arg(tokens, hijo_5)){
                                padre.children.add(hijo_5);
                                boolean continuar = true;
                                while(continuar){
                                    if(tokens.get(contador).value.equals(",")){
                                        System.out.println("Parseado: ,");
                                        parse_pointer hijo_6 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                        padre.children.add(hijo_6);
                                        contador += 1;
                                        
                                        parse_pointer hijo_7 = new parse_pointer(padre, "production", "callout_arg");
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
                                    parse_pointer hijo_8 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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
            parse_pointer hijo_9 = new parse_pointer(padre, "production", "method_name");
            if(method_name(tokens, hijo_9)){
                padre.children.add(hijo_9);
                if(tokens.get(contador).value.equals("(")){
                    System.out.println("Parseado: (");
                    parse_pointer hijo_10 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                    padre.children.add(hijo_10);
                    contador += 1;
                
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        parse_pointer hijo_11 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                        padre.children.add(hijo_11);
                        contador += 1;
                        return true;
                    }else{
                        parse_pointer hijo_12 = new parse_pointer(padre, "production", "expr");
                        if(expr(tokens, hijo_12)){
                            padre.children.add(hijo_12);
                            boolean continuar = true;
                            while(continuar){
                                if(tokens.get(contador).value.equals(",")){
                                    System.out.println("Parseado: ,");
                                    parse_pointer hijo_13 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                                    padre.children.add(hijo_13);
                                    contador += 1;
                                    parse_pointer hijo_14 = new parse_pointer(padre, "production", "expr");
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
                                parse_pointer hijo_15 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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

    public static boolean method_name(ArrayList<token> tokens){
        return id(tokens);
    }
    
    public static boolean location(ArrayList<token> tokens, parse_pointer padre){
        int inicio = contador;
        parse_pointer hijo_1 = new parse_pointer(padre, "production", "id");
        if(id(tokens, hijo_1)){
            padre.children.add(hijo_1);
            if(tokens.get(contador).value.equals("[")){
                System.out.println("Parseado [");
                parse_pointer hijo_2 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
                padre.children.add(hijo_2);
                contador += 1; 
                
                parse_pointer hijo_3 = new parse_pointer(padre, "production", "expr");
                if(expr(tokens, hijo_3)){
                    padre.children.add(hijo_3);
                    if(tokens.get(contador).value.equals("]")){
                        System.out.println("Parseado ]");
                        parse_pointer hijo_4 = new parse_pointer(padre, tokens.get(contador).type, tokens.get(contador).value);
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

    public static boolean expr(ArrayList<token> tokens){
        int inicio = contador; 
        if(tokens.get(contador).value.equals("-")){
            System.out.println("Parseado -");
            contador += 1; 
            
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
        }else if(tokens.get(contador).value.equals("!")){
            System.out.println("Parseado !");
            contador += 1;
            
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
        }else if(tokens.get(contador).value.equals("(")){
            if(expr(tokens)){
                if(tokens.get(contador).value.equals(")")){
                    System.out.println("Parseado )");
                    contador += 1; 
                    
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
                return false; 
            }
        }else if(location(tokens)){
            if(expr_alt(tokens)){
                return true; 
            }else{
                contador = inicio;
                return false;
            }
        }else if(method_call(tokens)){
            if(expr_alt(tokens)){
                return true; 
            }else{
                contador = inicio;
                return false;
            }
        }else if(literal(tokens)){
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
    }  

    public static boolean expr_alt(ArrayList<token> tokens, parse_pointer padre){
        int inicio = contador;
        
        parse_pointer hijo_1 = new parse_pointer(padre, "production", "bin_op");
        if(bin_op(tokens, hijo_1)){
            padre.children.add(hijo_1);
            parse_pointer hijo_2 = new parse_pointer(padre, "production", "expr");
             if(expr(tokens, hijo_2)){
                 padre.children.add(hijo_2);
                 parse_pointer hijo_3 = new parse_pointer(padre, "production", "expr_alt");
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

    public static boolean callout_arg(ArrayList<token> tokens){
        if(expr(tokens)){
            return true;
        }else  if(string_literal(tokens)){
            return true;
        }else{
            //System.out.println("Error");        
            return false;
        }
    }

    public static boolean bin_op(ArrayList<token> tokens){
        if (arith_op(tokens)){
            return true;
        } else  if (rel_op(tokens)){
            return true;
        } else if (eq_op(tokens)){
            return true;
        } else if (cond_op(tokens)){
            return true;
        } else {
            //System.out.println("Error");        
            return false;
        }
    }

    public static boolean arith_op(ArrayList<token> tokens){
        if(tokens.get(contador).value.equals("+")){
            contador +=1;
            System.out.println("Parseado: +");
            return true;
        }else if (tokens.get(contador).value.equals("-")){
            contador +=1;
            System.out.println("Parseado: -");
            return true; 
        }else if (tokens.get(contador).value.equals("*")){
            contador +=1;
            System.out.println("Parseado: *");
            return true;
        }else if (tokens.get(contador).value.equals("/")){
            contador +=1;
            System.out.println("Parseado: /");
            return true;
        }else if (tokens.get(contador).value.equals("%")){
            contador +=1;
            System.out.println("Parseado: %");
            return true;
        }else {
            //System.out.println("Error");
            return false;
        }
    }

    public static boolean rel_op(ArrayList<token> tokens){

        if(tokens.get(contador).value.equals("<")){
            contador +=1;
            System.out.println("Parseado: <");
            return true;
        } else if (tokens.get(contador).value.equals(">")){
            contador +=1;
            System.out.println("Parseado: >");
            return true; 
        }else if (tokens.get(contador).value.equals("<=")){
            contador +=1;
            System.out.println("Parseado: <=");
            return true; 
        } else if (tokens.get(contador).value.equals(">=")){
            contador +=1;
            System.out.println("Parseado: >=");
            return true; 
        } else {
            //System.out.println("Error");
            return false;
        }      
    }

    public static boolean eq_op(ArrayList<token> tokens){
        if(tokens.get(contador).value.equals("==")){
            contador +=1;
            System.out.println("Parseado: ==");
            return true;
        } else if (tokens.get(contador).value.equals("!=")){
            contador +=1;
            System.out.println("Parseado: !=");
            return true;
        } else {
            //System.out.println("Error");
            return false;
        }
    }

    public static boolean cond_op(ArrayList<token> tokens){
        if(tokens.get(contador).value.equals("&&")){
            contador +=1;
            System.out.println("Parseado: &&");
            return true;
        } else if (tokens.get(contador).value.equals("||")){
            contador +=1;
            System.out.println("Parseado: ||");
            return true;
        } else {
            //System.out.println("Error");
            return false;
        }
    }

    public static boolean literal(ArrayList<token> tokens){
        if (int_literal(tokens)){      
            return true;
        } else  if (char_literal(tokens)){
            return true;
        } else  if (bool_literal(tokens)){
            return true;
        } else {
            //System.out.println("Error");        
            return false;
        }
    }

    public static boolean id(ArrayList<token> tokens){
        if (tokens.get(contador).type.equals("Identifier")){
            System.out.println("Parseado type: <Identifier>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }
    }

    public static boolean char_literal(ArrayList<token> tokens){
        if(tokens.get(contador).type.equals("Char_Literal")){
            System.out.println("Parseado " + tokens.get(contador).value);
            contador += 1;
            return true;
        }else{
            return false;
        }
    }

    public static boolean int_literal(ArrayList<token> tokens){
        if (tokens.get(contador).type.equals("DECIMAL")){
            System.out.println("Parseado type: <int>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }
    }

    public static boolean hex_literal(ArrayList<token> tokens){
        if (tokens.get(contador).type.equals("HEXADECIMAL")){
            System.out.println("Parseado type: <hex>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }

}

    public static boolean string_literal(ArrayList<token> tokens){
        if (tokens.get(contador).type.equals("String_Literal")){
            System.out.println("Parseado type: <String_Literal>");  
            contador += 1;      
            return true;
        } else {
            return false;
        }
}

    public static boolean bool_literal(ArrayList<token> tokens){
        if (tokens.get(contador).value.equals("true") || tokens.get(contador).value.equals("false") ){
            System.out.println("Parseado type: <bool>");  
            contador += 1;      
            return true;
        } else {
   
            return false;
   
        }
    }
}
