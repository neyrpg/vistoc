package br.com.giltech.vistoc.entidade;

/**
 * Created by Gilciney Marques on 21/08/2017.
 */

public class Usuario {


    private String usuario;
    private String senha;

    public Usuario(String usuario, String senha){
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
