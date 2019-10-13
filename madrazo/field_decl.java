public static void field_decl(ArrayList<token> tokens){

    if(type(tokens)){

        if(tokens.get(contador).value.equals("{")){
            System.out.println("Parseado: "+tokens.get(contador).value+" = {");
            contador += 1; 

            if(id1(tokens)){
                if(tokens.get(contador).value.equals("}")){
                    System.out.println("Parseado: "+tokens.get(contador).value+" = }");
                    contador += 1;
                    if(tokens.get(contador).value.equals(",")){
                        System.out.println("Parseado: "+tokens.get(contador).value+" = ,");
                        contador += 1;
                        
                    } else if(tokens.get(contador).value.equals(";")){
                        System.out.println("Parseado: "+tokens.get(contador).value+" = ;");
                        contador += 1;
                        
                    } else {
                        System.out.println("Error: "+tokens.get(contador).value+" != ,  | ; ");
                        return false;
                    }
                }
            } else if (id2(tokens)){
                if(tokens.get(contador).value.equals("}")){
                    System.out.println("Parseado: "+tokens.get(contador).value+" = }");
                    contador += 1;
                }
            } else {
                System.out.println("Error: "+tokens.get(contador).value+" != id1  | id2 ");
                return false;
            }

        }

    }

}