public static boolean block(ArrayList<token> tokens){
    if(tokens.get(contador).value.equals("{")){
        System.out.println("Parseado: "+tokens.get(contador).value+" = {");
        contador += 1;
        
        boolean continuar = true;
        while(continuar){
            continuar = var_decl(tokens);
        }
        
        boolean continuar2 = true;
        while(continuar2){
            continuar2 = statement(tokens);
        }
        
        if(tokens.get(contador).value.equals("}")){
            System.out.println("Parseado: "+tokens.get(contador).value+" = }");
            contador += 1;
            
            return true;
        }else{
            return false;
        }
    }else{
        return false;
    }
}