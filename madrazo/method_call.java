
public static boolean method_call(ArrayList<token> tokens){
    if(tokens.get(contador).value.equals("callout")){
        System.out.println("Parseado: callout");
        contador += 1;
        
        if(tokens.get(contador).value.equals("(")){
            System.out.println("Parseado: (");
            contador += 1; 
            
            if(string_literal(tokens)){
                if(tokens.get(contador).value.equals(")")){
                    System.out.println("Parseado: )");
                    contador += 1; 
                    
                    return true;
                }else{
                    // debe ir una coma antes? 
                    callout_arg(tokens);
                    boolean continuar = true;
                    while(continuar){
                        if(tokens.get(contador).value.equals(",")){
                            System.out.println("Parseado: ,");
                            contador += 1;
                            continuar = callout_arg(tokens);
                        }else{break;}
                    }
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        contador += 1; 
                        return true;
                    }else {return false;}
                }
            }else {return false;}
        }else {return false;}
    }else{
        if(method_name(tokens)){
            if(tokens.get(contador).value.equals("(")){
                System.out.println("Parseado: (");
                contador += 1;
            
                if(tokens.get(contador).value.equals(")")){
                    System.out.println("Parseado: )");
                    contador += 1;
                    return true;
                }else{
                    expr(tokens);
                    boolean continuar = true;
                    while(continuar){
                        if(tokens.get(contador).value.equals(",")){
                            System.out.println("Parseado: ,");
                            contador += 1;
                            continuar = expr(tokens);
                        }else{
                            break;
                        }
                    }
                
                    if(tokens.get(contador).value.equals(")")){
                        System.out.println("Parseado: )");
                        contador += 1; 
                        return true;
                    }else {return false;}
                }
            }else {return false;}
        }else {return false;}
    }
}