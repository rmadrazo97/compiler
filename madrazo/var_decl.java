public static boolean var_decl(ArrayList<token> tokens){
    type(tokens);
    
    boolean first = id(tokens);
    boolean continuar = true;
    while(continuar){
        if(tokens.get(contador).value.equals(",")){
            System.out.println("Parseado: "+tokens.get(contador).value+" = ,");
            contador += 1;
            
            continuar = id(tokens);
        }else{
            break;
        }
    }
    if(tokens.get(contador).value.equals(";")){
        System.out.println("Parseado: "+tokens.get(contador).value+" = ;");
        contador += 1;
        
        return true;
    }else{
        return false;
        System.out.println("Error: "+tokens.get(contador).value+" !=  ; ");

    }
}