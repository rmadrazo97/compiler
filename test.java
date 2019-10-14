import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class test {

    private static void printLines(String name, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
    }

    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
    }

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-target")) {
                String stage = args[i + 1];
                // System.out.println(stage);

                if (stage.equals("scan")) {

                    System.out.println("This should run and compile scanner...");

                    try {
                        runProcess("javac Invoked.java");
                        runProcess("java Invoked");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    /*
                     * Scannerc lexer = new Scannerc(); lexer.main(new String[0]);
                     * System.out.println(lexer.token_stream);
                     */

                } else if (stage.equals("parser")) {
                    System.out.println("This should run and compile parser...");

                    /*
                     * Scannerc lexer = new Scannerc(); lexer.main(new String[0]);
                     * System.out.println(lexer.token_stream);
                     * 
                     * parser2 parser = new parser2(); parser.main(new String[0]);
                     */

                } else if (stage.equals("-help") || stage.equals("-h")) {
                    System.out.println("This should open the help cli...");
                } else {
                    System.out.println("Argument not valid.");
                }

            } else if (args[i].equals(null)) {
                // Se instancia el scanner, por ser el default
                /*
                 * Scannerc lexer = new Scannerc(); lexer.main(new String[0]);
                 * System.out.println(lexer.token_stream);
                 */
                System.out.println("test");
            }

        }
    }
}