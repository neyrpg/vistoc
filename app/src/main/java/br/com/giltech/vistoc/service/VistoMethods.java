package br.com.giltech.vistoc.service;

import br.com.giltech.vistoc.entidade.Extrato;
import br.com.giltech.vistoc.entidade.Resposta;
import br.com.giltech.vistoc.entidade.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Gilciney Marques on 21/08/2017.
 */

public interface VistoMethods {


    @POST("/api.php")
    Call<Resposta> acessar(@Body Usuario usuario);

    @GET("/api.php")
    Call<Resposta> getExtrato(@Query("token") String token);


}
