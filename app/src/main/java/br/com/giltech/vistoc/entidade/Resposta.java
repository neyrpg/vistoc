package br.com.giltech.vistoc.entidade;

/**
 * Created by Gilciney Marques on 21/08/2017.
 */

public class Resposta {

    private Boolean sucesso;
    private String erro;
    private Object data;


    public Boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public DadoCliente getAsData(){
        if(data instanceof DadoCliente){
            return (DadoCliente)data;
        }
        return null;
    }
    public Extrato getAsExtrato(){
        if(data instanceof Extrato){
            return (Extrato) data;
        }
        return null;
    }

}
