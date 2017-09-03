package br.com.giltech.vistoc.entidade;

import java.util.Date;

/**
 * Created by Gilciney Marques on 21/08/2017.
 */

public class Operacao {

    private String descricao;
    private String tipo;
    private String valor;
    private Date data;


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
