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
        
        program(token_stream);
    }
    
    public static void program(ArrayList<token> tokens){
        //class
        if(tokens.get(contador).value.equals("class")){
            System.out.println("Parseado: class");
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
                        System.out.println("Error");
                    }
                }else{
                    System.out.println("Error");
                }
            }else{
                System.out.println("Error");
            }
        }else{
            System.out.println("Error");
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

    public static boolean method_decl(ArrayList<token> tokens){
        int inicio = contador; 
        
        if(type(tokens)){
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

    public static boolean type(ArrayList<token> tokens){
        
        String value = tokens.get(contador).value;
        
        if(value.equals("int") || value.equals("boolean") || value.equals("void")){
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
}
