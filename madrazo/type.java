public static boolean type(ArrayList<token> tokens){
        
    String value = tokens.get(contador).value;
    
    if(value.equals("int") || value.equals("boolean") || value.equals("void")){
        System.out.println("Parseado: " + value);
        contador += 1;
        return true;
    }else{
        return false;
    }
}