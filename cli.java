import java.util.Scanner;
import java.lang.reflect.*;

public class cli{

    public static void main(String[] args ){

        for(int i = 0; i<args.length; i++ ){

            if(args[i].equals("-target")){
                String stage = args[i+1];
                //System.out.println(stage);

                if(stage.equals("scan")){

                    System.out.println("This should run and compile scanner...");

                    Scannerc lexer = new Scannerc();
                    lexer.main(new String[0]);
                    System.out.println(lexer.token_stream);

                }else if(stage.equals("parser")){
                    System.out.println("This should run and compile parser...");

                    Scannerc lexer = new Scannerc();
                    lexer.main(new String[0]);
                    System.out.println(lexer.token_stream);

                    parser2 parser = new parser2();
                    parser.main(new String[0]);

                }else if(stage.equals("-help") || stage.equals("-h")){
                    System.out.println("This should open the help cli...");
                }else{
                    System.out.println("Argument not valid.");
                }

            
            }else if(args[i].equals(null)){
                // Se instancia el scanner, por ser el default
                Scannerc lexer = new Scannerc();
                lexer.main(new String[0]);
                System.out.println(lexer.token_stream);
                System.out.println("test");
            }

        }

    }



}

/*
 * 
 * String one = args[0]; // =="one" //String two = args[1]; // =="two"
 * 
 * if(one.equals("-target")){ System.out.println("Succes!"); }
 */
