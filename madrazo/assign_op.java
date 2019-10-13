public static boolean assign_op(ArrayList<token> tokens){
    String value = tokens.get(contador).value;
    
    if(value.equals("=") || value.equals("+=") || value.equals("-=")){
        System.out.println("Parseado: " + value);
        contador += 1;
        return true;
    }else{
        return false;
    }
}