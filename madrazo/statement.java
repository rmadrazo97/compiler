public static boolean statement(ArrayList<token> tokens){
    int inicio = contador; 
    
    String value = tokens.get(contador).value;
    if(value.equals("if")){
        System.out.println("Parseado: if");
        contador += 1;
        
        if(tokens.get(contador).value.equals("(")){
            System.out.println("Parseado: (");
            contador += 1; 
            
            if(expr(tokens)){
                if(tokens.get(contador).value.equals(")")){
                    System.out.println("Parseado: )");
                    contador += 1;
                    
                    if(block(tokens)){
                        if(tokens.get(contador).value.equals("else")){
                            System.out.println("Parseado: else");
                            contador += 1; 
                            
                            return block(tokens);
                        }else{
                            return true;
                        }
                    }else{
                        return false;
                    }
                }else{
                     return false; 
                }

            }else {
                return false; 
            }
        }else {
            return false; 
        }
    } else if(value.equals("for")){

        System.out.println("Parseado: for");
        contador += 1; 
        
        if(id(tokens)){
            if(tokens.get(contador).value.equals("=")){
                System.out.println("Parseado: =");
                contador += 1; 
                
                if(expr(tokens)){
                    if(tokens.get(contador).value.equals(",")){
                        System.out.println("Parseado: ,");
                        contador += 1;
                        
                        //return expr(tokens) && block(tokens);
                        expr(tokens);
                        block(tokens);

                    }else {return false;}
                }else {return false;}
            }else {return false;}
        }else {return false;}
        
    }else if(value.equals("return")){
        System.out.println("Parseado: return");
        contador += 1;
        
        if(expr(tokens)){
            if(tokens.get(contador).value.equals(";")){
                System.out.println("Parseado: ;");
                contador += 1; 
                return true;
            }else {return false;}
        }else{
            contador = inicio; 
            if(tokens.get(contador).value.equals(";")){
                System.out.println("Parseado: ;");
                contador += 1; 
                return true; 
            }else {return false;}
        }
    }else if(value.equals("break")){
        System.out.println("Parseado: break");
        contador += 1; 
        
        if(tokens.get(contador).value.equals(";")){
            System.out.println("Parseado: ;");
            contador += 1;
            return true;
        }else {return false;}
    }else if(value.equals("continue")){
        System.out.println("Parseado: continue");
        contador += 1;
        
        if(tokens.get(contador).value.equals(";")){
            System.out.println("Parseado: ;");
            contador += 1;
            return true;
        }else {return false;}
    }
    
    if(block(tokens)){
        return true;
    }else{
        contador = inicio;
        if(method_call(tokens)){
            if(tokens.get(contador).value.equals(";")){
                System.out.println("Parseado: ;");
                contador += 1; 
                
                return true;
            }else {return false;}
        }else{
            contador = inicio;
            if(location(tokens)){
                if(assign_op(tokens)){
                    if(expr(tokens)){
                        if(tokens.get(contador).value.equals(";")){
                            System.out.println("Parseado: ;");
                            contador += 1;
                            return true;
                        }else {return false;}
                    }else {return false;}
                }else {return false;}
            }else {return false;}
        }
    }
    
}