import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.io.IOException;

public class Brain {

    public static void main(String[] args) throws FileNotFoundException, IOException{
        // LEER EL ARCHIVO, ESTO TIENEN QUE CAMBIARLO A SU PATH
        File contenido = new File("Programa1.decaf");
        Scanner scan = new Scanner(contenido);

        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter("Programa1.txt"));
        ArrayList<Character> temporal = new ArrayList<Character>();

        // EMPIEZA EL SCAN PRINCIPAL DEL ARCHIVO, SEGUIMOS TRABAJANDO SOBRE EL TXT
        // EL WHILE SIGNIFICA QUE VA A SEGUIR HASTA QUE SE ACABEN LAS LINEAS EN EL TXT
        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            // YA QUE TIENE LA LINEA, LA VA A EMPEZAR A RECORRER CARACTER POR CARACTER
            for (int i = 0; i < line.length(); i++) {
                char valor = line.charAt(i);

                //ArrayList<Character> temporal = new ArrayList<Character>();
                
                if(line.charAt(i) == '(' || line.charAt(i) == ')' || line.charAt(i) == ',' || line.charAt(i) == ';' || line.charAt(i) == '{' || line.charAt(i) == '}'){
                    temporal.add(' ');
                    temporal.add(line.charAt(i));
                    temporal.add(' ');
                }else{
                    temporal.add(valor);
                }
            }
            temporal.add('\n');

        }

        for (int z = 0; z < temporal.size(); z++) {

            // System.out.println(temporal.get(z));

            outputWriter.write(temporal.get(z));
            // outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();

    }
}