import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class Compiler {

    private static void printLines(String name, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
    }

    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " Output:", pro.getInputStream());
        printLines(command + " Error:", pro.getErrorStream());
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
    }

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-target") || args[i].equals("-t")) {
                String stage = args[i + 1];
                // System.out.println(stage);

                if (stage.equals("scan") || stage.equals("scanner")) {

                    System.out.println("This run and compile scanner...");

                    try {
                        runProcess("javac token.java");
                        runProcess("javac Scannerc.java");
                        runProcess("java Scannerc");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*
                     * Scannerc lexer = new Scannerc(); lexer.main(new String[0]);
                     * System.out.println(lexer.token_stream);
                    */

                } else if (stage.equals("parser")) {
                    System.out.println("This run and compile parser...");

                    try {
                        //runProcess("javac token.java");
                        //runProcess("javac Scannerc.java");
                        runProcess("javac Parse_pointer.java");
                        runProcess("javac parser.java");
                        runProcess("java parser");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*
                     * Scannerc lexer = new Scannerc(); lexer.main(new String[0]);
                     * System.out.println(lexer.token_stream);
                     * 
                     * parser2 parser = new parser2(); parser.main(new String[0]);
                    */

                } else if (stage.equals("-help") || stage.equals("-h")) {
                    System.out.println("This open the help cli...");

                    System.out.println("\nUsage: \n java Compiler <command> [options]\n");
                    System.out.println("Commands: \n -target, -t \n");
                    System.out.println("General Options: \n scan, scanner\n parser\n");
                    System.out.println("Examples: \n java Compiler -target scan                  This compile and run Scanner.\n java Compiler -target parser                This compile and run Parser.\n");
                } else {
                    System.out.println("Argument not valid.");
                }

            } else if (args[i].equals(null)) {
                // Se instancia el scanner, por ser el default
                /*
                 * Scannerc lexer = new Scannerc(); lexer.main(new String[0]);
                 * System.out.println(lexer.token_stream);
                */

                try {
                    runProcess("javac Scannerc.java");
                    runProcess("java Scannerc");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Success!");
            } else if(args[i].equals("-help") || args[i].equals("-h")){
                System.out.println("This open the help cli...");

                System.out.println("\nUsage: \n java Compiler <command> [options]\n");
                System.out.println("Commands: \n -target, -t \n");
                System.out.println("General Options: \n scan, scanner\n parser\n");
                System.out.println("Examples: \n java Compiler -target scan                  This compile and run Scanner.\n java Compiler -target parser                This compile and run Parser.\n");

            }

        }
    }
}