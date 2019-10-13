public static void method_decl(ArrayList<token> tokens){

    if(tokens.get(contador).value.equals("{")){
        System.out.println("Parseado: "+tokens.get(contador).value+" = {");
        contador += 1; 
        
        if (type(tokens)){
        
            System.out.println("tokens passed");
            
            if(tokens.get(contador).value.equals("}")){
            
                System.out.println("Parseado: "+tokens.get(contador).value+" = }");
                contador += 1; 
                if (id(tokens)){

                    if(tokens.get(contador).value.equals("(")){
            
                        boolean continuar = true;
                        while(continuar){
                            continuar = method_decl_alt(tokens);
                        }
                        if(tokens.get(contador).value.equals(")")){
                            block(tokens);
                        } else {
                            System.out.println("Error: "+tokens.get(contador).value+" != ) ");
                            return false;
                        }

                    } else {
                        System.out.println("Error: "+tokens.get(contador).value+" != ( ");
                        return false;
                    }

                }
            } else {
                System.out.println("Error: "+tokens.get(contador).value+" != } ");
                return false;
            }
        
        } else if(tokens.get(contador).value.equals("void")){
            
            System.out.println("Parseado: "+tokens.get(contador).value+" = void");
            contador += 1; 

            if(tokens.get(contador).value.equals("}")){
            
                System.out.println("Parseado: "+tokens.get(contador).value+" = }");
                contador += 1; 

                if (id(tokens)){

                    if(tokens.get(contador).value.equals("(")){
            
                        boolean continuar = true;
                        while(continuar){
                            continuar = method_decl_alt(tokens);
                        }
                        if(tokens.get(contador).value.equals(")")){
                            block(tokens);
                        } else {
                            System.out.println("Error: "+tokens.get(contador).value+" != ) ");
                            return false;
                        }

                    } else {
                        System.out.println("Error: "+tokens.get(contador).value+" != ( ");
                        return false;
                    }

                }
            } else {
                System.out.println("Error: "+tokens.get(contador).value+" != } ");
                return false;
            }
        
        } else {
            System.out.println("Error: "+tokens.get(contador).value+" != <type> | void ");
            return false;
        }
        

    } else {
        System.out.println("Error: "+tokens.get(contador).value+" != { ");
        return false;
    }

}


public static void method_decl_alt(ArrayList<token> tokens){
   //while -> { <type> <id> }+ , 

}