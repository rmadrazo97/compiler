public static boolean expr(ArrayList<token> tokens){
    if (expr(tokens)){

        if (bin_op(tokens) && expr(tokens)){
            return true;
        }
    } else if (method_call(tokens)){
        return true;
    } else if (literal(tokens)){
        return true;
    }else if(location(tokens)){
        return true;
    } else if (tokens.get(contador).value.equals("-")){
        System.out.println("Parseado: -");
        contador += 1;
        return(expr(tokens));
    } else if (tokens.get(contador).value.equals("!")){
        System.out.println("Parseado: !");
        contador += 1;
        return(expr(tokens));
    } else if (tokens.get(contador).value.equals("(")){
        System.out.println("Parseado: (");
        contador += 1;
        expr(tokens);
        if (tokens.get(contador).value.equals(")")){
            System.out.println("Parseado: )");
            contador +=1;
            return true;
        }else{
            return false;
        }
    }else{
        return false;
    }

}  