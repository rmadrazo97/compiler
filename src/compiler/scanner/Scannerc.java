
package compiler.scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Scannerc {
    public static ArrayList<token> token_stream = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {

        // DECLARACIÓN Y DEFINICIÓN DE VARIABLES DE ALFABETOS Y AUTÓMATAS.
        // ALFABETO DE NÚMEROS
        char numbers[] = { '0', 'x', '-', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
                'A', 'B', 'C', 'D', 'E', 'F' };

        // AUTÓMATA DE NÚMEROS
        int edges_numbers[][] = { { 2, 0, 6, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 3, 4, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 3, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 5, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
                { 5, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 },
                { 3, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

        // ALFABETO DE IDENTIFIERS
        char identifiers[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C',
                'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', '_' };

        // AUTÓMATA DE IDENTIFIERS
        int edges_identifiers[][] = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                        2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 },
                { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                        2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 } };

        // ALFABETO DE STRINGS
        char string_literal[] = { ' ', '!', '#', '$', '%', '&', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2',
                '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', ']',
                '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'o', 'p', 'q', 'r', 's',
                'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~', '\\', '"', '\'', 'n', 't' };

        // AUTÓMATA DE STRINGS
        int edges_string[][] = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0 },

                { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                        2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
                        2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 4, 0, 2, 2 },

                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2 },

                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }

        };

        // AUTÓMATA DE CARACTERES, UTILIZA EL MISMO ALFABETO QUE EL DE STRINGS
        int edges_char[][] = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0 }, // A ->
                                                                                                                 // 1
                { 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
                        3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 0, 5, 0, 0 }, // B ->
                                                                                                                 // 2
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0 }, // C ->
                                                                                                                 // 3
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3 }, // D ->
                                                                                                                 // 4
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // E ->
                                                                                                                 // 5
        };

        // MATRIZ TRIPLE,ES UN ARREGLO QUE CONTIENE A TODOS LOS AUTÓMATAS, PARA PODER
        // REFERENCIARLOS POR EL INDICE
        int[][] automatas[] = { edges_numbers, edges_identifiers, edges_string, edges_char };

        // MATRIZ DOBLE, ESTE ARREGLO CONTIENE A TODOS LOS ALFABETOS
        char[] alfabetos[] = { numbers, identifiers, string_literal };

        // LEER EL ARCHIVO, ESTO TIENEN QUE CAMBIARLO A SU PATH
        File contenido = new File("C:\\Users\\joseg\\OneDrive\\Desktop\\Programa1.decaf");
        Scanner scan = new Scanner(contenido);

        // A ESTOS ARREGLOS LES VOY A IR INSERTANDO LOS LEXEMES Y LOS MENSAJES DE ERROR
        // UN LEXEME ES UN CONJUNTO DE CARACTERES QUE TODAVIA NO HA SIDO CLASIFICADO
        // COMO UN TOKEN
        // UTILIZO LOS ARRAYLIST EN LUGAR DE ARREGLOS NORMALES PORQUE TIENEN FUNCIONES
        // COMO ADD()
        ArrayList<String> lexemes = new ArrayList<String>();
        ArrayList<String> errors = new ArrayList<String>();

        // ESTOS DOS CONTADORES AYUDAN PARA ENVIAR EL MENSAJE DE ERROR CORRECTO
        int contador_linea = 1;
        int contador_columna = 1;

        // VARIABLE QUE VA ACUMULANDO CARACTERES PARA IR EVALUANDO SI ES O NO UN TOKEN
        String semi_token = "";

        // STRING CON EL ERROR QUE DEPENDE DE LOS CONTADORES ANTERIORES
        String error = "";

        // EMPIEZA EL SCAN PRINCIPAL DEL ARCHIVO, SEGUIMOS TRABAJANDO SOBRE EL TXT
        // EL WHILE SIGNIFICA QUE VA A SEGUIR HASTA QUE SE ACABEN LAS LINEAS EN EL TXT
        while (scan.hasNextLine()) {
            // VARIABLE QUE CONTIENE UN STRING DE CADA LINEA QUE VA LEYENDO
            String line = scan.nextLine();

            // YA QUE TIENE LA LINEA, LA VA A EMPEZAR A RECORRER CARACTER POR CARACTER
            for (int i = 0; i < line.length(); i++) {

                // VARIABLE QUE CONTIENE EL CARACTER QUE LE CORRESPONDE ANALIZAR
                char valor = line.charAt(i);

                // VARIABLE QUE VA A GUARDAR EL CARACTER QUE LE SIGUE A VALOR
                char ahead;

                // MENSAJE DE ERROR
                error = "Line: " + Integer.toString(contador_linea) + ", Near Column: "
                        + Integer.toString(contador_columna);

                // VERIFICAR SI YA LLEGO AL FINAL DE LA LINEA PORQUE SI NO AHEAD NO ES NADA
                if (i != (line.length() - 1)) {
                    // SI NO HA LLEGADO AL FINAL, ENTONCES DEFINIR AHEAD COMO EL SIGUIENTE CARACTER
                    ahead = line.charAt(i + 1);
                } else {
                    // DE LO CONTRARIO, AHEAD ES $ Y SE REINICIA EL CONTADOR DE COLUMNA
                    ahead = '$';
                    contador_columna = 0;
                }

                // VERIFICACIONES PRINCIPALES DE COMENTARIOS Y WHITESPACE

                // COMENTARIOS
                if (valor == '/' && ahead == '/') {
                    contador_columna = 0;
                    break;
                }

                // WHITESPACE, REVISA POR ESPACIOS O TABS
                if (valor != ' ' && valor != '\t') {
                    // SI NO HAY ESPACIO, AGREGA EL CARACTER A SEMITOKEN
                    semi_token += valor;

                    // SI EL SIGUIENTE CARACTER ES UN ESPACIO O END OF LINE, ENTONCES ACABAMOS DE
                    // ENCONTRAR UN LEXEME
                    if ((ahead == ' ') || ahead == '$') {
                        // AGREGAR LEXEME Y EL ERROR FUNCIONA COMO SU UBICACIÓN, REINICIAR SEMITOKEN
                        lexemes.add(semi_token);
                        errors.add(error);
                        semi_token = "";
                    }
                }
                contador_columna += 1;
            }
            contador_linea += 1;

        }

        // A ESTE PUNTO YA TENEMOS LOS LEXEMES LISTOS PARA TOKENIZARLOS
        // AHORA HAY QUE ANALIZAR SI SON TOKENS O SI SE PUEDEN DIVIDIR EN DISTINTOS
        // TOKENS
        // ESTO ES PORQUE PUEDE HABER UN IF(ALGO) TODO COMO UN LEXEME POR EJEMPLO

        // FOR LOOP PARA IR ANALIZANDO CADA UNO DE LOS LEXEMES
        for (int i = 0; i < lexemes.size(); i++) {

            // INSTANCIAR UN OBJETO TOKEN COMO EL TEMPORAL QUE SE ANALIZARA
            token token_temporal = new token("", "");

            // LLAMAR A LA FUNCIÓN INTENTO PARA VER SI PASA ALGUNO DE LOS AUTÓMATAS, IR A
            // REVISAR FUNCIÓN PARA ENTENDER
            intento(lexemes.get(i), token_temporal, automatas, alfabetos);

            // VARIABLE QUE VERIFICA SI SE VA AGREGAR O NO UN PEDAZO DE LEXEME, MAS ABAJO SE
            // ENTIENDE POR QUE
            boolean addorwhat = true;

            // VERIFICACIÓN DE ARREGLO DENTRO DE LOS LEXEMES
            String posible_arreglo = "";
            String arreglo_aux = "";

            // SI EL PRIMER CARACTER EN EL LEXEME ES "
            if (lexemes.get(i).charAt(0) == '"') {

                // EMPEZAR A RECORRER LOS LEXEMES DESDE EL QUE TIENE " PARA VER SI OTRO QUE LE
                // SIGA TAMBIÉN TIENE "
                for (int j = i; j < lexemes.size(); j++) {
                    // LEXEME TEMPORAL A ANALIZAR
                    String inside_token = lexemes.get(j);

                    // AGREGAR EL LEXEME TEMPORAL A LA VARIABLE QUE PUEDE SER UN ARREGLO O NO
                    posible_arreglo += inside_token;
                    // ESTE ARREGLO AUXILIAR NOS VA A SERVIR PARA DEFINIR EL VALOR DE VALUE EN EL
                    // OBJETO TOKEN
                    arreglo_aux += inside_token + " ";

                    // SI EL FINAL DE UN LEXEME TEMPORAL ES " ENTONCES TERMINÓ UN STRING
                    if (inside_token.charAt(inside_token.length() - 1) == '"') {

                        // IR A REVISAR SI PASA EL AUTÓMATA DE STRING
                        token token_temporal2 = new token("", "");
                        intento(posible_arreglo, token_temporal2, automatas, alfabetos);

                        // BASADO EN LA VERIFICACIÓN SE DEFINE COMO UN STRING LITERAL O COMO UN ERROR
                        if (token_temporal2.type != "ERROR") {
                            token_temporal.type = token_temporal2.type;
                            token_temporal.value = arreglo_aux;
                            i = j;
                        }
                        break;
                    }
                }
            }

            // A ESTE PUNTO YA VERIFICAMOS QUE SEA UN STRING, SI NO LO ES, ENTONCES HAY QUE
            // HACER TODO LO DEMAS
            if (token_temporal.type == "ERROR") {

                // ANTES ERA UN POSIBLE STRING, AHORA ES UN POSIBLE ALGO PORQUE PUEDE SER UN
                // CHAR UN NÚMERO O UN IDENTIFIER
                String posible_algo = "";
                // ESTE GUARDARA LO MISMO QUE POSIBLE ALGO PERO CON UN CARACTER ADICIONAL DE
                // LOOKAHEAD
                String posible_algo_ahead = "";

                // EMPEZAR A RECORRER EL LEXEME EN CUESTIÓN
                for (int k = 0; k < token_temporal.value.length(); k++) {
                    posible_algo += token_temporal.value.charAt(k);
                    // LANZARLO EN INTENTO PARA VER SI ES ERROR O NO
                    token token_temporal3 = new token("", "");
                    intento(posible_algo, token_temporal3, automatas, alfabetos);

                    // SI NO ES ERROR ENTONCES PUEDE SER QUE SEA UN TOKEN O PARTE DE OTRO TIPO DE
                    // TOKEN
                    // POR EJEMPLO I ES UN IDENTIFIER PERO PUEDE SER PARTE DE LA PALABRA RESERVADA
                    // IF
                    if (token_temporal3.type != "ERROR") {

                        // VER SI LLEGÓ AL FINAL PARA DEFINIR EL VALOR DE LA VARIABLE DE LOOKAHEAD
                        if (k != token_temporal.value.length() - 1) {
                            posible_algo_ahead = posible_algo + token_temporal.value.charAt(k + 1);
                        } else {
                            posible_algo_ahead = posible_algo;
                        }

                        // VA A VERIFICAR SI EL LEXEME DE AHEAD ES ERROR O NO POR LO QUE MENCIONÉ ARRIBA
                        token token_temporal4 = new token("", "");
                        intento(posible_algo_ahead, token_temporal4, automatas, alfabetos);

                        // SI ES ERROR, ENTONCES NOS QUEDAMOS CON EL TOKEN NORMAL
                        if (token_temporal4.type == "ERROR") {
                            // ESTE TOKEN NUNCA ES ERROR, DE AQUI SALEN TODOS LOS BUENOS TOKENS
                            token_stream.add(token_temporal3);
                            System.out.println("<" + token_temporal3.type + ", " + token_temporal3.value + ">");
                            posible_algo = "";
                        }

                        // COMO YA SABEMOS QUE HUBO UN BUEN TOKEN, ENTONCES NO HAY QUE SACAR EL MENSAJE
                        // DE ERROR
                        addorwhat = false;
                    } else {
                        // DE LO CONTRARIO SI HAY ERROR
                        addorwhat = true;
                    }
                }
            }
            // COMO SI HAY ERROR, SE SACA EL SIGUIENTE MENSAJE CON SU CORRESPONDIENTE
            // MENSAJE DE ERROR
            if (addorwhat) {
                token_stream.add(token_temporal);
                System.out.println("<" + token_temporal.type + ", " + token_temporal.value + ">");
                if (token_temporal.type == "ERROR") {
                    System.out.println(errors.get(i));
                }
            }
        }
    }

    // FUNCIÓN PRINCIPAL DE VERIFICACIÓN DE AUTÓMATAS, RETORNA EL TIPO DE AUTÓMATA
    // QUE PASÓ O ERROR
    // ARGUMENTOS SON EL TOKEN A PROBAR, EL OBJETO DE TOKEN, LOS AUTÓMATAS Y LOS
    // ALFABETOS
    public static String intento(String token, token siguiente, int[][][] automatas, char[][] alfabetos) {
        // TOKEN CONTIENE LOS CARACTERES QUE VAMOS A ANALIZAR EN BASE A LOS AUTÓMATAS
        String token_temporal = token;

        // SEA ERROR O NO DE UNA VEZ ASIGNAMOS EL VALOR AL OBJETO
        siguiente.value = token_temporal;

        // EMPIEZA A VERIFICAR EN CADA UNA DE LAS FUNCIONES, LS DE KEYWORD, OPERATOR Y
        // DELIMITER SON AD HOC
        // ESTO QUIERE DECIR QUE SOLO SON HECHAS PARA ESA EXPRESION REGULAR, LAS DEMAS
        // SI SON CON AUTOMATAS
        if (verificar_keyword(token_temporal)) {
            siguiente.type = "Keyword";
        } else if (verificar_operator(token_temporal)) {
            siguiente.type = "Operator";
        } else if (verificar_delimiter(token_temporal)) {
            siguiente.type = "Delimiter";
        } else {
            // VERIFICACIÓN CON AUTÓMATAS! IR A REVISAR FUNCIÓN VERIFICAR PARA ENTENDER

            // VARIABLE DE ARREGLO QUE CONTIENE CADA CARACTER EN UNA POSICIÓN DIFERENTE, LO
            // SEPARA
            char separado[] = token_temporal.toCharArray();

            // SI COMIENZA CON ESE CARACTER ' HAY QUE IR A REVISAR QUE SEA UN CARACTER, POR
            // ESO UTILIZA ESA POSICIÓN DE AUTÓMATAS
            if (separado[0] == '\'') {
                siguiente.type = verificar(separado, alfabetos[2], automatas[3], 1);

                // AHORA, SI EMPIEZA CON " ENTONCES VA A REVISAR QUE SEA UN STRING
            } else if (separado[0] == '\"') {
                siguiente.type = verificar(separado, alfabetos[2], automatas[2], 2);

                // VERIFICACIÓN DE NUMEROS
            } else if (separado[0] == '0' || separado[0] == '1' || separado[0] == '2' || separado[0] == '3'
                    || separado[0] == '4' || separado[0] == '5' || separado[0] == '6' || separado[0] == '7'
                    || separado[0] == '8' || separado[0] == '9') {
                siguiente.type = verificar(separado, alfabetos[0], automatas[0], 3);

                // LO ÚLTIMO QUE QUEDA ES QUE SEA UN IDENTIFIER
            } else {
                siguiente.type = verificar(separado, alfabetos[1], automatas[1], 4);
            }
        }
        return siguiente.type;
    }

    // FUNCION FUNDAMENTAL DE VERIFICACION EN BASE A AUTOMATAS
    // PARAMETROS SON EL ARREGLO DE CARACTERES, EL ALFABETO, EL AUTOMATA Y EL TIPO
    // EL TIPO ES DE 1 A 4 Y SE USA PARA RETORNAR EL TIPO CORRECTO
    public static String verificar(char[] token, char[] alfabeto, int table[][], int tipo) {
        // ESTADO BASE ES 1
        int estado = 1;

        // ENCONTRO ES TRUE SIEMPRE Y CUANDO AL MOMENTO DE RECORRER TOKEN ENCUENTRE EL
        // CARACTER EN EL ALFABETO
        boolean encontro = false;
        // RESULT DEPENDE DE EL ESTADO DE FINALIZACION Y DE ENCONTRO
        boolean result = false;

        // EMPEZAR A RECORRER EL TOKEN POR CARACTERES
        for (int i = 0; i < token.length; i++) {
            // POR CADA TOKEN VA Y RECORRE EL ALFABETO PARA VER SI COINCIDE ALGUN CARACTER
            for (int j = 0; j < alfabeto.length; j++) {

                // SI ENCUENTRA ALGUNO, ENTONCES CAMBIA EL ESTADO DEPENDIENDO DEL AUTOMATA Y
                // ENCONTRO ES TRUE
                if (token[i] == alfabeto[j]) {
                    estado = table[estado - 1][j];
                    encontro = true;
                    break;
                }
            }

            // SI EN ALGÚN PUNTO NO ENCUENTRA O TERMINA CON EL ESTADO IGUAL A 0, FINALIZA EL
            // ANALISIS
            // SI EL ESTADO ES 0 SIGNIFICA QUE NO HAY ORILLAS DISPONIBLES PARA TRASLADARSE
            // EN EL AUTÓMATA
            if (!encontro || estado == 0) {
                result = false;
                break;
            } else {
                // DE LO CONTRARIO, RESULT ES TRUE
                encontro = false;
                result = true;
            }
        }

        // SI RESULT ES TRUE, PUEDE SER QUE NO SEA ERROR, TODO DEPENDE DEL ESTADO DE
        // FINALIZACIÓN
        if (result) {
            // SI EL TIPO ES DE CARACTER, EL ESTADO CORRECTO ES 5
            if (tipo == 1) {
                if (estado == 5) {
                    return "Char_Literal";
                } else {
                    return "ERROR";
                }
                // SI EL TIPO ES DE STRING, EL ESTADO CORRECTO ES EL 4
            } else if (tipo == 2) {
                if (estado == 4) {
                    return "String_Literal";
                } else {
                    return "ERROR";
                }
                // SI EL TIPO ES DE NÚMERO, EL ESTADO CORRECTO ES 3 O 2 PARA HEXA Y DECIMAL
            } else if (tipo == 3) {
                if (estado == 3 || estado == 2) {
                    return "DECIMAL";
                } else if (estado == 5) {
                    return "HEXADECIMAL";
                } else {
                    return "ERROR";
                }
                // SI EL TIPO ES 4 EL ESTADO CORRECTO ES 2
            } else if (tipo == 4) {
                if (estado == 2) {
                    return "Identifier";
                } else {
                    return "ERROR";
                }
            }
        } else {
            // SI RESULT ES FALSE ENTONCES ES ERROR
            return "ERROR";
        }
        // SI NO ENTRA EN NINGUNA REVISIÓN ES ERROR
        return "ERROR";
    }

    // ESTAS 3 FUNCIONES SON LO MISMO, HAY UN ARREGLO CON LO QUE ACEPTA Y REVISA SI
    // ESTA DENTRO DEL ARREGLO
    public static boolean verificar_keyword(String token) {
        String posibles[] = { "boolean", "break", "callout", "class", "continue", "else", "false", "for", "if", "int",
                "return", "true", "void" };

        List<String> list = Arrays.asList(posibles);
        if (list.contains(token)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean verificar_operator(String token) {
        String posibles[] = { "=", "!", "*", "/", "%", "+", "-", "<", "<=", ">=", ">", "==", "!=", "&&", "||" };

        List<String> list = Arrays.asList(posibles);
        if (list.contains(token)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean verificar_delimiter(String token) {
        String posibles[] = { "(", ")", "[", "]", "{", "}", ";", "," };

        List<String> list = Arrays.asList(posibles);
        if (list.contains(token)) {
            return true;
        } else {
            return false;
        }
    }
}
