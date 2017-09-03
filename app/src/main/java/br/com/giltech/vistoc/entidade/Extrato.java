package br.com.giltech.vistoc.entidade;

import java.util.List;

/**
 * Created by Gilciney Marques on 21/08/2017.
 */

public class Extrato {


    private String saldo;
    private List<Operacao> operacoes;

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public List<Operacao> getOperacoes() {
        return operacoes;
    }

    public void setOperacoes(List<Operacao> operacoes) {
        this.operacoes = operacoes;
    }
}
