/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import compiler.scanner.Scannerc;
import java.io.FileNotFoundException;
import java.io.IOException;
import parser.Parser;
import parser.parser2;

/**
 *
 * @author joseg
 */
public class Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scannerc lexer = new Scannerc();
        lexer.main(new String[0]);
        System.out.println(lexer.token_stream);
        
        parser2 parser = new parser2();
        parser.main(new String[0]);
        
        }
    
}
