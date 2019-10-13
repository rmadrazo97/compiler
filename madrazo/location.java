public static boolean location(ArrayList<token> tokens){
    // el primero debe ser <id>
    // el segundo (si existe) debe ser "["
    if(tokens.get(contador+1).value.equals("[")){ // si hay: '['
        id(tokens);
        if(tokens.get(contador).value.equals("[")){
            System.out.println("Parseado: [");
            contador +=1;
            // el tecero debe ser <expr>
            expr(tokens); // este suma el contador

            // el 4to debe ser "]"
            if (tokens.get(contador).value.equals("]")){
                System.out.println("Parseado: ]");
                contador += 1;
                return true;
            } else {
                System.out.println("Error");
                return false;
            }
        } else {
            System.out.println("Error");
            return false;
        }

    } else  {
        return(id(tokens)); // este suma el contador
    }

}