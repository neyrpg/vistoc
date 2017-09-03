package br.com.giltech.vistoc.Exception;

/**
 * Created by Gilciney Marques on 21/08/2017.
 */

public class VistoCaixaException extends Exception{

    private String classe;
    private Integer code;


    public VistoCaixaException(String classe, String mensagem){
        super(mensagem);
        this.classe = classe;
    }

    public String getClasse(){
        return this.classe;
    }

}
