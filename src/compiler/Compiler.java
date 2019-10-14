/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package compiler;

//import compiler.scanner.Scannerc;
import java.io.FileNotFoundException;
import java.io.IOException;
//import parser.Parser;
//import parser.parser2;

/**
 *
 * @author joseg
 */
public class Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {


        for (int i = 0; i < args.length; i++) {     //Se recorre los argumentos

            if (args[i].equals("-target") || args[i].equals("-t")) {        //Verifica si el primero elemento de los argumentos es -target
                String stage = args[i + 1];         //Si encuentra la bandera -target, stage guarda el argumento a correr  
                // System.out.println(stage);

                if (stage.equals("scan") || stage.equals("scanner")) {             //Si el argumento es scan, compilar y correr el scanner

                    System.out.println("This should run and compile scanner...");
                    
                    Scannerc lexer = new Scannerc();
                    lexer.main(new String[0]);
                    System.out.println(lexer.token_stream);
                    

                } else if (stage.equals("parser")) {                // Si el argumento es parser, se compila y se corre el scanner
                    System.out.println("This should run and compile parser...");

                    
                    Scannerc lexer = new Scannerc(); lexer.main(new String[0]);
                    System.out.println(lexer.token_stream); 
                    parser2 parser = new parser2(); parser.main(new String[0]);
                    

                } else if (stage.equals("-help") || stage.equals("-h")) {       // Si l bandera es -help o -h, esntonces deplegar la ayuda
                    System.out.println("\nUsage: \n java Compiler <command> [options]\n");
                    System.out.println("Commands: \n -target, -t \n");
                    System.out.println("General Options: \n scan, scanner\n parser\n");
                    System.out.println("Examples: \n java Compiler -target scan                  This compile and run Scanner.\n java Compiler -target parser                This compile and run Parser.\n");
                } else {
                    System.out.println("Argument not valid.");
                }

            } else if (args[i].equals(null)) {                                  //Si no ingresa nada, se corre el sacnner por default
                // Se instancia el scanner, por ser el default
                
                Scannerc lexer = new Scannerc();
                lexer.main(new String[0]);
                System.out.println(lexer.token_stream);
                System.out.println("test");
            
                System.out.println("Test.");
                
            }

        }



        /*
        Scannerc lexer = new Scannerc();
        lexer.main(new String[0]);
        System.out.println(lexer.token_stream);
        
        parser2 parser = new parser2();
        parser.main(new String[0]);
        */
        
        }
    
}
