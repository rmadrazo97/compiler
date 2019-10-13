
public static void program(ArrayList<token> tokens){
        
    //class
    if(tokens.get(contador).value.equals("class")){
        System.out.println("Parseado: "+tokens.get(contador).value+" = class");
        contador += 1;
        
        if(tokens.get(contador).value.equals("Program")){
            System.out.println("Parseado: "+tokens.get(contador).value+" = Program");
        contador += 1;
            
            if(tokens.get(contador).value.equals("{")){
                System.out.println("Parseado: "+tokens.get(contador).value+" = {");
                contador += 1;
                
                //field_decl()
                boolean continuar = true;
                while(continuar){
                    continuar = field_decl(tokens);
                }
                
                //method_decl
                boolean continuar2 = true;
                while(continuar2){
                    continuar2 = method_decl(tokens);
                }
                
                if(tokens.get(contador).value.equals("}")){
                    System.out.println("Parseado: "+tokens.get(contador).value+" = }");
                    return true;
                }else{
                    System.out.println("Error: "+tokens.get(contador).value+" != }");
                    return false;
                }
            }else{
                System.out.println("Error: "+tokens.get(contador).value+" != {");
                return false;
            }
        }else{
            System.out.println("Error: "+tokens.get(contador).value+" != Program");
            return false;
        }
    }else{
        System.out.println("Error: "+tokens.get(contador).value+" != class");
        return false;
    }

}